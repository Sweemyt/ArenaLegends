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
	}
}
