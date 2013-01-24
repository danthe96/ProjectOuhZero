package windowManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MatPanel extends JPanel{
	public void initButtons(int j, String[] path){
		BufferedImage[] buttonIcon = new BufferedImage[j];
		JButton[] matButton = new JButton[j];
		
		for(int i=0;i<j;i++)
		try {
			buttonIcon[i] = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(path[i]));  
			matButton[i] = new JButton(new ImageIcon(buttonIcon[i].getScaledInstance(50, 50, 0)));
			matButton[i].addActionListener( new ActionListener() {
				  @Override public void actionPerformed( ActionEvent e ) {
				      
				  }
				} );
			this.add(matButton[i]);
			System.out.println("dx");
		} catch (IOException e) {System.out.println("acfv");}
}
}
