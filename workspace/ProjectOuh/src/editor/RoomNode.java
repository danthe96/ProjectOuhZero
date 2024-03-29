package editor;

import loaders.MaterialLoader;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.util.BufferUtils;

import defs.Positions;


//TODO rebuild
public class RoomNode extends Node{

	static char CoordSeperator = ' ';
	protected int x;
	protected int y;
	protected int z;
	
	String[] Materials = new String[6];
	static String DefaultMaterialName = "Simple Material";
	static int [] indexes = { 2,0,1, 1,3,2 };
	static Vector2f[] texCoord = {new Vector2f(0,0),new Vector2f(1,0),new Vector2f(0,1),new Vector2f(1,1)};
	static float factor = 2f;
	
	RoomNode(int x, int y, int z) {
		super(genName(x,y,z));
		this.x=x;
		this.y=y;
		this.z=z;
	}
	//This consturctor creates a RoomNode BEHIND the geometry
	public RoomNode(Geometry geometry) {
		
		Vector3f coords = getCoordsByGeometry(geometry);
		coords = coords.add(getCoordsBehind(geometry.getName()));
		this.x = (int) (coords.x/factor);
		this.y = (int) (coords.y/factor);
		this.z = (int) (coords.z/factor);
		name = genName(x, y, z);
	}
	public static Vector3f getCoordsByGeometry(Geometry geometry) {
		return geometry.getLocalTranslation().add(getPostionFactorByString(geometry.getName()).mult(-1f));
	}
	public static String genName(int x, int y, int z) {
		
		return ""+x*factor+CoordSeperator+y*factor+CoordSeperator+z*factor;
	}
	
	
	public static String genName(int[] coords) {
		return genName(coords[0], coords[1], coords[2]);
	}

	public void updateWall(WallNode wallnode, AssetManager am) {

		
		detachAllChildren();
		Mesh mesh = new Mesh();
		texCoord[0] = new Vector2f(0,0);
		texCoord[1] = new Vector2f(1,0);
		texCoord[2] = new Vector2f(0,1);
		texCoord[3] = new Vector2f(1,1);
		
		Vector2f[] backTexCoords = new Vector2f[4];
		backTexCoords[0] = new Vector2f(1,0);
		backTexCoords[1] = new Vector2f(1,1);
		backTexCoords[2] = new Vector2f(0,0);
		backTexCoords[3] = new Vector2f(0,1);
		
		
		Vector3f [] vertices = new Vector3f[4];
		
		
		//down
		if (!wallnode.isPlot(x, y-1, z)) {
			vertices[0] = new Vector3f(0,0,0);
			vertices[1] = new Vector3f(0,0,1);
			vertices[2] = new Vector3f(1,0,0);
			vertices[3] = new Vector3f(1,0,1);
			
			mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
			mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
			mesh.setBuffer(Type.Index,    3, BufferUtils.createIntBuffer(indexes));
			mesh.updateBound();

			buildgeo(am, mesh, 0);
			mesh = new Mesh();
			
			//geo.setLocalTranslation(geo.getLocalTranslation().add(new Vector3f(0, -0.5f*factor, 0)));
		}
		//up
		if (!wallnode.isPlot(x, y+1, z)) {

			vertices[0] = new Vector3f(0,0,0);
			vertices[1] = new Vector3f(1,0,0);
			vertices[2] = new Vector3f(0,0,1);
			vertices[3] = new Vector3f(1,0,1);
			
			mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
			mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(backTexCoords));
			mesh.setBuffer(Type.Index,    3, BufferUtils.createIntBuffer(indexes));
			mesh.updateBound();

			buildgeo(am, mesh, 1);
			mesh = new Mesh();
			}
		//front
		if (!wallnode.isPlot(x, y, z-1)) {

			vertices[0] = new Vector3f(0,0,0);
			vertices[1] = new Vector3f(1,0,0);
			vertices[2] = new Vector3f(0,1,0);
			vertices[3] = new Vector3f(1,1,0);
			
			mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
			mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
			mesh.setBuffer(Type.Index,    3, BufferUtils.createIntBuffer(indexes));
			mesh.updateBound();

			buildgeo(am, mesh, 2);
			mesh = new Mesh();
				}

