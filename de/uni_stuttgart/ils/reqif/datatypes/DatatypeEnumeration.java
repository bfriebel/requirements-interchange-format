package de.uni_stuttgart.ils.reqif.datatypes;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.uni_stuttgart.ils.reqif.reqif.ReqIFConst;
public class DatatypeEnumeration extends Datatype {
	
	
	private Map<String, DatatypeEnumerationValue> enumValues = new LinkedHashMap<String, DatatypeEnumerationValue>();
	private String id;
	private String name;
	
	
	
	
	public String getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEnumValueName(String id) {
		return this.enumValues.get(id).getName();
	}
	
	public String getEnumValueKey(String id) {
		return this.enumValues.get(id).getKey();
	}
	
	public String getEnumValueOtherContent(String id) {
		return this.enumValues.get(id).getOtherContent();
	}
	
	
	

	public DatatypeEnumeration(String id, String name, Node enumeration) {
		super(id, name, ReqIFConst.ENUMERATION);
		
		this.id = id;
		this.name = name;
		
		NodeList values = enumeration.getChildNodes().item(1).getChildNodes();
		for(int value = 0; value < values.getLength(); value++) {
			
			if(!values.item(value).getNodeName().equals(ReqIFConst._TEXT)) {
				
				String identifier = values.item(value).getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
				String longName = values.item(value).getAttributes().getNamedItem(ReqIFConst.LONG_NAME).getTextContent();
				String key = values.item(value).getChildNodes().item(1).getChildNodes().item(1).getAttributes().getNamedItem(ReqIFConst.KEY).getTextContent();
				String otherContent;
				if (values.item(value).getChildNodes().item(1).getChildNodes().item(1).getAttributes().getNamedItem(ReqIFConst.OTHER_CONTENT) != null) {
					otherContent = values.item(value).getChildNodes().item(1).getChildNodes().item(1).getAttributes().getNamedItem(ReqIFConst.OTHER_CONTENT).getTextContent();
				}else{
					otherContent = "";
				}
				
				enumValues.put(identifier, new DatatypeEnumerationValue(identifier, longName, key, otherContent));
			}
		}
	}

}
