package de.uni_stuttgart.ils.reqif4j.specification;

import java.util.Map;

import org.w3c.dom.Node;

import de.uni_stuttgart.ils.reqif4j.datatypes.Datatype;
import de.uni_stuttgart.ils.reqif4j.reqif.ReqIFConst;

public class SpecRelationType extends SpecType {
	
	
	public SpecRelationType(Node specType, Map<String, Datatype> dataTypes) {
		super(specType, dataTypes);
		
		this.type = ReqIFConst.SPEC_RELATION_TYPE;
	}

}
