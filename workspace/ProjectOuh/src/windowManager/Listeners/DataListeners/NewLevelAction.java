package windowManager.Listeners.DataListeners;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;

import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;

import editor.BasicRoomBuilder;


//Button Neu:
	public class NewLevelAction extends AbstractAction{

		private static final long serialVersionUID = 1L;
		private BasicRoomBuilder canvasApplication;
		
		public NewLevelAction(){super();}
		public NewLevelAction(String name,BasicRoomBuilder canvasApplication){super(name);this.canvasApplication=canvasApplication;} 
		public NewLevelAction(String name, Icon icon){super(name,icon);}  
		
		@Override
		public void actionPerformed(ActionEvent arg0) {	
			InputManager inputManager=canvasApplication.getInputManager();
			if (inputManager.hasMapping("FLYCAM_RotateDrag"))
				inputManager.deleteMapping("FLYCAM_RotateDrag");
			inputManager.addMapping("FLYCAM_RotateDrag", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
			inputManager.addListener(canvasApplication.getFlyByCamera(),"FLYCAM_RotateDrag");;
			
		}
		
	}