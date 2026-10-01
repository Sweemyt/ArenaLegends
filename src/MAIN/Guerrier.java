package MAIN;

public class Guerrier extends Combattant {
	private int rage = 0; // 0 à 100

	public Guerrier(String nom, int pvMax, int attaque, int defense) {
		super(nom, pvMax, attaque, defense);
	}

	@Override
	public int attaquer(Combattant cible) {
		rage += 20;
		int degats = getAttaque();
		if (rage >= 100) {
			degats *= 2;
			rage = 0;
		}
		return cible.subirDegats(degats);
	}

	@Override
	public String getClasse() {
		return "Guerrier";
	}
}