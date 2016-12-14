package de.uni_stuttgart.ils.reqif4j.xhtml;

import org.w3c.dom.Node;

public class XHTMLLeaf extends XHTMLNode {

	public XHTMLLeaf(Node xhtmlElement) {
		super(xhtmlElement);
	}

	public XHTMLLeaf(Node xhtmlElement, XHTMLNode parent) {
		super(xhtmlElement, parent);
	}

}
