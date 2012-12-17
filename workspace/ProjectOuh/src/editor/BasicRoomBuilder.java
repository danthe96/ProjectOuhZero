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
import com.jme3.scene.Geometry;


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
	/**
	 * @param args
	 */
	
	WallNode wallnode = new WallNode("wallnode");
	
	  private ActionListener actionListener = new ActionListener() {
		  
		    public void onAction(String name, boolean keyPressed, float tpf) {
		    	if (keyPressed) {
		    		if (name.equals(FIRST_TOOL) || name.equals(SECOND_TOOL))
		    		{
		    			CollisionResults results = new CollisionResults();
		    			Ray ray = new Ray(cam.getLocation(), cam.getDirection());
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
	  
	  
	  public BasicRoomBuilder() {
			wallnode.openRoomEndDimensions[0] = Integer.parseInt(JOptionPane.showInputDialog("Enter length"))-2;
			wallnode.openRoomEndDimensions[1] = Integer.parseInt(JOptionPane.showInputDialog("Enter depth"))-2;
			wallnode.openRoomEndDimensions[2] = Integer.parseInt(JOptionPane.showInputDialog("Enter height"))-2;
			
			
			
		}
	
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
	@Override
    public void simpleUpdate(float tpf) {
	
    }
	public void updateWall() {
		wallnode.buildwall(assetManager);
	}
	
	
	private void initNodes() {
		rootNode.attachChild(wallnode);
		
		
	}
	

	@Override
	public void simpleInitApp() {
		
		System.out.println("Logger turned off!");
		Logger.getLogger("").setLevel(Level.SEVERE);
		
		initNodes();
		int[] startvalues = {0,0,0};
		int[] endvalues = {wallnode.openRoomEndDimensions[0]+1,wallnode.openRoomEndDimensions[1]+1,wallnode.openRoomEndDimensions[2]+1};
		wallnode.generateRoom(assetManager,startvalues, endvalues);
		
		AmbientLight ambient = new AmbientLight();
		ambient.setColor(ColorRGBA.White);
		rootNode.addLight(ambient);
		initCrossHairs();
		initTrigger();
		
	}

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
	    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
	    BitmapText ch = new BitmapText(guiFont, false);
	    ch.setSize(guiFont.getCharSet().getRenderedSize() * 3);
	    ch.setText("+");       
	    ch.setLocalTranslation(settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() *2/3, settings.getHeight()/2 + ch.getLineHeight() / 2, 0);
	    guiNode.attachChild(ch);
	    
	    String seperator = System.getProperty("line.separator");
	    ch = new BitmapText(guiFont, false);
	    ch.setSize(guiFont.getCharSet().getRenderedSize());
	    ch.setText("M for Material Manager"+seperator+"F for First Tool"+seperator+"G for second Tool"+seperator+"E for 'apply to area'"+seperator+"R for rebuilding textures");       
	    ch.setLocalTranslation(0, settings.getHeight()/2 + ch.getLineHeight() / 2, 0);
	    guiNode.attachChild(ch);
	   
	  }
	

}
