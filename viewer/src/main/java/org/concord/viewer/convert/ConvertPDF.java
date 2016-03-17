package org.concord.viewer.convert;


import java.io.IOException;

public class ConvertPDF implements ConvertFile {

	public String convert(String inputPath,String fileName) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		 Runtime rt = Runtime.getRuntime();
			 Process p=rt.exec("soffice --headless --convert-to pdf --outdir "+outputPath+" " +inputPath);
			 p.waitFor();
			 String TempFileName=null;
			 
			 TempFileName=fileName.substring(0,fileName.lastIndexOf("."));
			 TempFileName=TempFileName+".pdf";
			 System.out.println("converted");
					 return "/converted/"+TempFileName;
	}
	public void display (String outputPath)
	{
		
		
	}

}