package org.concord.viewer.getfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import org.concord.viewer.JsonResponse;


import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
//import org.apache.commons.vfs2.FileName;
//import org.concord.viewer.convert.ConvertHTML;
import org.concord.viewer.convert.ConvertNone;
import org.concord.viewer.convert.ConvertPDF;
import org.concord.viewer.convert.ConvertPSD;
import org.concord.viewer.convert.ForCsv;
import org.concord.viewer.convert.ForXls;
import org.concord.viewer.convert.ForXlsx;
import org.concord.viewer.typecheck.TypeCheck;
//import org.eclipse.core.resources.IProject;
//import org.eclipse.core.resources.IResource;

/*
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;*/
@Path("/send")
public class Getfile {
	
	String type;
	/*public static void e("current dir = " + dir);rror()
	{
		//error handling code
	}*/
	//file store on server
	//public String resultFilePath =null;
	private final String FOLDER_PATH = "/home/rucha/git/viewer/src/main/webapp/uploads/";
	@Path("/uploading")
	@POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
	
    public String uploadFile(@FormDataParam("upl") InputStream Inputfile, @FormDataParam("upl") FormDataContentDisposition fileDisposition) throws IOException, InterruptedException 
	{
		
		/*final String dir = System.getProperty("user.dir");
	    System.out.println("current directory"+dir);*/
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
            System.out.println("converted file path "+convertedFilePath);
            //JsonResponse jsonresponse = new JsonResponse(type,convertedFilePath);
           /* IProject project = root.getProject("viewer");
            project.refreshLocal(IResource.DEPTH_INFINITE, null);*/
          
        return convertedFilePath;
    }
/*	@Path("/view")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sendPath(){
System.out.println(resultFilePath);
		return resultFilePath;}*/
	
	public String call(File inputFile,String fileName) throws IOException, InterruptedException
	{
		String inputPath=inputFile.getAbsolutePath();
		String outputPath="";
		
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
   	    	String op1=c.convert(inputPath,fileName); 
   	    	outputPath="FrontViewer/viewer.html?file=.."+op1;
   	    	break;
   	    	
   	    //xls, xlsx, csv,ods
   	    case "text/csv": //csv
   	    	ForCsv cv = new ForCsv();
   	    	String op4=cv.convert(inputPath, fileName);
   	    	outputPath = "FrontViewer/iviewer.html?file=.."+op4 ;
   	    	break;
   	    case "application/vnd.ms-excel": //xls
   	    	ForXls xls = new ForXls();
   	    	String op3=xls.convert(inputPath, "/home/rucha/git/viewer/src/main/webapp/converted/");
   	    	outputPath = "FrontViewer/iviewer.html?file=.."+op3;
   	    	break;
   	    case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet": //xlsx
   	    	ForXlsx xlsx = new ForXlsx();
   	    	String op5=xlsx.convert(inputPath,"/home/rucha/git/viewer/src/main/webapp/converted/");
   	    	outputPath="FrontViewer/iviewer.html?file=.."+op5;
   	    	break;
   	    case "application/vnd.oasis.opendocument.spreadsheet": //ods
   	    	//ConvertHTML h = new ConvertHTML();
   	    	
   	    	//String op1=h.convert(inputPath,fileName);
   	    	//outputPath= op1 + "1";
   	    	//outputPath=h.convert(inputPath, fileName);
   	    	
   	    break;
   	    //website
   	    case "application/xhtml+xml": //xhtml
   	    case "text/html":  //html
   	    case "application/json":  //json
   	    case "text/plain":  //txt
   	    case "text/css":  //css
   	    case "application/xml": //xml
   	    case "text/x-c":
   	    case "text/x-java-source"://java
   	    case "text/javascript":
   	  case "text/ecmascript":	
   	  case	"text/x-script.phyton":
   	    	
   	    //images
   	     ConvertNone f =new ConvertNone();
		 String op2=f.convert(inputPath,fileName);
	    	outputPath= "FrontViewer/iviewer.html?file=../"+op2;
   	    	
	    	break;
   	 
   	    case "image/x-ms-bmp": //bmp
   	    case "image/jpeg":  //jpg
   	    case "image/jpg":
   	    case "image/png": //png
   	    case "image/gif": //gif
   	    
		 ConvertNone n =new ConvertNone();
		 String op6=n.convert(inputPath,fileName);
	    	outputPath= "FrontViewer/iviewer.html?file=../"+op6+"#1";
		
		break;
		//PDF
    	   case "application/pdf":
     	    case "application/x-pdf":
     	    	ConvertNone n1 =new ConvertNone();
     			 String op7=n1.convert(inputPath,fileName);
     			outputPath="FrontViewer/viewer.html?file=../"+op7;
       	    	break;
     	    	
   	    case "application/octet-stream":
   	    case "image/vnd.adobe.photoshop":
   	    	ConvertPSD psd= new ConvertPSD();
   	    	String op8=psd.convert(inputPath, fileName);
   	    	outputPath="FrontViewer/iviewer.html?file=../"+op8+"#1";
   	    break;
   	    default: 
   	    	outputPath="/ErrorMessage.html";
   	    	//error();
		}
		return outputPath;
	
	}

}



