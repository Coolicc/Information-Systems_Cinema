package entities;

import javax.persistence.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name = "Iznajmljivanje.getAllIznajmljivanjas", query = "SELECT i FROM Iznajmljivanje i")})
public class Iznajmljivanje {
	
	@Id
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datumOd;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datumDo;
	
	private boolean odobreno;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Sala sala;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Klijent klijent;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Film film;

	public Iznajmljivanje() {
		
	}
	
	public static int getNextId() {
		int id = 0;
		try {
			File f = new File("idFiles/IznajmljivanjeId.txt");
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

	public boolean isOdobreno() {
		return odobreno;
	}

	public void setOdobreno(boolean odobreno) {
		this.odobreno = odobreno;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Klijent getKlijent() {
		return klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}
	
	@Override
	public String toString() {
		return "Klijent: "+klijent.getUsername()+"; Sala: "+sala.getId()+"; Od: "
				+datumOd.get(Calendar.DAY_OF_MONTH)+"."+datumOd.get(Calendar.MONTH)+
				"."+datumOd.get(Calendar.YEAR)+" "+datumOd.get(Calendar.HOUR)+":"+
				datumOd.get(Calendar.MINUTE)+"; Do: "+datumDo.get(Calendar.DAY_OF_MONTH)+
				"."+datumDo.get(Calendar.MONTH)+"."+datumDo.get(Calendar.YEAR)+" "
				+datumDo.get(Calendar.HOUR)+":"+datumDo.get(Calendar.MINUTE);
	}
}
