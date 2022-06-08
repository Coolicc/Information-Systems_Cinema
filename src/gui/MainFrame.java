package gui;

import java.awt.BorderLayout;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import control.IznajmljivanjeSaleControl;
import control.KupovinaKarteControl;
import control.MenadzerControl;
import control.UtilityControl;
import entities.Klijent;
import entities.Sala;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TopPane top;
	
	private LeftPane left;
	
	private ListModel listModel;
	
	private Klijent klijent;
	
	private IznajmljivanjeSaleControl salaControl;
	
	private KupovinaKarteControl kartaControl;
	
	private MenadzerControl menadzerControl;
	
	private UtilityControl utilControl;
	
	private JList lista;
	
	private JPanel mainPanel;
	
	private boolean filmView;
	
	public void init() {
		salaControl = new IznajmljivanjeSaleControl();
		kartaControl = new KupovinaKarteControl();
		menadzerControl = new MenadzerControl();
		utilControl = new UtilityControl();
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
				
		left = new LeftPane(this);
		add(left, BorderLayout.WEST);
		
		initFilmView();
		
		top = new TopPane(this);
		top.init();
		add(top, BorderLayout.NORTH);
		
		setSize(1000, 800);
		setTitle("Bioskop");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void initFilmView() {
		mainPanel.removeAll();
		listModel = new FilmListModel(kartaControl.getAllFilms().stream()
				.map(FilmPanel::new).collect(Collectors.toList()));
        lista = new JList(listModel);
		JScrollPane listScroll = new JScrollPane(lista);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setCellRenderer(new FilmCellRenderer());
		lista.setFixedCellHeight(150);
		lista.addListSelectionListener( x -> {
			int index = x.getFirstIndex();
			FilmPanel panel = (FilmPanel)listModel.getElementAt(index);
			FilmWindow fw = new FilmWindow(panel.getFilm(), this);
			fw.init();
		});
		mainPanel.add(listScroll, BorderLayout.CENTER);	
		left.init();
		filmView = true;
	}
	
	public void initSalaView() {
		mainPanel.removeAll();
		listModel = new SalaListModel(salaControl.getAllSale().stream()
				.collect(Collectors.toList()));
		lista = new JList(listModel);
		JScrollPane listScroll = new JScrollPane(lista);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.addListSelectionListener( x -> {
			int index = x.getFirstIndex();
			Sala sala = (Sala) listModel.getElementAt(index);
			SalaWindow sw = new SalaWindow(sala, this);
			sw.init();
		});
		mainPanel.add(listScroll, BorderLayout.CENTER);
		left.removeAll();
		filmView = false;
	}
	
	public boolean getFilmView() {
		return filmView;
	}
	
	public static void main(String[] args) {
		MainFrame main = new MainFrame();
		main.init();
	}

	public TopPane getTop() {
		return top;
	}

	public void setTop(TopPane top) {
		this.top = top;
	}

	public LeftPane getLeft() {
		return left;
	}

	public void setLeft(LeftPane left) {
		this.left = left;
	}

	public ListModel getListModel() {
		return listModel;
	}

	public void setListModel(FilmListModel listModel) {
		this.listModel = listModel;
	}

	public Klijent getKlijent() {
		return klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}

	public IznajmljivanjeSaleControl getSalaControl() {
		return salaControl;
	}

	public KupovinaKarteControl getKartaControl() {
		return kartaControl;
	}

	public MenadzerControl getMenadzerControl() {
		return menadzerControl;
	}

	public UtilityControl getUtilControl() {
		return utilControl;
	}

	public JList getLista() {
		return lista;
	}

	public void setLista(JList lista) {
		this.lista = lista;
	}
	
}
