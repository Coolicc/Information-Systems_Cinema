package entities;

import javax.persistence.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

@Entity
public class Karta {
	
	@Id
	private int id;
	
	private int red;
	
	private int kolona;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Projekcija projekcija;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Klijent klijent;

	public Karta() {
		
	}
	
	public static int getNextId() {
		int id = 0;
		try {
			File f = new File("idFiles/kartaId.txt");
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

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getKolona() {
		return kolona;
	}

	public void setKolona(int kolona) {
		this.kolona = kolona;
	}

	public Projekcija getProjekcija() {
		return projekcija;
	}

	public void setProjekcija(Projekcija projekcija) {
		this.projekcija = projekcija;
	}

	public Klijent getKlijent() {
		return klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}
	
	public String stampanjeFormat() {
		return "Karta br.:\t"+id+"\nFilm:\t"+projekcija.getFilm().getNaziv()
				+"\nSala:\t"+projekcija.getSala().getId()+"\nRed:\t"+red+
				"\nKolona:\t"+kolona;
	}
	
	@Override
	public String toString() {
		return "Klijent: "+klijent.getUsername()+"; Film: "+projekcija.getFilm().getNaziv()
				+"; Sala: "+projekcija.getSala();
	}
}
