package defs;

import java.io.File;

public class Maindefinitions {

	// Hier werden alle statischen Referenzen gesammelt. Also zum Beispiel wo das Programm hinspeichert oder sowas.

	public static final String version = "Pre Alpha";
    public static final String maindirectory =System.getProperty("user.home") +"/Project Ouh "+ version +"/";
    public static final String savesdirectory = maindirectory +"Saves/";
    public static boolean makedirs = true;
    
    
	public static String getSavesdirectory() {
		return savesdirectory;
	}



	public static void checkFolders() {
	   makedirs = !new File(maindirectory).exists();
	   if (makedirs) {
		   new File(maindirectory).mkdir();
		   new File(savesdirectory).mkdir();
	   }
		
	}
}