package de.uni_stuttgart.ils.reqif4j.reqif;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.uni_stuttgart.ils.reqif4j.datatypes.Datatype;
import de.uni_stuttgart.ils.reqif4j.datatypes.DatatypeBoolean;
import de.uni_stuttgart.ils.reqif4j.datatypes.DatatypeEnumeration;
import de.uni_stuttgart.ils.reqif4j.datatypes.DatatypeInteger;
import de.uni_stuttgart.ils.reqif4j.datatypes.DatatypeString;
import de.uni_stuttgart.ils.reqif4j.datatypes.DatatypeXHTML;
import de.uni_stuttgart.ils.reqif4j.specification.SpecHierarchy;
import de.uni_stuttgart.ils.reqif4j.specification.SpecObject;
import de.uni_stuttgart.ils.reqif4j.specification.SpecObjectType;
import de.uni_stuttgart.ils.reqif4j.specification.SpecRelationType;
import de.uni_stuttgart.ils.reqif4j.specification.SpecType;
import de.uni_stuttgart.ils.reqif4j.specification.Specification;
import de.uni_stuttgart.ils.reqif4j.specification.SpecificationType;

public class ReqIFCoreContent {
	
	
	private Map<String, Datatype> dataTypes = new LinkedHashMap<String, Datatype>();
	private Map<String, SpecType> specTypes = new LinkedHashMap<String, SpecType>();
	private Map<String, SpecObject> specObjects = new LinkedHashMap<String, SpecObject>();
	private Map<String, Specification> specifications = new LinkedHashMap<String, Specification>();
	
	
	
	
	public Map<String, Datatype> getDatatypes() {
		return this.dataTypes;
	}
	
	public Datatype getDatatype(String id) {
		return this.dataTypes.get(id);
	}
	
	public Map<String, SpecType> getSpecTypes() {
		return this.specTypes;
	}
	
	public SpecType getSpecType(String id) {
		return this.specTypes.get(id);
	}
	
	public Map<String, SpecObject> getSpecObjects() {
		return this.specObjects;
	}
	
	public SpecObject getSpecObject(String id) {
		return this.specObjects.get(id);
	}
	
	public Map<String, Specification> getSpecifications() {
		return this.specifications;
	}
	
	public Specification getSpecification(String id) {
		return this.specifications.get(id);
	}
	
	public List<Specification> getSpecificationsList() {
		List<Specification> specifications = new ArrayList<Specification>();
		for(Specification specification: this.specifications.values()) {
			specifications.add(specification);
		}
		
		return specifications;
	}
	
