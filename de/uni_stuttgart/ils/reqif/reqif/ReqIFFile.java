package de.uni_stuttgart.ils.reqif.reqif;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReqIFFile {
	
	
	protected String path;
	protected String name;
	protected int numberOfReqIFDocuments;
	protected Map<String, InputStream> picturesIS;
	protected Map<String, Map<String, InputStream>> picturesInReqIFDocument = new LinkedHashMap<String, Map<String, InputStream>>();
	protected Map<String, ReqIFDocument> reqifDocuments = new LinkedHashMap<String, ReqIFDocument>();
	
	
	
	
	public String getPath() {
		return this.path;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getReqIFDocumentsCount() {
		return this.numberOfReqIFDocuments;
	}
	
	public Map<String, ReqIFDocument> getReqIFDocuments() {
		return this.reqifDocuments;
	}
	
	public List<ReqIFDocument> getReqIFDocumentsList() {
		List<ReqIFDocument> reqIFDocuments= new ArrayList<ReqIFDocument>();
		reqIFDocuments.addAll(this.reqifDocuments.values());
		return reqIFDocuments;
	}
	
	public Map<String, InputStream> getPicturesInputStreams(String reqifDocumentName) {
		return this.picturesInReqIFDocument.get(reqifDocumentName.split("\\.")[0]);
	}
	
	public InputStream getPictureInputStream(String reqifDocumentName, String pictureFileName) {
		return this.picturesInReqIFDocument.get(reqifDocumentName).get(pictureFileName);
	}
	
	
}
