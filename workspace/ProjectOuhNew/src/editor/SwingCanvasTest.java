package editor;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme3.app.SimpleApplication;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class SwingCanvasTest extends SimpleApplication {

	@Override
	public void simpleInitApp() {
		System.out.println("Logger turned off!");
		Logger.getLogger("").setLevel(Level.SEVERE);

		flyCam.setDragToRotate(true);
		if (inputManager.hasMapping("FLYCAM_RotateDrag"))
			inputManager.deleteMapping("FLYCAM_RotateDrag");
		inputManager.addMapping("FLYCAM_RotateDrag", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
		inputManager.addListener(flyCam,"FLYCAM_RotateDrag");
		
		
		
		
		//Test:
		Box mesh =new Box(1,1,1);

		Geometry geom = new Geometry("A cube", mesh); // wrap shape into geometry
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");   // create material
		System.out.println("ALLO"+mat); 
		mat.setTexture("ColorMap", assetManager.loadTexture("Assets/test.png"));

		geom.setMaterial(mat);       					// assign material to geometry
		Geometry geom2=geom.clone();
		geom.move(0,1,4);
		geom2.move(2,3,4);

		rootNode.attachChild(geom);                    // attach geometry to a node
		rootNode.attachChild(geom2);        
		        
		     
		        
		        
	}
	
	
}
