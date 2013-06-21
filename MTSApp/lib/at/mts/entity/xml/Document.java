package at.mts.entity.xml;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Document {

	public Document(String xml) throws XmlException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	
		    factory.setNamespaceAware(true);
		    DocumentBuilder builder = factory.newDocumentBuilder();
	
		    document = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		}
		catch (Exception e) {
			throw new XmlException(e);
		}
	}
	
	private org.w3c.dom.Document document;

	public static Document emptyDocument(String rootTagName) throws XmlException {
		return new Document("<?xml version=\"1.0\" encoding=\"UTF-8\"?><"+rootTagName+" />");
	}

	public Element getRootElement() {
		return new Element(document.getDocumentElement());
	}
	
	public Element newElement(String tagName) {
		return new Element(document.createElement(tagName));
	}
	
	public String asXml() throws XmlException {
	    try {
			DOMSource domSource = new DOMSource(document);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(domSource, result);
			return writer.toString();
	    }
	    catch(TransformerException e) {
	       throw new XmlException(e);
	    }
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass().equals(obj.getClass())) {
			Document other = (Document)obj;
			return document.equals(other);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return document.hashCode();
	}
}
