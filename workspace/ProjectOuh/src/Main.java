

import defs.Maindefinitions;
import editor.BasicRoomBuilder;


public class Main {


	public static void main(String[] args) {
		
		Maindefinitions.checkFolders();
		new BasicRoomBuilder().start();
	}

}
