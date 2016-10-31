package de.uni_stuttgart.ils.reqif.attributes;

import java.util.ArrayList;
import java.util.List;

public class AttributeValueXHTMLElementList {
	
	
	private int size;
	private List<java.lang.String> xhtmlElements;
	private List<List<java.lang.String>> xhtmlContents;
	
	
	
	
	public int size() {
		return this.size;
	}
	
	public void add(java.lang.String string, List<java.lang.String> list) {
		this.xhtmlElements.add(string);
		this.xhtmlContents.add(list);
		this.size++;
	}
	
	public java.lang.String getElementType(int index) {
		return xhtmlElements.get(index);
	}
	
	public List<java.lang.String> getElementContentList(int index) {
		return this.xhtmlContents.get(index);
	}
	
	public List<java.lang.String> getElementList() {
		return this.xhtmlElements;
	}
	
	
	
	
	public AttributeValueXHTMLElementList() {
		
		this.size = 0;
		this.xhtmlElements = new ArrayList<java.lang.String>();
		this.xhtmlContents = new ArrayList<List<java.lang.String>>();
	}

}
