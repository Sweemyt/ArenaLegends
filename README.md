# Arena Legends — simulateur de tournoi (Java)

TP Java B3 FS : un simulateur de tournoi de combattants en console, en Java pur.

## Lancer le projet

Tous les fichiers sont dans le package `MAIN`. On lance uniquement `Main` :

- le menu (dé, rang, coup critique) tourne jusqu'à ce qu'on tape `0` ;
- à la sortie du menu, `Main` exécute les tests des parties 2 à 5, puis les 100 tournois sans affichage.

| Fichier | Contenu |
|---|---|
| `Main.java` | Partie 1 (menu) + tests des parties 2 à 5 |
| `StatsArene.java` | Partie 2 : statistiques, tri à bulles, doublons, grille 2D |
| `Combattant.java` | Parties 3 et 4 : classe abstraite encapsulée |
| `Guerrier.java`, `Mage.java`, `Voleur.java`, `Paladin.java` | Partie 4 : classes de héros |
| `Tournoi.java` | Partie 5 : inscription, duels, tournoi, classement, stats |

---

## Partie 1 — Pourquoi un `do...while` est-il plus adapté qu'un `while` pour un menu ?

Un menu doit **s'afficher au moins une fois** avant que l'utilisateur puisse faire un choix. Avec un `do...while`, le corps de la boucle (afficher le menu, lire le choix) s'exécute d'abord et la condition (`choix != 0`) est testée ensuite. Avec un `while`, la condition est testée avant la première exécution : il faudrait donc initialiser `choix` à une valeur bidon (ou dupliquer l'affichage du menu avant la boucle) pour entrer dans la boucle.

---

## Partie 2 — Quelle est la différence entre `int[] b = a;` et recopier le tableau case par case ?

`int[] b = a;` ne copie **pas** le tableau : elle copie la **référence**. `a` et `b` désignent alors le *même* tableau en mémoire, donc modifier l'un modifie l'autre. Recopier case par case crée un **nouveau tableau** indépendant.

```java
int[] a = {1, 2, 3};
int[] b = a;                       // alias : même tableau
int[] c = new int[a.length];
for (int i = 0; i < a.length; i++) {
    c[i] = a[i];                   // vraie copie
}

a[0] = 99;
System.out.println(a[0]);  // 99
System.out.println(b[0]);  // 99  -> b a "suivi" a (même tableau)
System.out.println(c[0]);  // 1   -> c n'a pas bougé (tableau indépendant)
```

C'est exactement pour cette raison que `sansDoublons` retourne un **nouveau** tableau et ne modifie pas celui d'origine.

---

## Partie 3 — Pourquoi un setter `setPv(int pv)` casserait-il l'encapsulation même s'il vérifie les bornes ?

Vérifier les bornes (0 ≤ pv ≤ pvMax) ne suffit pas, parce que l'encapsulation ne protège pas seulement des valeurs absurdes : elle protège aussi les **règles métier** qui lient les changements de PV au reste de l'objet. Un `setPv` permettrait à n'importe quel code extérieur de :

- fixer les PV sans passer par `subirDegats`, donc **contourner la défense** et le minimum de 1 dégât ;
- ne **pas alimenter l'historique** des 5 derniers dégâts ;
- **ressusciter** un combattant K.O. (pv = 0 → pv = 50) alors que `soigner` l'interdit ;
- soigner ou blesser sans aucune règle, sans passer par un point d'entrée unique.

Avec uniquement `subirDegats` et `soigner`, la vie ne change que par des opérations qui ont un sens dans le jeu, et toutes les règles sont garanties au même endroit.

**Copie défensive (partie 3, point 6)** : `getHistoriqueDegats()` retourne un `clone()` du tableau. Le test dans `Main` modifie la copie reçue (`h[4] = 9999`), et l'historique interne de l'objet reste inchangé.

---

## Partie 4 — Pourquoi `subirDegatsBruts` est-elle `protected` ?

Cette méthode ignore la défense : c'est un comportement très puissant, réservé aux sous-classes qui en ont besoin (le `Mage`, dont le sort ignore la défense).

- Si elle était `private`, le `Mage` ne pourrait pas l'appeler.
- Si elle était `public`, **n'importe quel code** pourrait infliger des dégâts en ignorant la défense, ce qui casserait la règle « les dégâts réels valent d - defense, minimum 1 ».
- `protected` est donc le bon compromis : accessible aux sous-classes, fermée au code extérieur.

Remarque : en Java, `protected` donne aussi l'accès aux classes du **même package**. Ici tout est dans `MAIN`, donc le `Mage` appelle `cible.subirDegatsBruts(...)` sans problème ; dans un projet découpé en packages, c'est l'héritage qui donnerait l'accès.

## Partie 4 — Dans `Combattant c = new Mage(...); c.attaquer(x);`, quelle méthode est appelée et pourquoi ?

C'est la méthode **`attaquer` de `Mage`** qui est appelée.

- Le **type déclaré** (`Combattant`) est le type de la variable, connu à la compilation. Il détermine **ce qu'on a le droit d'appeler** : le compilateur vérifie que `attaquer` existe dans `Combattant` (c'est une méthode abstraite). On ne pourrait pas appeler une méthode propre au `Mage` sans cast.
- Le **type réel** (`Mage`) est le type de l'objet créé avec `new`, connu à l'exécution. Il détermine **quelle version** de la méthode s'exécute.

Ce mécanisme s'appelle la **liaison dynamique** (polymorphisme) : la JVM choisit la méthode redéfinie en fonction du type réel de l'objet. C'est ce qui permet à `Tournoi.duel` de manipuler des `Combattant` sans savoir si c'est un Guerrier, un Mage, un Voleur ou un Paladin : chacun attaque à sa manière.

---

## Partie 5 — Quelle classe gagne le plus souvent ? Est-ce équilibré ?

**Protocole** : 100 tournois complets sans affichage (méthode `centTournois()` de `Main`), avec 8 combattants (2 Guerriers, 2 Mages, 2 Voleurs, 2 Paladins) recréés à chaque tournoi.

| Combattant | Classe | PV | ATK | DEF | Autre |
|---|---|---|---|---|---|
| Kaelen | Guerrier | 120 | 18 | 6 | |
| Brutus | Guerrier | 150 | 20 | 10 | |
| Elyndra | Mage | 90 | 22 | 3 | |
| Merlan | Mage | 100 | 20 | 4 | |
| Shade | Voleur | 100 | 17 | 5 | esquive 30 % |
| Vipera | Voleur | 90 | 19 | 4 | esquive 25 % |
| Aldric | Paladin | 130 | 16 | 8 | |
| Seraph | Paladin | 140 | 15 | 10 | |

**Résultat** (titres de champion sur 100 tournois, observé sur 3 exécutions consécutives) :

| Classe | Titres |
|---|---|
| Guerrier | 100 |
| Mage | 0 |
| Voleur | 0 |
| Paladin | 0 |

**Ce n'est pas équilibré** avec ces statistiques : les Guerriers gagnent (quasi) systématiquement. Explications :

- Les dégâts sont réduits de la **défense à plat** (`d - defense`). Avec 15 à 20 d'attaque face à 10 de défense, un Voleur ou un Paladin n'inflige que 5 à 10 dégâts par coup, alors que la rage du Guerrier **double ses dégâts toutes les 5 attaques**.
- Brutus cumule le plus de PV (150), la meilleure défense (10) et la meilleure attaque physique (20) : il est difficile à battre.
- Le Mage ne dispose que de 3 sorts puissants (30 de mana chacun) ; ensuite il tombe à `attaque ÷ 2`, ce qui est trop faible pour finir un duel.
- L'esquive (25 à 40 %) ne compense pas la faiblesse des dégâts du Voleur.

**Pistes de rééquilibrage** : réduire les PV et la défense des Guerriers, augmenter la récupération de mana du Mage, ou remplacer la défense « à plat » par un pourcentage de réduction. Les résultats dépendent des statistiques choisies : ils peuvent changer si on modifie les valeurs dans `creerTournoi()`.
