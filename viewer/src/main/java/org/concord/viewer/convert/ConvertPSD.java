package org.concord.viewer.convert;

import java.io.IOException;



public class ConvertPSD implements ConvertFile {

	@Override
	public String convert(String inputPath, String fileName) throws IOException, InterruptedException {
		Process p;
		Runtime rt = Runtime.getRuntime();
		String TempFileName=fileName.substring(0,fileName.lastIndexOf("."));
		p=rt.exec("convert "+inputPath+" "+"/home/rucha/git/viewer/src/main/webapp/converted/psd/"+TempFileName+".jpeg");
		
		// TODO Auto-generated method stub
		return ("converted/psd/"+TempFileName+"-0.jpeg");
	}

	@Override
	public void display(String outputPath) {
		// TODO Auto-generated method stub
		
	}

}
