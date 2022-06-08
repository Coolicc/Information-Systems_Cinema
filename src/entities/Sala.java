package entities;

import javax.persistence.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name = "Sala.getAllSale", query = "SELECT s FROM Sala s")})
public class Sala {
	
	@Id
	private int id;
	
	private int brojRedova;
	
	private int brojKolona;
	
	@OneToMany (mappedBy="sala", fetch=FetchType.EAGER)
	private List<Projekcija> projekcije;
	
	@OneToMany (mappedBy="sala", fetch=FetchType.EAGER)
	private List<Iznajmljivanje> iznajmljivanja;

	public Sala() {
		
	}
	
	public static int getNextId() {
		int id = 0;
		try {
			File f = new File("idFiles/salaId.txt");
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

	public int getBrojRedova() {
		return brojRedova;
	}

	public void setBrojRedova(int brojRedova) {
		this.brojRedova = brojRedova;
	}

	public int getBrojKolona() {
		return brojKolona;
	}

	public void setBrojKolona(int brojKolona) {
		this.brojKolona = brojKolona;
	}
	
	public int brojSedista() {
		return brojKolona*brojRedova;
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
	
	public void addIznajmljivanje(Iznajmljivanje i) {
		iznajmljivanja.add(i);
	}
	
	public void addProjekcija(Projekcija p) {
		projekcije.add(p);
	}
	
	@Override
	public String toString() {
		return "Sala " + id;
	}
}
