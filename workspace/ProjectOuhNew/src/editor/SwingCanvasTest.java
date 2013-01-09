package editor;

import com.jme3.app.SimpleApplication;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;

public class SwingCanvasTest extends SimpleApplication {

	@Override
	public void simpleInitApp() {

		flyCam.setDragToRotate(true);
		if (inputManager.hasMapping("FLYCAM_RotateDrag"))
			inputManager.deleteMapping("FLYCAM_RotateDrag");
		inputManager.addMapping("FLYCAM_RotateDrag", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
		inputManager.addListener(flyCam,"FLYCAM_RotateDrag");
		
		Quad mesh =new Quad(1,1);

		
		Geometry geom = new Geometry("A shape", mesh); // wrap shape into geometry
		Material mat = new Material(assetManager,      
		    "Common/MatDefs/Misc/ShowNormals.j3md");   // create material
		geom.setMaterial(mat);                         // assign material to geometry
		Geometry geom2=geom.clone();
		geom.move(1,2,4);
		geom2.move(2,3,4);
		
		
		// if you want, transform (move, rotate, scale) the geometry.
		rootNode.attachChild(geom);                    // attach geometry to a node
		rootNode.attachChild(geom2);        
		        
		        
		        
		        
	}
	
	
}
