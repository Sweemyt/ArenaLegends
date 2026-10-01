package MAIN;

import java.util.Random;

public abstract class Combattant {
	protected static final Random RNG = new Random();
	private static int nbCombattants = 0;

	private final String nom;
	private final int pvMax;
	private int pv;
	private final int attaque;
	private final int defense;
	private final int[] historiqueDegats = new int[5];
	private int victoires = 0;

	public Combattant(String nom, int pvMax, int attaque, int defense) {
		if (nom == null) {
			throw new IllegalArgumentException("nom ne doit pas être null");
		}
		if (nom.length() < 3 || nom.length() > 15) {
			throw new IllegalArgumentException("nom doit faire entre 3 et 15 caractères, reçu : " + nom.length());
		}
		if (pvMax < 50 || pvMax > 300) {
			throw new IllegalArgumentException("pvMax doit être entre 50 et 300, reçu : " + pvMax);
		}
		if (attaque < 5 || attaque > 50) {
			throw new IllegalArgumentException("attaque doit être entre 5 et 50, reçu : " + attaque);
		}
		if (defense < 0 || defense > 30) {
			throw new IllegalArgumentException("defense doit être entre 0 et 30, reçu : " + defense);
		}
		this.nom = nom;
		this.pvMax = pvMax;
		this.pv = pvMax;
		this.attaque = attaque;
		this.defense = defense;
		nbCombattants++;
	}

	public abstract int attaquer(Combattant cible);

	public abstract String getClasse();

	public String getNom() { return nom; }
	public int getPvMax() { return pvMax; }
	public int getPv() { return pv; }
	public int getAttaque() { return attaque; }
	public int getDefense() { return defense; }
	public int getVictoires() { return victoires; }
	public static int getNbCombattants() { return nbCombattants; }

	public int[] getHistoriqueDegats() {
		return historiqueDegats.clone();
	}

	public int subirDegats(int d) {
		if (d < 0) {
			throw new IllegalArgumentException("les dégâts ne peuvent pas être négatifs, reçu : " + d);
		}
		return appliquer(Math.max(1, d - defense));
	}

	protected int subirDegatsBruts(int d) {
		if (d < 0) {
			throw new IllegalArgumentException("les dégâts ne peuvent pas être négatifs, reçu : " + d);
		}
		return appliquer(d);
	}

	private int appliquer(int reels) {
		pv = Math.max(0, pv - reels);
		for (int i = 0; i < historiqueDegats.length - 1; i++) {
			historiqueDegats[i] = historiqueDegats[i + 1];
		}
		historiqueDegats[historiqueDegats.length - 1] = reels;
		return reels;
	}

	public int soigner(int s) {
		if (s < 0) {
			throw new IllegalArgumentException("le soin ne peut pas être négatif, reçu : " + s);
		}
		if (estKO()) {
			return 0;
		}
		int avant = pv;
		pv = Math.min(pvMax, pv + s);
		return pv - avant;
	}

	public void soignerCompletement() {
		soigner(pvMax - pv);
	}

	public boolean estKO() {
		return pv == 0;
	}

	void ajouterVictoire() {
		victoires++;
	}

	@Override
	public String toString() {
		return nom + " (" + getClasse() + ") [" + pv + "/" + pvMax + " PV] ATK " + attaque + " DEF " + defense;
	}
}