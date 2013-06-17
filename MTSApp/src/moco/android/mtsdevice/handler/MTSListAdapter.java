package moco.android.mtsdevice.handler;

import java.util.List;

import moco.android.mtsdevice.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import at.mts.entity.Patient;

public class MTSListAdapter<T> extends ArrayAdapter<T> {
	
	private Context context;
    private int textViewResourceId;
    private List<T> items;

	public MTSListAdapter(Context context, int textViewResourceId, List<T> items) {
		super(context, textViewResourceId, items);

		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.items = items;
	}
	
	@Override
    public View getView(int position, View v, ViewGroup parent) {
        View view = v;
        if(view == null){
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(textViewResourceId, null);
        }

        TextView text = (TextView)view.findViewById(R.id.MTSView);

        if(items.get(position) != null) {
            text.setTextColor(Color.BLACK);
            text.setBackgroundColor(00000000);
            
            if(Mode.getActiveMode() == Mode.therapy)
            	text.setText(((Patient)items.get(position)).toTherapyOverviewString());
            else if(Mode.getActiveMode() == Mode.salvage) {
            	text.setText(((Patient)items.get(position)).toSalvageOverviewString());
            	text.setTextColor(((Patient)items.get(position)).getCategory().getTriageColor());
            }
            else
                text.setText(items.get(position).toString());
        }

        return view;
    }
}