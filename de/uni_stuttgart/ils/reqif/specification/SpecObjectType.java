package de.uni_stuttgart.ils.reqif.specification;

import java.util.Map;

import org.w3c.dom.Node;

import de.uni_stuttgart.ils.reqif.datatypes.Datatype;
import de.uni_stuttgart.ils.reqif.reqif.ReqIFConst;

public class SpecObjectType extends SpecType {
	
	
	public SpecObjectType(Node specType, Map<String, Datatype> dataTypes) {
		super(specType, dataTypes);
		
		this.type = ReqIFConst.SPEC_OBJECT_TYPE;
	}

}
