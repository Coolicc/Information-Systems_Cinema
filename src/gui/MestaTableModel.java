package gui;

import javax.swing.table.AbstractTableModel;

public class MestaTableModel extends AbstractTableModel {

	private byte[] sedista;
	private int redN, kolonaN;
	
	public MestaTableModel(byte[] sedista, int redN, int kolonaN) {
		this.sedista = sedista;
		this.redN = redN;
		this.kolonaN = kolonaN;
	}
	
	@Override
	public int getRowCount() {
		return redN;
	}

	@Override
	public int getColumnCount() {
		return kolonaN;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (sedista[rowIndex*kolonaN+columnIndex] > 0) {
			return "Z";
		}
		return "";
	}

}
