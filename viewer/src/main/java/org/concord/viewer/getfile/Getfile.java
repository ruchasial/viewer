package org.concord.viewer.getfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.apache.commons.vfs2.FileName;
import org.concord.viewer.convert.ConvertHTML;
import org.concord.viewer.convert.ConvertNone;
import org.concord.viewer.convert.ConvertPDF;
import org.concord.viewer.typecheck.TypeCheck;

@Path("/upload")
public class Getfile {
	
	
	/*public static void error()
	{
		//error handling code
	}*/
	//file store on server
	
	private final String FOLDER_PATH = "/home/rucha/workspace/proj/viewer/uploads/";
	
	
	@POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
	
    public String uploadFile(@FormDataParam("file") InputStream Inputfile, @FormDataParam("file") FormDataContentDisposition fileDisposition) throws IOException, InterruptedException 
	{
		
        OutputStream outpuStream = null;
        String fileName = fileDisposition.getFileName();
        System.out.println("File Name: " + fileName);
        String filePath = FOLDER_PATH + fileName;
         
            int read = 0;
            byte[] bytes = new byte[1024];
            outpuStream = new FileOutputStream(new File(filePath));
            while ((read = Inputfile.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
                }
            outpuStream.flush();
            outpuStream.close();
            
            
        //} finally {
            if(outpuStream != null){
               System.out.println("File Uploaded");
            }
            String convertedFilePath=call(new File(filePath),fileName);
        return convertedFilePath;
    }
	
	
	
	public String call(File inputFile,String fileName) throws IOException, InterruptedException
	{
		String inputPath=inputFile.getAbsolutePath();
		String outputPath="";
		String type;
		type = TypeCheck.check(inputFile);
		
		switch (type)
		{
		case "application/msword":  //doc
   	    case "application/vnd.openxmlformats-officedocument.wordprocessingml.document": //docx
   	    case "application/vnd.ms-word.document.macroenabled.12": //docm
   	    case "application/vnd.oasis.opendocument.text": //odt
   	    case "application/vnd.ms-powerpoint":   //ppt
   	    case "application/vnd.openxmlformats-officedocument.presentationml.presentation":  //pptx
   	    	ConvertPDF c= new ConvertPDF();
   	    	outputPath=c.convert(inputPath,fileName);
   	    	break;
   	    	//xls, xlsx, csv,ods
   	    case "text/csv": //csv
   	    case "application/vnd.ms-excel": //xls
   	    case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet": //xlsx
   	    case "application/vnd.oasis.opendocument.spreadsheet": //ods
   	    	ConvertHTML h = new ConvertHTML();
   	    	outputPath=h.convert(inputPath,fileName);
   	    	break;
   	    case "application/xhtml+xml": //xhtml
   	    case "text/html":  //html
   	    case "application/json":  //json
   	    case "text/plain":  //txt
   	    case "text/css":  //css
   	    case "application/xml": //xml
		 //images
   	    case "image/x-ms-bmp": //bmp
   	    case "image/jpeg":  //jpg
   	    case "image/png": //png
		 ConvertNone n =new ConvertNone();
		 outputPath=n.convert(inputPath,fileName);
		 break;
   	    default: 
   	    	//error();
		}
		return outputPath;
	
	}

}



