package de.uni_stuttgart.ils.reqif.datatypes;

import de.uni_stuttgart.ils.reqif.reqif.ReqIFConst;

public class DatatypeXHTML extends Datatype {
	
	
	private String id;
	private String name;
	
	
	
	
	public String getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	
	
	public DatatypeXHTML(String id, String name) {
		super(id, name, ReqIFConst.XHTML);
		
		this.id = id;
		this.name = name;
	}

}
