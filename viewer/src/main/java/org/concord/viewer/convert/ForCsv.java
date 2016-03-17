package org.concord.viewer.convert;

import java.io.IOException;

public class ForCsv implements ConvertFile {

	@Override
	public String convert(String inputPath, String fileName) throws IOException, InterruptedException {
		 Runtime rt = Runtime.getRuntime();
		 Process p=rt.exec("soffice --headless --convert-to html --outdir "+outputPath+" " +inputPath);
		 p.waitFor();
		 String TempFileName=null;
		 
		 TempFileName=fileName.substring(0,fileName.lastIndexOf("."));
		 TempFileName=TempFileName+".html";
		 System.out.println("converted");
				 return "/converted/"+TempFileName;
	}

	@Override
	public void display(String outputPath) {
		// TODO Auto-generated method stub

	}

}
