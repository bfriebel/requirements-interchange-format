package de.uni_stuttgart.ils.reqif.reqif;

import java.io.InputStream;
import java.util.HashMap;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

public class ReqIFz extends ReqIFFile {
	
	
	public ReqIFz(String filePath) {
		
		try {
			
			ZipFile zipFile = new ZipFile(filePath);
			
			this.path = zipFile.getFile().getPath();
			this.name = zipFile.getFile().getName();
			
			for(Object object: zipFile.getFileHeaders()) {
				
				FileHeader fileHeader = (FileHeader) object;
				
				if(fileHeader != null && fileHeader.getFileName().endsWith("reqif")) {
					
					this.numberOfReqIFDocuments++;
					FileHeader picturesFolderFileHeader = zipFile.getFileHeader(fileHeader.getFileName().split("\\.")[0]);
					if(picturesFolderFileHeader != null) {
						
						picturesIS = new HashMap<String, InputStream>();
						for(Object obj2: zipFile.getFileHeaders()) {
							
							FileHeader pictureFileHeader = (FileHeader) obj2;
							String pictureFileName = pictureFileHeader.getFileName().substring(pictureFileHeader.getFileName().lastIndexOf(System.getProperty("file.separator")));
							if( pictureFileName.endsWith("png")   ||
								pictureFileName.endsWith("jpeg")  ||
								pictureFileName.endsWith("jpg")		  ) {
								
								this.picturesIS.put(pictureFileName, zipFile.getInputStream(pictureFileHeader));
							}
						}
						this.picturesInReqIFDocument.put(fileHeader.getFileName().split(".")[0], this.picturesIS);
					}
					this.reqifDocuments.put(fileHeader.getFileName(), new ReqIFDocument(zipFile.getInputStream(fileHeader), zipFile.getFile().getPath(), fileHeader.getFileName()));
				}
			}
			
		} catch (ZipException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
