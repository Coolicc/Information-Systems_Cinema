package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entities.Popust;
import entities.Projekcija;

public class PopustForm extends JDialog {
	
	private MainFrame mainFrame;
	private JTextField txtProcenat, txtYear1, txtMonth1, txtDay1, txtYear2, txtMonth2, txtDay2;
	
	public PopustForm(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void init() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		this.add(mainPanel);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Procenat snizenja: "), gbc);
		txtProcenat = new JTextField();
		txtProcenat.setColumns(8);
		gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
		mainPanel.add(txtProcenat, gbc);
		
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
		mainPanel.add(new JLabel("Datum do: "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
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
		
		JButton btnDodaj = new JButton("Dodaj");
		gbc.gridx = 0;
		gbc.gridy = 16;
		gbc.gridwidth = 1;
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
		this.setTitle("Popust");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void dodaj(ActionEvent e) {
		Popust novi = new Popust();
		int day1 = 0;
		int month1 = 0;
		int year1 = 0;
		int day2 = 0;
		int month2 = 0;
		int year2 = 0;
		double procenat;
		try {
			day1 = Integer.parseInt(txtDay1.getText());
			month1 = Integer.parseInt(txtMonth1.getText());
			year1 = Integer.parseInt(txtYear1.getText());
			day2 = Integer.parseInt(txtDay2.getText());
			month2 = Integer.parseInt(txtMonth2.getText());
			year2 = Integer.parseInt(txtYear2.getText());
			procenat = Double.parseDouble(txtProcenat.getText());
		} catch(Exception x) {
			return;
		}
		Calendar pocetak = Calendar.getInstance();
		pocetak.set(Calendar.YEAR, year1);
		pocetak.set(Calendar.MONTH, month1);
		pocetak.set(Calendar.DAY_OF_MONTH, day1);
		Calendar kraj = Calendar.getInstance();
		kraj.set(Calendar.YEAR, year2);
		kraj.set(Calendar.MONTH, month2);
		kraj.set(Calendar.DAY_OF_MONTH, day2);
		novi.setDatumDo(kraj);
		novi.setDatumOd(pocetak);
		novi.setId(Popust.getNextId());
		novi.setProcenat(procenat);
		List<Projekcija> projekcije = mainFrame.getKartaControl().getAllProjekcije().stream()
				.filter(x -> (x.getDatumOd().compareTo(pocetak) >= 0) && (x.getDatumOd().compareTo(kraj) <= 0))
				.collect(Collectors.toList());
		novi.setProjekcije(projekcije);
		projekcije.stream().forEach(x -> {
			x.addPopust(novi);
			mainFrame.getMenadzerControl().saveProjekcija(x);
		});
		if (mainFrame.getMenadzerControl().savePopust(novi)) {
			this.dispose();
		}
	}
}
