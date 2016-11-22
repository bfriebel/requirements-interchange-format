package de.uni_stuttgart.ils.reqif4j.datatypes;

import de.uni_stuttgart.ils.reqif4j.reqif.ReqIFConst;

public class DatatypeString extends Datatype {
	
	
	private int maxLength;
	
	
	
	
	public int getMaxLength() {
		return this.maxLength;
	}
	
	
	
	
	public DatatypeString(String id, String name, String maxLength) {
		super(id, name, ReqIFConst.STRING);
		
		this.maxLength = Integer.parseInt(maxLength);
	}

}
