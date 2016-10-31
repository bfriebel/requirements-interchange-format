package de.uni_stuttgart.ils.reqif.attributes;

public class AttributeValueBoolean extends AttributeValue {
	
	
	public AttributeValueBoolean(String value, AttributeDefinition type) {
		super(value, type);
		
		if(value.toLowerCase().equals("true")) {
			this.value = true;
		}else{
			this.value = false;
		}
	}

}
