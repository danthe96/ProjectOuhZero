package editor;
//TODO rebuild
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import loaders.MaterialData;
import loaders.MaterialLoader;
public class MaterialViewer extends JFrame implements ActionListener, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MaterialData m;
	JTextField Name;
	JTextField MaterialPath;
	JTable table;
	JFrame testviewer;
	JMEMaterialViewer jmeview;
	JPanel panel;
	Thread t;
	static String[] columnNames= {"Name","Value","Type","x"};
	
	
	public MaterialViewer(MaterialData m) {
		if (m== null) {
			m = new MaterialData(true);
			setTitle("Add Material");
		}
		else setTitle(m.getName());
		this.m = m;
		
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		panel = new JPanel();
		add(panel, BorderLayout.PAGE_START);
		
		panel.setLayout(new GridLayout(4,2)); 
		panel.add(new JLabel("Name"));
		Name = new JTextField(m.getName());
		panel.add(Name);

		panel.add(new JLabel("Material Path"));
		MaterialPath = new JTextField(m.getMaterialPath());
		panel.add(MaterialPath);
		
		JButton button = new JButton("Add");
		button.addActionListener(this);
		button.setActionCommand("add");
		panel.add(button);
		
		button = new JButton("View in JME");
		button.addActionListener(this);
		button.setActionCommand("Update");
		panel.add(button);
		
		testviewer = new JFrame();
		testviewer.setSize(150,150);
		testviewer.setVisible(true);
		
		initWindowListener();


		inittable();
		setSize(300,800);
		setVisible(true);
		
	}
	private void startJMEViewer() {
		try {
			jmeview.stop();
		}
		catch (Exception e) {};
		final MaterialData md = m;
		t = new Thread(new Runnable() {
		    @Override
		    public void run() {
		        jmeview = new JMEMaterialViewer(md);
		        jmeview.start();
		        
		    }
		});

		t.start();
	}
	private void initWindowListener() {
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				m.setName(Name.getText());
				m.setMaterialPath(MaterialPath.getText());
				MaterialLoader.getLoader().updateMaterialData(m);
				testviewer.setVisible(false);
				
				try {
					jmeview.destroy();
				}
				catch(Exception e) {}
			}

			@Override
			public void windowActivated(WindowEvent e) {
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				
			}
		});
	}
	private void inittable() {
		try {
			remove(table);
		}
		catch (Exception e) {};
		
		final Object[][] rowData = m.getTableData();
		table = new JTable(new AbstractTableModel() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public String getColumnName(int col) {
		        return columnNames[col].toString();
		    }
		    public int getRowCount() { return rowData.length; }
		    public int getColumnCount() { return columnNames.length; }
		    public Object getValueAt(int row, int col) {
		        return rowData[row][col];
		    }
		    public boolean isCellEditable(int row, int col)
		        { return true; }
		    public void setValueAt(Object value, int row, int col) {
		        rowData[row][col] = value;
		        updateData(row,col, value);
		        fireTableCellUpdated(row, col);
		    }
		});
		table.addMouseListener(this);
		add(table, BorderLayout.CENTER);
		setVisible(true);
	}
	void updateData(int ID, int col, Object value) {
		m.changeData(ID, col, value);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if ("add".equals(arg0.getActionCommand())) {

			Object[] possibilities = {"Int", "Flo", "Col","Tex","boo"};
			String Type = (String)JOptionPane.showInputDialog(
			                    new JFrame(),
			                    "Select Type",
			                    "Select Type", JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    possibilities,
			                    "Int");
			String Name = JOptionPane.showInputDialog("Enter a name");
			Object value=null;
			if (Type == "Col") {
				value = JColorChooser.showDialog(null, "Farbe", null );
			}
			if (value == null) value = JOptionPane.showInputDialog("Enter a value");
			m.add(Name,Type, value);
			inittable();
		}
		if ("Update".equals(arg0.getActionCommand())) {
			inittable();
			startJMEViewer();
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		int row = table.rowAtPoint(e.getPoint());
		int column = table.columnAtPoint(e.getPoint());
		Object[][] tabledata = m.getTableData();
		if(row>-1 && row < table.getRowCount()){
		   if (column == 3 && 0 == JOptionPane.showConfirmDialog(new JFrame(), "Sicher?")) {
			   m.remove(row);
		   }
		
		
		if ("Col".equals(tabledata[row][2])) {
			if (column == 1) {
				updateData(row,column,JColorChooser.showDialog(null, "Farbe", (Color)tabledata[row][1] ));

			}
			
			tabledata = m.getTableData();
			Graphics g = testviewer.getGraphics();
			g.setColor((Color) tabledata[row][1]);
			g.fillRect(0, 0, testviewer.getWidth(), testviewer.getHeight());
			testviewer.setVisible(true);
		}
		if ("Tex".equals(tabledata[row][2])) {
			 try {
				Image  image = ImageIO .read(new File ((String) tabledata[row][1] ));
				testviewer.getGraphics().drawImage(image, 0, 0, testviewer.getWidth(), testviewer.getHeight(), null);
				testviewer.setVisible(true);
			} catch (IOException e1) {
				//JOptionPane.showMessageDialog(null, "Ooooops, that is not a picture :�");
				e1.printStackTrace();
			}
		}
		}
	}

}
