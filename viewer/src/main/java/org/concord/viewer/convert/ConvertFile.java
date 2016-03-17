package org.concord.viewer.convert;

import java.io.IOException;
//rucha made a change
public interface ConvertFile {
	String inputPath = "";
	String outputPath = "/home/rucha/git/viewer/src/main/webapp/converted/";

	public  String convert (String inputPath, String fileName) throws IOException, InterruptedException;
	public void display(String outputPath);
}