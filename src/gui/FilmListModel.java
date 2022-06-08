package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import entities.Film;

public class FilmListModel extends AbstractListModel {
	
	List<FilmPanel> filmovi;
	
	public FilmListModel() {
		filmovi = new ArrayList<FilmPanel>();
	}
	
	public FilmListModel(List<FilmPanel> filmovi) {
		this.filmovi = filmovi;
	}
	
	public List<FilmPanel> getFilmovi() {
		return filmovi;
	}

	public void setFilmovi(List<FilmPanel> filmovi) {
		this.filmovi = filmovi;
	}

	@Override
	public int getSize() {
		return filmovi.size();
	}

	@Override
	public Object getElementAt(int index) {
		return filmovi.get(index);
	}

}
