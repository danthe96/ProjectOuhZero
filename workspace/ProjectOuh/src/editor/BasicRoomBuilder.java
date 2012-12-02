package editor;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;


public class BasicRoomBuilder extends SimpleApplication{

	private static final String DEL_BLOCK = "Del Block";

	private static final String ADD_BLOCK = "Add Block";;

	/**
	 * @param args
	 */
	
	WallNode wallnode = new WallNode("wallnode");
	
	  private ActionListener actionListener = new ActionListener() {
		  
		    public void onAction(String name, boolean keyPressed, float tpf) {
		    	if (keyPressed) 
		    		{
		    		CollisionResults results = new CollisionResults();
		        	Ray ray = new Ray(cam.getLocation(), cam.getDirection());
		        	wallnode.collideWith(ray, results);
		        	
		        	if (results.size() >0) {
		        		if (name.equals(DEL_BLOCK)) wallnode.addAndUpdate(new RoomNode(results.getCollision(0).getGeometry()), assetManager);
		        		if (name.equals(ADD_BLOCK)) wallnode.removeBlock(RoomNode.getCoordsByGeometry(results.getCollision(0).getGeometry()), assetManager,results.getCollision(0).getGeometry().getName());
		        	}
		    	}
		    }
	  };
	
	
	public static void main(String[] args) {
		new BasicRoomBuilder().start();

	}
	
	
	public BasicRoomBuilder() {
		wallnode.openRoomEndDimensions[0] = Integer.parseInt(JOptionPane.showInputDialog("Enter length"))-2;
		wallnode.openRoomEndDimensions[1] = Integer.parseInt(JOptionPane.showInputDialog("Enter depth"))-2;
		wallnode.openRoomEndDimensions[2] = Integer.parseInt(JOptionPane.showInputDialog("Enter height"))-2;
		
		
		
	}


	private void initNodes() {
		rootNode.attachChild(wallnode);
		
		
	}

	@Override
	public void simpleInitApp() {
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
		inputManager.addMapping(DEL_BLOCK, new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); inputManager.addListener(actionListener, DEL_BLOCK);
		inputManager.addMapping(ADD_BLOCK, new MouseButtonTrigger(MouseInput.BUTTON_RIGHT)); inputManager.addListener(actionListener, ADD_BLOCK);
		
	}

	void initCrossHairs() { 
	    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
	    BitmapText ch = new BitmapText(guiFont, false);
	    ch.setSize(guiFont.getCharSet().getRenderedSize() * 3);
	    ch.setText("+");       
	    ch.setLocalTranslation(settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() *2/3, settings.getHeight()/2 + ch.getLineHeight() / 2, 0);
	    guiNode.attachChild(ch);
	  }
	

}