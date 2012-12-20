package windowManager.Listeners.DataListeners;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

//Button Neu:
	public class NewLevelAction extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public NewLevelAction(){super();}
		public NewLevelAction(String name){super(name);} 
		public NewLevelAction(String name, Icon icon){super(name,icon);}  
		
		@Override
		public void actionPerformed(ActionEvent arg0) {	
			
			String s= JOptionPane.showInputDialog("Es geht !!!");
			System.out.println(s);
			System.out.println(this);	
		}
		
	}