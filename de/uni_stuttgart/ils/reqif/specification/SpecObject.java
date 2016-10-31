package de.uni_stuttgart.ils.reqif.specification;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.uni_stuttgart.ils.reqif.attributes.AttributeDefinition;
import de.uni_stuttgart.ils.reqif.attributes.AttributeValue;
import de.uni_stuttgart.ils.reqif.attributes.AttributeValueBoolean;
import de.uni_stuttgart.ils.reqif.attributes.AttributeValueEnumeration;
import de.uni_stuttgart.ils.reqif.attributes.AttributeValueInteger;
import de.uni_stuttgart.ils.reqif.attributes.AttributeValueString;
import de.uni_stuttgart.ils.reqif.attributes.AttributeValueXHTML;
import de.uni_stuttgart.ils.reqif.reqif.ReqIFConst;

public class SpecObject {
	
	
	private String id;
	private SpecType type;
	private Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
	
	
	
	
	public String getID() {
		return this.id;
	}
	
	public String getSpecType() {
		return this.type.getType();
	}
	
	public String getSpecTypeName() {
		return this.type.getName();
	}
	
	public Map<String, AttributeValue> getAttributes() {
		return this.attributeValues;
	}
	
	public Object getAttribute(String attributeName) {
		return this.attributeValues.get(attributeName).getValue();
	}
	
	public boolean isREQ() {
		boolean temp = false;
		if(this.type.getType().equals(ReqIFConst.REQ)) {
			
			for(AttributeValue attValue: this.attributeValues.values()) {
				
				if(attValue.getDatatype().equals(ReqIFConst.BOOLEAN) && attValue.getName().contains(ReqIFConst.REQ)) {
					
					if((Boolean)attValue.getValue()) {
						return true;
						
					}else{
						return false;
					}
				}
			}
		}
		return temp;
	}
	
	public boolean isHeadline() {
		if(this.type.getType().equals(ReqIFConst.HEADLINE)) {
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isSubReq() {
		boolean temp = false;
		if(this.type.getType().equals(ReqIFConst.REQ)) {
			
			for(AttributeValue attValue: this.attributeValues.values()) {
				
				if(attValue.getDatatype().equals(ReqIFConst.BOOLEAN) && attValue.getName().contains(ReqIFConst.SUB) && attValue.getName().contains(ReqIFConst.REQ)) {
					
					if((Boolean)attValue.getValue()) {
						return true;
						
					}else{
						return false;
					}
				}
			}
		}
		return temp;
	}
	
	public boolean isText() {
		if(!this.isREQ() && !this.isHeadline() && !this.isSubReq()) {
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
	public SpecObject(Node specObject, SpecType specType) {
		
		this.id = specObject.getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
		this.type = specType;
		
		if(			((Element)specObject).getElementsByTagName(ReqIFConst.VALUES).getLength() > 0
				&&	((Element)specObject).getElementsByTagName(ReqIFConst.VALUES).item(0).getChildNodes().getLength() > 0		) {
			
			NodeList attributeValues = ((Element)specObject).getElementsByTagName(ReqIFConst.VALUES).item(0).getChildNodes();
			for(int attval = 0; attval < attributeValues.getLength(); attval++) {
				
				Node attribute = attributeValues.item(attval);
				String attValNodeName = attribute.getNodeName();
				if(!attValNodeName.equals(ReqIFConst._TEXT)) {
					
					String attributeDefinitionRef = ((Element)attribute).getElementsByTagName(ReqIFConst.DEFINITION).item(0).getChildNodes().item(1).getTextContent();
					String attributeDefinitionName = specType.getAttributeDefinitions().get(attributeDefinitionRef).getName();
					String attributeValue;
					AttributeDefinition attributeDefinition = specType.getAttributeDefinition(attributeDefinitionRef);
					
					switch(attValNodeName.substring(attValNodeName.lastIndexOf("-")+1)) {
					
						case ReqIFConst.BOOLEAN:		if(attribute.getAttributes().getNamedItem(ReqIFConst.THE_VALUE) !=null) {
															attributeValue = attribute.getAttributes().getNamedItem(ReqIFConst.THE_VALUE).getTextContent();
														}else{
															attributeValue = "";
														}
														this.attributeValues.put(attributeDefinitionName, new AttributeValueBoolean(attributeValue, attributeDefinition));
														break;
							
						case ReqIFConst.INTEGER:		if(attribute.getAttributes().getNamedItem(ReqIFConst.THE_VALUE) !=null) {
															attributeValue = attribute.getAttributes().getNamedItem(ReqIFConst.THE_VALUE).getTextContent();
														}else{
															attributeValue = "";
														}
														this.attributeValues.put(attributeDefinitionName, new AttributeValueInteger(attributeValue, attributeDefinition));
														break;
							
						case ReqIFConst.STRING:			if(attribute.getAttributes().getNamedItem(ReqIFConst.THE_VALUE) !=null) {
															attributeValue = attribute.getAttributes().getNamedItem(ReqIFConst.THE_VALUE).getTextContent();
														}else{
															attributeValue = "";
														}
														this.attributeValues.put(attributeDefinitionName, new AttributeValueString(attributeValue, attributeDefinition));
														break;
							
						case ReqIFConst.ENUMERATION:	String enumValueRef = ((Element)attribute).getElementsByTagName(ReqIFConst.VALUES).item(0).getChildNodes().item(1).getTextContent(); 
														attributeValue = specType.getEnumValueName(enumValueRef);
														this.attributeValues.put(attributeDefinitionName, new AttributeValueEnumeration(attributeValue, attributeDefinition));
														break;
							
						case ReqIFConst.XHTML:			this.attributeValues.put(attributeDefinitionName, new AttributeValueXHTML(attribute, attributeDefinition));
														break;
												
						default:						break;
					}
				}
			}
		}
		
		if(this.attributeValues.size() < specType.getAttributeDefinitions().size()) {
			
			for(AttributeDefinition attributeDefinition: specType.getAttributeDefinitions().values()) {
				
				if(!this.attributeValues.containsKey(attributeDefinition.getName())) { 
					
					switch(attributeDefinition.getType().getType()) {
					
						case ReqIFConst.BOOLEAN:		this.attributeValues.put(attributeDefinition.getName(), new AttributeValueBoolean(attributeDefinition.getDefaultValue(), attributeDefinition));
												break;
						
						case ReqIFConst.INTEGER:		this.attributeValues.put(attributeDefinition.getName(), new AttributeValueInteger(attributeDefinition.getDefaultValue(), attributeDefinition));
												break;
						
						case ReqIFConst.STRING:		this.attributeValues.put(attributeDefinition.getName(), new AttributeValueString(attributeDefinition.getDefaultValue(), attributeDefinition));
												break;
						
						case ReqIFConst.ENUMERATION:	this.attributeValues.put(attributeDefinition.getName(), new AttributeValueEnumeration(attributeDefinition.getDefaultValue(), attributeDefinition));
												break;
						
						case ReqIFConst.XHTML:		this.attributeValues.put(attributeDefinition.getName(), new AttributeValueXHTML(attributeDefinition.getDefaultValue(), attributeDefinition));
												break;
											
						default:				break;
					}
				}
			}
		}
		
	}

}
