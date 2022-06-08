package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Film.getAllFilms", query = "SELECT f FROM Film f"),
				@NamedQuery(name = "Film.getAllFilmsByZanr", query = "SELECT f FROM Film f WHERE :zanr MEMBER OF f.zanrovi")})
public class Film {
	
	@Id
	private int id;
	
	private String naziv;
	
	private int trajanje;
	
	private String opis;
	
	@Lob
	private byte[] poster;
	
	@ManyToMany
	@JoinTable(name="FilmZanr")
	private List<Zanr> zanrovi;
	
	@OneToMany (mappedBy="film")
	private List<Projekcija> projekcije;
	
	@OneToMany (mappedBy="film")
	private List<Iznajmljivanje> iznajmljivanja;

	public Film() {
		zanrovi = new ArrayList<Zanr>();
		projekcije = new ArrayList<>();
		iznajmljivanja = new ArrayList<>();
	}
	
	public static int getNextId() {
		int id = 0;
		try {
			File f = new File("idFiles/FilmId.txt");
			BufferedReader br = new BufferedReader(new FileReader(f));
			id = Integer.parseInt(br.readLine());
			br.close();
			f.delete();
			f.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(""+(id+1));
			bw.close();
		} catch(Exception e) {
			
		}
		return id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public byte[] getPoster() {
		return poster;
	}

	public void setPoster(byte[] poster) {
		this.poster = poster;
	}

	public List<Zanr> getZanrovi() {
		return zanrovi;
	}

	public void setZanrovi(List<Zanr> zanrovi) {
		this.zanrovi = zanrovi;
	}
	
	public void addZanr(Zanr z) {
		zanrovi.add(z);
	}

	public List<Projekcija> getProjekcije() {
		return projekcije;
	}

	public void setProjekcije(List<Projekcija> projekcije) {
		this.projekcije = projekcije;
	}

	public List<Iznajmljivanje> getIznajmljivanja() {
		return iznajmljivanja;
	}

	public void setIznajmljivanja(List<Iznajmljivanje> iznajmljivanja) {
		this.iznajmljivanja = iznajmljivanja;
	}
	
	public void addProjekcija(Projekcija p) {
		projekcije.add(p);
	}
	
	public void addIznajmljivanje(Iznajmljivanje i) {
		iznajmljivanja.add(i);
	}
	
	@Override
	public String toString() {
		return "Naziv: "+naziv+"; Trajanje: "+trajanje+"min";
	}
}
