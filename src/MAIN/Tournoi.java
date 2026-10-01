package MAIN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Tournoi {
	public static final String[] CLASSES = {"Guerrier", "Mage", "Voleur", "Paladin"};
	private static final int MAX_PARTICIPANTS = 8;
	private static final int MAX_TOURS = 50;

	private final ArrayList<Combattant> participants = new ArrayList<>();

	public boolean inscrire(Combattant c) {
		if (c == null || participants.size() >= MAX_PARTICIPANTS) {
			return false;
		}
		for (Combattant p : participants) {
			if (p.getNom().equalsIgnoreCase(c.getNom())) {
				return false;
			}
		}
		participants.add(c);
		return true;
	}

	public boolean desinscrire(String nom) {
		Iterator<Combattant> it = participants.iterator();
		while (it.hasNext()) {
			if (it.next().getNom().equalsIgnoreCase(nom)) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	public Combattant duel(Combattant a, Combattant b) {
		return duel(a, b, true);
	}

	public Combattant duel(Combattant a, Combattant b, boolean afficher) {
		Combattant premier = (b.getAttaque() > a.getAttaque()) ? b : a;
		Combattant second = (premier == a) ? b : a;

		if (afficher) {
			System.out.println("\n--- DUEL : " + a.getNom() + " VS " + b.getNom() + " ---");
		}

		int tour = 1;
		while (tour <= MAX_TOURS && !a.estKO() && !b.estKO()) {
			int d1 = premier.attaquer(second);
			if (afficher) {
				System.out.println("Tour " + tour + " : " + premier.getNom() + " inflige " + d1 + " -> " + second);
			}
			if (!second.estKO()) {
				int d2 = second.attaquer(premier);
				if (afficher) {
					System.out.println("Tour " + tour + " : " + second.getNom() + " inflige " + d2 + " -> " + premier);
				}
			}
			tour++;
		}

		Combattant vainqueur;
		if (a.estKO()) {
			vainqueur = b;
		} else if (b.estKO()) {
			vainqueur = a;
		} else {
			long pa = (long) a.getPv() * b.getPvMax();
			long pb = (long) b.getPv() * a.getPvMax();
			vainqueur = (pb > pa) ? b : (pa > pb ? a : premier);
		}
		vainqueur.ajouterVictoire();
		if (afficher) {
			System.out.println(">>> Vainqueur : " + vainqueur.getNom());
		}
		return vainqueur;
	}

	public Combattant lancer() {
		return lancer(true);
	}

	public Combattant lancer(boolean afficher) {
		if (participants.size() < 2) {
			throw new IllegalStateException("Il faut au moins 2 combattants pour lancer un tournoi");
		}
		ArrayList<Combattant> courants = new ArrayList<>(participants);
		int numeroTour = 1;

		while (courants.size() > 1) {
			Collections.shuffle(courants);
			if (afficher) {
				System.out.println("\n===== TOUR " + numeroTour + " (" + courants.size() + " combattants) =====");
			}
			ArrayList<Combattant> vainqueurs = new ArrayList<>();
			for (int i = 0; i < courants.size(); i += 2) {
				if (i + 1 < courants.size()) {
					Combattant v = duel(courants.get(i), courants.get(i + 1), afficher);
					v.soignerCompletement();
					vainqueurs.add(v);
				} else {
					vainqueurs.add(courants.get(i));
				}
			}
			courants = vainqueurs;
			numeroTour++;
		}

		Combattant champion = courants.get(0);
		if (afficher) {
			System.out.println("\n*** CHAMPION : " + champion.getNom() + " (" + champion.getClasse() + ") ***");
		}
		return champion;
	}

	public ArrayList<Combattant> classement() {
		ArrayList<Combattant> copie = new ArrayList<>(participants);
		for (int i = 0; i < copie.size() - 1; i++) {
			for (int j = 0; j < copie.size() - 1 - i; j++) {
				if (copie.get(j).getVictoires() < copie.get(j + 1).getVictoires()) {
					Combattant tmp = copie.get(j);
					copie.set(j, copie.get(j + 1));
					copie.set(j + 1, tmp);
				}
			}
		}
		return copie;
	}

	public int[] statsParClasse() {
		int[] totaux = new int[CLASSES.length];
		for (Combattant c : participants) {
			int idx = indexClasse(c.getClasse());
			if (idx >= 0) totaux[idx] += c.getVictoires();
		}
		System.out.println("\n=== Victoires par classe ===");
		for (int i = 0; i < CLASSES.length; i++) {
			System.out.println(CLASSES[i] + " : " + totaux[i]);
		}
		return totaux;
	}

	public static int indexClasse(String classe) {
		for (int i = 0; i < CLASSES.length; i++) {
			if (CLASSES[i].equals(classe)) return i;
		}
		return -1;
	}

	public ArrayList<Combattant> getParticipants() {
		return new ArrayList<>(participants);
	}
}