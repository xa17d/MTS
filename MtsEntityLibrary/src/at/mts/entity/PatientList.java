package at.mts.entity;

import java.util.ArrayList;
import java.util.List;

import at.mts.entity.xml.Document;
import at.mts.entity.xml.Element;
import at.mts.entity.xml.XmlException;

/**
 * Liste von Patienten, mit nur wenigen Eigenschaften
 */
@SuppressWarnings("serial")
public class PatientList extends ArrayList<PatientListItem> {
	public PatientList() { }
	public PatientList(String xml) {
		
		Document doc;
		try {
			doc = new Document(xml);

	        Element root = doc.getRootElement();
	        
	        List<Element> childs = root.getChildren();
	        for (Element child : childs) {
	        	PatientListItem item = new PatientListItem();
	        	
	        	item.setNameGiven(child.getChild("name").getChildText("given"));
	        	item.setNameFamily(child.getChild("name").getChildText("family"));
	        	item.setCategory(TriageCategory.getValueOf(child.getChildText("triagekategorie")));
	        	item.setTreatment(Treatment.valueOf(child.getChildText("behandlung")));
	        	item.setUrl(child.getChildText("url"));
	        	
				add(item);
			}
		} catch (XmlException e) {
			throw new IllegalArgumentException("xml invalid", e);
		}

	}
	
	public String asXml() {
		Document doc;
		try {
			doc = Document.emptyDocument("patientlist");
	
			Element rootElement = doc.getRootElement();
	
			for (PatientListItem item : this) {
				
				Element p = doc.newElement("patient");
				rootElement.addContent(p);
				
				Element name = doc.newElement("name");
				p.addContent(name);
				
				name.addContent(doc.newElement("given").addContent(item.getNameGiven()));
				name.addContent(doc.newElement("family").addContent(item.getNameFamily()));
				
				p.addContent(doc.newElement("triagekategorie").addContent(triageCategoryToString(item.getCategory())));
				p.addContent(doc.newElement("behandlung").addContent(treatmentToString(item.getTreatment())));
				p.addContent(doc.newElement("url").addContent(item.getUrl()));
			}
			
	        String xml = doc.asXml();
	        return xml;
		} catch (XmlException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private String triageCategoryToString(TriageCategory category) {
		if (category == null) {
			return TriageCategory.notSpecified.toString();
		} else {
			return category.toString();
		}
	}
	
	private String treatmentToString(Treatment treatment) {
		if (treatment == null) {
			return Treatment.notSpecified.toString();
		} else {
			return treatment.toString();
		}
	}
}
