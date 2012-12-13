package editor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import loaders.MaterialData;
import loaders.MaterialLoader;

public class MaterialManager extends JFrame implements ActionListener, MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MaterialLoader ml = MaterialLoader.getLoader();
	String[] columnNames = {"Name","edit","del"};
	JTable table;
	JButton add;
	public MaterialManager() {

		setLayout(new GridLayout(4,2)); 
		JButton update = new JButton("update");
		update.addActionListener(this);
		add(update);
		
		
		add = new JButton("add");
		add.setActionCommand("add");
		add.addActionListener(this);
		add(add);
		
		inittable();
		setSize(300,150);
	
	}
	private void inittable() {
		try {
			remove(table);
		}
		catch (Exception e) {};
		
		final Object[][] rowData = new Object[ml.knownmaterials.size()][3];
		for (int i=0; i<rowData.length; i++) {
			rowData[i][0] = ml.knownmaterials.get(i);
			rowData[i][1] = "edit";
			rowData[i][2] = " x ";
		}
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
		        fireTableCellUpdated(row, col);
		    }
		});
		table.addMouseListener(this);
		add(table);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		inittable();
		if (arg0.getActionCommand().equals("add")) editMaterial(null);
		
	}
	private void editMaterial(MaterialData md) {
		new MaterialViewer(md);
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {

		   int row = table.rowAtPoint(e.getPoint());
		  int column = table.columnAtPoint(e.getPoint());
		   if (column == 2 && 0 == JOptionPane.showConfirmDialog(new JFrame(), "Sicher?")) {
			   ml.removeMaterialData(ml.knownmaterials.get(row));
		   }
		   if (column == 1) editMaterial(ml.getMaterialData(row));
		   inittable();
		}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
