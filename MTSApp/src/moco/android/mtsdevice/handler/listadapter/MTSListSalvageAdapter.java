package moco.android.mtsdevice.handler.listadapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import moco.android.mtsdevice.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import at.mts.entity.PatientListItem;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;

public class MTSListSalvageAdapter<T extends PatientListItem> extends ArrayAdapter<T> {
	
	private Context context;
    private int textViewResourceId;
    private List<T> items;

	public MTSListSalvageAdapter(Context context, int textViewResourceId, List<T> items) {
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
        	text.setText(items.get(position).toSalvageString());
        	text.setTextColor(items.get(position).getCategory().getTriageColor());
        }

        return view;
    }
	
	private void filterAndSortItems() {
		
		Iterator<T> it = items.iterator();
		T item;
		
		List<T> immediateItems = new ArrayList<T>();
		List<T> delayedItems = new ArrayList<T>();
		List<T> deceasedItems = new ArrayList<T>();
		
		while(it.hasNext()) {
			item = it.next();
			if(item.getTreatment() != Treatment.sighted)
				it.remove();
			
			if(item.getCategory() == TriageCategory.immediate)
				immediateItems.add(item);
			
			if(item.getCategory() == TriageCategory.delayed)
				delayedItems.add(item);
			
			if(item.getCategory() == TriageCategory.deceased)
				deceasedItems.add(item);
		}
		
		items.clear();
		items.addAll(immediateItems);
		items.addAll(delayedItems);
		items.addAll(deceasedItems);
	}
}