		//back
		if (!wallnode.isPlot(x, y, z+1)) {

			vertices[0] = new Vector3f(0,0,0);
			vertices[1] = new Vector3f(0,1,0);
			vertices[2] = new Vector3f(1,0,0);
			vertices[3] = new Vector3f(1,1,0);
			
			mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
			mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(backTexCoords));
			mesh.setBuffer(Type.Index,    3, BufferUtils.createIntBuffer(indexes));
			mesh.updateBound();

			buildgeo(am, mesh, 3);
			mesh = new Mesh();
				}
		//Right
		if (!wallnode.isPlot(x-1, y, z)) {

			vertices[0] = new Vector3f(0,0,0);
			vertices[1] = new Vector3f(0,1,0);
			vertices[2] = new Vector3f(0,0,1);
			vertices[3] = new Vector3f(0,1,1);
			
			mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
			mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(backTexCoords));
			mesh.setBuffer(Type.Index,    3, BufferUtils.createIntBuffer(indexes));
			mesh.updateBound();

			buildgeo(am, mesh, 4);
			mesh = new Mesh();
				}
		//Left
		if (!wallnode.isPlot(x+1, y, z)) {

			vertices[0] = new Vector3f(0,0,0);
			vertices[1] = new Vector3f(0,0,1);
			vertices[2] = new Vector3f(0,1,0);
			vertices[3] = new Vector3f(0,1,1);
			
			mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
			mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
			mesh.setBuffer(Type.Index,    3, BufferUtils.createIntBuffer(indexes));
			mesh.updateBound();

			buildgeo(am, mesh, 5);
			mesh = new Mesh();
		}
		
		
	}

	protected void buildgeo(AssetManager am, Mesh mesh, int position) {
		Geometry geo = new Geometry(Positions.positions[position], mesh);
		Material mat = getMaterial(position, am);
		geo.setMaterial(mat);
		geo.setLocalTranslation(new Vector3f(x*factor,y*factor,z*factor).add(getPostionFactor(position)));
		geo.setLocalScale(factor);
		attachChild(geo);
	}

	private static Vector3f getPostionFactor(int position) {
		switch (position) {
		case 0: case 2:case 4: return Vector3f.ZERO;
		case 1: return new Vector3f(0, 1f*factor, 0);
		case 3: return new Vector3f(0, 0, 1f*factor);
		case 5: return new Vector3f(1f*factor, 0, 0);
		
		}
		return null;
	}

	private static Vector3f getCoordsBehind(String name) {
		for (int i=0; i<Positions.positions.length;i++ ) {
			if (name.equals(Positions.positions[i])) {
				return Positions.positionsbehind[i].mult(factor);
			}
		}
		return null;
	}
	private static Vector3f getPostionFactorByString(String name) {
		for (int i=0; i<Positions.positions.length;i++ ) {
			if (name.equals(Positions.positions[i])) return getPostionFactor(i);
		}
		return null;
	}
	private Material getMaterial(int Postition,AssetManager am) {
		if (Materials[Postition] != null) return MaterialLoader.getLoader().get(Materials[Postition],am);
		return MaterialLoader.getLoader().get(DefaultMaterialName, am);
	}

	public int[] getCoords() {
		int[] result = {x,y,z};
		return result;
	}
	public String[] getNeighbors() {
		String[] result = new String[6];
		result[0] = genName(x-1, y, z);
		result[1] = genName(x+1, y, z);
		result[2] = genName(x, y-1, z);
		result[3] = genName(x, y+1, z);
		result[4] = genName(x, y, z-1);
		result[5] = genName(x, y, z+1);
		return result;
	}
	public int[][] getNeighborsCoords() {
		int[][] result = {
		 {x-1, y, z},
		 {x+1, y, z},
		 {x, y-1, z},
		 {x, y+1, z},
		 {x, y, z-1},
		 {x, y, z+1}};
		return result;
	}
	public boolean updateMaterial(String name, String materialName, AssetManager am) {
		
		return setMaterial(Positions.PostitionIDByString(name), materialName, am);
	}
	private boolean setMaterial(int postition, String materialName, AssetManager am) {
		Materials[postition] = materialName;
		Material m = getMaterial(postition, am);
		Geometry geo = (Geometry) getChild(Positions.positions[postition]);
		if (geo != null && !m.equals(geo.getMaterial())) {
			getChild(Positions.positions[postition]).setMaterial(getMaterial(postition, am)); 
			return true;
			}
		else return false;
		
	}
}
