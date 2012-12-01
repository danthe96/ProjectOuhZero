//Bender did this.

package loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import util.MaterialComparator;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;

import defs.Maindefinitions;

public class MaterialLoader implements Serializable{

	static String savedir = defs.Maindefinitions.getSavesdirectory() + "Materials/";
	static String savename = "Materials.main";
	static String filetype =".save";
	static MaterialLoader ml;
	
	ArrayList<Material> materials = new ArrayList<Material>(); // hier stehen alle Materialien drinnen die schon einmal vom Programm geladen wurden.
	public ArrayList<String> knownmaterials = new ArrayList<String>(); // hier steht der Rest :)
	
	public static MaterialLoader getLoader(){
		if (ml == null) ml = loadML();
		if (ml == null) {
			JOptionPane.showMessageDialog(null,"No saved Material was found. If you run it for the first time this is normal. However, if you believe that live isnt fair and this should not have happend please report in the forum.");
			ml = new MaterialLoader();
		}
		return ml;
		
	}

	
	public Material get(String Name, AssetManager am) {
		Material m = new Material();
		m.setName(Name);
		int ID = Collections.binarySearch(materials, m, new MaterialComparator());
		if (ID == -1) {
			return loadMaterial(Name, am);
		}
		return materials.get(ID);
		
	}
	private static MaterialLoader loadML() {
		if (Maindefinitions.makedirs) new File(savedir).mkdir();
		return (MaterialLoader) load( savedir+savename );
	}

	public void updateMaterialData(MaterialData m) {
		int ID = Collections.binarySearch(knownmaterials, m.getName());
		if (ID == -1) knownmaterials.add(m.getName());
		save(savedir+m.getName()+filetype,m);
		MaterialLoader saveobj = new MaterialLoader();
		saveobj.knownmaterials = (ArrayList<String>) this.knownmaterials.clone();
		save(savedir+savename, saveobj);
	}
	
	private void save(String filepath, Object ob) {
		OutputStream fis = null;  
		try 
		{ 
		  fis = new FileOutputStream(filepath); 
		  System.out.println(new File(savedir).getAbsolutePath());
		  ObjectOutputStream o = new ObjectOutputStream( fis ); 
		  o.writeObject(ob);
		} 
		catch ( IOException e ) { 
			System.err.println( e ); }
		try { fis.close(); } catch ( Exception e ) { } 
		
	}


	private Material loadMaterial(String name, AssetManager am) {
		
		
		
		Material m =  ((MaterialData) load(savedir+name+filetype)).formMaterial(am);
		if (m == null) {
			JOptionPane.showMessageDialog(null,name+" could not be found.");
		}
		else {
			materials.add(m);
			Collections.sort(materials, new MaterialComparator());
		}
		
		return m;
	}

	private static Object load(String filepath) {
		InputStream fis = null; 
		Object ob; 
		try 
		{ 
		  fis = new FileInputStream(filepath); 
		  ObjectInputStream o = new ObjectInputStream( fis ); 
		  ob = o.readObject(); 
		} 
		catch ( IOException e ) { 
			System.err.println( e ); 
			return null;} 
		catch ( ClassNotFoundException e ) { System.err.println( e );
			return null;} 
		finally { try { fis.close(); } catch ( Exception e ) { } }
		return ob;
	}


	public void removeMaterialData(String string) {
		knownmaterials.remove(string);
	}


	public MaterialData getMaterialData(int row) {
		// TODO Auto-generated method stub
		return (MaterialData) load(savedir+knownmaterials.get(row)+filetype);
	}
}