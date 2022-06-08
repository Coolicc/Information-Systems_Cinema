package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.stream.Collectors;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import entities.Film;
import entities.Projekcija;

public class FilmWindow extends JDialog {
	
	private Film film;
	private MainFrame mainFrame;
	private JList lista;
	private ProjekcijeListModel projekcijeModel;
	
	public FilmWindow(Film film, MainFrame mainFrame) {
		this.film = film;
		this.mainFrame = mainFrame;
	}
	
	public void init() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		this.add(mainPanel);
		
		gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
		byte[] imageData = film.getPoster();
		Icon image = null;
		if (imageData == null) {
			image = new ImageIcon("./images/No_Image_Available.png");
		} else {
			image = new ImageIcon(imageData);
		}
		mainPanel.add(new JLabel(image), gbc);
		
		gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
		gbc.gridheight = 1;
		mainPanel.add(new JLabel("Naziv: "+film.getNaziv()), gbc);
		
		gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
		gbc.gridheight = 1;
		mainPanel.add(new JLabel("Trajanje: "+film.getTrajanje()+"minuta"), gbc);
		
		gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
		gbc.gridheight = 1;
		mainPanel.add(new JLabel("Kratak opis:"), gbc);
		gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 5;
		gbc.gridheight = 1;
		JTextArea txtOpis = new JTextArea();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(txtOpis);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		txtOpis.setLineWrap(true);
		txtOpis.setEditable(false);
		txtOpis.setColumns(20);
		txtOpis.setRows(5);
		txtOpis.setText(film.getOpis());
		mainPanel.add(scroll, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 8;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Projekcije: "), gbc);
		JScrollPane scroll2 = new JScrollPane();
		lista = new JList();
		scroll2.setViewportView(lista);
		scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.gridwidth = 5;
		gbc.gridheight = 5;
		mainPanel.add(scroll2, gbc);
		projekcijeModel = new ProjekcijeListModel(film.getProjekcije().stream()
					.filter(x -> x.getDatumOd().compareTo(Calendar.getInstance()) > 0)
					.collect(Collectors.toList()));
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setModel(projekcijeModel);
		
		JButton btnOdobri = new JButton("Kupi kartu");
		gbc.gridx = 2;
		gbc.gridy = 14;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		btnOdobri.addActionListener(this::kupi);
		mainPanel.add(btnOdobri, gbc);
		JButton btnExit = new JButton("Izadji");
		gbc.gridx = 4;
		gbc.gridy = 14;
		gbc.gridwidth = 1;
		btnExit.addActionListener(x -> this.dispose());
		mainPanel.add(btnExit, gbc);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Film");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void kupi(ActionEvent e) {
		KupovinaKarteForm kkf = new KupovinaKarteForm((Projekcija) lista.getSelectedValue(), mainFrame);
		kkf.init();
	}
}
