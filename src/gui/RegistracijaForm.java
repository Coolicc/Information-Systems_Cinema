package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entities.Klijent;

public class RegistracijaForm extends JDialog {
	
	private MainFrame mainFrame;
	private JTextField txtUsername, txtName, txtSurname, txtEmail, txtDay, txtMonth, txtYear;
	private JPasswordField txtPassword1, txtPassword2;
	
	public RegistracijaForm(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void init() {
		this.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		this.add(mainPanel);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(new JLabel("Korisnicko ime: "), gbc);
		txtUsername = new JTextField();
		txtUsername.setColumns(20);
		gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
		mainPanel.add(txtUsername, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Lozinka: "), gbc);
		txtPassword1 = new JPasswordField();
		txtPassword1.setColumns(20);
		gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
		mainPanel.add(txtPassword1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Ponovite lozinku: "), gbc);
		txtPassword2 = new JPasswordField();
		txtPassword2.setColumns(20);
		gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
		mainPanel.add(txtPassword2, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Ime: "), gbc);
		txtName = new JTextField();
		txtName.setColumns(20);
		gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
		mainPanel.add(txtName, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Prezime: "), gbc);
		txtSurname = new JTextField();
		txtSurname.setColumns(20);
		gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
		mainPanel.add(txtSurname, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("E-Mail: "), gbc);
		txtEmail = new JTextField();
		txtEmail.setColumns(20);
		gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
		mainPanel.add(txtEmail, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		mainPanel.add(new JLabel("Datum rodjenja: "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 6;
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
		
		JButton btnRegister = new JButton("Registruj se");
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		btnRegister.addActionListener(this::register);
		mainPanel.add(btnRegister, gbc);
		JButton btnExit = new JButton("Izadji");
		gbc.gridx = 2;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		btnExit.addActionListener(x -> this.dispose());
		mainPanel.add(btnExit, gbc);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Registracija");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void register(ActionEvent e) {
		Klijent novi = new Klijent();
		String name = txtName.getText();
		String surname = txtSurname.getText();
		String username = txtUsername.getText();
		String password1 = new String(txtPassword1.getPassword());
		String password2 = new String(txtPassword2.getPassword());
		String mail = txtEmail.getText();
		int day = 0;
		int month = 0;
		int year = 0;
		try {
			day = Integer.parseInt(txtDay.getText());
			month = Integer.parseInt(txtMonth.getText());
			year = Integer.parseInt(txtYear.getText());
		} catch(Exception x) {
			return;
		}
		if (!password1.equals(password2) || day>31 || day<1 || month<1 || year<1
				|| month>12 || year<1900) {
			return;
		}
		Calendar dob = Calendar.getInstance();
		dob.set(Calendar.YEAR, year);
		dob.set(Calendar.MONTH, month);
		dob.set(Calendar.DAY_OF_MONTH, day);
		novi.setDatumRodjenja(dob);
		novi.setEmail(mail);
		novi.setIme(name);
		novi.setPassword(password1);
		novi.setPrezime(surname);
		novi.setTip(Klijent.KLIJENT);
		novi.setUsername(username);
		if (mainFrame.getUtilControl().saveKlijent(novi)) {
			this.dispose();
		}
	}
}
