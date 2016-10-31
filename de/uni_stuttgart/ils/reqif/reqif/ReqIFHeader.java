package de.uni_stuttgart.ils.reqif.reqif;

import org.w3c.dom.Element;

public class ReqIFHeader {
	
	
	private String id;
	private String author = "";
	private String title = "";
	private String toolID;
	private String sourceToolID = "";
	private String reqifVersion = "";
	//private String comment = "";
	private String creationDate = "";
	
	
	
	
	public String getID() {
		return this.id;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getToolID() {
		return this.toolID;
	}
	
	public String getSourceToolID() {
		return this.sourceToolID;
	}
	
	public String getReqIFVersion() {
		return this.reqifVersion;
	}
	
	/*public String getComment() {
		return this.comment;
	}*/
	
	public String getCreationDate() {
		return this.creationDate;
	}
	
	
	
	
	public ReqIFHeader(Element theHeader) {
		
		this.id = theHeader.getElementsByTagName(ReqIFConst.REQ_IF_HEADER).item(0).getAttributes().getNamedItem(ReqIFConst.IDENTIFIER).getTextContent();
		this.toolID = theHeader.getElementsByTagName(ReqIFConst.REQ_IF_TOOL_ID).item(0).getTextContent();
		
		if(theHeader.getElementsByTagName(ReqIFConst.SOURCE_TOOL_ID).getLength() > 0) {
			this.sourceToolID = theHeader.getElementsByTagName(ReqIFConst.SOURCE_TOOL_ID).item(0).getTextContent();
		}
		if(theHeader.getElementsByTagName(ReqIFConst.REQ_IF_VERSION).getLength() > 0) {
			this.reqifVersion = theHeader.getElementsByTagName(ReqIFConst.REQ_IF_VERSION).item(0).getTextContent();
		}
		if(theHeader.getElementsByTagName(ReqIFConst.COMMENT).getLength() > 0) {
			//this.comment = theHeader.getElementsByTagName(ReqIFConst.COMMENT).item(0).getTextContent();
			this.author = theHeader.getElementsByTagName(ReqIFConst.COMMENT).item(0).getTextContent().split("Created by: ")[1];
		}
		if(theHeader.getElementsByTagName(ReqIFConst.CREATION_TIME).getLength() > 0) {
			String[] date = theHeader.getElementsByTagName(ReqIFConst.CREATION_TIME).item(0).getTextContent().split("T")[0].split("-");
			this.creationDate = date[2] + "." + date[1] + "." + date[0];
		}
		if(theHeader.getElementsByTagName(ReqIFConst.TITLE).getLength() > 0) {
			this.title = theHeader.getElementsByTagName(ReqIFConst.TITLE).item(0).getTextContent().replace("_Template", "");
		}
	}

}
