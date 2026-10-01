package MAIN;

public class Mage extends Combattant {
	private static final int MANA_MAX = 100;
	private static final int COUT_SORT = 30;
	private int mana = MANA_MAX;

	public Mage(String nom, int pvMax, int attaque, int defense) {
		super(nom, pvMax, attaque, defense);
	}

	@Override
	public int attaquer(Combattant cible) {
		if (mana >= COUT_SORT) {
			mana -= COUT_SORT;
			return cible.subirDegatsBruts(getAttaque() * 2); // sort : ignore la défense
		}
		mana = Math.min(MANA_MAX, mana + 15);
		return cible.subirDegats(getAttaque() / 2); // attaque affaiblie + récupération de mana
	}

	@Override
	public String getClasse() {
		return "Mage";
	}
}