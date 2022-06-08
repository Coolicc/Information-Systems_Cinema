package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import entities.Sala;

public class SalaListModel extends AbstractListModel {

	private List<Sala> sale;
	
	public SalaListModel() {
		sale = new ArrayList<Sala>();
	}
	
	public SalaListModel(List<Sala> sale) {
		this.sale = sale;
	}

	@Override
	public int getSize() {
		return sale.size();
	}

	@Override
	public Object getElementAt(int index) {
		return sale.get(index);
	}
	
}
