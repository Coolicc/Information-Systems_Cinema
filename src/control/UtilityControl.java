package control;

import java.util.ArrayList;
import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entities.*;
import utils.*;

public class UtilityControl {
	
	public Klijent login(String username, String password) {
		EntityManager em = PersistenceUtil.getEntityManager();
		Klijent k = em.find(Klijent.class, username);
		if (k == null || !k.getPassword().equals(password)) {
			return null;
		}
		else {
			return k;
		}
	}
	
	public boolean saveKlijent(Klijent klijent) {
		EntityManager em = PersistenceUtil.getEntityManager();
		Klijent k = em.find(Klijent.class, klijent.getUsername());
		EntityTransaction et = em.getTransaction();
		et.begin();
		if (k != null) {
			em.merge(klijent);
		}
		else {
			em.persist(klijent);
		}
		em.flush();
		et.commit();
		return true;
	}
}
