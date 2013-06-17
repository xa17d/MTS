package at.mts.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import at.mts.entity.cda.CdaBoolean;
import at.mts.entity.cda.CdaDocument;

public class Patient {
	
	private UUID id;
	private TriageCategory category;
	private ArrayList<SalvageInfo> salvageinfo = new ArrayList<SalvageInfo>();
	
	public Patient(CdaDocument document) {
		
		setId(document.getPatientId());
		setNameGiven(document.getPatientNameGiven());
		setNameFamily(document.getPatientNameFamily());
		setBirthTime(document.getPatientBirthTime());
		setGender(document.getPatientGender());        
		
		setWalkable(CdaBoolean.getValueOf(document.getBody().get("gehfaehigkeit")));
		
		setRespiration(Condition.getValueOf(document.getBody().get("respiration")));
		setPerfusion(Condition.getValueOf(document.getBody().get("perfusion")));
		setMentalStatus(Condition.getValueOf(document.getBody().get("mentalerstatus")));
		setCategory(TriageCategory.getValueOf(document.getBody().get("triagekategory")));
		setTreatment(Treatment.getValueOf(document.getBody().get("behandlung")));
		
		setGps(document.getBody().get("gps"));
		setPhaseOfLife(PhaseOfLife.getValueOf(document.getBody().get("lebensphase")));
		setSalvageInfo(document.getBody().get("bergeinformation"));
		setPlacePosition(document.getBody().get("hilfplatzposition"));
		setUrgency(Integer.parseInt(document.getBody().get("dringlichkeit")));
		setDiagnosis(document.getBody().get("diagnose"));
		
		String blutdruck= document.getBody().get("blutdruck");
		if (blutdruck != null) {
			String[] sysdia =blutdruck.split(":" , 2);
			setBloodPressureSystolic(Integer.parseInt(sysdia[0]));
			setBloodPressureDiastolic(Integer.parseInt(sysdia[1]));
		}
		
		String puls = document.getBody().get("puls");
		if (puls != null) {
			setPulse(Integer.parseInt(puls));
		}
		
		setCourseOfTreatment(document.getBody().get("behandlungsverlauf"));
		
		setReadyForTransport(CdaBoolean.getValueOf(document.getBody().get("transportbereitschaft")));
		
		setHospital(document.getBody().get("zielkrankenhaus"));
		setHealthInsurance((document.getBody().get("krankenkasse")));
		
	
		setVersion(document.getIdV().getVersion());
		setTimestamp(document.getDocumentDate());

		Bodyparts bodyParts = new Bodyparts();
		for (String key : document.getBody().keySet()) {
			if (Bodyparts.isBodypart(key)) {
				bodyParts.set(key, document.getBody().get(key));
			}
		}
		setBodyparts(bodyParts);
	}
	
	public Patient(UUID id) {
		
		setId(id);
	}
	
	public Patient() {}
	
	/**
	 * Setter
	 */
	public void setId(UUID value) { this.id = value; }
	public void setCategory(TriageCategory category) { this.category = category; }
	
	public void addSalvageInfo(SalvageInfo info) { this.salvageinfo.add(info); }
	
	public void setSalvageInfo(String salvageInfoString) {
		getSalvageInfo().clear();
		
		if (salvageInfoString != null) {
			String[] sList = salvageInfoString.split(";");
			for (String s : sList) {
				String st = s.trim();
				if (!st.isEmpty()) {
					addSalvageInfo(SalvageInfo.valueOf(st));
				}
			}
		}
	}
	
	/**
	 * Getter
	 */
	public UUID getId() { return id; }
	public ArrayList<SalvageInfo> getSalvageInfo() { return salvageinfo; }
	public TriageCategory getCategory() { return category; }
	
	public String getSalvageInfoString() {
		
		String s = "";
		
		Iterator<SalvageInfo> it = salvageinfo.iterator();
		
		while(it.hasNext())
			s = s + it.next() + ";\n";
		
		return s;
	}
	
	/**
	 * Eigenschaften
	 */
	
	private int version;
	public int getVersion() { return version; }
	public void setVersion(int value) { this.version = value; }
	
	public Date timesamp;
	public Date getTimestamp() { return timesamp; }
	public void setTimestamp(Date value) { this.timesamp = value; }
	
	private String nameGiven;
	public String getNameGiven() { return nameGiven; }
	public void setNameGiven(String value) { this.nameGiven = value; }

	private String nameFamily;
	public String getNameFamily() { return nameFamily; }
	public void setNameFamily(String value) { this.nameFamily = value; }

