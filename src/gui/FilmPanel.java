package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entities.Film;

public class FilmPanel extends JPanel {
	
	private Film film;
	
	public FilmPanel(Film film) {
		this.film = film;
		
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
		dataPanel.add(new JLabel(film.getNaziv()));
		dataPanel.add(new JLabel("Trajanje:\t"+film.getTrajanje()));
		
		this.add(dataPanel, BorderLayout.NORTH);
		ImageIcon image = null;
		if (film.getPoster() == null) {
			image = new ImageIcon("images/No_Image_Available.png");
		}
		else {
			image = new ImageIcon(film.getPoster());
		}
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(new JLabel(image));
		add(dataPanel);
		this.setMaximumSize(new Dimension(300, 150));
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}
	
}
