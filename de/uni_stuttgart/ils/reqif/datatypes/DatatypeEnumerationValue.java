package de.uni_stuttgart.ils.reqif.datatypes;

public class DatatypeEnumerationValue {
	
	
	private java.lang.String id;
	private java.lang.String name;
	private java.lang.String key;
	private java.lang.String otherContent;
	
	
	
	
	public String getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getOtherContent() {
		return this.otherContent;
	}
	
	
	
	
	public DatatypeEnumerationValue(String id, String name, String key, String otherContent) {
		
		this.id = id;
		this.name = name;
		this.key = key;
		this.otherContent = otherContent;
	}

}
