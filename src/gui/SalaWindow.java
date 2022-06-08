package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import entities.Sala;

public class SalaWindow extends JDialog {
	
	private Sala sala;
	private MainFrame mainFrame;
	
	public SalaWindow(Sala sala, MainFrame mainFrame) {
		this.sala = sala;
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
		gbc.gridheight = 1;
		mainPanel.add(new JLabel("Sala: "+sala.getId()), gbc);
		
		gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
		gbc.gridheight = 1;
		mainPanel.add(new JLabel("Broj redova: "+sala.getBrojRedova()), gbc);
		
		gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
		gbc.gridheight = 1;
		mainPanel.add(new JLabel("Broj kolona: "+sala.getBrojKolona()), gbc);
		
		gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
		gbc.gridheight = 1;
		mainPanel.add(new JLabel("Ukupan broj sedista: "+sala.getBrojKolona()*sala.getBrojRedova()), gbc);
		
		JButton btnIznajmi = new JButton("Iznajmi");
		gbc.gridx = 0;
		gbc.gridy = 14;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		btnIznajmi.addActionListener(this::iznajmi);
		mainPanel.add(btnIznajmi, gbc);
		JButton btnExit = new JButton("Izadji");
		gbc.gridx = 2;
		gbc.gridy = 14;
		gbc.gridwidth = 1;
		btnExit.addActionListener(x -> this.dispose());
		mainPanel.add(btnExit, gbc);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Sala");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void iznajmi(ActionEvent e) {
		IznajmljivanjeSaleForm isf = new IznajmljivanjeSaleForm(sala, mainFrame);
		isf.init();
	}
}
