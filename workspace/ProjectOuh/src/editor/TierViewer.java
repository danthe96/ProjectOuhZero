package editor;


import javax.swing.JFrame;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Rectangle;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class TierViewer extends JFrame{
	int viewedDimension = 0;
	int direction = -1;
	int viewedPoint = 0;
	int viewedPlots = 64;
	int[] startvalues = {0,0};
	private WallNode wallnode;
	
	TierViewer(WallNode wallnode) {
		this.wallnode = wallnode;
		
		
	}
	
	void init() {
		viewedDimension = 0;
		viewedPoint = 3;
		RoomNode r;
		
		for (int d1=startvalues[0]; d1<startvalues[0]+viewedPlots; d1++) 
			for (int d2=startvalues[1]; d2<startvalues[1]+viewedPlots; d2++) 
				
		
		for (Spatial s: wallnode.getChildren()) {
			r = (RoomNode)s;
			if (r.getCoords()[viewedDimension] == viewedPoint) {
				
			}
		}
	}
	
	
}