

import defs.Maindefinitions;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Maindefinitions.checkFolders();
	
		
		new editor.BasicRoomBuilder().start();
	}

}
