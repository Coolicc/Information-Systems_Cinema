package entities;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class Klijent {
	
	public static final int KLIJENT = 0;
	public static final int RADNIK = 1;
	public static final int MENADZER = 2;
	
	@Id
	private String username;
	
	private String password;
	
	private String ime;
	
	private String prezime;
	
	@Temporal(TemporalType.DATE)
	private Calendar datumRodjenja;
	
	private String email;
	
	private int tip;
	
	@OneToMany (mappedBy="klijent", fetch=FetchType.EAGER)
	private List<Karta> karte;
	
	@OneToMany (mappedBy="klijent", fetch=FetchType.EAGER)
	private List<Iznajmljivanje> iznajmljivanja;

	public Klijent() {
		karte = new ArrayList<Karta>();
		iznajmljivanja = new ArrayList<Iznajmljivanje>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Calendar getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(Calendar datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTip() {
		return tip;
	}

	public void setTip(int tip) {
		this.tip = tip;
	}

	public List<Karta> getKarte() {
		return karte;
	}

	public void setKarte(List<Karta> karte) {
		this.karte = karte;
	}

	public List<Iznajmljivanje> getIznajmljivanja() {
		return iznajmljivanja;
	}

	public void setIznajmljivanja(List<Iznajmljivanje> iznajmljivanja) {
		this.iznajmljivanja = iznajmljivanja;
	}
	
	public void addKarta(Karta k) {
		karte.add(k);
	}
	
	public void addIznajmljivanje(Iznajmljivanje i) {
		iznajmljivanja.add(i);
	}
	
	@Override
	public String toString() {
		return "Username: "+username+"; Ime: "+ime+"; Prezime: "+prezime;
	}
}
