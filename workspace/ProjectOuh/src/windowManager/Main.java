package windowManager;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import windowManager.Listeners.DataListeners.CloseEditorAction;
import windowManager.Listeners.DataListeners.LoadLevelAction;
import windowManager.Listeners.DataListeners.NewLevelAction;
import windowManager.Listeners.DataListeners.SaveLevelAction;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;

import editor.BasicRoomBuilder;

public class Main {

	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// Einstellungen für die JME
				AppSettings settings = new AppSettings(true);  
				settings.setWidth(640);
				settings.setHeight(480);
				settings.setFrameRate(120);
				
				
				BasicRoomBuilder canvasApplication = new BasicRoomBuilder();
				canvasApplication.setSettings(settings);
				canvasApplication.createCanvas(); // create canvas!
				JmeCanvasContext ctx = (JmeCanvasContext) canvasApplication.getContext();
				ctx.setSystemListener(canvasApplication);
				Dimension dim = new Dimension(640, 480);
				ctx.getCanvas().setPreferredSize(dim);
				
				
				JFrame window = new JFrame("Swing Application");
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				// add all your Swing components ...		    	  
				JPanel panel = new JPanel(new FlowLayout()); // a panel
				
				JMenuBar menuBar = new JMenuBar();
				JMenu fileMenu = new JMenu( "Datei", true);
				menuBar.add( fileMenu );
				
				fileMenu.add( new NewLevelAction("Neu") );
				fileMenu.add( new LoadLevelAction("Öffnen") );
				fileMenu.add( new SaveLevelAction("Speichern") );
				fileMenu.add( new CloseEditorAction("Beenden") );
				
				JMenu helpMenu = new JMenu( "Hilfe" );
				menuBar.add( helpMenu );
				helpMenu.add( new JMenuItem("Über das Programm") );
				
				window.setJMenuBar( menuBar );
				
				JPanel p=new JPanel();
				Dimension d =new Dimension(240,480);
				p.setSize(d);
				p.setPreferredSize(d);
				p.setMinimumSize(d);
					
				JTabbedPane tabbedPane = new JTabbedPane();
				tabbedPane.addTab("Material", p);
				tabbedPane.addTab("Model", new JPanel());
				tabbedPane.addTab("Platzhalter", new JPanel());
				
				panel.add(tabbedPane);
				
				// add the JME canvas
				panel.add(ctx.getCanvas());
				
				window.add(panel);
				window.pack();
				window.setVisible(true);
				
				canvasApplication.startCanvas();
			}
		});

	}

}