	private Date birthTime;
	public Date getBirthTime() { return birthTime; }
	public void setBirthTime(Date value) { this.birthTime = value; }

	private Gender gender;
	public Gender getGender() { return gender; }
	public void setGender(Gender value) { this.gender = value; }

	private Boolean walkable;
	public Boolean getWalkable() { return walkable; }
	public void setWalkable(Boolean value) { this.walkable = value; }

	private Condition respiration;
	public Condition getRespiration() { return respiration; }
	public void setRespiration(Condition value) { this.respiration = value; }

	private Condition perfusion;
	public Condition getPerfusion() { return perfusion; }
	public void setPerfusion(Condition value) { this.perfusion = value; }

	private Condition mentalStatus;
	public Condition getMentalStatus() { return mentalStatus; }
	public void setMentalStatus(Condition value) { this.mentalStatus = value; }
	
	private PhaseOfLife phaseOfLife;
	public PhaseOfLife getPhaseOfLife() { return phaseOfLife; }
	public void setPhaseOfLife(PhaseOfLife value) { this.phaseOfLife = value; }
	
	private String gps;
	public String getGps() { return gps; }
	public void setGps(String value) { this.gps = value; }

	private String placePosition;
	public String getPlacePosition() { return placePosition; }
	public void setPlacePosition(String value) { this.placePosition = value; }

	private Integer urgency;
	public Integer getUrgency() { return urgency; }
	public void setUrgency(Integer value) { this.urgency = value; }

	private String diagnosis;
	public String getDiagnosis() { return diagnosis; }
	public void setDiagnosis(String value) { this.diagnosis = value; }

	private Integer bloodPressureSystolic;
	public Integer getBloodPressureSystolic() { return bloodPressureSystolic; }
	public void setBloodPressureSystolic(Integer value) { this.bloodPressureSystolic = value; }

	private Integer bloodPressureDiastolic;
	public Integer getBloodPressureDiastolic() { return bloodPressureDiastolic; }
	public void setBloodPressureDiastolic(Integer value) { this.bloodPressureDiastolic = value; }

	private Integer pulse;
	public Integer getPulse() { return pulse; }
	public void setPulse(Integer value) { this.pulse = value; }
	
	private String courseOfTreatment;
	public String getCourseOfTreatment() { return courseOfTreatment; }
	public void setCourseOfTreatment(String value) { this.courseOfTreatment = value; }

	private Boolean readyForTransport;
	public Boolean getReadyForTransport() { return readyForTransport; }
	public void setReadyForTransport(Boolean value) { this.readyForTransport = value; }

	private String hospital;
	public String getHospital() { return hospital; }
	public void setHospital(String value) { this.hospital = value; }

	private String healthInsurance;
	public String getHealthInsurance() { return healthInsurance; }
	public void setHealthInsurance(String value) { this.healthInsurance = value; }
	
	private Treatment treatment;
	public Treatment getTreatment() { return treatment; }
	public void setTreatment(Treatment value) { this.treatment = value; }
	
	private Bodyparts bodyparts = new Bodyparts();
	public Bodyparts getBodyparts() { return bodyparts; }
	public void setBodyparts(Bodyparts value) { this.bodyparts = value; }
	
	@Override
	public String toString() {
		return 
				"name: "+nameFamily+" "+nameGiven+"; "+
				"birthtime: "+birthTime+"; "+
				"gender: "+gender+"; "+
				"category: "+category+"; "+
				"walkable: "+walkable+"; "+
				"respiration: "+respiration+"; "+
				"perfusion: "+perfusion+"; "+
				"mentalStatus: "+mentalStatus+"; "+
				"phaseOfLife: "+phaseOfLife+"; "+
				"salvageInfo: "+getSalvageInfoString()+"; "+
				"placePosition: "+placePosition+"; "+
				"urgency: "+urgency+"; "+
				"pressure: "+bloodPressureSystolic+":"+bloodPressureDiastolic+"; "+
				"pulse: "+pulse+"; "+
				"readyForTransport: "+readyForTransport+"; "+
				"hospital: "+hospital+"; "+
				"healthInsurance: "+healthInsurance+"; "+
				"treatment: "+treatment;
				
	}
	
	/**
	 * statisch aktuellen Patienten definieren
	 */
	private static Patient selectedPatient;
	
	public static Patient getSelectedPatient() {
		return selectedPatient;
	}
	
	public static void setSelectedPatient(Patient p) {
		selectedPatient = p;
	}
}
