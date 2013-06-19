package moco.android.mtsdevice.handler.listadapter;

import moco.android.mtsdevice.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MTSBodyInjuryAdapter<T> extends ArrayAdapter<T> {

	private Context context;
    private int textViewResourceId;
    private T[] objects;
	
	public MTSBodyInjuryAdapter(Context context, int textViewResourceId, T[] objects) {
		super(context, textViewResourceId, objects);

		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.objects = objects;
	}
	
	@Override
    public View getView(int position, View v, ViewGroup parent) {
        
		View view = v;
        if(view == null){
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(textViewResourceId, null);
        }

        TextView text = (TextView)view.findViewById(R.id.MTSView);

        if(objects[position] != null) {
            text.setTextColor(Color.BLACK);
            text.setBackgroundColor(00000000);
            text.setText(objects[position].toString());
        }

        return view;
    }

}
