package MAIN;

import java.util.Random;

public class StatsArena {
	private static final Random RNG = new Random();

	static double moyenne(int[] t) {
		int somme = 0;
		for (int i = 0; i < t.length; i++) {
			somme += t[i];
		}
		return (double) somme / t.length;
	}

	static int max(int[] t) {
		int m = t[0];
		for (int i = 1; i < t.length; i++) {
			if (t[i] > m) m = t[i];
		}
		return m;
	}

	static int min(int[] t) {
		int m = t[0];
		for (int i = 1; i < t.length; i++) {
			if (t[i] < m) m = t[i];
		}
		return m;
	}

	static int trierDecroissant(int[] t) {
		int echanges = 0;
		int n = t.length;
		boolean permute;
		do {
			permute = false;
			for (int i = 0; i < n - 1; i++) {
				if (t[i] < t[i + 1]) {
					int tmp = t[i];
					t[i] = t[i + 1];
					t[i + 1] = tmp;
					echanges++;
					permute = true;
				}
			}
			n--;
		} while (permute);
		return echanges;
	}

	static int[] sansDoublons(int[] t) {
		int nb = 0;
		for (int i = 0; i < t.length; i++) {
			if (!dejaVu(t, i)) nb++;
		}
		int[] resultat = new int[nb];
		int k = 0;
		for (int i = 0; i < t.length; i++) {
			if (!dejaVu(t, i)) resultat[k++] = t[i];
		}
		return resultat;
	}

	private static boolean dejaVu(int[] t, int i) {
		for (int j = 0; j < i; j++) {
			if (t[j] == t[i]) return true;
		}
		return false;
	}

	static char[][] creerArene() {
		char[][] g = new char[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				g[i][j] = '.';
			}
		}
		for (int k = 0; k < 6; k++) {
			placer(g, '#');
		}
		placer(g, 'A');
		placer(g, 'B');
		return g;
	}

	private static void placer(char[][] g, char c) {
		int x, y;
		do {
			x = RNG.nextInt(8);
			y = RNG.nextInt(8);
		} while (g[x][y] != '.');
		g[x][y] = c;
	}

	static void afficherGrille(char[][] g) {
		for (int i = 0; i < g.length; i++) {
			StringBuilder ligne = new StringBuilder();
			for (int j = 0; j < g[i].length; j++) {
				ligne.append(g[i][j]).append(' ');
			}
			System.out.println(ligne.toString().trim());
		}
	}

	static int distance(char[][] g) {
		int xa = -1, ya = -1, xb = -1, yb = -1;
		for (int i = 0; i < g.length; i++) {
			for (int j = 0; j < g[i].length; j++) {
				if (g[i][j] == 'A') {
					xa = i;
					ya = j;
				} else if (g[i][j] == 'B') {
					xb = i;
					yb = j;
				}
			}
		}
		if (xa < 0 || xb < 0) return -1;
		return Math.abs(xa - xb) + Math.abs(ya - yb);
	}
}