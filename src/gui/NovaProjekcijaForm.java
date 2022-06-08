package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import entities.Film;
import entities.Projekcija;
import entities.Sala;

public class NovaProjekcijaForm extends JDialog {
	
	private MainFrame mainFrame;
	private JTextField txtDay, txtMonth, txtYear, txtHour, txtMinute, txtCena;
	private JList lista1, lista2;
	private FilmListModel filmModel;
	private SalaListModel salaModel;
	
	public NovaProjekcijaForm(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void init() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		this.add(mainPanel);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Datum: "), gbc);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
		txtDay = new JTextField();
		txtDay.setColumns(2);
		txtMonth = new JTextField();
		txtMonth.setColumns(2);
		txtYear = new JTextField();
		txtYear.setColumns(4);
		datePanel.add(txtDay);
		datePanel.add(Box.createHorizontalStrut(5));
		datePanel.add(txtMonth);
		datePanel.add(Box.createHorizontalStrut(5));
		datePanel.add(txtYear);
		mainPanel.add(datePanel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Pocetak: "), gbc);
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
		txtHour = new JTextField();
		txtHour.setColumns(2);
		txtMinute = new JTextField();
		txtMinute.setColumns(2);
		timePanel.add(txtHour);
		timePanel.add(Box.createHorizontalStrut(5));
		timePanel.add(txtMinute);
		mainPanel.add(timePanel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Cena: "), gbc);
		txtCena = new JTextField();
		txtCena.setColumns(8);
		gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
		mainPanel.add(txtCena, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Film: "), gbc);
		JScrollPane scroll = new JScrollPane();
		lista1 = new JList();
		lista1.setCellRenderer(new FilmCellRenderer());
		scroll.setViewportView(lista1);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JPanel pomPanel = new JPanel(new BorderLayout());
		pomPanel.add(scroll);
		pomPanel.setMaximumSize(new Dimension(300, 300));
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		mainPanel.add(pomPanel, gbc);
		filmModel = new FilmListModel(mainFrame.getKartaControl().getAllFilms().stream()
				.map(FilmPanel::new).collect(Collectors.toList()));
		lista1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista1.setModel(filmModel);
		
		gbc.gridx = 1;
		gbc.gridy = 10;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		mainPanel.add(new JLabel("Sala: "), gbc);
		JScrollPane scroll2 = new JScrollPane();
		lista2 = new JList();
		scroll2.setViewportView(lista2);
		scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JPanel pomPanel2 = new JPanel(new BorderLayout());
		pomPanel2.add(scroll2);
		pomPanel2.setMaximumSize(new Dimension(300, 300));
		gbc.gridx = 1;
		gbc.gridy = 11;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		mainPanel.add(pomPanel2, gbc);
		salaModel = new SalaListModel(mainFrame.getSalaControl().getAllSale());
		lista2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista2.setModel(salaModel);
		
		JButton btnDodaj = new JButton("Dodaj");
		gbc.gridx = 0;
		gbc.gridy = 16;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		btnDodaj.addActionListener(this::dodaj);
		mainPanel.add(btnDodaj, gbc);
		JButton btnExit = new JButton("Izadji");
		gbc.gridx = 2;
		gbc.gridy = 16;
		gbc.gridwidth = 1;
		btnExit.addActionListener(x -> this.dispose());
		mainPanel.add(btnExit, gbc);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Projekcija");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void dodaj(ActionEvent e) {
		Projekcija nova = new Projekcija();
		nova.setId(Projekcija.getNextId());
		mainFrame.getMenadzerControl().saveProjekcija(nova);
		int day = 0;
		int month = 0;
		int year = 0;
		double cena = 0;
		int hour = 0;
		int minute = 0;
		try {
			day = Integer.parseInt(txtDay.getText());
			month = Integer.parseInt(txtMonth.getText());
			year = Integer.parseInt(txtYear.getText());
			cena = Double.parseDouble(txtCena.getText());
			hour = Integer.parseInt(txtHour.getText());
			minute = Integer.parseInt(txtMinute.getText());
		} catch(Exception x) {
			return;
		}
		FilmPanel filmP = (FilmPanel) lista1.getSelectedValue();
		Film film = filmP.getFilm();
		film.addProjekcija(nova);
		mainFrame.getMenadzerControl().saveFilm(film);
		Sala sala = (Sala) lista2.getSelectedValue();
		sala.addProjekcija(nova);
		mainFrame.getSalaControl().saveSala(sala);
		nova.setFilm(film);
		nova.setSala(sala);
		Calendar pocetak = Calendar.getInstance();
		pocetak.set(Calendar.YEAR, year);
		pocetak.set(Calendar.MONTH, month);
		pocetak.set(Calendar.DAY_OF_MONTH, day);
		pocetak.set(Calendar.HOUR_OF_DAY, hour);
		pocetak.set(Calendar.MINUTE, minute);
		Calendar kraj = (Calendar) pocetak.clone();
		kraj.add(Calendar.MINUTE, film.getTrajanje());
		nova.setCena(cena);
		nova.setBrojSlobodnihMesta(sala.getBrojKolona()*sala.getBrojRedova());
		nova.setDatumOd(pocetak);
		nova.setDatumDo(kraj);
		nova.setMesta(new byte[nova.getBrojSlobodnihMesta()]);
		mainFrame.getMenadzerControl().getAllPopusti().stream()
					.filter(x -> (x.getDatumOd().compareTo(nova.getDatumOd()) < 0) && (x.getDatumDo().compareTo(nova.getDatumOd()) > 0))
					.forEach(x -> {
						x.addProjekcija(nova);
						nova.addPopust(x);
						mainFrame.getMenadzerControl().savePopust(x);
					});
		if (mainFrame.getMenadzerControl().saveProjekcija(nova)) {
			this.dispose();
		}
	}
}
