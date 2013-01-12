package defs;

import com.jme3.math.Vector3f;

/**
 * This class is used by BasicRoomBuilder. Gets positions from names and some data attached to them.
 * @author Bender
 *
 */
public class Positions {
	public static String[] positions = {"down", "up","front","back","right","left"};
	public static Vector3f[] positionsbehind= {new Vector3f(0,-1,0), new Vector3f(0,1,0), new Vector3f(0,0,-1), new Vector3f(0,0,1), new Vector3f(-1,0,0), new Vector3f(1,0,0)};
	public static int down = 0;
	public static int up = 1;
	public static int front = 2;
	public static int back = 3;
	public static int left = 4;
	public static int right = 5;
	
	/**
	 * Returns the ID from a position name. 
	 * @param s
	 * @return ID
	 */
	public static int PostitionIDByString(String s) {
		for (int i=0; i<positions.length;i++) if (s.equals(positions[i])) return i;
		return -1;
	}
	
	/**
	 * The Positions are always parallel to two axis. It returns the third one.
	 * @param position
	 * @return Dimension
	 */
	public static int getDimension(int position) { 
		Vector3f vec = positionsbehind[position];
		if (vec.x!=0) return 0;
		if (vec.y!=0) return 1;
		return 2;
		
		
	}
	
}