	public List<SpecHierarchy> getOrderedSpecHierarchyList() {
		
		List<SpecHierarchy> orderedSpecHierarchies = new ArrayList<SpecHierarchy>();
		for(Specification specification: this.specifications.values()) {
			orderedSpecHierarchies.addAll(specification.getAllSpecHierarchies());
		}
		return orderedSpecHierarchies;
	}
	
	
	
	
	public ReqIFCoreContent(Element coreContent) {
		
		
		if(coreContent.getElementsByTagName("DATATYPES").item(0).hasChildNodes()) {
			
			NodeList dataTypes = coreContent.getElementsByTagName("DATATYPES").item(0).getChildNodes();
			for(int datatype = 0; datatype<  dataTypes.getLength(); datatype++) {
				
				Node dataType = dataTypes.item(datatype);
				String dataTypeNodeName = dataType.getNodeName();
				if(!dataTypeNodeName.equals(ReqIFConst._TEXT)) {
					
					String dataTypeID = dataType.getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
					String dataTypeName = dataType.getAttributes().getNamedItem(ReqIFConst.LONG_NAME).getTextContent();
					
					switch(dataTypeNodeName.substring(dataTypeNodeName.lastIndexOf("-")+1)) {
					
						case ReqIFConst.BOOLEAN: 		this.dataTypes.put(dataTypeID, new DatatypeBoolean(dataTypeID, dataTypeName));
														break;
										
						case ReqIFConst.INTEGER:	 	String min = dataType.getAttributes().getNamedItem(ReqIFConst.MIN).getTextContent();
														String max = dataType.getAttributes().getNamedItem(ReqIFConst.MAX).getTextContent();
														this.dataTypes.put(dataTypeID, new DatatypeInteger(dataTypeID, dataTypeName, min, max));
														break;	
										
						case ReqIFConst.STRING: 		String maxLength = dataType.getAttributes().getNamedItem(ReqIFConst.MAX_LENGTH).getTextContent();
														this.dataTypes.put(dataTypeID, new DatatypeString(dataTypeID, dataTypeName, maxLength));
														break;
										
						case ReqIFConst.ENUMERATION: 	Node enumeration = dataType;
														this.dataTypes.put(dataTypeID, new DatatypeEnumeration(dataTypeID, dataTypeName, enumeration));
														break;
						
						case ReqIFConst.XHTML: 			this.dataTypes.put(dataTypeID, new DatatypeXHTML(dataTypeID, dataTypeName));
														break;
											
						default:						this.dataTypes.put(null, new Datatype(dataTypeID, dataTypeName, ReqIFConst.UNDEFINED));
														break;
					}
				}
			}
		}
		
		
		
		
		if(coreContent.getElementsByTagName(ReqIFConst.SPEC_TYPES).item(0).hasChildNodes()) {
			
			NodeList specTypes = coreContent.getElementsByTagName(ReqIFConst.SPEC_TYPES).item(0).getChildNodes();
			for(int spectype = 0; spectype < specTypes.getLength(); spectype++) {
				
				Node specType = specTypes.item(spectype);
				String specTypeNodeName = specType.getNodeName();
				if(!specTypeNodeName.equals(ReqIFConst._TEXT)) {
					
					String specTypeID = specType.getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
					
					switch(specTypeNodeName) {
					
						case ReqIFConst.SPECIFICATION_TYPE: this.specTypes.put(specTypeID, new SpecificationType(specType, this.dataTypes));
															break;
					
						case ReqIFConst.SPEC_OBJECT_TYPE: 	this.specTypes.put(specTypeID, new SpecObjectType(specType, this.dataTypes));
															break;
					
						case ReqIFConst.SPEC_RELATION_TYPE: this.specTypes.put(specTypeID, new SpecRelationType(specType, this.dataTypes));
															break;
						
						default:							this.specTypes.put(specTypeID, new SpecType(specType, this.dataTypes));
															break;
					}
				}
			}
		}
		
		
		
		
		if(coreContent.getElementsByTagName(ReqIFConst.SPEC_OBJECT).getLength() > 0) {
					
			NodeList specObjects = coreContent.getElementsByTagName(ReqIFConst.SPEC_OBJECT);
			for(int specobj = 0; specobj < specObjects.getLength(); specobj++) {
				
				Node specObj = specObjects.item(specobj);
				String specObjID = specObj.getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
				String specObjTypeRef = ((Element)specObj).getElementsByTagName(ReqIFConst.SPEC_OBJECT_TYPE_REF).item(0).getTextContent();
				
				this.specObjects.put(specObjID, new SpecObject(specObj, this.specTypes.get(specObjTypeRef)));
			}
		}
		
		
		
		
		if(coreContent.getElementsByTagName(ReqIFConst.SPECIFICATION).getLength() > 0) {
			
			NodeList specifications = coreContent.getElementsByTagName(ReqIFConst.SPECIFICATION);
			for(int spec = 0; spec < specifications.getLength(); spec++) {
				
				Node specification = specifications.item(spec);
				String specID = specification.getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
				String specTypeRef = ((Element)specification).getElementsByTagName(ReqIFConst.SPECIFICATION_TYPE_REF).item(0).getTextContent();
				
				this.specifications.put(specID, new Specification(specification, this.specTypes.get(specTypeRef), this.specObjects));
			}
		}
		
		
	}

}
