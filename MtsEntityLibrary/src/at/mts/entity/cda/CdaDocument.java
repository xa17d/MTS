package at.mts.entity.cda;
import at.mts.entity.Bodyparts;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Enumeration;
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

import at.mts.entity.Condition;
import at.mts.entity.Gender;
import at.mts.entity.Patient;
import at.mts.entity.PhaseOfLife;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;
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
		setDocumentDate(new Date());
		setIdV(new CdaIdV(UUID.randomUUID(), 1));
	}
	
	/**
	 * Erzeugt ein CdaDocument aus einem XML-String
	 * @param document CDA Dokument als XML-String
	 */
	public CdaDocument(String document) {
		setDocumentDate(new Date());
		load(document);
	}
	
	/**
	 * Erzeugt ein CdaDocument aus einem Patient-Objekt
	 * @param patient Patientendaten
	 */
	public CdaDocument(Patient patient) {
		
		setIdV(new CdaIdV(patient.getId(), patient.getVersion()));
		
        setPatientNameGiven(patient.getNameGiven());
        setPatientNameFamily(patient.getNameFamily());
        setPatientId(patient.getId());
        setPatientGender(patient.getGender());
        setPatientBirthTime(patient.getBirthTime());
        
        setParentIdV(null);  //??
        setDocumentDate(patient.getTimestamp());
        
        //#############--BODY--#############*/
        body = new CdaBody();
        //-----VITALZEICHEN
        setBody(body, "gehfaehigkeit", patient.getWalkable(), CdaBoolean.asCdaValue(patient.getWalkable()));
        setBody(body, "respiration", patient.getRespiration(), Condition.asCdaValue(patient.getRespiration()));
        setBody(body, "perfusion", patient.getPerfusion(), Condition.asCdaValue(patient.getPerfusion()));
        setBody(body, "mentalerstatus", patient.getMentalStatus(), Condition.asCdaValue(patient.getMentalStatus()));
        setBody(body, "triagekategorie", patient.getCategory(), TriageCategory.asCdaValue(patient.getCategory()));
        setBody(body, "behandlung", patient.getTreatment(), Treatment.asCdaValue(patient.getTreatment()));
                
                //-----DETAILS
        setBody(body, "gps", patient.getGps());
        setBody(body, "lebensphase",  patient.getPhaseOfLife(), PhaseOfLife.asCdaValue(patient.getPhaseOfLife()));
        setBody(body, "bergeinformation", patient.getSalvageInfoString());
        setBody(body, "hilfplatzposition", patient.getPlacePosition());
        setBody(body, "dringlichkeit", patient.getUrgency(), patient.getUrgency());
        setBody(body, "diagnose", patient.getDiagnosis());
        
        if (patient.getBloodPressureSystolic() != null && patient.getBloodPressureDiastolic() != null) {
        	setBody(body, "blutdruck", patient.getBloodPressureSystolic()+":"+patient.getBloodPressureDiastolic());
        }
        setBody(body, "puls", patient.getPulse(), patient.getPulse());
        setBody(body, "behandlungsverlauf", patient.getCourseOfTreatment());
        setBody(body, "transportbereitschaft", patient.getReadyForTransport(), CdaBoolean.asCdaValue(patient.getReadyForTransport()));
        setBody(body, "zielkrankenhaus", patient.getHospital());
        setBody(body, "krankenkasse", patient.getHealthInsurance());
        //-----VERLETZUNGEN
        //set(String part, String information)
        Bodyparts bodyparts = patient.getBodyparts();
        if (bodyparts != null) {
        
        	for (String key : bodyparts.keySet()) {
				body.set(key, bodyparts.get(key));
			}
        }
	}
	
	private void setBody(CdaBody body, String key, String value) {
		setBody(body, key, value, value);
	}
	
	private void setBody(CdaBody body, String key, Object value, Object cdaValue) {
		if (value != null) {
			body.set(key, cdaValue.toString());
		}
	}
	
	/**
	 * Liest ein CdaDocument aus einem XML-String.
	 * Die bestehenden Daten gehen dabei verloren.
	 * @param document CDA Dokument als XML-String
	 */
	public void load(String document) {
		
		try{
			
			body = new CdaBody(); // Body loeschen
			Document doc = new SAXBuilder().build(new ByteArrayInputStream(document.getBytes("UTF-8")));
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
	        
	        if (root.getChild("relatedDocument", NS) != null && root.getChild("relatedDocument", NS).getChild("parentDocument", NS) != null) {
		        UUID idFromCdaParent =UUID.fromString(root.getChild("relatedDocument", NS).getChild("parentDocument", NS).getChild("id", NS).getAttributeValue("extension"));
		        int versionNrParent = Integer.parseInt(root.getChild("relatedDocument", NS).getChild("parentDocument", NS).getChild("versionNumber", NS).getAttributeValue("value"));	
		        setParentIdV(new CdaIdV(idFromCdaParent, versionNrParent));
	        }
	        
	        String effectiveTime = root.getChild("effectiveTime", NS).getAttributeValue("value"); 
	        setDocumentDate((Date) formatter.parse(effectiveTime));
	        
	        
	        
	        //#############--BODY--#############*/
	        //-----VITALZEICHEN

	        String textVital = root.getChild("component", NS).getChild("structuredBody",NS).getChild("component", NS).getChild("section", NS).getChildText("text", NS);

	        splitInfo(textVital);
	        
	        //-----DETAILS
	        List <Element> liste = root.getChild("component", NS).getChild("structuredBody",NS).getChildren();
	        String textDetails =liste.get(1).getChild("section", NS).getChild("text",NS).getText();

	        splitInfo(textDetails);
	        
	        //-----VERLETZUNGEN
	        String textInjury =liste.get(1).getChild("section", NS).getChild("component",NS).getChild("section",NS).getChild("text",NS).getText();

	        splitInfo(textInjury);
	        
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Setzt/Aktualisiert alle Property-Werte aus dem CDA-Body
	 * @param info ist eine Section aus dem CDA-Body
	 */
	private void splitInfo(String bodyValue){
		
		String[] lines = bodyValue.split("<br ?/?>");
		
		for (String line : lines) {
			String[] keypair = line.trim().split(":", 2);
			if (keypair.length > 1) {
				String key = keypair[0].trim();
				String value = keypair[1].trim();
				body.set(key, value);
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
			InputStream stream = getClass().getResourceAsStream("/at/mts/entity/resources/blank.xml");
			Document doc = new SAXBuilder().build(stream);
	        Namespace NS = Namespace.getNamespace("urn:hl7-org:v3");     
	        
	        
	        Element root = doc.getRootElement();
	        
	        root.getChild("author", NS).getChild("assignedAuthor",NS).getChild("assignedPerson", NS).getChild("name", NS).getChild("given", NS).setText("Max");
	        root.getChild("author", NS).getChild("assignedAuthor",NS).getChild("assignedPerson", NS).getChild("name", NS).getChild("family", NS).setText("Mustermann");
	        root.getChild("author", NS).getChild("assignedAuthor",NS).getChild("id", NS).setAttribute("extension", "f51e7681-fc6b-4032-8f28-008f471fe32f");
	        root.getChild("author", NS).getChild("time", NS).setAttribute("value", "20130506"); 
	        
	        
	        root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("patient", NS).getChild("name", NS).getChild("given", NS).setText(getPatientNameGiven());
	        root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("patient", NS).getChild("name", NS).getChild("family", NS).setText(getPatientNameFamily());
	        root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("id", NS).setAttribute("extension", getPatientId().toString());
	        root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("patient", NS).getChild("administrativeGenderCode", NS).setAttribute("code", Gender.asCdaValue(getPatientGender()));

	        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	        Date birthTime = getPatientBirthTime();
	        if (birthTime == null) { birthTime = formatter.parse("18000101"); }
	        root.getChild("recordTarget", NS).getChild("patientRole",NS).getChild("patient", NS).getChild("birthTime", NS).setAttribute("value", formatter.format(birthTime));
	        
	        
	        root.getChild("id", NS).setAttribute("extension", getIdV().getId().toString());
	        root.getChild("versionNumber", NS).setAttribute("value", Integer.toString(getIdV().getVersion()));
	        
	        Element relatedDocument = root.getChild("relatedDocument", NS);
	        if (getParentIdV() == null) {
	        	relatedDocument.removeChild("parentDocument", NS);
	        }
	        else {
		        root.getChild("relatedDocument", NS).getChild("parentDocument", NS).getChild("id", NS).setAttribute("extension", getParentIdV().getId().toString());
		        root.getChild("relatedDocument", NS).getChild("parentDocument", NS).getChild("versionNumber", NS).setAttribute("value", Integer.toString(getParentIdV().getVersion()));	
	        }
	        
	        Date effectiveTime = getDocumentDate();
	        if (effectiveTime == null) { effectiveTime = new Date(); }
	        root.getChild("effectiveTime", NS).setAttribute("value", formatter.format(effectiveTime)); 
	        
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
	
}
