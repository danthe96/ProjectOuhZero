package windowManager.Listeners.DataListeners;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;

//Button Schlieﬂen:
	public class CloseEditorAction extends AbstractAction{
		
		public CloseEditorAction(){super();}
		public CloseEditorAction(String name){super(name);} 
		public CloseEditorAction(String name, Icon icon){super(name,icon);}  

	private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {		
			System.exit(0);
		}
		
	}