package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entities.Klijent;

public class TopPane extends JPanel {
	
	private MainFrame mainFrame;
	
	private JTextField txtUsername;
	
	private JPasswordField txtPassword;
	
	public TopPane(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void init() {
		notLoggedInView();
	}
	
	public void notLoggedInView() {
		removeAll();
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		JButton filmBtn = new JButton("Filmovi");
		filmBtn.addActionListener(this::showFilms);
		JButton saleBtn = new JButton("Sale");
		saleBtn.addActionListener(this::showSale);
		buttonPane.add(filmBtn);
		buttonPane.add(Box.createHorizontalStrut(20));
		buttonPane.add(saleBtn);
		
		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
		topPane.add(new JLabel("Username:"));
		topPane.add(Box.createHorizontalStrut(10));
		txtUsername = new JTextField();
		txtUsername.setColumns(20);
		txtUsername.setMaximumSize(new Dimension(150, 25));
		topPane.add(txtUsername);
		
		JPanel midPane = new JPanel();
		midPane.setLayout(new BoxLayout(midPane, BoxLayout.X_AXIS));
		midPane.add(new JLabel("Password:"));
		midPane.add(Box.createHorizontalStrut(10));
		txtPassword = new JPasswordField();
		txtPassword.setColumns(20);
		txtPassword.setMaximumSize(new Dimension(150, 25));
		midPane.add(txtPassword);
		
		JPanel botPane = new JPanel();
		botPane.setLayout(new BoxLayout(botPane, BoxLayout.X_AXIS));
		JButton loginBtn = new JButton("Uloguj se");
		loginBtn.addActionListener(this::login);
		botPane.add(loginBtn);
		botPane.add(Box.createHorizontalStrut(15));
		JButton registerBtn = new JButton("Registruj se");
		registerBtn.addActionListener(this::register);
		botPane.add(registerBtn);
		
		JPanel loginPane = new JPanel();
		loginPane.setLayout(new BoxLayout(loginPane, BoxLayout.Y_AXIS));
		loginPane.add(topPane);
		loginPane.add(midPane);
		loginPane.add(botPane);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(buttonPane);
		add(Box.createHorizontalGlue());
		add(loginPane);
	}
	
	public void loggedInView() {
		removeAll();
		
		JButton filmBtn = new JButton("Filmovi");
		filmBtn.addActionListener(this::showFilms);
		JButton saleBtn = new JButton("Sale");
		saleBtn.addActionListener(this::showSale);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(filmBtn);
		add(Box.createHorizontalStrut(20));
		add(saleBtn);
		
		if (mainFrame.getKlijent().getTip() == Klijent.MENADZER) {
			JButton newFilmBtn = new JButton("Dodaj Film");
			newFilmBtn.addActionListener(this::showNewFilm);
			JButton newProjekcijaBtn = new JButton("Dodaj Projekciju");
			newProjekcijaBtn.addActionListener(this::showNewProjekcija);
			JButton popustBtn = new JButton("Dodaj Popust");
			popustBtn.addActionListener(this::showPopust);
			JButton IznajmljivanjaBtn = new JButton("Odobri iznajmljivanja");
			IznajmljivanjaBtn.addActionListener(this::showIznajmljivanja);
			
			add(Box.createHorizontalStrut(20));
			add(newFilmBtn);
			add(Box.createHorizontalStrut(20));
			add(newProjekcijaBtn);
			add(Box.createHorizontalStrut(20));
			add(popustBtn);
			add(Box.createHorizontalStrut(20));
			add(IznajmljivanjaBtn);
		}
		
		JButton logoutBtn = new JButton("Izloguj se");
		logoutBtn.addActionListener(this::logout);
		add(Box.createHorizontalGlue());
		add(logoutBtn);
	}
	
	public void showNewFilm(ActionEvent e) {
		NoviFilmForm nff = new NoviFilmForm(mainFrame);
		nff.init();
	}
	
	public void showNewProjekcija(ActionEvent e) {
		NovaProjekcijaForm npf = new NovaProjekcijaForm(mainFrame);
		npf.init();
	}
	
	public void showPopust(ActionEvent e) {
		PopustForm pf = new PopustForm(mainFrame);
		pf.init();
	}
	
	public void showIznajmljivanja(ActionEvent e) {
		OdobravanjeIznajmljivanjaForm oif = new OdobravanjeIznajmljivanjaForm(mainFrame);
		oif.init();
	}
	
	public void showFilms(ActionEvent e) {
		if (!mainFrame.getFilmView()) {
			mainFrame.initFilmView();
		}
	}
	
	public void showSale(ActionEvent e) {
		if (mainFrame.getFilmView()) {
			mainFrame.initSalaView();
		}
	}
	
	public void login(ActionEvent e) {
		Klijent k = mainFrame.getUtilControl().login(txtUsername.getText(), new String(txtPassword.getPassword()));
		if (k != null) {
			mainFrame.setKlijent(k);
			loggedInView();
			this.repaint();
		}
	}
	
	public void logout(ActionEvent e) {
		mainFrame.setKlijent(null);
		notLoggedInView();
		this.repaint();
	}
	
	public void register(ActionEvent e) {
		RegistracijaForm r = new RegistracijaForm(mainFrame);
		r.init();
	}
}
