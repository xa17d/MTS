package moco.android.mtsdevice.triage;

import moco.android.mtsdevice.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import at.mts.entity.TriageCategory;


public class TriageActivity extends Activity implements OnClickListener {
	
	private TriageCategory category;
	
	private Button walkYes;
	private Button walkNo;
	private Button respirationStable;
	private Button respirationCritical;
	private Button perfusionStable;
	private Button perfusionCritical;
	private Button mentalStable;
	private Button mentalCritical;
	
	private Button save;
	private Button restart;
	private Button tagColor;
	
	private Drawable buttonBackground;	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.triage_main);
        
        initButtons();      
       
        walkYes.setOnClickListener(new WalkButtonListener());
        walkNo.setOnClickListener(new WalkButtonListener());
        
        respirationStable.setOnClickListener(new RespirationButtonListener());
        respirationCritical.setOnClickListener(new RespirationButtonListener());
        
        perfusionStable.setOnClickListener(new PerfusionButtonListener());
        perfusionCritical.setOnClickListener(new PerfusionButtonListener());
        
        mentalStable.setOnClickListener(new MentalButtonListener());
        mentalCritical.setOnClickListener(new MentalButtonListener());
        
        save.setOnClickListener(this);
        restart.setOnClickListener(this);
        
        buttonBackground = walkYes.getBackground();
        category = TriageCategory.notSpecified;
        
        tagColor.setBackgroundColor(category.getTriageColor());
    }
    
	@Override
	public void onClick(View v) {

		/**
		 * Speichern
		 */
		if(v == save) {
			if(category == TriageCategory.notSpecified) {
				new AlertDialog.Builder(this) 
	            	.setMessage(R.string.error_category_missing)
	            	.setNeutralButton(R.string.error_ok, null)
	            	.show();
			}
			else {
				//TODO Patient aktualisieren
				
				/**
				 * naechste Seite
				 */
				Intent myIntent = new Intent(this, TriageSalvageinfoActivity.class);
                startActivity(myIntent);
			}
		}
		/**
		 * Eingabe loeschen
		 */
		else if (v == restart) {
			new AlertDialog.Builder(this)
        		.setMessage(R.string.question_restart)
        		.setNegativeButton(R.string.no, null)
        		.setPositiveButton(R.string.yes, new ResetListener())
        		.show();
		}
	}
	
	private class ResetListener implements android.content.DialogInterface.OnClickListener {

		@SuppressLint("NewApi")
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			
			walkYes.setClickable(true);
			walkNo.setClickable(true);
			
			walkYes.setBackground(buttonBackground);
			walkNo.setBackground(buttonBackground);
			respirationStable.setBackground(buttonBackground);
			respirationCritical.setBackground(buttonBackground);
			perfusionStable.setBackground(buttonBackground);
			perfusionCritical.setBackground(buttonBackground);
			mentalStable.setBackground(buttonBackground);
			mentalCritical.setBackground(buttonBackground);
			
			category = TriageCategory.notSpecified;
			
			tagColor.setBackgroundColor(category.getTriageColor());
		}
	}
	
	/**
	 * Gehfaehigkeit
	 * @author Lucas
	 */
	private class WalkButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			/**
			 * Patient kann laufen --> MINOR
			 */
			if(v == walkYes) {
				walkYes.setBackgroundColor(Color.GREEN);
				respirationStable.setBackgroundColor(Color.GREEN);
				perfusionStable.setBackgroundColor(Color.GREEN);
				mentalStable.setBackgroundColor(Color.GREEN);
				category = TriageCategory.minor;
			}
			/**
			 * Patient kann nicht laufen --> Atmung kontrollieren
			 */
			else {
				walkNo.setBackgroundColor(Color.RED);
				respirationStable.setClickable(true);
				respirationCritical.setClickable(true);
			}
			
			walkYes.setClickable(false);
			walkNo.setClickable(false);
			
			tagColor.setBackgroundColor(category.getTriageColor());
		}
	}
	
	/**
	 * Spontane Atmung
	 * @author Lucas
	 */
	private class RespirationButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			/**
			 * KEINE Atmung vorhanden --> DECEASED
			 */
			if(v == respirationCritical) {
				respirationCritical.setBackgroundColor(Color.RED);
				perfusionCritical.setBackgroundColor(Color.RED);
				mentalCritical.setBackgroundColor(Color.RED);
				category = TriageCategory.deceased;
			}
			/**
			 * Atmung vorhanden --> Perfusion kontrollieren
			 */
			else {
				respirationStable.setBackgroundColor(Color.GREEN);
				perfusionStable.setClickable(true);
				perfusionCritical.setClickable(true);
			}
			
			respirationStable.setClickable(false);
			respirationCritical.setClickable(false);
			
			tagColor.setBackgroundColor(category.getTriageColor());
		}
	}
	
	/**
	 * Blutkreislauf
	 * @author Lucas
	 */
	private class PerfusionButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			/**
			 * KEIN stabiler Blutkreislauf vorhanden
			 */
			if(v == perfusionCritical) {
				perfusionCritical.setBackgroundColor(Color.RED);
				mentalCritical.setBackgroundColor(Color.RED);
				category = TriageCategory.immediate;
			}
			/**
			 * stabiler Blutkreislauf vorhanden --> Mentaler Status kontrollieren
			 */
			else {
				perfusionStable.setBackgroundColor(Color.GREEN);
				mentalStable.setClickable(true);
				mentalCritical.setClickable(true);
			}
			
			perfusionStable.setClickable(false);
			perfusionCritical.setClickable(false);
			
			tagColor.setBackgroundColor(category.getTriageColor());
		}
	}
	
	/**
	 * Mentaler Status
	 * @author Lucas
	 */
	private class MentalButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			/**
			 * KEIN ausreichendes Reaktionsvermögen
			 */
			if(v == mentalCritical) {
				mentalCritical.setBackgroundColor(Color.RED);
				category = TriageCategory.immediate;
			}
			/**
			 * ausreichendes Reaktionsvermögen
			 */
			else {
				mentalStable.setBackgroundColor(Color.GREEN);
				category = TriageCategory.delayed;
			}
			
			mentalStable.setClickable(false);
			mentalCritical.setClickable(false);
			
			tagColor.setBackgroundColor(category.getTriageColor());
		}
	}
	
	private void initButtons() {
		
		walkYes = (Button)findViewById(R.id.walk_yes);
        walkNo = (Button)findViewById(R.id.walk_no);
        
        respirationStable = (Button)findViewById(R.id.respiration_stable);
        respirationCritical = (Button)findViewById(R.id.respiration_critical);
        
        perfusionStable = (Button)findViewById(R.id.perfusion_stable);
        perfusionCritical = (Button)findViewById(R.id.perfusion_critical);

        mentalStable = (Button)findViewById(R.id.mental_stable);
        mentalCritical = (Button)findViewById(R.id.mental_critical);
        
        save = (Button)findViewById(R.id.save);
        restart = (Button)findViewById(R.id.restart);
        tagColor = (Button)findViewById(R.id.tag_color);
        
        respirationStable.setClickable(false);
        respirationCritical.setClickable(false);
        perfusionStable.setClickable(false);
        perfusionCritical.setClickable(false);
        mentalStable.setClickable(false);
        mentalCritical.setClickable(false);
        tagColor.setClickable(false);
	}
}
