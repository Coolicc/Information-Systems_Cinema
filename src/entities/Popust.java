package entities;

import javax.persistence.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name = "Popust.getAllPopusti", query = "SELECT p FROM Popust p")})
public class Popust {
	
	@Id
	private int id;
	
	@Temporal(TemporalType.DATE)
	private Calendar datumOd;
	
	@Temporal(TemporalType.DATE)
	private Calendar datumDo;
	
	private double procenat;
	
	@ManyToMany (mappedBy="popusti", fetch=FetchType.EAGER)
	private List<Projekcija> projekcije;
	
	public double izracunajPopust(double cena) {
		return cena*(100.0-procenat);
	}

	public Popust() {
		projekcije = new ArrayList<>();
	}
	
	public static int getNextId() {
		int id = 0;
		try {
			File f = new File("idFiles/popustId.txt");
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

	public double getProcenat() {
		return procenat;
	}

	public void setProcenat(double procenat) {
		this.procenat = procenat;
	}

	public List<Projekcija> getProjekcije() {
		return projekcije;
	}

	public void setProjekcije(List<Projekcija> projekcije) {
		this.projekcije = projekcije;
	}
	
	public void addProjekcija(Projekcija p) {
		projekcije.add(p);
	}
	
	@Override
	public String toString() {
		return "Procenat: "+procenat+"; Od: "+datumOd.toString()+"; Do: "+datumDo.toString();
	}
}
