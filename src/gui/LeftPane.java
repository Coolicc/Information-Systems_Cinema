package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entities.Zanr;

public class LeftPane extends JPanel {
	
	private MainFrame mainFrame;
	private JComboBox<Zanr> zanrCB;
	private JComboBox<Calendar> dateCB;
	private JButton prikaziSve;
	
	public LeftPane(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void init() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		changeSize(this, 200, Integer.MAX_VALUE);
		
		JLabel zanrLbl = new JLabel("Zanr: ");
		zanrLbl.setAlignmentX(1f);
		JLabel dateLbl = new JLabel("Datum: ");
		dateLbl.setAlignmentX(1f);
		
		zanrCB = new JComboBox<Zanr>();
		mainFrame.getKartaControl().getAllZanrs().stream().forEach(x -> zanrCB.addItem(x));
		changeSize(zanrCB, Integer.MAX_VALUE, 25);
		zanrCB.addActionListener(this::filterZanr);
		
		dateCB = new JComboBox<Calendar>();
		for (int i = 0; i < 11; i++) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, i);
			dateCB.addItem(c);
		}
		changeSize(dateCB, Integer.MAX_VALUE, 25);
		dateCB.addActionListener(this::filterDate);
		
		prikaziSve = new JButton("Prikazi sve filmove");
		changeSize(prikaziSve, Integer.MAX_VALUE, 25);
		prikaziSve.addActionListener(this::filterAll);
		
		add(zanrLbl);
		add(zanrCB);
		add(dateLbl);
		add(dateCB);
		add(prikaziSve);
		this.setVisible(true);
	}
	
	private void changeSize(JComponent comp, int width, int height) {
		comp.setMinimumSize(new Dimension(width, height));
		comp.setPreferredSize(new Dimension(width, height));
		comp.setMaximumSize(new Dimension(width, height));
	}
	
	private void filterZanr(ActionEvent e) {
		Zanr z = (Zanr) zanrCB.getSelectedItem();
		FilmListModel flm = (FilmListModel) mainFrame.getListModel();
		flm.setFilmovi(mainFrame.getKartaControl().getAllFilmsByZanr(z).stream()
				.map(FilmPanel::new).collect(Collectors.toList()));
	}
	
	private void filterDate(ActionEvent e) {
		Calendar c = (Calendar) dateCB.getSelectedItem();
		FilmListModel flm = (FilmListModel) mainFrame.getListModel();
		flm.setFilmovi(mainFrame.getKartaControl().getAllFilmsByDate(c).stream()
				.map(FilmPanel::new).collect(Collectors.toList()));
	}
	
	private void filterAll(ActionEvent e) {
		FilmListModel flm = (FilmListModel) mainFrame.getListModel();
		flm.setFilmovi(mainFrame.getKartaControl().getAllFilms().stream()
				.map(FilmPanel::new).collect(Collectors.toList()));
	}
}
