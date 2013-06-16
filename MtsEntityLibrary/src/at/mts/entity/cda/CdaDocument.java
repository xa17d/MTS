package at.mts.entity.cda;
import at.mts.entity.Bodyparts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.Date;

import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

import at.mts.entity.Bodyparts;
import at.mts.entity.Gender;
import at.mts.entity.Patient;
/**
 * CDA-Dokument.
 * Ermoeglicht das einlesen oder erzeugen eines CDA-Dokuments.
 * Eigenschaften koennen mittels get- und set-Methoden ausgelesen oder geandert werden.
 * @author Daniel Gehrer
 */
public class CdaDocument {
	
	/**
	 * Erzeugt ein neues, leeres CDA-Dokument
	 */
	public CdaDocument() {
		setIdV(new CdaIdV(UUID.randomUUID(), 1));
	}
	
	/**
	 * Erzeugt ein CdaDocument aus einem XML-String
	 * @param document CDA Dokument als XML-String
	 */
	public CdaDocument(String document) {
		load(document);
	}
	
	/**
	 * Erzeugt ein CdaDocument aus einem Patient-Objekt
	 * @param patient Patientendaten
	 */
	public CdaDocument(Patient patient) {
		setIdV(new CdaIdV(UUID.randomUUID(), 1));
		
        setPatientNameGiven(patient.getNameGiven());
        setPatientNameFamily(patient.getNameFamily());
        setPatientId(patient.getId());
        setPatientGender(patient.getGender());
        setPatientBirthTime(patient.getBirthTime());
        
        setParentIdV(getIdV());  //??
        setDocumentDate(patient.getTimestamp());
        
        //#############--BODY--#############*/
        body = new CdaBody();
        //-----VITALZEICHEN
        body.set("gehfaehigkeit", patient.getWalkable().toString());
        body.set("respiration", patient.getRespiration().toString());
        body.set("perfusion", patient.getPerfusion().toString());
        body.set("mentalerstatus", patient.getMentalStatus().toString());
        body.set("triagekategorie", patient.getCategory().toString());
        body.set("behandlung", patient.getTreatMent().toString());
        
        //-----DETAILS
        body.set("gps", patient.getGps());
        body.set("lebensphase", patient.getPhaseOfLife().toString());
        body.set("bergeinformation", patient.getSalvageInfoString());
        body.set("hilfplatzposition", patient.getPlacePosition());
        body.set("dringlichkeit", patient.getUrgency().toString()); 
        body.set("diagnose", patient.getDiagnosis());
        body.set("blutdruck", patient.getBloodPressureSystolic().toString()+":"+patient.getBloodPressureDiastolic().toString());
        body.set("puls", patient.getPulse().toString());
        body.set("behandlungsverlauf", patient.getCourseOfTreatment()); //
        body.set("transportbereitschaft", patient.getReadyForTransport().toString());
        body.set("zielkrankenhaus", patient.getHospital().toString());
        body.set("krankenkasse", patient.getHealthInsurance().toString());
        //-----VERLETZUNGEN
        //set(String part, String information)
        body.set("FRONT_HEAD",patient.getBodyparts().get("FRONT_HEAD"));
        body.set("FRONT_NECK",patient.getBodyparts().get("FRONT_NECK"));   
        body.set("FRONT_CHEST",patient.getBodyparts().get("FRONT_CHEST"));
        body.set("FRONT_ABDOMEN",patient.getBodyparts().get("FRONT_ABDOMEN"));
        body.set("FRONT_R_UPPERARM",patient.getBodyparts().get("FRONT_R_UPPERARM"));
        body.set("FRONT_L_UPPERARM",patient.getBodyparts().get("FRONT_L_UPPERARM"));
        body.set("FRONT_R_FOREARM",patient.getBodyparts().get("FRONT_R_FOREARM"));
        body.set("FRONT_L_FOREARM",patient.getBodyparts().get("FRONT_L_FOREARM"));
        body.set("FRONT_R_HAND",patient.getBodyparts().get("FRONT_R_HAND"));
        body.set("FRONT_L_HAND",patient.getBodyparts().get("FRONT_L_HAND"));
        body.set("FRONT_L_UPPERLEG",patient.getBodyparts().get("FRONT_L_UPPERLEG"));
        body.set("FRONT_R_UPPERLEG",patient.getBodyparts().get("FRONT_R_UPPERELG"));
        body.set("FRONT_L_SHANK",patient.getBodyparts().get("FRONT_L_SHANK"));
        body.set("FRONT_R_SHANK",patient.getBodyparts().get("FRONT_R_SHANK"));
        body.set("FRONT_L_FOOT",patient.getBodyparts().get("FRONT_L_FOOT"));
        body.set("FRONT_R_FOOT",patient.getBodyparts().get("FRONT_R_FOOT"));
        body.set("BACK_HEAD",patient.getBodyparts().get("BACK_HEAD"));
        body.set("BACK_NECK",patient.getBodyparts().get("BACK_NECK"));
        body.set("BACK_UPPERBACK",patient.getBodyparts().get("BACK_UPPERBACK"));
        body.set("BACK_LOWERBACK",patient.getBodyparts().get("BACK_LOWERBACK"));
        body.set("BACK_R_UPPERARM",patient.getBodyparts().get("BACK_R_UPPERARM"));
        body.set("BACK_L_UPPERARM",patient.getBodyparts().get("BACK_L_UPPERARM"));
        body.set("BACK_R_FOREARM",patient.getBodyparts().get("BACK_R_FOREARM"));
        body.set("BACK_L_FOREARM",patient.getBodyparts().get("BACK_L_FOREARM"));
        body.set("BACK_R_HAND",patient.getBodyparts().get("BACK_R_HAND"));
        body.set("BACK_L_HAND",patient.getBodyparts().get("BACK_L_HAND"));
        body.set("BACK_L_UPPERLEG",patient.getBodyparts().get("BACK_L_UPPERLEG"));
        body.set("BACK_R_UPPERLEG",patient.getBodyparts().get("BACK_R_UPPERLEG"));
        body.set("BACK_L_SHANK",patient.getBodyparts().get("BACK_L_SHANK"));
        body.set("BACK_R_SHANK",patient.getBodyparts().get("BACK_R_SHANK"));
        body.set("BACK_L_FOOT",patient.getBodyparts().get("BACK_L_FOOT"));
        body.set("BACK_R_FOOT",patient.getBodyparts().get("BACK_R_FOOT"));
		
	}
	
