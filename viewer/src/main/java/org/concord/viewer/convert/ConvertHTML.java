package org.concord.viewer.convert;

import java.io.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ConvertHTML implements ConvertFile {

	public String convert(String inputPath , String fileName) throws IOException, InterruptedException 
	{
		 Runtime rt = Runtime.getRuntime();
		 Process p=rt.exec("soffice --headless --convert-to html --outdir "+outputPath+" " +inputPath);
		 p.waitFor();
		 String TempFileName=null;
		 
		 TempFileName=fileName.substring(0,fileName.lastIndexOf("."));
		 TempFileName=TempFileName+".html";
				 
				 return outputPath+TempFileName;

	}
	public void display (String outputPath)
	{
		
		
	}

}