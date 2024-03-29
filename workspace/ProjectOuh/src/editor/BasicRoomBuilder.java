package editor;


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import loaders.MaterialLoader;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.scene.Geometry;


/**
 * @author Bender
 * Contains the editor JME3 scene.
 */
public class BasicRoomBuilder extends SimpleApplication{


	private static final String FIRST_TOOL = "First Tool";
	private static final String SECOND_TOOL = "Second Tool";
	private static final String OPENMM = "open Material Manager";
	private static final String CHANGEFIRSTTOOL = "Change first tool";
	private static final String CHANGESECONDTOOL = "Change second tool";
	private static final String APPLYTOALL = "apply to all";
	private static final String REPAINT = "repaint";

	private int firstTool = -2;
	private int secondTool = -1;
	
	private boolean applytoall = false;
	
	WallNode wallnode = new WallNode("wallnode");
	
	  private ActionListener actionListener = new ActionListener() {
		  
		    /* (non-Javadoc)
		     * @see com.jme3.input.controls.ActionListener#onAction(java.lang.String, boolean, float)
		     */
		    public void onAction(String name, boolean keyPressed, float tpf) {
		    	if (keyPressed) {
		    		if (name.equals(FIRST_TOOL) || name.equals(SECOND_TOOL))
		    		{
		    			Vector2f click2d = inputManager.getCursorPosition();
		    	        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
		    	        Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
		    			CollisionResults results = new CollisionResults();
		    			Ray ray = new Ray(cam.getLocation(), dir);
		    			wallnode.collideWith(ray, results);
		        	
		    			if (results.size() >0) {
		    				int ToolID;
		    				if (name.equals(FIRST_TOOL)) ToolID = firstTool;
		    				else ToolID = secondTool;
		    				
		    				useTool(results.getCollision(0).getGeometry(),ToolID);
		    			}
		    		}
		    		if (name.equals(OPENMM)){
		    			new MaterialManager();
		    			stop();
		    		}
		    		if (name.equals(REPAINT)) updateWall();
		    		if (name.equals(CHANGESECONDTOOL)||name.equals(CHANGEFIRSTTOOL)) selectMaterial(name);
		    	}
		    	
		    	if (name.equals(APPLYTOALL)) applytoall = keyPressed;
		    }
		    
		    
			/**
			 * Uses a tool on a geometry.
			 * @param geo
			 * @param ToolID
			 */
			public void useTool(Geometry geo, int ToolID) {
				if (ToolID == -1) {
					wallnode.removeBlock(RoomNode.getCoordsByGeometry(geo), assetManager,geo.getName());
					return;
				}
				if (ToolID == -2) {
					wallnode.addAndUpdate(new RoomNode(geo), assetManager);
					return;
				}
				wallnode.updateMaterial(geo,MaterialLoader.getLoader().getKnownMaterials()[ToolID], getAssetManager(), applytoall);
				
			}
	  };
	  
	  
	  /**
	 * Creates a BasicRoom by getting some basic dimensions
	 */
	public BasicRoomBuilder() {
			wallnode.openRoomEndDimensions[0] = Integer.parseInt(JOptionPane.showInputDialog("Enter length"))-2;
			wallnode.openRoomEndDimensions[1] = Integer.parseInt(JOptionPane.showInputDialog("Enter depth"))-2;
			wallnode.openRoomEndDimensions[2] = Integer.parseInt(JOptionPane.showInputDialog("Enter height"))-2;
			
			
			
		}
	
