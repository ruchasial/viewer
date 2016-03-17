package org.concord.viewer.convert;

import java.io.IOException;
import java.io.InputStream;

import com.x5.template.Theme;

import net.minidev.json.JSONValue;

import com.x5.template.Chunk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.Process;


public class ForXlsx implements ConvertFile {

	@Override
	@SuppressWarnings("unchecked")
	public String convert(String inputPath, String outputPath) throws JSONException, InterruptedException,RuntimeException, IOException 
	{
		Theme theme = new Theme("Template");

		int nos;
		InputStream inp = new FileInputStream(inputPath);
		XSSFWorkbook workbook = new XSSFWorkbook(inp) ;
	
		nos = workbook.getNumberOfSheets();
		
		

		File f = new File(inputPath);
		
		String fname = f.getName();
		int pos = fname.lastIndexOf(".");
		if (pos > 0) {
			 // As libreoffice cannot chane=ge name of the output html,
			// I am adding E at the start of the  filename; 
		   // fname = "E"+fname.substring(0, pos);
		    fname = fname.substring(0, pos);
		}
	
// Creating Directory for storing supporting files				
			File dir =new File(outputPath+"C"+fname);
		if(!dir.exists())
		{		
			if(dir.mkdir())
				System.out.println("Directory created");
			else
				System.out.println("Not created");
		}
		
		
		// Creating JSON object on sheet at a time
		
		//String[] sheetArray =new String[nos];
		System.out.println(dir.getPath());
		JSONArray ja = new JSONArray();
		for (int i=0;i<nos;i++)
		{
			String Sheetname = workbook.getSheetName(i);
			//sheetArray[i]= Sheetname;
			JSONObject jo =new JSONObject();
			jo.put("no", i);
			jo.put("id","sheet00"+(i+1));
			jo.put("name", Sheetname);
			ja.put(jo);
		}
		JSONObject mainjson=new JSONObject();
		mainjson.put("fortab", ja);
		workbook.close();
	
		// For creation of main html page
		
		Chunk html = theme.makeChunk("main#index");
		html.set("nos",nos);
		html.set("dir",dir.getName());
		String JSONWidget =JSONObject.valueToString(mainjson);
		html.set("widget1", ((Map<String,Object>) JSONValue.parse(JSONWidget)).get("fortab"));
		html.set("widget2", ((Map<String,Object>) JSONValue.parse(JSONWidget)).get("fortab"));

		PrintStream out = new PrintStream(dir+".html");
		html.render(out);
		html.resetTags();
		
		//Tabstrip rendering
		
		Chunk tab = theme.makeChunk("main#tabscript");
		tab.set("main", dir.getName());
		tab.set("dir",dir.getName());
		tab.set("widget3", ((Map<String,Object>) JSONValue.parse(JSONWidget)).get("fortab"));
		
		PrintStream otab = new PrintStream(dir.getAbsolutePath()+"/"+"tabstrip"+".html");
		tab.render(otab);
		tab.resetTags();

		//Support File
//		String Support = dir.getPath()+"\\"+fname+"supported"+".html";
		String Support = dir.getAbsolutePath()+"/"+fname+".html";
		//File fs = new File(Support);
		System.out.println(dir.getAbsolutePath());
		//Convert Create Support Document here outputpath for command is String Support;
		Process p1;
		Runtime qt = Runtime.getRuntime();
		p1  = qt.exec("libreoffice --headless --convert-to html --outdir "+dir.getAbsolutePath()+" "+inputPath);
		p1.waitFor();
		System.out.println(Support);
		int tableStart[] =new int[nos];
		int tableEnd[] =new int[nos];
		try{
		Process p;
		String cd= " grep -n \"<table\" "+Support;
		 Runtime rt = Runtime.getRuntime();
		 			String[] cmd = {"python","/home/rucha/git/viewer/src/ass.py",cd};
		 					
		 			//{"/bin/sh","-c","grep","-n","'<table'",Support};
				
		 			/*ProcessBuilder pb = new ProcessBuilder(cmd);
				pb.redirectErrorStream(true);
		 			p= pb.start();*/
		 					p=rt.exec(cmd);
		
			
				BufferedReader std = new BufferedReader(new InputStreamReader (p.getInputStream() ));
				String s =null;
				//System.out.println(std.readLine());
				int pq=0;
				
				while((s=std.readLine())!=null){					
					tableStart[pq++]= Integer.parseInt(s.substring(0,s.indexOf(':')));
				}
				pq=0;
				String cd2= " grep -n \"</table>\" "+Support;
				 
				 			String[] cmd2 = {"python","/home/rucha/git/viewer/src/ass.py",cd2};
		
				p= rt.exec(cmd2);
						//"/bin/sh -c grep -n \"</table>\" "+Support);
				std = new BufferedReader(new InputStreamReader (p.getInputStream() ));

				while((s=std.readLine())!=null){	
					tableEnd[pq++]= Integer.parseInt(s.substring(0,s.indexOf(':')));
				}
		pq=0;

				for(int i=0;i<nos;i++,pq++)
				{
					StringBuilder content = new StringBuilder();
					String spq = null;
					String cd3="sed -n '"+tableStart[pq]+","+tableEnd[pq]+"p' "+Support; 
					String[] cmd3 = {"python","/home/rucha/git/viewer/src/ass.py",cd3};
					p= rt.exec(cmd3);
					std = new BufferedReader(new InputStreamReader (p.getInputStream()));
					while((spq=std.readLine())!=null){
						content.append(spq+"\n");
					}
					
					
					File ns =new File(dir.getAbsolutePath()+"/"+"sheet00"+(i+1)+".html");
					ns.createNewFile();
					Chunk sheet = theme.makeChunk("main#sheets");
					sheet.set("main", dir.getName());
					sheet.set("dir", "converted/"+dir.getName());
					sheet.set("contents", content);
				    PrintStream st = new PrintStream(ns);
					sheet.render(st);
					//System.out.println(content);
				}
				
		}catch(Throwable t)
		{
			t.printStackTrace();
		}
			
		return "/converted/C"+fname+".html";
	}
	@Override
	public void display(String outputPath) {
		// TODO Auto-generated method stub

	}

}
