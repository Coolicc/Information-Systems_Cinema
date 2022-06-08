package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import entities.Sala;
import entities.Zanr;

public class ZanrListModel extends AbstractListModel {
	
	private List<Zanr> zanrovi;
	
	public ZanrListModel() {
		zanrovi = new ArrayList<Zanr>();
	}
	
	public ZanrListModel(List<Zanr> zanrovi) {
		this.zanrovi = zanrovi;
	}

	@Override
	public int getSize() {
		return zanrovi.size();
	}

	@Override
	public Object getElementAt(int index) {
		return zanrovi.get(index);
	}
}