	/** Opens a dialogue to change the tool given in the parameter.
	 * @param ToolName
	 */
	protected void selectMaterial(String name) {
		String[] materials = MaterialLoader.getLoader().getKnownMaterials();
		String[] options = new String[materials.length+2];
		options[0] = "Remove";
		options[1] = "Update";
		for (int i=2; i<options.length; i++) options[i] = materials[i-2];
		
		String s = (String)JOptionPane.showInputDialog(
                new JFrame(),
                "Select Tool. Choose wisely.",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                "Remove");
		int i;
		for (i=0; i<options.length; i++) {
			if (options[i].equals(s)) break;
		}
		i-=2;
		if (name.equals(CHANGEFIRSTTOOL)) firstTool = i;
		else secondTool = i;
		
	}
/*	@Override
//    public void simpleUpdate(float tpf) {
//	
    }*/
	/**
	 * updates all walls
	 */
	public void updateWall() {
		wallnode.buildwall(assetManager);
	}
	
	
	/**
	 * Initializes the nodes (not much currently)
	 */
	private void initNodes() {
		rootNode.attachChild(wallnode);
		
		
	}
	

	/* (non-Javadoc)
	 * @see com.jme3.app.SimpleApplication#simpleInitApp()
	 */
	@Override
	public void simpleInitApp() {
		flyCam.setDragToRotate(true);					//man muss durch klicken die Kamera drehen, damit die Maus Frei wird
		
		if (inputManager.hasMapping("FLYCAM_RotateDrag"))
			inputManager.deleteMapping("FLYCAM_RotateDrag");
		inputManager.addMapping("FLYCAM_RotateDrag", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
		//inputManager.addListener(flyCam, "FLYCAM_RotateDrag");
		//Dadurch, dass es l�uft, obwohl dem kein Listener zugewiesen wird, wird das ganze nachher leider �berschrieben -.-. Nur wo ?		

		
		System.out.println("Logger turned off!");
		Logger.getLogger("").setLevel(Level.SEVERE);
		
		initNodes();
		int[] startvalues = {0,0,0};
		int[] endvalues = {wallnode.openRoomEndDimensions[0]+1,wallnode.openRoomEndDimensions[1]+1,wallnode.openRoomEndDimensions[2]+1};
		wallnode.generateRoom(assetManager,startvalues, endvalues);
		
		
		initCrossHairs();
		initTrigger();
		
		initLights();
		
	}

	/**
	 * ambient light.
	 */
	private void initLights() {
		AmbientLight ambient = new AmbientLight();
		ambient.setColor(ColorRGBA.White);
		rootNode.addLight(ambient);
	}

	/**
	 * Initializes the Input Mapping
	 */
	private void initTrigger() {
		inputManager.addMapping(FIRST_TOOL, new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); inputManager.addListener(actionListener, FIRST_TOOL);
		inputManager.addMapping(SECOND_TOOL, new MouseButtonTrigger(MouseInput.BUTTON_RIGHT)); inputManager.addListener(actionListener, SECOND_TOOL);
		inputManager.addMapping(OPENMM, new KeyTrigger(KeyInput.KEY_M)); inputManager.addListener(actionListener, OPENMM);
		inputManager.addMapping(CHANGEFIRSTTOOL, new KeyTrigger(KeyInput.KEY_F)); inputManager.addListener(actionListener, CHANGEFIRSTTOOL);
		inputManager.addMapping(CHANGESECONDTOOL, new KeyTrigger(KeyInput.KEY_G)); inputManager.addListener(actionListener, CHANGESECONDTOOL);
		inputManager.addMapping(APPLYTOALL, new KeyTrigger(KeyInput.KEY_E)); inputManager.addListener(actionListener, APPLYTOALL);
		inputManager.addMapping(REPAINT, new KeyTrigger(KeyInput.KEY_R)); inputManager.addListener(actionListener, REPAINT);
		
	}

	void initCrossHairs() { 
	    // TODO remove
	    String seperator = System.getProperty("line.separator");
	    BitmapText ch = new BitmapText(guiFont, false);
	    ch.setSize(guiFont.getCharSet().getRenderedSize());
	    ch.setText("M for Material Manager"+seperator+"F for First Tool"+seperator+"G for second Tool"+seperator+"E for 'apply to area'"+seperator+"R for rebuilding textures");       
	    ch.setLocalTranslation(0, settings.getHeight()/2 + ch.getLineHeight() / 2, 0);
	    guiNode.attachChild(ch);
	   
	  }
	

}
