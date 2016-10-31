package de.uni_stuttgart.ils.reqif.datatypes;

import de.uni_stuttgart.ils.reqif.reqif.ReqIFConst;

public class DatatypeBoolean extends Datatype {
	
	private String id;
	private String name;
	
	
	
	
	public String getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	
	
	public DatatypeBoolean(String id, String name) {
		super(id, name, ReqIFConst.BOOLEAN);
		
		this.id = id;
		this.name = name;
	}

}
