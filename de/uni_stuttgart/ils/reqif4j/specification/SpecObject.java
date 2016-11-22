package de.uni_stuttgart.ils.reqif4j.specification;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.uni_stuttgart.ils.reqif4j.attributes.AttributeDefinition;
import de.uni_stuttgart.ils.reqif4j.attributes.AttributeValue;
import de.uni_stuttgart.ils.reqif4j.attributes.AttributeValueBoolean;
import de.uni_stuttgart.ils.reqif4j.attributes.AttributeValueEnumeration;
import de.uni_stuttgart.ils.reqif4j.attributes.AttributeValueInteger;
import de.uni_stuttgart.ils.reqif4j.attributes.AttributeValueString;
import de.uni_stuttgart.ils.reqif4j.attributes.AttributeValueXHTML;
import de.uni_stuttgart.ils.reqif4j.reqif.ReqIFConst;

public class SpecObject {
	
	
	private String id;
	private SpecType specType;
	private String type;
	private Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
	
	
	
	
	public String getID() {
		return this.id;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getSpecType() {
		return this.specType.getType();
	}
	
	public String getSpecTypeName() {
		return this.specType.getName();
	}
	
	public Map<String, AttributeValue> getAttributes() {
		return this.attributeValues;
	}
	
	public Object getAttribute(String attributeName) {
		return this.attributeValues.get(attributeName).getValue();
	}
	
	public boolean isReq() {
		
		if(this.type.equals(ReqIFConst.REQ)) {
			
			for(AttributeValue attValue: this.attributeValues.values()) {
				
				if(attValue.getDatatype().equals(ReqIFConst.BOOLEAN) && attValue.getName().toLowerCase().contains(ReqIFConst.REQ.toLowerCase())) {
					
					if((Boolean)attValue.getValue()) {
						return true;
						
					}else{
						return false;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isHeadline() {
		
		if(this.type.equals(ReqIFConst.HEADLINE)) {
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isSubReq() {
		
		if(this.type.equals(ReqIFConst.SUB_REQ)) {
			
			for(AttributeValue attValue: this.attributeValues.values()) {
				
				if(attValue.getDatatype().equals(ReqIFConst.BOOLEAN) && attValue.getName().toLowerCase().contains(ReqIFConst.SUB.toLowerCase()) && attValue.getName().toLowerCase().contains(ReqIFConst.REQ.toLowerCase())) {
					
					if((Boolean)attValue.getValue()) {
						return true;
						
					}else{
						return false;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isText() {
		
		if(!this.isReq() && !this.isHeadline() && !this.isSubReq()) {
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
	public SpecObject(Node specObject, SpecType specType) {
		
		this.id = specObject.getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
		this.specType = specType;
		
		if(this.specType.getName().toLowerCase().contains(ReqIFConst.REQ.toLowerCase())) {
			
			if(this.specType.getName().toLowerCase().contains(ReqIFConst.SUB.toLowerCase())) {
				this.type = ReqIFConst.SUB_REQ;
			
			}else{
				this.type = ReqIFConst.REQ;
			}
		}else if(this.specType.getName().toLowerCase().contains(ReqIFConst.HEADLINE.toLowerCase())) {
			this.type = ReqIFConst.HEADLINE;
		
		}else{
			this.type = ReqIFConst.TEXT;
		}
		
		if(			((Element)specObject).getElementsByTagName(ReqIFConst.VALUES).getLength() > 0
				&&	((Element)specObject).getElementsByTagName(ReqIFConst.VALUES).item(0).hasChildNodes()		) {
			
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
					
					switch(attributeDefinition.getDataType().getType()) {
					
						case ReqIFConst.BOOLEAN:		this.attributeValues.put(attributeDefinition.getName(), new AttributeValueBoolean(attributeDefinition.getDefaultValue(), attributeDefinition));
														break;
						
						case ReqIFConst.INTEGER:		this.attributeValues.put(attributeDefinition.getName(), new AttributeValueInteger(attributeDefinition.getDefaultValue(), attributeDefinition));
														break;
						
						case ReqIFConst.STRING:			this.attributeValues.put(attributeDefinition.getName(), new AttributeValueString(attributeDefinition.getDefaultValue(), attributeDefinition));
														break;
						
						case ReqIFConst.ENUMERATION:	this.attributeValues.put(attributeDefinition.getName(), new AttributeValueEnumeration(attributeDefinition.getDefaultValue(), attributeDefinition));
														break;
						
						case ReqIFConst.XHTML:			this.attributeValues.put(attributeDefinition.getName(), new AttributeValueXHTML(attributeDefinition.getDefaultValue(), attributeDefinition));
														break;
											
						default:						break;
					}
				}
			}
		}
		
	}

}
