package at.mts.entity.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Element {

	public Element(org.w3c.dom.Node node) {
		this.node = node;
	}
	
	private org.w3c.dom.Node node;

	public List<Element> getChildren() {
		NodeList list = node.getChildNodes();
		ArrayList<Element> result = new ArrayList<Element>(list.getLength());
		for (int i = 0; i < list.getLength(); i++) {
			org.w3c.dom.Node n = list.item(i);
			if (n instanceof org.w3c.dom.Element) {
				result.add(new Element(list.item(i)));
			}
		}
		return result;
	}

	private boolean isElement() { return (node instanceof org.w3c.dom.Element); }
	
	private org.w3c.dom.Element asElement() {
		return (org.w3c.dom.Element)node;
	}
	
	public Element getChild(String tagName) {
		if (isElement()) {
			for (Element element : getChildren()) {
				if (element.isElement()) {
					if (element.asElement().getTagName().equals(tagName)) {
						return element;
					}
				}
			}
		}
		return null;
	}
	
	public Element getChild(String tagName, Object namespace) {
		return getChild(tagName);
	}

	public String getChildText(String tagName) {
		Element child = getChild(tagName);
		if (child != null) {
			return child.getText();
		}
		else { return null; }
	}

	public void addContent(Element p) {
		node.appendChild(p.node);
	}

	public Element addContent(String text) {
		if (text == null) { text = ""; }
		org.w3c.dom.Node n = node.getOwnerDocument().createTextNode(text);
		node.appendChild(n);
		return this;
	}
	
	public Element addContent(Comment comment) {
		org.w3c.dom.Node n = node.getOwnerDocument().createComment(comment.getText());
		node.appendChild(n);
		addContent("");
		return this;
	}

	public String getAttributeValue(String name) {
		if (isElement()) {
			org.w3c.dom.Attr a = asElement().getAttributeNode(name);
			if (a!=null) {
				return a.getValue();
			} else {
				return null;
			}
		}
		return null;
	}

	public String getText() {
		return node.getTextContent();
	}

	public void setAttribute(String name, String value) {
		if (value == null) { value = ""; }
		if (isElement()) {
			asElement().setAttribute(name, value);
		}
	}

	public void setText(String text) {
		if (text == null) { text = ""; }
		removeContent();
		node.setTextContent(text);
	}

	public void removeChild(String tagName, Object NS) {
		Element child = getChild(tagName);
		if (child != null) {
			node.removeChild(child.node);
		}
	}

	public void removeContent() {
		NodeList l = node.getChildNodes();
		org.w3c.dom.Node[] nodes = new org.w3c.dom.Node[l.getLength()];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = l.item(i);
		}
		
		for (Node n : nodes) {
			node.removeChild(n);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass().equals(obj.getClass())) {
			Element other = (Element)obj;
			return node.equals(other.node);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return node.hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		
		r.append("-"+node.getNodeName()+"\n");
		for (Element e : getChildren()) {
			r.append(e.toString().trim().replace("-", "--")+"\n");
		}
		
		return r.toString();
	}
}
