package moco.android.mtsdevice.handler.listadapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.Area;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import at.mts.entity.Patient;
import at.mts.entity.PatientListItem;
import at.mts.entity.Treatment;

public class MTSListTherapyAdapter<T extends PatientListItem> extends ArrayAdapter<T> {
	
	private Context context;
    private int textViewResourceId;
    private List<T> items;

	public MTSListTherapyAdapter(Context context, int textViewResourceId, List<T> items) {
		super(context, textViewResourceId, items);

		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.items = items;
		
		filterAndSortItems();
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
            text.setText(items.get(position).toTherapyString());
        }

        return view;
    }
	
	private void filterAndSortItems() {
		
		Iterator<T> it = items.iterator();
		T item;
		
		while(it.hasNext()) {
			item = it.next();
			
			/**
			 * wenn gewaehlter Patient noch nicht geborgen (oder bereits abtransportiert) wurde
			 * ODER
			 * Patient an anderem Behandlungsplatz
			 * ==> aus Liste entfernen
			 */
			if(item.getTreatment() != Treatment.salvaged || !Area.getActiveArea().matchesCategory(item.getCategory()))
				items.remove(item);
		}
		
		//TODO nach dringlichkeit sortieren
	}
}