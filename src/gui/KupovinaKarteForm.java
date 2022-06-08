package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import entities.Karta;
import entities.Projekcija;

public class KupovinaKarteForm extends JDialog {
	
	private MainFrame mainFrame;
	private Projekcija projekcija;
	private JTable table;
	private MestaTableModel model;
	
	public KupovinaKarteForm(Projekcija projekcija, MainFrame mainFrame) {
		this.projekcija = projekcija;
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
		mainPanel.add(new JLabel("Izaberite sediste: "), gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 5;
		table = new JTable();
		JScrollPane scroll2 = new JScrollPane();
		scroll2.setViewportView(table);
		scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanel.add(scroll2, gbc);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setCellSelectionEnabled(true);
		model = new MestaTableModel(projekcija.getMesta(), projekcija.getSala().getBrojRedova(),
				projekcija.getSala().getBrojKolona());
		table.setModel(model);
		
		JButton btnKupi = new JButton("Kupi");
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		btnKupi.addActionListener(this::kupi);
		mainPanel.add(btnKupi, gbc);
		JButton btnExit = new JButton("Izadji");
		gbc.gridx = 2;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		btnExit.addActionListener(x -> this.dispose());
		mainPanel.add(btnExit, gbc);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Karta");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void kupi(ActionEvent e) {
		Karta nova = new Karta();
		nova.setId(Karta.getNextId());
		mainFrame.getKartaControl().saveKarta(nova);
		nova.setProjekcija(projekcija);
		projekcija.addKarta(nova);
		mainFrame.getMenadzerControl().saveProjekcija(projekcija);
		nova.setKlijent(mainFrame.getKlijent());
		mainFrame.getKlijent().addKarta(nova);
		mainFrame.getUtilControl().saveKlijent(mainFrame.getKlijent());
		nova.setRed(table.getSelectedRow());
		nova.setKolona(table.getSelectedColumn());
		projekcija.getMesta()[table.getColumnCount()*table.getSelectedRow()+table.getSelectedColumn()] = 1;
		if (mainFrame.getKartaControl().saveKarta(nova)) {
			if (JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(this,
				"Hocete li da istampate kartu?", "Stampanje",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, null, null)) {
					mainFrame.getKartaControl().stampaj(nova);
			}
			this.dispose();
		}
	}

}
