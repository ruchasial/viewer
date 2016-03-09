package org.concord.viewer.typecheck;
import java.io.File;
import java.io.IOException;
import org.apache.tika.Tika;
public class TypeCheck {



	
		public static String check(File input) throws IOException{
		
			Tika tika = new Tika();	      
		      //detecting the file type using detect method
		      return tika.detect(input);
	}
	}

