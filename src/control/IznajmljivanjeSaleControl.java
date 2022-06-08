package control;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import utils.PersistenceUtil;
import entities.*;


public class IznajmljivanjeSaleControl {
	
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
	
	public Sala getSala(int id) {
		return em.find(Sala.class, id);
	}
	
	public Klijent getKlijent(String username) {
		return em.find(Klijent.class, username);
	}
	
	public List<Iznajmljivanje> getAllIznajmljivanjas() {
		TypedQuery<Iznajmljivanje> q = em.createNamedQuery("Iznajmljivanje.getAllIznajmljivanjas", Iznajmljivanje.class);
		List<Iznajmljivanje> ret = null;
		try {
			ret = q.getResultList();
		} catch(NoResultException e) {
			return null;
		}
		return ret;
	}
	
	public boolean saveIznajmljivanje(Iznajmljivanje iznajmljivanje) {
		EntityManager em = PersistenceUtil.getEntityManager();
		Iznajmljivanje i = em.find(Iznajmljivanje.class, iznajmljivanje.getId());
		EntityTransaction et = em.getTransaction();
		et.begin();
		if (i != null) {
			em.merge(iznajmljivanje);
		}
		else {
			em.persist(iznajmljivanje);
		}
		em.flush();
		et.commit();
		return true;
	}
	
	public boolean saveSala(Sala sala) {
		EntityManager em = PersistenceUtil.getEntityManager();
		Sala s = em.find(Sala.class, sala.getId());
		EntityTransaction et = em.getTransaction();
		et.begin();
		if (s != null) {
			em.merge(sala);
		}
		else {
			em.persist(sala);
		}
		em.flush();
		et.commit();
		return true;
	}
}
