package de.uni_stuttgart.ils.reqif.specification;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.uni_stuttgart.ils.reqif.attributes.AttributeDefinition;
import de.uni_stuttgart.ils.reqif.attributes.AttributeDefinitionBoolean;
import de.uni_stuttgart.ils.reqif.attributes.AttributeDefinitionEnumeration;
import de.uni_stuttgart.ils.reqif.attributes.AttributeDefinitionInteger;
import de.uni_stuttgart.ils.reqif.attributes.AttributeDefinitionString;
import de.uni_stuttgart.ils.reqif.attributes.AttributeDefinitionXHTML;
import de.uni_stuttgart.ils.reqif.datatypes.Datatype;
import de.uni_stuttgart.ils.reqif.datatypes.DatatypeEnumeration;
import de.uni_stuttgart.ils.reqif.reqif.ReqIFConst;

public class SpecType {
	
	
	private Map<String, AttributeDefinition> attributeDefinitions = new LinkedHashMap<String, AttributeDefinition>();
	private String id;
	protected String name;
	protected String type;
	
	
	
	
	public String getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public Map<String, AttributeDefinition> getAttributeDefinitions() {
		return this.attributeDefinitions;
	}
	
	public AttributeDefinition getAttributeDefinition(String id) {
		return this.attributeDefinitions.get(id);
	}
	
	public String getEnumValueName(String id) {
		
		for(AttributeDefinition attributeDefinition: this.attributeDefinitions.values()) {
			
			if(attributeDefinition.getDataType().getClass().equals(DatatypeEnumeration.class)) {
				
				if(((DatatypeEnumeration)attributeDefinition.getDataType()).getEnumValueName(id) != null) {
					
					return ((DatatypeEnumeration)attributeDefinition.getDataType()).getEnumValueName(id);
				}
			}
		}
		return "";
	}
	
	public String getEnumValueKey(String id) {
		
		for(AttributeDefinition attributeDefinition: this.attributeDefinitions.values()) {
			
			if(attributeDefinition.getDataType().getClass().equals(DatatypeEnumeration.class)) {
				
				if(((DatatypeEnumeration)attributeDefinition.getDataType()).getEnumValueName(id) != null) {
					
					return ((DatatypeEnumeration)attributeDefinition.getDataType()).getEnumValueKey(id);
				}
			}
		}
		return "";
	}
	
	public String getEnumValueOtherContent(String id) {
		
		for(AttributeDefinition attributeDefinition: this.attributeDefinitions.values()) {
			
			if(attributeDefinition.getDataType().getClass().equals(DatatypeEnumeration.class)) {
				
				if(((DatatypeEnumeration)attributeDefinition.getDataType()).getEnumValueName(id) != null) {
					
					return ((DatatypeEnumeration)attributeDefinition.getDataType()).getEnumValueOtherContent(id);
				}
			}
		}
		return "";
	}
	
	
	
	
	public SpecType(Node specType, Map<String, Datatype> dataTypes) {
		
		this.id = specType.getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
		this.name = specType.getAttributes().getNamedItem(ReqIFConst.LONG_NAME).getTextContent();
		this.type = ReqIFConst.UNDEFINED;
		
		if(specType.getChildNodes().getLength() > 0) {
			NodeList attributeDefinitions = specType.getChildNodes().item(1).getChildNodes();

			for(int specatt = 0; specatt < attributeDefinitions.getLength(); specatt++) {
				
				Node attributeDefinition = attributeDefinitions.item(specatt);
				String attDefNodeName = attributeDefinition.getNodeName();
				if(!attDefNodeName.equals(ReqIFConst._TEXT)) {
					
					String attDefID = attributeDefinition.getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
					
					switch(attDefNodeName.substring(attDefNodeName.lastIndexOf("-")+1)) {
					
						case ReqIFConst.BOOLEAN:		this.attributeDefinitions.put(attDefID, new AttributeDefinitionBoolean(attributeDefinition, dataTypes));
														break;
											
						case ReqIFConst.INTEGER:		this.attributeDefinitions.put(attDefID, new AttributeDefinitionInteger(attributeDefinition, dataTypes));
														break;
											
						case ReqIFConst.STRING:			this.attributeDefinitions.put(attDefID, new AttributeDefinitionString(attributeDefinition, dataTypes));
														break;
											
						case ReqIFConst.ENUMERATION:	this.attributeDefinitions.put(attDefID, new AttributeDefinitionEnumeration(attributeDefinition, dataTypes));
														break;
											
						case ReqIFConst.XHTML:			this.attributeDefinitions.put(attDefID, new AttributeDefinitionXHTML(attributeDefinition, dataTypes));
														break;
											
						default:						this.attributeDefinitions.put(attDefID, new AttributeDefinition(attributeDefinition, dataTypes));
														break;
					}
				}
			}
		}
	}

}
