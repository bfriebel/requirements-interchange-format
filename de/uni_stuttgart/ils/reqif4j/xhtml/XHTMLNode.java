package de.uni_stuttgart.ils.reqif4j.xhtml;

import org.w3c.dom.Node;



public class XHTMLNode {

	protected String tagName;
	protected XHTMLNode parent = null;
	
	
	/**
	 * @return the node name of this xhtml node
	 */
	public String getTagName() {
		return this.tagName;
	}
	
	/**
	 * @return the parent XHTMLNode of this XHTMLNode
	 */
	public XHTMLNode getParent() {
		return this.parent;
	}
	
	
	
	public XHTMLNode(Node xhtmlElement) {
		
		this.tagName = xhtmlElement.getNodeName();
	}
	
	public XHTMLNode(Node xhtmlElement, XHTMLNode parent) {
		
		this.tagName = xhtmlElement.getNodeName();
		this.parent = parent;
	}
	
	@Override
	public String toString() {
		return tagName;
	}
	
}