	/**
	 * Liest ein CdaDocument aus einem XML-String.
	 * Die bestehenden Daten gehen dabei verloren.
	 * @param document CDA Dokument als XML-String
	 */
	public void load(String document) {
		
		try{
			
			body = new CdaBody(); // Body loeschen
			Document doc = new SAXBuilder().build(document);
	        Namespace NS = Namespace.getNamespace("urn:hl7-org:v3");
	        
	        Element root = doc.getRootElement();
	        
	        setAuthorNameGiven(root.getChild("author", NS).getChild("assignedAuthor",NS).getChild("assignedPerson", NS).getChild("name", NS).getChild("given", NS).getText());
	        setAuthorNameFamily(root.getChild("author", NS).getChild("assignedAuthor",NS).getChild("assignedPerson", NS).getChild("name", NS).getChild("family", NS).getText());
	        setAuthorId(UUID.fromString((root.getChild("author", NS).getChild("assignedAuthor",NS).getChild("id", NS).getAttributeValue("extension"))));
	        
	        setPatientNameGiven(root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("patient", NS).getChild("name", NS).getChild("given", NS).getText());
	        setPatientNameFamily(root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("patient", NS).getChild("name", NS).getChild("family", NS).getText());
	        setPatientId(UUID.fromString((root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("id", NS).getAttributeValue("extension"))));
	        setPatientGender(Gender.getValueOf(root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("patient", NS).getChild("administrativeGenderCode", NS).getAttributeValue("code")));

	        String birthtime = root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("patient", NS).getChild("birthTime", NS).getAttributeValue("value");
	        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	        setPatientBirthTime((Date) formatter.parse(birthtime));
	        
	        UUID idFromCda =UUID.fromString(root.getChild("id", NS).getAttributeValue("extension"));
	        int versionNr = Integer.parseInt(root.getChild("versionNumber", NS).getAttributeValue("value"));	
	        setIdV(new CdaIdV(idFromCda,versionNr));
	        
	        UUID idFromCdaParent =UUID.fromString(root.getChild("relatedDocument", NS).getChild("parentDocument", NS).getChild("id", NS).getAttributeValue("extension"));
	        int versionNrParent = Integer.parseInt(root.getChild("relatedDocument", NS).getChild("parentDocument", NS).getChild("versionNumber", NS).getAttributeValue("value"));	
	        setParentIdV(new CdaIdV(idFromCdaParent, versionNrParent));
	        
	        String effectiveTime = root.getChild("effectiveTime", NS).getAttributeValue("value"); 
	        setDocumentDate((Date) formatter.parse(effectiveTime));
	        
	        
	        
	        //#############--BODY--#############*/
	        //-----VITALZEICHEN

	        String textVital = root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChildText("text", NS);
	        String[] ttextVital =textVital.split("\\r?\\n");
	        	
	        splitInfo(ttextVital);
	        
	        //-----DETAILS
	        List <Element> liste = root.getChild("component", NS).getChild("structuredBody",NS).getChildren();
	        String[] ttextDetails =liste.get(1).getChild("section", NS).getChild("text",NS).getText().split("\\r?\\n");

	        splitInfo(ttextDetails);
	        
	        //-----VERLETZUNGEN
	        String[] ttextInjury =liste.get(1).getChild("section", NS).getChild("component",NS).getChild("section",NS).getChild("text",NS).getText().split("\\r?\\n");

	        splitInfo(ttextInjury);
	        
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Setzt/Aktualisiert alle Property-Werte aus dem CDA-Body
	 * @param info ist eine Section aus dem CDA-Body
	 */
	private void splitInfo(String[] info){
		String[] keypair;
		for(String e : info){
        	keypair = e.split(":", 2);
        	keypair[0]=keypair[0].replaceAll("\\s", "");
        	if(!keypair[0].isEmpty()){	
        		keypair[1]= keypair[1].replaceAll("\\s", "");
        		body.set(keypair[0], keypair[1]);
        	}	
        }
	}
	

	
	/**
	 * Gibt das CDA-Dokument als XML-String zurueck
	 * @return CDA-Dokument als XML-String
	 */
	public String asXml() {
		String xml="";
		try{
			Document doc = new SAXBuilder().build("blank.xml");
	        Namespace NS = Namespace.getNamespace("urn:hl7-org:v3");     
	        
	        
	        Element root = doc.getRootElement();
	        
	        root.getChild("author", NS).getChild("assignedAuthor",NS).getChild("assignedPerson", NS).getChild("name", NS).getChild("given", NS).setText("Max");
	        root.getChild("author", NS).getChild("assignedAuthor",NS).getChild("assignedPerson", NS).getChild("name", NS).getChild("family", NS).setText("Mustermann");
	        root.getChild("author", NS).getChild("assignedAuthor",NS).getChild("id", NS).setAttribute("extension", "f51e7681-fc6b-4032-8f28-008f471fe32f");
	        root.getChild("author", NS).getChild("time", NS).setAttribute("value", "20130506"); 
	        
	        
	        root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("patient", NS).getChild("name", NS).getChild("given", NS).setText(getPatientNameGiven());
	        root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("patient", NS).getChild("name", NS).getChild("family", NS).setText(getPatientNameFamily());
	        root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("id", NS).setAttribute("extension", getPatientId().toString());
	        root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("patient", NS).getChild("administrativeGenderCode", NS).setAttribute("code",getPatientGender().toString());

	        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	        root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("patient", NS).getChild("birthTime", NS).setAttribute("value", formatter.format((getPatientBirthTime())));
	        
	        
	        root.getChild("id", NS).setAttribute("extension",getIdV().getId().toString());
	        root.getChild("versionNumber", NS).setAttribute("value", getIdV().getVersion().toString());
	        
	        root.getChild("relatedDocument", NS).getChild("parentDocument", NS).getChild("id", NS).setAttribute("extension", getParentIdV().getId().toString());
	        root.getChild("relatedDocument", NS).getChild("parentDocument", NS).getChild("versionNumber", NS).setAttribute("value",getParentIdV().getVersion().toString());	
	        
	        root.getChild("effectiveTime", NS).setAttribute("value", formatter.format((getDocumentDate()))); 
	        
	        //#############--BODY--#############*/
	        //-----VITALZEICHEN
	        
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).setText("");
	        
	        // System.out.println(vital);
	        
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).addContent(new Comment(" gehfaehigkeit? Werte: ja/nein "));
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).addContent("gehfaehigkeit: "+body.get("gehfaehigkeit")+"<br /> ");
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).addContent(new Comment(" respiration? Werte: stabil/kritisch "));
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).addContent("respiration: "+body.get("respiration")+" <br /> ");
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).addContent(new Comment(" perfusion? Werte: stabil/kritisch "));
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).addContent("perfusion: "+body.get("perfusion")+" <br />");
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).addContent(new Comment(" mentalerstatus? Werte: stabil/kritisch "));
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).addContent("mentalerstatus: "+body.get("mentalerstatus")+" <br /> ");
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).addContent(new Comment(" Werte: immediate, delayed, minor, deceased "));
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).addContent("triagekategorie: "+body.get("triagekategorie")+" <br /> ");
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).addContent(new Comment(" Werte: undefiniert, gesichtet, geborgen, abtransportiert "));
	        root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChild("text", NS).addContent("behandlung: "+body.get("behandlung")+" <br />");
	           
	        //-----DETAILS
	       
	        List <Element> liste = root.getChild("component", NS).getChild("structuredBody",NS).getChildren();
	        liste.get(1).getChild("section", NS).getChild("text",NS).setText("");
	        
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent(new Comment(" Format: 46.992365,10.022476,5.0 wobei die erste Zahl die geographische Breite, die zweite Zahl die geographische LŠnge in Grad und die dritte Zahl die Genauigkeit in Meter. Oder kA wenn keine Position vorhanden "));
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent("gps: "+body.get("gps")+" <br />");
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent(new Comment(" Werte: Kind, Erwachsen "));
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent("lebensphase: "+body.get("lebensphase")+" <br />");
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent(new Comment(" Werte: Freitext String"));
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent("bergeinformation: "+body.get("bergeinformation")+" <br />");
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent(new Comment(" Werte: Freitext String"));
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent("hilfplatzposition: "+body.get("hilfplatzposition")+" <br />");
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent(new Comment(" Werte: 1-5 (5 am Dringlichsten) "));
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent("dringlichkeit: "+body.get("dringlichkeit")+" <br />");
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent(new Comment(" Werte: ICD10-Code - getrennt durch ; "));
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent("diagnose: "+body.get("diagnose")+" <br />");
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent(new Comment(" Werte: int systolisch : int diastolisch "));
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent("blutdruck: "+body.get("blutdruck")+" <br /> ");
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent(new Comment(" Werte: int bpm "));
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent("puls: "+body.get("puls")+" <br />");
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent(new Comment(" Werte: Freitext String - getrennt durch ; "));
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent("behandlungsverlauf: "+body.get("behandlungsverlauf")+"<br />");
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent(new Comment(" Werte: ja/nein "));
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent("transportbereitschaft: "+body.get("transportbereitschaft")+"<br />");
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent(new Comment(" Freitext String "));
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent("zielkrankenhaus: "+body.get("zielkrankenhaus")+"<br />");
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent(new Comment(" Werte: Freitext String "));
	        liste.get(1).getChild("section", NS).getChild("text",NS).addContent("krankenkasse: "+body.get("krankenkasse")+"<br /> ");
	        
	        //-----VERLETZUNGEN
	        
	        String verletzungen=
            	"				Vorne_Kopf: "+body.get("Vorne_Kopf")+"<br />\n"
            	+"				Vorne_Hals: "+body.get("Vorne_Hals")+"<br />\n"
            	+"				Vorne_Brust: "+body.get("Vorne_Brust")+"<br />\n"
            	+"				Vorne_Bauch: "+body.get("Vorne_Bauch")+"<br />\n"
            	+"				Vorne_ROberarm: "+body.get("Vorne_ROberarm")+"<br />\n"
            	+"				Vorne_LOberarm: "+body.get("Vorne_LOberarm")+"<br />\n"
            	+"				Vorne_RUnterarm: "+body.get("Vorne_RUnterarm")+"<br />\n"
            	+"				Vorne_LUnterarm: "+body.get("Vorne_LUnterarm")+"<br />\n"
            	+"				Vorne_RHand: "+body.get("Vorne_RHand")+"<br />\n"
            	+"				Vorne_LHand: "+body.get("Vorne_LHand")+"<br />\n"
            	+"				Vorne_LOberschenkel: "+body.get("Vorne_LOberschenkel")+"<br />\n"
            	+"				Vorne_ROberschenkel: "+body.get("Vorne_ROberschenkel")+"<br />\n"
            	+"				Vorne_LUnterschenkel: "+body.get("Vorne_LUnterschenkel")+"<br />\n"
            	+"				Vorne_RUnterschenkel: "+body.get("Vorne_RUnterschenkel")+"<br />\n"
            	+"				Vorne_LFuss: "+body.get("Vorne_LFuss")+"<br />\n"
            	+"				Vorne_RFuss: "+body.get("Vorne_RFuss")+"<br />\n"
            	+"				Hinten_Kopf: "+body.get("Hinten_Kopf")+"<br />\n"
            	+"				Hinten_Hals: "+body.get("Hinten_Hals")+"<br />\n"
            	+"				Hinten_Brust: "+body.get("Hinten_Brust")+"<br />\n"
            	+"				Hinten_Bauch: "+body.get("Hinten_Bauch")+"<br />\n"
            	+"				Hinten_ROberarm: "+body.get("Hinten_ROberarm")+"<br />\n"
            	+"				Hinten_LOberarm: "+body.get("Hinten_LOberarm")+"<br />\n"
            	+"				Hinten_RUnterarm: "+body.get("Hinten_RUnterarm")+"<br />\n"
            	+"				Hinten_LUnterarm: "+body.get("Hinten_LUnterarm")+"<br />\n"
            	+"				Hinten_RHand: "+body.get("Hinten_RHand")+"<br />\n"
            	+"				Hinten_LHand: "+body.get("Hinten_LHand")+"<br />\n"
            	+"				Hinten_LOberschenkel: "+body.get("Hinten_LOberschenkel")+"<br />\n"
            	+"				Hinten_ROberschenkel: "+body.get("Hinten_ROberschenkel")+"<br />\n"
            	+"				Hinten_LUnterschenkel: "+body.get("Hinten_LUnterschenkel")+"<br />\n"
            	+"				Hinten_RUnterschenkel: "+body.get("Hinten_RUnterschenkel")+"<br />\n"
            	+"				Hinten_LFuss: "+body.get("Hinten_LFuss")+"<br />\n"
            	+"				Hinten_RFuss: "+body.get("Hinten_RFuss")+"<br />\n";
	        
	        liste.get(1).getChild("section", NS).getChild("component",NS).getChild("section",NS).getChild("text",NS).setText("");
	        liste.get(1).getChild("section", NS).getChild("component",NS).getChild("section",NS).getChild("text",NS).addContent(new Comment("Werte: Freitext String †BERALL"));
	        
	        liste.get(1).getChild("section", NS).getChild("component",NS).getChild("section",NS).getChild("text",NS).addContent(verletzungen);
	        
	        
	        Format format = Format.getPrettyFormat();
	        format.setEncoding("UTF-8");
	        
			XMLOutputter outputter = new XMLOutputter(format);
			outputter.escapeAttributeEntities("&lt;");
	        xml = outputter.outputString(doc);
	        
	       
	        System.out.println(xml);
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
		
		return xml;
		
	}
	
	/**
	 * ID und Version des Dokuments
	 */
	private CdaIdV idV = null;
	/**
	 * @return ID und Version des Dokuments
	 */
	public CdaIdV getIdV() { return idV; }
	/**
	 * Setzt ID und Version des Dokuments
	 * @param value neuer Wert
	 */
	public void setIdV(CdaIdV value) { idV = value; }
	
	/**
	 * ID und Version des vorgaenger Dokuments
	 */
	private CdaIdV parentIdV = null;
	/**
	 * @return ID und Version des vorgaenger Dokuments
	 */
	public CdaIdV getParentIdV() { return parentIdV; }
	/**
	 * Setzt ID und Version des vorgaenger Dokuments 
	 * @param value neuer Wert
	 */
	public void setParentIdV(CdaIdV value) { parentIdV = value; }
	
	/**
	 * ID des Patienten im Header
	 */
	private UUID patientId = null;
	/**
	 * @return ID des Patienten im Header
	 */
	public UUID getPatientId() { return patientId; }
	/**
	 * Setzt ID des Patienten im Header
	 * @param value neuer Wert
	 */
	public void setPatientId(UUID value) { patientId = value; }
	
	
	/**
	 * Vorname des Patienten im Header
	 */
	private String patientNameGiven;
	/**
	 * @return Vorname des Patienten im Header
	 */
	public String getPatientNameGiven(){  return patientNameGiven;  }
	/**
	 * Setzt den Vornamen des Patienten im Header
	 * @param name neuer Name
	 */
	private void setPatientNameGiven(String name){
		patientNameGiven=name;
	}
	
	/**
	 * Nachname des Patienten im Header
	 */
	private String patientNameFamily;
	/**
	 * @return Nachname des Patienten im Header
	 */
	public String getPatientNameFamily(){  return patientNameFamily;  }
	/**
	 * Setzt den Nachnamen des Patienten im Header
	 * @param name neuer Name
	 */
	private void setPatientNameFamily(String name){
		patientNameFamily=name;
	}

	/**
	 * Geburtsdatum des Patienten im Header
	 */
	private Date patientBirthTime;
	/**
	 * @return Geburtsdatum des Patienten im Header
	 */
	public Date getPatientBirthTime(){  return patientBirthTime;  }
	/**
	 * Setzt das Geburtsdatum des Patienten im Header
	 * @param date neues Datum
	 */
	public void setPatientBirthTime(Date date){
		patientBirthTime = date;
	}	

	/**
	 * Geschlecht des Patienten im Header
	 */
	private Gender patientGender;
	/**
	 * @return Geschlecht des Patienten im Header
	 */
	public Gender getPatientGender(){  return patientGender;  }
	/**
	 * Setzt das Geschlecht des Patienten im Header
	 * @param gender neues Geschlecht
	 */
	private void setPatientGender(Gender gender){
		patientGender = gender;
	}
	
	/**
	 * Nachname des Authors im Header
	 */
	private String authorNameFamily;
	/**
	 * @return Nachname des Authors im Header
	 */
	public String getAuthorNameFamily(){  return authorNameFamily;  }
	/**
	 * Setzt den Nachnamen des Authors im Header
	 * @param name neuer Name
	 */
	private void setAuthorNameFamily(String name){
		authorNameFamily=name;
	}

	/**
	 * Vorname des Authors im Header
	 */
	private String authorNameGiven;
	/**
	 * @return Vorname des Authors im Header
	 */
	public String getAuthorNameGiven(){  return authorNameGiven;  }
	/**
	 * Setzt den Vornamen des Authors im Header
	 * @param name neuer Name
	 */
	private void setAuthorNameGiven(String name){
		authorNameGiven=name;
	}
	
	/**
	 * Erstell-Datum des Dokuments
	 */
	private Date documentDate;
	/**
	 * @return Erstell-Datum des Dokuments
	 */
	public Date getDocumentDate() { return documentDate; }
	/**
	 * Setzt das Erstell-Datum des Dokuments
	 * @param newdate neues Datum
	 */
	public void setDocumentDate(Date newdate) {
		documentDate = newdate;
	}
	
	/**
	 * ID des Authors im Header
	 */
	private UUID authorId;
	/**
	 * @return ID des Authors im Header
	 */
	public UUID getAuthorId(){  return authorId;  }
	/**
	 * Setzt die ID des Authors im Header
	 * @param value neuer Wert
	 */
	public void setAuthorId(UUID value){
		authorId = value;
	}
	

	private CdaBody body = new CdaBody();
	/**
	 * @return CDA-Body
	 */
	public CdaBody getBody() { return body; }
	
	public Bodyparts getBodyParts(){
		Bodyparts allparts = new Bodyparts();
		
		
		allparts.set("Vorne_Kopf",body.get("Vorne_Kopf"));
		allparts.set("Vorne_Hals" ,body.get("Vorne_Hals"));
		allparts.set("Vorne_Brust" ,body.get("Vorne_Brust"));
    	allparts.set("Vorne_Bauch" ,body.get("Vorne_Bauch"));
    	allparts.set("Vorne_ROberarm",body.get("Vorne_ROberarm"));
    	allparts.set("Vorne_LOberarm" ,body.get("Vorne_LOberarm"));
    	allparts.set("Vorne_RUnterarm" ,body.get("Vorne_RUnterarm"));
    	allparts.set("Vorne_LUnterarm" ,body.get("Vorne_LUnterarm"));
    	allparts.set("Vorne_RHand" ,body.get("Vorne_RHand"));
    	allparts.set("Vorne_LHand" ,body.get("Vorne_LHand"));
    	allparts.set("Vorne_LOberschenkel" ,body.get("Vorne_LOberschenkel"));
    	allparts.set("Vorne_ROberschenkel" ,body.get("Vorne_ROberschenkel"));
    	allparts.set("Vorne_LUnterschenkel" ,body.get("Vorne_LUnterschenkel"));
    	allparts.set("Vorne_RUnterschenkel" ,body.get("Vorne_RUnterschenkel"));
    	allparts.set("Vorne_LFuss" ,body.get("Vorne_LFuss"));
    	allparts.set("Vorne_RFuss" ,body.get("Vorne_RFuss"));
    	allparts.set("Hinten_Kopf" ,body.get("Hinten_Kopf"));
    	allparts.set("Hinten_Hals" ,body.get("Hinten_Hals"));
    	allparts.set("Hinten_Brust" ,body.get("Hinten_Brust"));
    	allparts.set("Hinten_Bauch" ,body.get("Hinten_Bauch"));
    	allparts.set("Hinten_ROberarm" ,body.get("Hinten_ROberarm"));
    	allparts.set("Hinten_LOberarm" ,body.get("Hinten_LOberarm"));
    	allparts.set("Hinten_RUnterarm" ,body.get("Hinten_RUnterarm"));
    	allparts.set("Hinten_LUnterarm" ,body.get("Hinten_LUnterarm"));
    	allparts.set("Hinten_RHand" ,body.get("Hinten_RHand"));
    	allparts.set("Hinten_LHand" ,body.get("Hinten_LHand"));
    	allparts.set("Hinten_LOberschenkel" ,body.get("Hinten_LOberschenkel"));
    	allparts.set("Hinten_ROberschenkel" ,body.get("Hinten_ROberschenkel"));
    	allparts.set("Hinten_LUnterschenkel" ,body.get("Hinten_LUnterschenkel"));
    	allparts.set("Hinten_RUnterschenkel" ,body.get("Hinten_RUnterschenkel"));
    	allparts.set("Hinten_LFuss" ,body.get("Hinten_LFuss"));
    	allparts.set("Hinten_RFuss" ,body.get("Hinten_RFuss"));;
		
		
		return allparts;
	}
	
}
