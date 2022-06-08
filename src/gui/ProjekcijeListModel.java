package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import entities.Projekcija;
import entities.Sala;

public class ProjekcijeListModel extends AbstractListModel {

	private List<Projekcija> projekcije;
	
	public ProjekcijeListModel() {
		projekcije = new ArrayList<Projekcija>();
	}
	
	public ProjekcijeListModel(List<Projekcija> projekcije) {
		this.projekcije = projekcije;
	}
	
	public void setProjekcije(List<Projekcija> projekcije) {
		this.projekcije = projekcije;
	}
	
	public List<Projekcija> getProjekcije() {
		return projekcije;
	}

	@Override
	public int getSize() {
		return projekcije.size();
	}

	@Override
	public Object getElementAt(int index) {
		return projekcije.get(index);
	}
}
