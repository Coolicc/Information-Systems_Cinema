package entities;

import javax.persistence.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name = "Projekcija.getAllProjekcije", query = "SELECT p FROM Projekcija p"),
				@NamedQuery(name = "Projekcija.getAllProjekcijeByFilm", query = "SELECT p FROM Projekcija p WHERE :film = p.film")})
public class Projekcija {
	
	@Id
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datumOd;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datumDo;
	
	private double cena;
	
	@Lob
	private byte[] mesta;
	
	private int brojSlobodnihMesta;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Film film;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Sala sala;
	
	@OneToMany (mappedBy="projekcija", fetch=FetchType.EAGER)
	private List<Karta> karte;
	
	@ManyToMany (fetch=FetchType.EAGER)
	@JoinTable(name="ProjekcijaPopust")
	private List<Popust> popusti;

	public Projekcija() {
		karte = new ArrayList<>();
		popusti = new ArrayList<>();
	}
	
	public static int getNextId() {
		int id = 0;
		try {
			File f = new File("idFiles/projekcijaId.txt");
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

	public Calendar getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(Calendar datumOd) {
		this.datumOd = datumOd;
	}

	public Calendar getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(Calendar datumDo) {
		this.datumDo = datumDo;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public byte[] getMesta() {
		return mesta;
	}

	public void setMesta(byte[] mesta) {
		this.mesta = mesta;
	}

	public int getBrojSlobodnihMesta() {
		return brojSlobodnihMesta;
	}

	public void setBrojSlobodnihMesta(int brojSlobodnihMesta) {
		this.brojSlobodnihMesta = brojSlobodnihMesta;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public List<Karta> getKarte() {
		return karte;
	}

	public void setKarte(List<Karta> karte) {
		this.karte = karte;
	}

	public List<Popust> getPopusti() {
		return popusti;
	}

	public void setPopusti(List<Popust> popusti) {
		this.popusti = popusti;
	}
	
	public void addPopust(Popust p) {
		popusti.add(p);
	}
	
	public void addKarta(Karta k) {
		karte.add(k);
	}
	
	public void zauzmiMesto(int red, int kolona) {
		mesta[(red-1)*sala.getBrojKolona()+kolona]=1;
	}
	
	public double izracunajCenu() {
		Popust maxPok = null;
		for (Popust p: popusti) {
			if (maxPok == null) {
				maxPok = p;
			}
			else if (p.getProcenat()>maxPok.getProcenat()) {
				maxPok = p;
			}
		}
		if (maxPok != null) {
			return maxPok.izracunajPopust(cena);
		}
		else {
			return cena;
		}
	}
	
	@Override
	public String toString() {
		return "Film: "+film.getNaziv()+"; Sala: "+sala.getId()+"; Od: "
				+datumOd.get(Calendar.DAY_OF_MONTH)+"."+datumOd.get(Calendar.MONTH)+
				"."+datumOd.get(Calendar.YEAR)+" "+datumOd.get(Calendar.HOUR)+":"+
				datumOd.get(Calendar.MINUTE)+"; Do: "+datumDo.get(Calendar.DAY_OF_MONTH)+
				"."+datumDo.get(Calendar.MONTH)+"."+datumDo.get(Calendar.YEAR)+" "
				+datumDo.get(Calendar.HOUR)+":"+datumDo.get(Calendar.MINUTE)+"; Cena: "+
				izracunajCenu();
	}
}
