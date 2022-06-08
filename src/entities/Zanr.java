package entities;

import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Zanr.getAllZanrs", query = "SELECT z FROM Zanr z")})
public class Zanr {
	
	@Id
	private String naziv;
	
	@ManyToMany(mappedBy="zanrovi", fetch=FetchType.EAGER)
	private List<Film> filmovi;

	public Zanr() {
	
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Film> getFilmovi() {
		return filmovi;
	}

	public void setFilmovi(List<Film> filmovi) {
		this.filmovi = filmovi;
	}
	
	public void addFilm(Film f) {
		filmovi.add(f);
	}
	
	@Override
	public String toString() {
		return naziv;
	}
}
