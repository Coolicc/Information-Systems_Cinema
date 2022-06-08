package control;

import utils.PersistenceUtil;
import entities.*;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class KupovinaKarteControl {
	
	EntityManager em = PersistenceUtil.getEntityManager();
	
	public List<Film> getAllFilms() {
		TypedQuery<Film> q = em.createNamedQuery("Film.getAllFilms", Film.class);
		List<Film> ret = null;
		try {
			ret = q.getResultList();
		} catch(NoResultException e) {
			return null;
		}
		return ret;
	}
	
	public List<Zanr> getAllZanrs() {
		TypedQuery<Zanr> q = em.createNamedQuery("Zanr.getAllZanrs", Zanr.class);
		List<Zanr> ret = null;
		try {
			ret = q.getResultList();
		} catch(NoResultException e) {
			return null;
		}
		return ret;
	}
	
	public List<Film> getAllFilmsByZanr(Zanr z) {
		TypedQuery<Film> q = em.createNamedQuery("Film.getAllFilmsByZanr", Film.class);
		q.setParameter("zanr", z);
		List<Film> ret = null;
		try {
			ret = q.getResultList();
		} catch(NoResultException e) {
			return null;
		}
		return ret;
	}
	
	public List<Film> getAllFilmsByDate(Calendar c) {
		return getAllProjekcije().stream().filter(x -> x.getDatumOd().get(Calendar.YEAR) == c.get(Calendar.YEAR) 
													&& x.getDatumOd().get(Calendar.DAY_OF_YEAR) == c.get(Calendar.DAY_OF_YEAR))
									.map(x -> x.getFilm()).collect(Collectors.toList());
	}
	
	public List<Projekcija> getAllProjekcije() {
		TypedQuery<Projekcija> q = em.createNamedQuery("Projekcija.getAllProjekcije", Projekcija.class);
		List<Projekcija> ret = null;
		try {
			ret = q.getResultList();
		} catch(NoResultException e) {
			return null;
		}
		return ret;
	}
	
	public List<Projekcija> getAllProjekcijeByFilm(Film f) {
		TypedQuery<Projekcija> q = em.createNamedQuery("Projekcija.getAllProjekcijeByFilm", Projekcija.class);
		q.setParameter("film", f);
		List<Projekcija> ret = null;
		try {
			ret = q.getResultList();
		} catch(NoResultException e) {
			return null;
		}
		return ret;
	}
	
	public boolean saveKarta(Karta karta) {
		EntityManager em = PersistenceUtil.getEntityManager();
		Karta k = em.find(Karta.class, karta.getId());
		EntityTransaction et = em.getTransaction();
		et.begin();
		if (k != null) {
			em.merge(karta);
		}
		else {
			em.persist(karta);
		}
		em.flush();
		et.commit();
		return true;
	}
	
	public void stampaj(Karta k) {
		//TODO: Implementirati stampanje karte u nekoj narednoj verziji programa
	}
}
