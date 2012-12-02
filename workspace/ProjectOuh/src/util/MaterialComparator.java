package util;

import java.util.Comparator;

import com.jme3.material.Material;

public class MaterialComparator  implements Comparator<Material>{

	
	public int compare(Material arg0, Material arg1) {
		
		return arg0.getName().compareTo(arg1.getName());
	}
		
}
