package moco.android.mtsdevice.triage;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.ScanTagActivity;
import moco.android.mtsdevice.handler.DeviceButtons;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import at.mts.entity.*;


public class TriageActivity extends Activity {
	
	private TriageCategory category;
	private Patient patient;
	
	private Button walkYes;
	private Button walkNo;
	private Button respirationYes;
	private Button respirationNo;
	private Button respirationStable;
	private Button respirationCritical;
	private Button perfusionStable;
	private Button perfusionCritical;
	private Button mentalStable;
	private Button mentalCritical;
	
	private Button save;
	private Button restart;
	private Button tagColor;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	//TODO nur zum Testen
    	patient = new Patient();
    	Patient.setSelectedPatient(patient);
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.triage_main);
        
        initButtons();

        category = TriageCategory.notSpecified;
        patient.setCategory(category);
    }
    
    @Override
    public void onResume() {
    	
    	super.onResume();
    	
    	category = patient.getCategory();
    	tagColor.setBackgroundColor(category.getTriageColor());
    }
    
    /**
     * Menue-Knoepfe
     * @param v
     */
	public void menuClick(View v) {

		/**
		 * Speichern
		 */
		if(v == save) {
			
			if(category == TriageCategory.notSpecified) {
				new AlertDialog.Builder(this) 
	            	.setMessage(R.string.error_category_missing)
	            	.setNeutralButton(R.string.ok, null)
	            	.show();
			}
			else if(category == TriageCategory.minor) {
				patient.setCategory(category);
				
				Intent intent = new Intent(this, ScanTagActivity.class);
				startActivity(intent);
                finish();
			}
			else {
				patient.setCategory(category);
				
				Intent intent = new Intent(this, TriageSalvageinfoActivity.class);
                startActivity(intent);
                finish();
			}
		}
		/**
		 * Eingabe loeschen
		 */
		else if (v == restart) {
			
			new AlertDialog.Builder(this)
        		.setMessage(R.string.question_restart)
        		.setNegativeButton(R.string.no, null)
        		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						recreate();
					}
				})
        		.show();
		}
		/**
		 * Farbe manuell aendern
		 */
		else if(v == tagColor) {
			
			Intent intent = new Intent(this, TriageColorSelection.class);
            startActivity(intent);
		}
	}
	
	
	/**
	 * gefaehigkeit?
	 * @param v View
	 */
	public void walkClick(View v) {
		
		/**
		 * Patient kann laufen --> MINOR
		 */
		if(v == walkYes) {
			walkYes.setBackgroundColor(Color.GREEN);
			respirationYes.setBackgroundColor(Color.GREEN);
			respirationStable.setBackgroundColor(Color.GREEN);
			perfusionStable.setBackgroundColor(Color.GREEN);
			mentalStable.setBackgroundColor(Color.GREEN);
			category = TriageCategory.minor;
			patient.setCategory(category);
		}
		/**
		 * Patient kann nicht laufen --> Atmung kontrollieren
		 */
		else {
			walkNo.setBackgroundColor(Color.RED);
			respirationYes.setEnabled(true);
			respirationNo.setEnabled(true);
		}
		
		walkYes.setEnabled(false);
		walkNo.setEnabled(false);
		
		tagColor.setBackgroundColor(category.getTriageColor());
	}
	
	/**
	 * Spontane Atmung vorhanden?
	 * @param v View
	 */
	public void respiration1Click(View v) {
		
		/**
		 * KEINE Atmung vorhanden --> DECEASED
		 */
		if(v == respirationNo) {
			respirationNo.setBackgroundColor(Color.RED);
			respirationCritical.setBackgroundColor(Color.RED);
			perfusionCritical.setBackgroundColor(Color.RED);
			mentalCritical.setBackgroundColor(Color.RED);
			category = TriageCategory.deceased;
			patient.setCategory(category);
		}
		/**
		 * Atmung vorhanden --> Atemgeschwindigkeit kontrollieren
		 */
		else {
			respirationYes.setBackgroundColor(Color.GREEN);
			respirationStable.setEnabled(true);
			respirationCritical.setEnabled(true);
		}
		
		respirationYes.setEnabled(false);
		respirationNo.setEnabled(false);
		
		tagColor.setBackgroundColor(category.getTriageColor());
	}
	
	/**
	 * Stabile Atmung vorhanden?
	 * @param v View
	 */
	public void respiration2Click(View v) {
		
		/**
		 * KEINE stabile Atmung vorhanden --> IMMEDIATE
		 */
		if(v == respirationCritical) {
			respirationCritical.setBackgroundColor(Color.RED);
			perfusionCritical.setBackgroundColor(Color.RED);
			mentalCritical.setBackgroundColor(Color.RED);
			category = TriageCategory.immediate;
			patient.setCategory(category);
		}
		/**
		 * Atmung vorhanden --> Perfusion kontrollieren
		 */
		else {
			respirationStable.setBackgroundColor(Color.GREEN);
			perfusionStable.setEnabled(true);
			perfusionCritical.setEnabled(true);
		}
		
		respirationStable.setEnabled(false);
		respirationCritical.setEnabled(false);
		
		tagColor.setBackgroundColor(category.getTriageColor());
	}
	
	/**
	 * Stabiler Blutkreislauf vorhanden?
	 * @param v
	 */
	public void perfusionClick(View v) {
		
		/**
		 * KEIN stabiler Blutkreislauf vorhanden --> IMMEDIATE
		 */
		if(v == perfusionCritical) {
			perfusionCritical.setBackgroundColor(Color.RED);
			mentalCritical.setBackgroundColor(Color.RED);
			category = TriageCategory.immediate;
			patient.setCategory(category);
		}
		/**
		 * stabiler Blutkreislauf vorhanden --> Mentaler Status kontrollieren
		 */
		else {
			perfusionStable.setBackgroundColor(Color.GREEN);
			mentalStable.setEnabled(true);
			mentalCritical.setEnabled(true);
		}
		
		perfusionStable.setEnabled(false);
		perfusionCritical.setEnabled(false);
		
		tagColor.setBackgroundColor(category.getTriageColor());
	}
	
	/**
	 * Ausreichender geistiger Zustand vorhanden?
	 * @param v
	 */
	public void mentalClick(View v) {
		
		/**
		 * KEIN ausreichendes Reaktionsvermögen --> IMMEDIATE
		 */
		if(v == mentalCritical) {
			mentalCritical.setBackgroundColor(Color.RED);
			category = TriageCategory.immediate;
			patient.setCategory(category);
		}
		/**
		 * ausreichendes Reaktionsvermögen --> DELAYED
		 */
		else {
			mentalStable.setBackgroundColor(Color.GREEN);
			category = TriageCategory.delayed;
			patient.setCategory(category);
		}
		
		mentalStable.setEnabled(false);
		mentalCritical.setEnabled(false);
		
		tagColor.setBackgroundColor(category.getTriageColor());
	}
	
	private void initButtons() {
		
		walkYes = (Button)findViewById(R.id.walk_yes);
        walkNo = (Button)findViewById(R.id.walk_no);
        
        respirationYes = (Button)findViewById(R.id.respiration_present);
        respirationNo = (Button)findViewById(R.id.respiration_notpresent);
        
        respirationStable = (Button)findViewById(R.id.respiration_stable);
        respirationCritical = (Button)findViewById(R.id.respiration_critical);
        
        perfusionStable = (Button)findViewById(R.id.perfusion_stable);
        perfusionCritical = (Button)findViewById(R.id.perfusion_critical);

        mentalStable = (Button)findViewById(R.id.mental_stable);
        mentalCritical = (Button)findViewById(R.id.mental_critical);
        
        save = (Button)findViewById(R.id.save);
        restart = (Button)findViewById(R.id.restart);
        tagColor = (Button)findViewById(R.id.tag_color);
        
        respirationYes.setEnabled(false);
        respirationNo.setEnabled(false);
        respirationStable.setEnabled(false);
        respirationCritical.setEnabled(false);
        perfusionStable.setEnabled(false);
        perfusionCritical.setEnabled(false);
        mentalStable.setEnabled(false);
        mentalCritical.setEnabled(false);
	}
	
	public void showWalkText(View view) {
		
		new AlertDialog.Builder(this) 
    		.setMessage(R.string.walk_criteria)
    		.setNeutralButton(R.string.ok, null)
    		.show();
	}
	
	public void showRespiration1Text(View view) {
		
		new AlertDialog.Builder(this) 
    		.setMessage(R.string.respiration1_criteria)
    		.setNeutralButton(R.string.ok, null)
    		.show();
	}
	
	public void showRespiration2Text(View view) {
		
		new AlertDialog.Builder(this) 
    		.setMessage(R.string.respiration2_criteria)
    		.setNeutralButton(R.string.ok, null)
    		.show();
	}
	
	public void showPerfusionText(View view) {
		
		new AlertDialog.Builder(this) 
    		.setMessage(R.string.perfusion_criteria)
    		.setNeutralButton(R.string.ok, null)
    		.show();
	}
	
	public void showMentalText(View view) {
		
		new AlertDialog.Builder(this) 
    		.setMessage(R.string.mental_criteria)
    		.setNeutralButton(R.string.ok, null)
    		.show();
	}

	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.getToModeSelection(this);
	}
}