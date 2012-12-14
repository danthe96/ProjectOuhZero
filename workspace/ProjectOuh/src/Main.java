

import defs.Maindefinitions;


public class Main {


	public static void main(String[] args) {
		
		Maindefinitions.checkFolders();
		new editor.BasicRoomBuilder().start();
	}

}
