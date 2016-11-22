package de.uni_stuttgart.ils.reqif4j.attributes;

public class AttributeValueInteger extends AttributeValue {
	
	
	public AttributeValueInteger(String value, AttributeDefinition type) {
		super(value, type);
		
		this.value = Integer.parseInt(value);
	}

}
