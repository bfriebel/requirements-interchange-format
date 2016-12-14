package de.uni_stuttgart.ils.reqif4j.xhtml;

import org.w3c.dom.Node;

import de.uni_stuttgart.ils.reqif4j.attributes.XHTML;

public class XHTMLElementText extends XHTMLLeaf {
	
	private String textContent;
	
	public String getTextContent() {
		return this.textContent;
	}
	
	

	public XHTMLElementText (Node xhtmlElement) {
		super(xhtmlElement);
		
		this.tagName = XHTML.TEXT;
		this.textContent = xhtmlElement.getTextContent().trim();
	}

	public XHTMLElementText(Node xhtmlElement, XHTMLNode parent) {
		super(xhtmlElement, parent);
		
		this.tagName = XHTML.TEXT;
		this.textContent = xhtmlElement.getTextContent().trim();
	}
	
	@Override
	public String toString() {
		return tagName + " {" + textContent + "}";
	}

}
