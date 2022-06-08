package control;

import utils.PersistenceUtil;
import entities.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class MenadzerControl {
	
	EntityManager em = PersistenceUtil.getEntityManager();
	
	public List<Sala> getAllSale() {
		TypedQuery<Sala> q = em.createNamedQuery("Sala.getAllSale", Sala.class);
		List<Sala> ret = null;
		try {
			ret = q.getResultList();
		} catch(NoResultException e) {
			return null;
		}
		return ret;
	}
	
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
	
	public List<Popust> getAllPopusti() {
		TypedQuery<Popust> q = em.createNamedQuery("Popust.getAllPopusti", Popust.class);
		List<Popust> ret = null;
		try {
			ret = q.getResultList();
		} catch(NoResultException e) {
			return null;
		}
		return ret;
	}
	
	public boolean saveProjekcija(Projekcija projekcija) {
		EntityManager em = PersistenceUtil.getEntityManager();
		Projekcija p = em.find(Projekcija.class, projekcija.getId());
		EntityTransaction et = em.getTransaction();
		et.begin();
		if (p != null) {
			em.merge(projekcija);
		}
		else {
			em.persist(projekcija);
		}
		em.flush();
		et.commit();
		return true;
	}
	
	public boolean saveZanr(Zanr zanr) {
		EntityManager em = PersistenceUtil.getEntityManager();
		Zanr z = em.find(Zanr.class, zanr.getNaziv());
		EntityTransaction et = em.getTransaction();
		et.begin();
		if (z != null) {
			
			em.merge(zanr);
		}
		else {
			em.persist(zanr);
		}
		em.flush();
		et.commit();
		return true;
	}
	
	public boolean saveFilm(Film film) {
		EntityManager em = PersistenceUtil.getEntityManager();
		Film f = em.find(Film.class, film.getId());
		EntityTransaction et = em.getTransaction();
		et.begin();
		if (f != null) {
			em.merge(film);
		}
		else {
			em.persist(film);
		}
		em.flush();
		et.commit();
		return true;
	}
	
	public boolean savePopust(Popust popust) {
		EntityManager em = PersistenceUtil.getEntityManager();
		Popust p = em.find(Popust.class, popust.getId());
		EntityTransaction et = em.getTransaction();
		et.begin();
		if (p != null) {
			em.merge(popust);
		}
		else {
			em.persist(popust);
		}
		em.flush();
		et.commit();
		return true;
	}
}
