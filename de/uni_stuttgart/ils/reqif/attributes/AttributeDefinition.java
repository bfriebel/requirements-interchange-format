package de.uni_stuttgart.ils.reqif.attributes;

import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.uni_stuttgart.ils.reqif.datatypes.Datatype;
import de.uni_stuttgart.ils.reqif.reqif.ReqIFConst;

public class AttributeDefinition {
	
	
	private String id;
	private String name;
	private Datatype type;
	private String defaultValue;
	
	
	
	
	public String getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Datatype getDataType() {
		return this.type;
	}
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	
	
	
	public AttributeDefinition(Node attributeDefinition, Map<String, Datatype> dataTypes) {
		
		this.id = attributeDefinition.getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
		this.name = attributeDefinition.getAttributes().getNamedItem(ReqIFConst.LONG_NAME).getTextContent();
		
		Element attDef = (Element) attributeDefinition;
		String typeID = attDef.getElementsByTagName(ReqIFConst.TYPE).item(0).getChildNodes().item(1).getTextContent();
		this.type = dataTypes.get(typeID);
		
		NodeList defVal = attDef.getElementsByTagName(ReqIFConst.DEFAULT_VALUE);
		if(defVal.getLength() > 0) {
			
			Node attDefVal = defVal.item(0).getChildNodes().item(1);
			if(attDefVal != null && attDefVal.hasAttributes() && attDefVal.getAttributes().getNamedItem(ReqIFConst.THE_VALUE) != null) {
				this.defaultValue = attDefVal.getAttributes().getNamedItem(ReqIFConst.THE_VALUE).getTextContent();
			}
		}
	}

}
