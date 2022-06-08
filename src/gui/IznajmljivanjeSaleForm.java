package gui;

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
import entities.Iznajmljivanje;
import entities.Projekcija;
import entities.Sala;

public class IznajmljivanjeSaleForm extends JDialog {

	private MainFrame mainFrame;
	private Sala sala;
	private JTextField txtDay1, txtMonth1, txtYear1, txtHour1, txtMinute1,
						txtDay2, txtMonth2, txtYear2, txtHour2, txtMinute2;
	private JList lista1;
	private FilmListModel filmModel;
	
	public IznajmljivanjeSaleForm(Sala sala, MainFrame mainFrame) {
		this.sala = sala;
		this.mainFrame = mainFrame;
	}
	
	public void init() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		this.add(mainPanel);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Datum od: "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		JPanel datePanel1 = new JPanel();
		datePanel1.setLayout(new BoxLayout(datePanel1, BoxLayout.X_AXIS));
		txtDay1 = new JTextField();
		txtDay1.setColumns(2);
		txtMonth1 = new JTextField();
		txtMonth1.setColumns(2);
		txtYear1 = new JTextField();
		txtYear1.setColumns(4);
		datePanel1.add(txtDay1);
		datePanel1.add(Box.createHorizontalStrut(5));
		datePanel1.add(txtMonth1);
		datePanel1.add(Box.createHorizontalStrut(5));
		datePanel1.add(txtYear1);
		mainPanel.add(datePanel1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Pocetak od: "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		JPanel timePanel1 = new JPanel();
		timePanel1.setLayout(new BoxLayout(timePanel1, BoxLayout.X_AXIS));
		txtHour1 = new JTextField();
		txtHour1.setColumns(2);
		txtMinute1 = new JTextField();
		txtMinute1.setColumns(2);
		timePanel1.add(txtHour1);
		timePanel1.add(Box.createHorizontalStrut(5));
		timePanel1.add(txtMinute1);
		mainPanel.add(timePanel1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Datum do: "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		JPanel datePanel2 = new JPanel();
		datePanel2.setLayout(new BoxLayout(datePanel2, BoxLayout.X_AXIS));
		txtDay2 = new JTextField();
		txtDay2.setColumns(2);
		txtMonth2 = new JTextField();
		txtMonth2.setColumns(2);
		txtYear2 = new JTextField();
		txtYear2.setColumns(4);
		datePanel2.add(txtDay2);
		datePanel2.add(Box.createHorizontalStrut(5));
		datePanel2.add(txtMonth2);
		datePanel2.add(Box.createHorizontalStrut(5));
		datePanel2.add(txtYear2);
		mainPanel.add(datePanel2, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Pocetak do: "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		JPanel timePanel2 = new JPanel();
		timePanel2.setLayout(new BoxLayout(timePanel2, BoxLayout.X_AXIS));
		txtHour2 = new JTextField();
		txtHour2.setColumns(2);
		txtMinute2 = new JTextField();
		txtMinute2.setColumns(2);
		timePanel2.add(txtHour2);
		timePanel2.add(Box.createHorizontalStrut(5));
		timePanel2.add(txtMinute2);
		mainPanel.add(timePanel2, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Film (opcionalno): "), gbc);
		JScrollPane scroll = new JScrollPane();
		lista1 = new JList();
		lista1.setCellRenderer(new FilmCellRenderer());
		scroll.setViewportView(lista1);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		mainPanel.add(scroll, gbc);
		filmModel = new FilmListModel(mainFrame.getKartaControl().getAllFilms().stream()
				.map(FilmPanel::new).collect(Collectors.toList()));
		lista1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista1.setModel(filmModel);
		
		JButton btnDodaj = new JButton("Posalji zahtev");
		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		btnDodaj.addActionListener(this::posaljiZahtev);
		mainPanel.add(btnDodaj, gbc);
		JButton btnExit = new JButton("Izadji");
		gbc.gridx = 2;
		gbc.gridy = 11;
		gbc.gridwidth = 1;
		btnExit.addActionListener(x -> this.dispose());
		mainPanel.add(btnExit, gbc);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Iznajmljivanje");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void posaljiZahtev(ActionEvent e) {
		Iznajmljivanje novo = new Iznajmljivanje();
		novo.setId(Iznajmljivanje.getNextId());
		mainFrame.getSalaControl().saveIznajmljivanje(novo);
		int day1 = 0;
		int month1 = 0;
		int year1 = 0;
		int hour1 = 0;
		int minute1 = 0;
		int day2 = 0;
		int month2 = 0;
		int year2 = 0;
		int hour2 = 0;
		int minute2 = 0;
		try {
			day1 = Integer.parseInt(txtDay1.getText());
			month1 = Integer.parseInt(txtMonth1.getText());
			year1 = Integer.parseInt(txtYear1.getText());
			hour1 = Integer.parseInt(txtHour1.getText());
			minute1 = Integer.parseInt(txtMinute1.getText());
			day2 = Integer.parseInt(txtDay2.getText());
			month2 = Integer.parseInt(txtMonth2.getText());
			year2 = Integer.parseInt(txtYear2.getText());
			hour2 = Integer.parseInt(txtHour2.getText());
			minute2 = Integer.parseInt(txtMinute2.getText());
		} catch(Exception x) {
			return;
		}
		if (!lista1.isSelectionEmpty()) {
			FilmPanel filmP = (FilmPanel) lista1.getSelectedValue();
			Film film = filmP.getFilm();
			film.addIznajmljivanje(novo);
			novo.setFilm(film);
			mainFrame.getMenadzerControl().saveFilm(film);
		}
		Calendar pocetak = Calendar.getInstance();
		pocetak.set(Calendar.YEAR, year1);
		pocetak.set(Calendar.MONTH, month1);
		pocetak.set(Calendar.DAY_OF_MONTH, day1);
		pocetak.set(Calendar.HOUR_OF_DAY, hour1);
		pocetak.set(Calendar.MINUTE, minute1);
		novo.setDatumOd(pocetak);
		Calendar kraj = Calendar.getInstance();
		kraj.set(Calendar.YEAR, year2);
		kraj.set(Calendar.MONTH, month2);
		kraj.set(Calendar.DAY_OF_MONTH, day2);
		kraj.set(Calendar.HOUR_OF_DAY, hour2);
		kraj.set(Calendar.MINUTE, minute2);
		novo.setDatumDo(kraj);
		sala.addIznajmljivanje(novo);
		novo.setSala(sala);
		mainFrame.getSalaControl().saveSala(sala);
		mainFrame.getKlijent().addIznajmljivanje(novo);
		novo.setKlijent(mainFrame.getKlijent());
		mainFrame.getUtilControl().saveKlijent(mainFrame.getKlijent());
		if (mainFrame.getSalaControl().saveIznajmljivanje(novo)) {
			this.dispose();
		}
	}
}
