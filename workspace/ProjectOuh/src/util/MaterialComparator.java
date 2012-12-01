package util;

import java.util.Comparator;

import com.jme3.material.Material;

public class MaterialComparator  implements Comparator<Material>{

	
	public int compare(Material arg0, Material arg1) {
		
		return StringComparator.compare2Strings(arg0.getName(), arg1.getName());
	}
		
}
