package de.uni_stuttgart.ils.reqif.datatypes;

public class Datatype {
	
	
	private String id;
	private String name;
	private String type;
	
	
	
	
	public String getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	
	
	
	public Datatype(String id, String name, String type) {
		
		this.id = id;
		this.name = name;
		this.type = type;
	}

}
