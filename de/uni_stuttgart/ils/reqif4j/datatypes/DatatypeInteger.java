package de.uni_stuttgart.ils.reqif4j.datatypes;

import de.uni_stuttgart.ils.reqif4j.reqif.ReqIFConst;

public class DatatypeInteger extends Datatype {
	
	
	private int max;
	private int min;
	
	
	
	
	public int getMax() {
		return this.max;
	}
	
	public int getMin() {
		return this.min;
	}
	
	
	
	
	public DatatypeInteger(String id, String name, String min, String max) {
		super(id, name, ReqIFConst.INTEGER);
		
		this.min = Integer.parseInt(min);
		this.max = Integer.parseInt(max);
	}

}
