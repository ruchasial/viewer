package org.concord.viewer.convert;

import java.io.IOException;

//rucha made a change
public interface ConvertFile {
	String inputPath = "";
	String outputPath = "/home/rucha/workspace/proj/viewer/uploads/converted/";
	public  String convert (String inputPath, String fileName) throws IOException, InterruptedException;
	public void display(String outputPath);
}