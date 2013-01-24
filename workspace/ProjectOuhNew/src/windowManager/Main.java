package windowManager;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import windowManager.ButtonActions.CloseEditorAction;
import windowManager.ButtonActions.LoadLevelAction;
import windowManager.ButtonActions.NewLevelAction;
import windowManager.ButtonActions.SaveLevelAction;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;

import editor.SwingCanvasTest;

public class Main {
	
	static SwingCanvasTest canvasApplication;

	
	public static void main(String[] args) {

		java.awt.EventQueue.invokeLater(new Runnable() {
		      public void run() {
		    	  AppSettings settings = new AppSettings(true);
		    	  settings.setResolution(640, 480);
		    	  settings.setBitsPerPixel(32);
		    	  settings.setRenderer("LWJGL-OpenGL2");
		    	  settings.setSamples(4);
		    	  settings.setFrameRate(120);
		    	  
		    	  
		    	  
		    	  canvasApplication = new SwingCanvasTest();
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
					
					fileMenu.add( new NewLevelAction(canvasApplication,"Neu") );
					fileMenu.add( new LoadLevelAction("Öffnen") );
					fileMenu.add( new SaveLevelAction("Speichern") );
					fileMenu.add( new CloseEditorAction("Beenden") );
					
					JMenu helpMenu = new JMenu( "Hilfe" );
					menuBar.add( helpMenu );
					helpMenu.add( new JMenuItem("Über das Programm") );
					
					window.setJMenuBar( menuBar );
					
					
					MatPanel p = new MatPanel();
					
					Dimension d = new Dimension(240,480);
					p.setSize(d);
					p.setPreferredSize(d);
					p.setMinimumSize(d);
						
					JTabbedPane tabbedPane = new JTabbedPane();
					tabbedPane.addTab("Material", p);
					tabbedPane.addTab("Model", new JPanel());
					tabbedPane.addTab("Platzhalter", new JPanel());
					
					String[] paths = {"Assets/test.png","Assets/Wandtest.png"};
					
					p.initButtons(paths.length, paths);
					
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
