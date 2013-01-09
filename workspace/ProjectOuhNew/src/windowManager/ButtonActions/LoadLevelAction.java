package windowManager.ButtonActions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;

//Button Laden:
	public class LoadLevelAction extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public LoadLevelAction(){super();}
		public LoadLevelAction(String name){super(name);} 
		public LoadLevelAction(String name, Icon icon){super(name,icon);}  
		
		@Override
		public void actionPerformed(ActionEvent arg0) {		
			System.out.println(this);		
		}
		
	}