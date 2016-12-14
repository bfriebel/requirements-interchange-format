package de.uni_stuttgart.ils.reqif4j.xhtml;

import org.w3c.dom.Node;

public class XHTMLElementObject extends XHTMLElement {

	private String data;
	
	public String getData() {
		return this.data;
	}
	
	
	
	public XHTMLElementObject (Node xhtmlElement) {
		super(xhtmlElement);
		
		this.data = xhtmlElement.getAttributes().getNamedItem("data").getTextContent().trim().replace("/", System.getProperty("file.separator"));
	}

	public XHTMLElementObject(Node xhtmlElement, XHTMLNode parent) {
		super(xhtmlElement, parent);
		
		this.data = xhtmlElement.getAttributes().getNamedItem("data").getTextContent().trim().replace("/", System.getProperty("file.separator"));
	}
	
	@Override
	public String toString() {
		return tagName + " {" + data +  "}" + (!children.isEmpty() ? "\t" + children.toString() : "");
	}

}
