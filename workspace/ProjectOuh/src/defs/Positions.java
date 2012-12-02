package defs;

import com.jme3.math.Vector3f;

public class Positions {
	public static String[] positions = {"down", "up","front","back","left","right"};
	public static Vector3f[] positionsbehind= {new Vector3f(0,-1,0), new Vector3f(0,1,0), new Vector3f(0,0,-1), new Vector3f(0,0,1), new Vector3f(-1,0,0), new Vector3f(1,0,0)};
	public static int down = 0;
	public static int up = 1;
	public static int front = 2;
	public static int back = 3;
	public static int left = 4;
	public static int right = 5;
	
	
}
