package MAIN;

import java.util.Scanner;
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
//import java.util.Scanner;

public class Main {
	static String desTypes[] = {"d4","d6","d8","d10","d12","d20"};
	
	static void afficherMenu() {
		System.out.println("\n=== ARENA LEGENDS ===");
		System.out.println("1. Lancer un dé");
		System.out.println("2. Calculer un rang");
		System.out.println("3. Test de coup critique");
		System.out.println("0. Quitter");
	}
	
	int demanderFacesDes(Scanner sc) {
		System.out.print("Votre proposition (4-20) : ");
		return sc.nextInt();
	}
	
	static int genererD4() { return new Random().nextInt(4) + 1; }
	static int genererD6() { return new Random().nextInt(6) + 1; }
	static int genererD8() { return new Random().nextInt(8) + 1; }
	static int genererD10() { return new Random().nextInt(10); }
	static int genererD12() { return new Random().nextInt(12) + 1; }
	static int genererD20() { return new Random().nextInt(20) + 1; }
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int choix;
		String desChoix;
		int valeur;
		
		int point;
		
        int totalAttaques = 10_000;
        int nbCritiques = 0;
        int serieActuelle = 0;
        int maxSerie = 0;

		do {
			afficherMenu();
			choix = sc.nextInt();

			switch (choix) {
			case 1:
				System.out.print("Types de Dès : ");
				System.out.println(Arrays.toString(desTypes));
				System.out.print("Ton Dès : ");
				
				do {
					desChoix = sc.next();
					
					switch (desChoix) {
						case "d4":
							valeur = genererD4();
							System.out.println("Votre lancer : " + valeur);
							break;
						
						case "d6":
							valeur = genererD6();
							System.out.println("Votre lancer : " + valeur);
							break;
						
						case "d8":
							valeur = genererD8();
							System.out.println("Votre lancer : " + valeur);
							break;
						
						case "d10":
							valeur = genererD10();
							if (valeur == 0) { valeur = 10; } else { System.out.println("Votre lancer : " + valeur); }
							break;
						
						case "d12":
							valeur = genererD12();
							System.out.println("Votre lancer : " + valeur);
							break;
						
						case "d20":
							valeur = genererD20();
							System.out.println("Votre lancer : " + valeur);
							break;
						
						default :
							System.out.println("Valeur incorrect ...");
					}
				} while (!desChoix.equals("d4") && !desChoix.equals("d6") && !desChoix.equals("d8") && !desChoix.equals("d10") && !desChoix.equals("d12") && !desChoix.equals("d20"));
				break;
				
			case 2:
				System.out.println("*tap 0 pour exit au menu.");
				do {
					System.out.print("Points : ");
					point = sc.nextInt();
					if (point < 100) { System.out.println(point + "pts correspond au Rang Bronze"); }
					else if (point >= 100 && point < 500) { System.out.println(point + "pts correspond au Rang Argent"); }
					else if (point >= 500 && point < 1500) { System.out.println(point + "pts correspond au Rang Or"); }
					else { System.out.println(point + "pts correspond au Rang Légende"); }
				} while ( point != 0 );
				break;
				
			case 3:

		        for (int i = 0; i < totalAttaques; i++) {
		            
		            boolean estCritique = Math.random() < 0.15;

		            if (estCritique) {
		                nbCritiques++;
		                serieActuelle++;
		                
		                if (serieActuelle > maxSerie) {
		                    maxSerie = serieActuelle;
		                }
		            } else {
		                serieActuelle = 0;
		            }
		        }

		        double pourcentageReel = ((double) nbCritiques / totalAttaques) * 100;

		        System.out.println("\n=== Résultats de la simulation ===");
		        System.out.println("Nombre total de critiques : " + nbCritiques);
		        System.out.println("Pourcentage réel obtenu : " + pourcentageReel + "%");
		        System.out.println("Plus longue série de critiques consécutifs : " + maxSerie);
				break;
			
			default:
				if (choix == 0) { System.out.println("Au Revoir et à Bientôt !!!"); }
				else {System.out.println("Choix invalide, entrez un nombre entre 0 et 3."); }
			}

		} while (choix != 0);
		sc.close();
		testPartie2();
		testPartie3();
		testPartie4();
		testPartie5();
		centTournois();
	}

	static String tableauEnTexte(int[] t) {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < t.length; i++) {
			if (i > 0) sb.append(", ");
			sb.append(t[i]);
		}
		return sb.append("]").toString();
	}

	static void testPartie2() {
		System.out.println("\n##### PARTIE 2 #####");
		int[] scores = {42, 87, 15, 99, 63, 87, 5, 71, 99, 34, 50, 28};
		System.out.println("Moyenne : " + StatsArena.moyenne(scores));
		System.out.println("Max : " + StatsArena.max(scores) + " | Min : " + StatsArena.min(scores));

		int[] sansDoublons = StatsArena.sansDoublons(scores);
		System.out.println("Sans doublons (" + sansDoublons.length + " cases) : " + tableauEnTexte(sansDoublons));
		System.out.println("Original inchangé : " + tableauEnTexte(scores));

		int echanges = StatsArena.trierDecroissant(scores);
		System.out.println("Trié : " + tableauEnTexte(scores) + " (" + echanges + " échanges)");

		char[][] arene = StatsArena.creerArene();
		StatsArena.afficherGrille(arene);
		System.out.println("Distance A-B : " + StatsArena.distance(arene));

		int[] a = {1, 2, 3};
		int[] b = a; 
		int[] c = new int[a.length];
		for (int i = 0; i < a.length; i++) c[i] = a[i];
		a[0] = 99;
		System.out.println("a[0]=" + a[0] + " | b[0]=" + b[0] + " (alias) | c[0]=" + c[0] + " (copie)");
	}

	static void testPartie3() {
		System.out.println("\n##### PARTIE 3 #####");
		try {
			new Guerrier("Bob", 120, 70, 5);
		} catch (IllegalArgumentException e) {
			System.out.println("Erreur attendue : " + e.getMessage());
		}
		try {
			new Guerrier(null, 120, 20, 5);
		} catch (IllegalArgumentException e) {
			System.out.println("Erreur attendue : " + e.getMessage());
		}

		Guerrier g = new Guerrier("Kaelen", 120, 18, 6);
		g.subirDegats(30);
		g.subirDegats(2);
		System.out.println(g);
		g.soigner(1000);
		System.out.println("Après soin : " + g);

		int[] h = g.getHistoriqueDegats();
		h[4] = 9999;
		System.out.println("Historique modifié dehors : " + h[4] + " | dans l'objet : " + g.getHistoriqueDegats()[4]);

		g.subirDegats(500);
		System.out.println("Soin sur K.O. : " + g.soigner(50) + " PV rendus, " + g);
	}

	static void testPartie4() {
		System.out.println("\n##### PARTIE 4 #####");
		Combattant c = new Mage("Elyndra", 90, 22, 3);
		Combattant cible = new Guerrier("Dummy", 200, 10, 10);
		System.out.println("Mage attaque : " + c.attaquer(cible) + " -> " + cible);

		Combattant paladin = new Paladin("Aldric", 130, 16, 8);
		paladin.subirDegats(60);
		System.out.println("Avant : " + paladin);
		paladin.attaquer(cible);
		System.out.println("Après attaque (soin 10 %) : " + paladin);
		System.out.println("Combattants créés (static) : " + Combattant.getNbCombattants());
	}

	static Tournoi creerTournoi() {
		Tournoi t = new Tournoi();
		t.inscrire(new Guerrier("Kaelen", 120, 18, 6));
		t.inscrire(new Guerrier("Brutus", 150, 20, 10));
		t.inscrire(new Mage("Elyndra", 90, 22, 3));
		t.inscrire(new Mage("Merlan", 100, 20, 4));
		t.inscrire(new Paladin("Aldric", 130, 16, 8));
		t.inscrire(new Paladin("Seraph", 140, 15, 10));
		return t;
	}

	static void testPartie5() {
		System.out.println("\n##### PARTIE 5 #####");
		Tournoi t = creerTournoi();
		System.out.println("Doublon refusé : " + !t.inscrire(new Mage("KAELEN", 90, 20, 3)));
		System.out.println("9e inscrit refusé : " + !t.inscrire(new Mage("Neuvieme", 90, 20, 3)));

		t.lancer();

		System.out.println("\n=== Classement ===");
		java.util.ArrayList<Combattant> classement = t.classement();
		for (int i = 0; i < classement.size(); i++) {
			Combattant c = classement.get(i);
			System.out.println((i + 1) + ". " + c.getNom() + " (" + c.getClasse() + ") - " + c.getVictoires() + " victoire(s)");
		}
		t.statsParClasse();

		t.getParticipants().clear();
		System.out.println("\nAprès getParticipants().clear() : " + t.getParticipants().size() + " participants (attendu 8)");
	}

	static void centTournois() {
		System.out.println("\n##### 100 TOURNOIS SANS AFFICHAGE #####");
		int[] champions = new int[Tournoi.CLASSES.length];
		for (int i = 0; i < 100; i++) {
			Combattant champion = creerTournoi().lancer(false);
			champions[Tournoi.indexClasse(champion.getClasse())]++;
		}
		for (int i = 0; i < champions.length; i++) {
			System.out.println(Tournoi.CLASSES[i] + " : " + champions[i] + " titre(s) sur 100");
		}
	}
}

