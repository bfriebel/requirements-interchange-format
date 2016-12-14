package de.uni_stuttgart.ils.reqif4j.specification;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.uni_stuttgart.ils.reqif4j.attributes.AttributeValue;
import de.uni_stuttgart.ils.reqif4j.attributes.AttributeValueXHTML;
import de.uni_stuttgart.ils.reqif4j.attributes.AttributeValueXHTMLElementList;
import de.uni_stuttgart.ils.reqif4j.reqif.ReqIFConst;
import de.uni_stuttgart.ils.reqif4j.xhtml.XHTMLNode;
import de.uni_stuttgart.ils.reqif4j.xhtml.XHTMLElementDiv;

public class SpecHierarchy {
	
	
	private String specHierarchyID;
	private int hierarchyLvl;
	private int section;
	private SpecObject specObject;
	private Map<String, SpecHierarchy> children = new LinkedHashMap<String, SpecHierarchy>();
	
	
	
	
	public String getSpecHierarchyID() {
		return this.specHierarchyID;
	}
	
	public String getSpecObjectID(){
		return this.specObject.getID();
	}
	
	public int getHierarchyLvl() {
		return this.hierarchyLvl;
	}
	
	public int getSection() {
		return this.section;
	}
	
	public SpecObject getSpecObject() {
		return this.specObject;
	}
	
	public Map<String, AttributeValue> getAttributes() {
		return this.specObject.getAttributes();
	}
	
	public Object getAttribute(String attributeName) {
		return (Object)this.specObject.getAttribute(attributeName);
	}
	
	public AttributeValueXHTMLElementList getXHTMLContent() {
		for(AttributeValue attributeValue: this.specObject.getAttributes().values()) {
			if(attributeValue.getDatatype().equals(ReqIFConst.XHTML)) {
				return (AttributeValueXHTMLElementList)attributeValue.getValue();
			}
		}
		return null;
	}
	
	public List<XHTMLNode> getXHTMLDivContent() {
		for(AttributeValue attributeValue: this.specObject.getAttributes().values()) {
			if(attributeValue instanceof AttributeValueXHTML) {
				XHTMLElementDiv value = ((AttributeValueXHTML)attributeValue).getDivValue();
				if(value != null) {
					return value.getChildren();
				}
			}
		}
		return null;
	}
	
	public String getSpecType() {
		return this.specObject.getSpecType();
	}
	
	public String getSpecTypeName() {
		return this.specObject.getSpecTypeName();
	}
	
	public String getType() {
		return this.specObject.getType();
	}
	
	public boolean isReq() {
		if(this.specObject.isReq()) {
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isDef() {
		if(this.specObject.isReq() && this.section == 1) {
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isHeadline() {
		if(this.specObject.isHeadline()) {
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isSubReq() {
		if(this.specObject.isSubReq()) {
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isText() {
		if(this.specObject.isText()) {
			return true;
		}else{
			return false;
		}
	}
	
	public List<SpecHierarchy> getDirectChildren() {
		
		List<SpecHierarchy> children = new ArrayList<SpecHierarchy>();
		children.add((SpecHierarchy) this.children.values());
		
		return children;
	}
	
	public List<SpecHierarchy> getAllChildren() {
		
		List<SpecHierarchy> allChildren = new ArrayList<SpecHierarchy>();
		for(SpecHierarchy specHierarchy: this.children.values()) {
			allChildren.add(specHierarchy);
			for(SpecHierarchy child: specHierarchy.getAllChildren()) {
				allChildren.add(child);
			}
		}
		
		return allChildren;
	}
	
	
	
	
	public SpecHierarchy(int hierarchyLvl, int section, Node specHierarchy, Map<String, SpecObject> specObjects) {
		
		this.hierarchyLvl = hierarchyLvl;
		this.section = section;
		this.specHierarchyID = specHierarchy.getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
		
		for(int childnode = 0; childnode < specHierarchy.getChildNodes().getLength(); childnode ++) {
			Node childNode = specHierarchy.getChildNodes().item(childnode);
			if(childNode.getNodeName().equals(ReqIFConst.OBJECT)) {
				String specObjectRef = ((Element)childNode).getElementsByTagName(ReqIFConst.SPEC_OBJECT_REF).item(0).getTextContent();
				this.specObject = specObjects.get(specObjectRef);
			}
		}
		
		if(			((Element)specHierarchy).getElementsByTagName(ReqIFConst.CHILDREN).getLength() > 0
				&&	((Element)specHierarchy).getElementsByTagName(ReqIFConst.CHILDREN).item(0).getChildNodes().getLength() > 0		) {
			
			NodeList children = ((Element)specHierarchy).getElementsByTagName(ReqIFConst.CHILDREN).item(0).getChildNodes();
			for(int child = 0; child < children.getLength(); child++) {
				
				Node newSpecHierarchy = children.item(child);
				if(!newSpecHierarchy.getNodeName().equals(ReqIFConst._TEXT)) {
					
					String specHierarchyID = newSpecHierarchy.getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
					
					this.children.put(specHierarchyID, new SpecHierarchy(this.hierarchyLvl+1, section, newSpecHierarchy, specObjects));
				}
			}
		}
	}
	
}
