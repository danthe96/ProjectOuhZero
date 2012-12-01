import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import util.Converter;

import com.jme3.math.ColorRGBA;
import com.jme3.shader.VarType;


import defs.Maindefinitions;


public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Maindefinitions.checkFolders();
	
		
		new editor.MaterialManager();
	}

}
