package loaders;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

/**
 * As the JME Material needs an AssetManager, which is connected to a JME scene, MaterialData is needed to store Material data.
 * @author David
 *
 */
public class MaterialData implements Comparable<MaterialData>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name;
	private String MaterialPath ="Common/MatDefs/Light/Lighting.j3md";
	public ArrayList<Data> data = new ArrayList<Data>();
	
	/**
	 * Needed for Serialization. DO NOT DELETE!
	 */
	public MaterialData() {
		
	}
	/** 
	 * If generateBase is true, some basic values are added to the MaterialData
	 * @param generateBase
	 */
	public MaterialData(boolean generateBase) {
		if (generateBase) {
			//TODO add all values
			add("UseMaterialColors","boo", "true");
			add("Ambient","Col", Color.white);
			add("Diffuse","Col",Color.white);
			add("Shininess","Flo","15f");
		}
	}
	public String getName() {
		return Name;
	}
	public void setName(String Name) {
		if (Name.equals("")) Name = "unnamed";
		this.Name = Name;
	}
	@Override
	public int compareTo(MaterialData arg0) {

		return getName().compareTo(arg0.getName());
	}
	
	public String getMaterialPath() {
		return MaterialPath;
	}
	public void setMaterialPath(String materialPath) {
		MaterialPath = materialPath;
	}

	/**
	 * Adds a parameter to the MaterialData. 
	 */
	public void add(String name, String Type, Object value) {
		Data d = new Data();
		d.Name = name;
		d.Type =Type;
		d.setValue(value);
		data.add(d);
	}
	/** 
	 * Returns the content as data useable for a table
	 * @return TableData
	 */
	public Object[][] getTableData() {
		Object[][] result = new Object[data.size()][4];
		int i=0;
		for (Data d: data) {
			result[i][0] = d.Name;
			result[i][1] = d.getValue();
			result[i][2] = d.Type;
			result[i][3] = "x";
			i++;
		}
		return result;
	}
	/**
	 * @author Bender
	 * Used by MaterialData to store values more easily.
	 */
	private class Data implements Serializable{
		private static final long serialVersionUID = 1L;
		public String Name;
		Object value;
		String Type;
		static final String Integer = "Int";
		static final String Float = "Flo";
		static final String Color = "Col";
		static final String Texture = "Tex";
		static final String Boolean = "boo";
		void setValue(Object val) {
			value = val;
		}
		public Object getValue() {
			return value;
		}
		
	}
	public void changeData(int iD, int col, Object value) {
		if (col == 0) data.get(iD).Name = (String) value;
		if (col == 2) data.get(iD).Type = (String) value;
		if (col == 1) data.get(iD).setValue(value);
	}
	public Material formMaterial(AssetManager am) {
		Material result = new Material(am,MaterialPath);
		result.setName(Name);
		for (int i=0;i<data.size();i++) {
			Data d = data.get(i);
			if (d.Type.equals(Data.Integer)) {
				result.setInt(d.Name, Integer.parseInt((String) d.getValue()));
			}
			if (d.Type.equals(Data.Float)) {
				result.setFloat(d.Name, Float.parseFloat((String) d.getValue()));
			}
			if (d.Type.equals(Data.Color)) {
				ColorRGBA color = util.Converter.ColorToColorRGBA((Color) d.getValue());
				result.setColor(d.Name, color);
			}
			if (d.Type.equals(Data.Texture)) {
				 TextureKey key = new TextureKey((String)d.getValue());
				 key.setGenerateMips(true);
				result.setTexture(d.Name,am.loadTexture(key));
			}
			if (d.Type.equals(Data.Boolean)) {
				result.setBoolean(d.Name,Boolean.parseBoolean((String) d.getValue()));
			}
			//JOptionPane.showMessageDialog(null, "Could not read type "+d.Type+" of "+d.Name+" in MaterialData "+Name);
			}
			
		return result;
	}
	public void remove(int ID) {
		data.remove(ID);
		
	}
}
