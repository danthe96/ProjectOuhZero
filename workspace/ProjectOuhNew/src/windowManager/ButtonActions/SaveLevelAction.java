package windowManager.ButtonActions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;

//Button Speichern:
	public class SaveLevelAction extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public SaveLevelAction(){super();}
		public SaveLevelAction(String name){super(name);} 
		public SaveLevelAction(String name, Icon icon){super(name,icon);}  
		
		@Override
		public void actionPerformed(ActionEvent arg0) {		
			System.out.println(this);		
		}
		
	}