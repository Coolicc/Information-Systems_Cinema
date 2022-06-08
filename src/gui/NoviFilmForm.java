package gui;

import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import entities.Zanr;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import entities.Film;

public class NoviFilmForm extends JDialog {
	
	private MainFrame mainFrame;
	private JTextField txtName, txtTrajanje;
	private byte[] image;
	private JFileChooser fchChooser;
	private JTextArea txtDescription;
	private JList lista;
	private ZanrListModel zanrModel;
	
	public NoviFilmForm(MainFrame mainFrame) {
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
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(new JLabel("Naziv filma: "), gbc);
		txtName = new JTextField();
		txtName.setColumns(20);
		gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
		mainPanel.add(txtName, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Trajanje: "), gbc);
		txtTrajanje = new JTextField();
		txtTrajanje.setColumns(4);
		gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
		mainPanel.add(txtTrajanje, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Poster: "), gbc);
		JButton btnAvatar = new JButton("Izaberite sliku");
		gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
		mainPanel.add(btnAvatar, gbc);
		image = null;
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "png", "gif", "jpeg");
		fchChooser = new JFileChooser();
		fchChooser.setFileFilter(filter);
		btnAvatar.addActionListener(x -> {
			int returnVal = fchChooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File fileIn = fchChooser.getSelectedFile();
				BufferedImage bImage = null;
				try {
					bImage = ImageIO.read(fileIn);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				BufferedImage outputImage = new BufferedImage(100, 100, bImage.getType());
				Graphics2D g2d = outputImage.createGraphics();
		        g2d.drawImage(bImage, 0, 0, 100, 100, null);
		        g2d.dispose();
		        String formatName = fileIn.getName().substring(fileIn.getName().lastIndexOf(".") + 1);
		        File fileOut = new File("temp."+formatName);
		        try {
					fileOut.createNewFile();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
		        try {
					ImageIO.write(outputImage, formatName, fileOut);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try (FileInputStream in = new FileInputStream(fileOut.getAbsolutePath())) {
					int b;
					List<Byte> imgByte = new ArrayList<Byte>();
					while ((b = in.read())!=-1) {
						imgByte.add((byte) b);
						System.out.println(b);
					}
					image = new byte[imgByte.size()];
					for (int i = 0; i < image.length; i++) {
						image[i] = imgByte.get(i);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (fileOut != null) fileOut.delete();
			}
		});
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Opis: "), gbc);
		JScrollPane scroll = new JScrollPane();
		txtDescription = new JTextArea();
		scroll.setViewportView(txtDescription);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 3;
		mainPanel.add(scroll, gbc);
		txtDescription.setColumns(30);
		txtDescription.setRows(4);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Zanrovi: "), gbc);
		JScrollPane scroll2 = new JScrollPane();
		lista = new JList();
		scroll2.setViewportView(lista);
		scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 3;
		gbc.gridheight = 5;
		mainPanel.add(scroll2, gbc);
		zanrModel = new ZanrListModel(mainFrame.getKartaControl().getAllZanrs());
		lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lista.setModel(zanrModel);
		
		JButton btnDodaj = new JButton("Dodaj");
		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		btnDodaj.addActionListener(this::dodaj);
		mainPanel.add(btnDodaj, gbc);
		JButton btnExit = new JButton("Izadji");
		gbc.gridx = 2;
		gbc.gridy = 11;
		gbc.gridwidth = 1;
		btnExit.addActionListener(x -> this.dispose());
		mainPanel.add(btnExit, gbc);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Film");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void dodaj(ActionEvent e) {
		Film novi = new Film();
		String naziv = txtName.getText();
		String opis = txtDescription.getText();
		int trajanje;
		try {
			trajanje = Integer.parseInt(txtTrajanje.getText());
		}catch (Exception x) {
			return;
		}
		int[] indexi = lista.getSelectedIndices();
		for (int i: indexi) {
			Zanr z = (Zanr)zanrModel.getElementAt(i);
			novi.addZanr(z);
			mainFrame.getMenadzerControl().saveZanr(z);
		}
		novi.setId(Film.getNextId());
		novi.setNaziv(naziv);
		novi.setOpis(opis);
		novi.setTrajanje(trajanje);
		novi.setPoster(image);
		if (mainFrame.getMenadzerControl().saveFilm(novi)) {
			this.dispose();
		}
	}
}
