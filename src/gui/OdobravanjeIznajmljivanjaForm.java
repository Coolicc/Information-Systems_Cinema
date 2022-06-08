package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import entities.Iznajmljivanje;

public class OdobravanjeIznajmljivanjaForm extends JDialog {
	
	private MainFrame mainFrame;
	private JList lista;
	private IznajmljivanjeListModel iznajmljivanjeModel;
	
	public OdobravanjeIznajmljivanjaForm(MainFrame mainFrame) {
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
		mainPanel.add(new JLabel("Iznajmljivanja: "), gbc);
//		JScrollPane scroll2 = new JScrollPane();
//		lista = new JList();
//		scroll2.setViewportView(lista);
		lista = new JList();
		JScrollPane scroll2 = new JScrollPane(lista);
		
		scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 5;
		mainPanel.add(scroll2, gbc);
		iznajmljivanjeModel = new IznajmljivanjeListModel(mainFrame.getSalaControl()
				.getAllIznajmljivanjas().stream()
				.filter(x -> (!x.isOdobreno()) && (x.getDatumOd()
				.compareTo(Calendar.getInstance())>0)).collect(Collectors.toList()));
		lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lista.setModel(iznajmljivanjeModel);
		
		JButton btnOdobri = new JButton("Odobri");
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		btnOdobri.addActionListener(this::odobri);
		mainPanel.add(btnOdobri, gbc);
		JButton btnExit = new JButton("Izadji");
		gbc.gridx = 2;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		btnExit.addActionListener(x -> this.dispose());
		mainPanel.add(btnExit, gbc);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Iznajmljivanja");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void odobri(ActionEvent e) {
		int[] indexi = lista.getSelectedIndices();
		List<Iznajmljivanje> listIznIzbaci = iznajmljivanjeModel.getIznajmljivanja();
		for (int i: indexi) {
			Iznajmljivanje iznajmljivanje = (Iznajmljivanje) iznajmljivanjeModel.getElementAt(i);
			iznajmljivanje.setOdobreno(true);
			listIznIzbaci.add(iznajmljivanje);
			mainFrame.getSalaControl().saveIznajmljivanje(iznajmljivanje);
		}
		iznajmljivanjeModel.getIznajmljivanja().removeAll(listIznIzbaci);
	}
}
