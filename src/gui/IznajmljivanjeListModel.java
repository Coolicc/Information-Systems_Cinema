package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import entities.Iznajmljivanje;

public class IznajmljivanjeListModel extends AbstractListModel {
	
	List<Iznajmljivanje> iznajmljivanja;
	
	public IznajmljivanjeListModel() {
		iznajmljivanja = new ArrayList<Iznajmljivanje>();
	}
	
	public IznajmljivanjeListModel(List<Iznajmljivanje> iznajmljivanja) {
		this.iznajmljivanja = iznajmljivanja;
	}
	
	public List<Iznajmljivanje> getIznajmljivanja() {
		return iznajmljivanja;
	}

	public void setIznajmljivanja(List<Iznajmljivanje> iznajmljivanja) {
		this.iznajmljivanja = iznajmljivanja;
	}

	@Override
	public int getSize() {
		return iznajmljivanja.size();
	}

	@Override
	public Object getElementAt(int index) {
		return iznajmljivanja.get(index);
	}
}
