package defs;

import java.io.File;
/**
 * This class contains basic static references. For Example the version name, or the save folder.
 */
public class Maindefinitions {

	
	public static final String version = "Pre Alpha";
    public static final String maindirectory =System.getProperty("user.home") +"/Project Ouh "+ version +"/";
    public static final String savesdirectory = maindirectory +"Saves/";
    
    
    /**
     * If this is true, folders MUST be newly created to prevent errors!
     */
    public static boolean makedirs = true;
  

	/**
	 * Should run before starting the program atm. Checks if all necessary folder exists and starts creating them.
	 */
	public static void checkFolders() {
	   makedirs = !(new File(maindirectory).exists() && new File(savesdirectory).exists());
	   if (makedirs) {
		   new File(maindirectory).mkdir();
		   new File(savesdirectory).mkdir();
	   }
	}
}
