package de.uni_stuttgart.ils.reqif.attributes;

public class AttributeValue {
	
	
	private String name;
	protected Object value;
	private AttributeDefinition type;
	
	
	
	
	public String getName() {
		return this.name;
	}
	
	public Object getValue() {
		return this.value;
	}
	
	public AttributeDefinition getAttributeDefinitionType() {
		return this.type;
	}
	
	public String getDatatype() {
		return this.type.getDataType().getType();
	}
	
	
	
	
	public AttributeValue(String value, AttributeDefinition type) {
		
		this.name = type.getName();
		this.value = value;
		this.type = type;
	}
	
	public AttributeValue(AttributeDefinition type) {
		
		this.name = type.getName();
		this.type = type;
	}

}
