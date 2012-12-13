package editor;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import defs.Positions;


public class WallNode extends Node {
	public int[] openRoomEndDimensions= new int[3]; //In diesem Bereich ist immer Nichts. (Spart VIEL Rechenleistung)
	public int[] openRoomStartDimensions = {1,1,1};
	
	public WallNode(String string) {
		super(string);
	}

	boolean isPlot(int x,int y, int z) {
		if (isOpenRoom(x, y, z)) return true;
		return isActualPlot(x,y,z);
	}

	boolean isActualPlot(int x,int y, int z) {
		return isActualPlot(RoomNode.genName(x, y, z));
	}
	
	boolean isActualPlot(String genName) {
		return getChild(genName) != null;
	}

	boolean isPlot(String s) {
		
		return false;
	}

	public boolean isOpenRoom(int x, int y, int z) {
		if (x<openRoomStartDimensions[0] || x > openRoomEndDimensions[0]) return false; 
		if (y<openRoomStartDimensions[1] || y > openRoomEndDimensions[1]) return false; 
		if (z<openRoomStartDimensions[2] || z > openRoomEndDimensions[2]) return false; 
		
		return true;
	}

	public void addAndUpdate(RoomNode roomNode,AssetManager am) {
		attachChild(roomNode);
		roomNode.updateWall(this, am);
		for (String s: roomNode.getNeighbors()) {
			try {
				((RoomNode)getChild(s)).updateWall(this, am);
			}
			catch( Exception e) {}
		}
		
	}
	public void generateRoom(AssetManager am, int[] startvalues, int[] endvalues) {
		
		for (int x=startvalues[0]; x<=endvalues[0]; x++)
			for (int y=startvalues[1]; y<=endvalues[1]; y++)
				for (int z=startvalues[2]; z<=endvalues[2]; z++)  {
					if (!isOpenRoom(x,y,z)) getOrCreateRoomNode(x, y, z);
				}
		buildwall(am);
		
		
	}

	public void buildwall(AssetManager am) {
		for (Spatial s: getChildren()) {
			((RoomNode) s).updateWall(this, am);
		}
	}
	RoomNode getOrCreateRoomNode(int x, int y, int z) {
		String NodeName = RoomNode.genName(x,y,z);
		RoomNode node =(RoomNode)getChild(NodeName);
		if (node != null) return node;
		else {
			node = new RoomNode(x,y,z);
			attachChild(node);
			return node;
		}
	}
	
	
	public void removeBlock(Vector3f coordsByGeometry, AssetManager assetManager,String position) {
		removeBlock((int)(coordsByGeometry.x/RoomNode.factor),(int)(coordsByGeometry.y/RoomNode.factor),(int)(coordsByGeometry.z/RoomNode.factor), assetManager,position);
		
	}

	public void removeBlock(int x, int y, int z, AssetManager am,String position) {
		RoomNode r = (RoomNode) getChild(RoomNode.genName(x, y, z));
		detachChild(r);
		
		int[] copystart = openRoomStartDimensions.clone();
		int[] copyend = openRoomEndDimensions.clone();
		for (int[] a: r.getNeighborsCoords()) {
			
			if (isOpenRoom(a[0], a[1], a[2])) {
				if (position.equals(Positions.positions[0])) openRoomStartDimensions[1]++; 
				if (position.equals(Positions.positions[1])) openRoomEndDimensions[1]--; 
				if (position.equals(Positions.positions[2])) openRoomStartDimensions[2]++; 
				if (position.equals(Positions.positions[3])) openRoomEndDimensions[2]--; 
				if (position.equals(Positions.positions[4])) openRoomStartDimensions[0]++; 
				if (position.equals(Positions.positions[5])) openRoomEndDimensions[0]--; 
				generateRoom(am, copystart, copyend);
				break;
				}
			}
		for (String s: r.getNeighbors()) {
			RoomNode room = (RoomNode) getChild(s);
			if (room != null) room.updateWall(this, am);
		}
	}

	public void updateMaterial(Geometry geo, String materialName, AssetManager am, boolean applytoall) {
		Vector3f vec = RoomNode.getCoordsByGeometry(geo);
		vec = vec.divide(RoomNode.factor);
		RoomNode r = (RoomNode) getChild(RoomNode.genName((int)vec.x,(int)vec.y,(int)vec.z));
		updateMaterial(geo.getName(), r, am, applytoall, materialName);
	}

	private void updateMaterial(String name, RoomNode r, AssetManager am,
			boolean applytoall, String materialName) {
		boolean change = r.updateMaterial(name, materialName, am);
		if (applytoall && change) {
			int unaffecteddimension = Positions.getDimension(Positions.PostitionIDByString(name));
			int[][] allcoords = r.getNeighborsCoords();
			for (int[] coords: allcoords) {
				if (coords[unaffecteddimension] == r.getCoords()[unaffecteddimension]) {
					RoomNode newnode = (RoomNode) getChild(RoomNode.genName(coords));
					if (newnode!= null) updateMaterial(name,newnode,am,applytoall, materialName);
				}
			}
		}
		
	}
}
