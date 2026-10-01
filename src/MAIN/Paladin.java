package MAIN;

public class Paladin extends Guerrier {

	public Paladin(String nom, int pvMax, int attaque, int defense) {
		super(nom, pvMax, attaque, defense);
	}

	@Override
	public int attaquer(Combattant cible) {
		int degats = super.attaquer(cible);
		soigner((int) Math.round(degats * 0.10));
		return degats;
	}

	@Override
	public String getClasse() {
		return "Paladin";
	}
}