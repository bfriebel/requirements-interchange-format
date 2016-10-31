package de.uni_stuttgart.ils.reqif.reqif;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ReqIFDocument {
	
	
	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder builder;
	private Document reqifDocument;
	
	protected String filePath;
	private String fileName;
	
	private ReqIFHeader header;
	private ReqIFCoreContent content;
    
    
    public String getFilePath() {
    	return this.filePath;
    }
    
    public String getFileName() {
    	return this.fileName;
    }
	
	public ReqIFHeader getHeader() {
    	return this.header;
    }
    
    public ReqIFCoreContent getCoreContent() {
    	return this.content;
    }
	
    
    
    
	public ReqIFDocument(String filePath) throws FileNotFoundException {
		
		this.filePath = filePath;
		this.fileName = this.filePath.substring(filePath.lastIndexOf(System.getProperty("file.separator"))+1);
		
		try {
			
			
			/*//
			long reqifStart = System.currentTimeMillis();
			long parseStart = System.currentTimeMillis();
			//*/
			
			
			this.builder = factory.newDocumentBuilder();
			
			this.reqifDocument = this.builder.parse(this.filePath);
				
			/*//
			long parseStop = System.currentTimeMillis();
			System.out.println("+––––––––––––––––––––––––––––––+");
			System.out.println("|             Path             |");
			System.out.println("+––––––––––––––––––––––––––––––+");
			System.out.println("| XML-Parse-Time:\t" + (parseStop-parseStart) + " ms |");
			//*/
					
			
			if(this.reqifDocument.getElementsByTagName(ReqIFConst.THE_HEADER).getLength() > 0 && this.reqifDocument.getElementsByTagName(ReqIFConst.THE_HEADER).item(0).hasChildNodes()) {
				header = new ReqIFHeader((Element)this.reqifDocument.getElementsByTagName(ReqIFConst.THE_HEADER).item(0));
			}
			content = new ReqIFCoreContent((Element)this.reqifDocument.getElementsByTagName(ReqIFConst.CORE_CONTENT).item(0));
			
			
			/*//
			long reqifStop = System.currentTimeMillis();
			System.out.println("| ReqIF-Creation-Time:\t" + (reqifStop-reqifStart) + " ms |");
			//*/
			
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public ReqIFDocument(InputStream is, String filePath) throws FileNotFoundException {
		
		this.filePath = filePath;
		this.fileName = this.filePath.substring(filePath.lastIndexOf(System.getProperty("file.separator"))+1);
		
		try {
			
			
			/*//
			long reqifStart = System.currentTimeMillis();
			long parseStart = System.currentTimeMillis();
			//*/
			
			
			this.builder = factory.newDocumentBuilder();
			
			this.reqifDocument = this.builder.parse(is);
			
			
			/*//
			long parseStop = System.currentTimeMillis();
			System.out.println("+––––––––––––––––––––––––––––––+");
			System.out.println("|              IS              |");
			System.out.println("+––––––––––––––––––––––––––––––+");
			System.out.println("| XML-Parse-Time:\t " + (parseStop-parseStart) + " ms |");
			//*/
			
			if(this.reqifDocument.getElementsByTagName(ReqIFConst.THE_HEADER).getLength() > 0 && this.reqifDocument.getElementsByTagName(ReqIFConst.THE_HEADER).item(0).hasChildNodes()) {
				header = new ReqIFHeader((Element)this.reqifDocument.getElementsByTagName(ReqIFConst.THE_HEADER).item(0));
			}
			content = new ReqIFCoreContent((Element)this.reqifDocument.getElementsByTagName(ReqIFConst.CORE_CONTENT).item(0));
			
			
			/*//
			long reqifStop = System.currentTimeMillis();
			System.out.println("| ReqIF-Creation-Time:\t " + (reqifStop-reqifStart) + " ms |");
			//*/
			
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public ReqIFDocument(InputStream is, String zipFilePath, String fileName) {
		
		this.filePath = zipFilePath;		//		TODO
		this.fileName = fileName;
		
		try {
			
			this.builder = factory.newDocumentBuilder();
			
			this.reqifDocument = this.builder.parse(is);
			
			if(this.reqifDocument.getElementsByTagName(ReqIFConst.THE_HEADER).getLength() > 0 && this.reqifDocument.getElementsByTagName(ReqIFConst.THE_HEADER).item(0).hasChildNodes()) {
				header = new ReqIFHeader((Element)this.reqifDocument.getElementsByTagName(ReqIFConst.THE_HEADER).item(0));
			}
			content = new ReqIFCoreContent((Element)this.reqifDocument.getElementsByTagName(ReqIFConst.CORE_CONTENT).item(0));
			
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}
