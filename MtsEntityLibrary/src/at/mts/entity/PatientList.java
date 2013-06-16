package at.mts.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Liste von Patienten, mit nur wenigen Eigenschaften
 */
@SuppressWarnings("serial")
public class PatientList extends ArrayList<PatientListItem> {
	public PatientList() { }
	public PatientList(String xml) {
		
		Document doc;
		try {
			doc = new SAXBuilder().build(new ByteArrayInputStream(xml.getBytes("UTF-8")));

	        Element root = doc.getRootElement();
	        
	        List<Element> childs = root.getChildren();
	        for (Element child : childs) {
	        	PatientListItem item = new PatientListItem();
	        	
	        	item.setNameGiven(child.getChild("name").getChildText("given"));
	        	item.setNameFamily(child.getChild("name").getChildText("family"));
	        	item.setCategory(TriageCategory.valueOf(child.getChildText("triagekategorie")));
	        	item.setTreatment(Treatment.valueOf(child.getChildText("behandlung")));
	        	item.setUrl(child.getChildText("url"));
	        	
				add(item);
			}
		} catch (JDOMException e) {
			throw new IllegalArgumentException("xml is invalid", e);
		} catch (IOException e) {
			throw new IllegalArgumentException("xml is invalid", e);
		}
	}
	
	public String asXml() {
		Document doc = new Document();
		
		Element rootElement = new Element("patientlist");
		doc.setRootElement(rootElement);

		for (PatientListItem item : this) {
			
			Element p = new Element("patient");
			rootElement.addContent(p);
			
			Element name = new Element("name");
			p.addContent(name);
			
			name.addContent(new Element("given").addContent(item.getNameGiven()));
			name.addContent(new Element("family").addContent(item.getNameFamily()));
			
			p.addContent(new Element("triagekategorie").addContent(triageCategoryToString(item.getCategory())));
			p.addContent(new Element("behandlung").addContent(treatmentToString(item.getTreatment())));
			p.addContent(new Element("url").addContent(item.getUrl()));
		}
		
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        String xml = outputter.outputString(doc);
        return xml;
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
