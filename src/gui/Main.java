package gui;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entities.*;
import control.*;

public class Main {
	
	private static IznajmljivanjeSaleControl iznControl = new IznajmljivanjeSaleControl();
	private static KupovinaKarteControl karControl = new KupovinaKarteControl();
	private static MenadzerControl menControl = new MenadzerControl();
	private static UtilityControl utilControl = new UtilityControl();
	
	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.init();
	}
}
