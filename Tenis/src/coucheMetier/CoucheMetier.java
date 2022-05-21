package coucheMetier;

import classeMetier.*;

public class CoucheMetier 
{
	private  static CoucheMetier instance = new CoucheMetier();
	
	/**
	 * Le constructeur est prive pour qu'elle ne puisse pas etre instancie depuis l'exterieur
	 */
	private CoucheMetier(){}

	/**
	 * Methode qui retourne l'unique instance de la fabrique
	 * @return instance de la CoucheMetier
	 */
	public static CoucheMetier getInstance()
	{
		return instance;
	}
	
	/**
	 * Methode qui test si les donnees sur un joueur sont  valide et respectes les contrainte metier
	 * @param nom
	 * @param prenom
	 * @param style
	 * @param nomImage
	 * @return   joueur
	 */
	public Joueur testerContraintesJoueur(String nom, String prenom, String style, String nomImage) throws ExceptionMetier 
	{
		Joueur joueur = new Joueur();
		
		if(nom.trim().compareTo("") == 0)
			throw new ExceptionMetier("La chaine du nom est vide");
		
		joueur.setNom(nom);
		
		if(prenom.trim().compareTo("") == 0)
			throw new ExceptionMetier("La chaine du prenom est vide");
		
		joueur.setPrenom(prenom);
		joueur.setStyleJeu(style);
		
		if(nomImage == null || nomImage.trim().compareTo("") == 0)
			joueur.setPhoto("aucune.jpg");
		else
			joueur.setPhoto(nomImage);
		
				
		return joueur;
	}

	/**
	 *  Methode qui test si les donnees sur une equipe sont  valide et respectes les contrainte metier
	 * @param nom
	 * @param joueur1
	 * @param joueur2
	 * @return
	 * @throws ExceptionMetier
	 */
	public Equipe testerContraintesEquipe(String nom, int joueur1, int joueur2) throws ExceptionMetier
	{
		Equipe equipe = new Equipe();
		
		if(nom.trim().compareTo("") == 0)
			throw new ExceptionMetier("La chaine du nom est vide");
		
		equipe.setNom(nom);
		equipe.setJ1(joueur1);
		equipe.setJ2(joueur2);
		
		return equipe;
	}

	/**
	 *  Methode qui test si les donnees sur une rencontre sont  valide et respectes les contrainte metier
	 * @param phase
	 * @param e1
	 * @param e2
	 * @param arbitre
	 * @param table
	 * @param gagnant
	 * @param resultat
	 * @return
	 */
	public Rencontre testerContraintesRencontre(String phase, int e1, int e2, int arbitre, int table, int gagnant,String resultat)  throws ExceptionMetier
	{
		Rencontre rencontre = new Rencontre();
		

		if(phase.trim().compareTo("") == 0)
			throw new ExceptionMetier("La chaine de la phase est vide");
		
		rencontre.setPhase(phase);
		rencontre.setE1(e1);
		rencontre.setE2(e2);
		rencontre.setArbitre(arbitre);
		rencontre.setTable(table);
		rencontre.setGagnant(gagnant);
		rencontre.setResultat(resultat);
		
		return rencontre;
	}

	/**
	 * Methode qui test si les donnees sur une rencontre pour une modification sont  valide et respectes les contrainte metier  
	 * @param idRencontre
	 * @param phase
	 * @param equipe1
	 * @param equipe2
	 * @param arbitre
	 * @param table
	 * @param gagnant
	 * @param resultat
	 * @return
	 */
	public Rencontre testerContraintesModifierRencontre(int idRencontre, String phase, String equipe1, String equipe2, String arbitre, String table, String gagnant, String resultat)  throws ExceptionMetier
	{
		Rencontre rencontre = new Rencontre();
		
		rencontre.setId(idRencontre);
		rencontre.setPhase(phase);
		rencontre.setE1(Integer.parseInt(equipe1));
		rencontre.setE2(Integer.parseInt(equipe2));
		rencontre.setArbitre(Integer.parseInt(arbitre));
		rencontre.setTable(Integer.parseInt(table));
		
		if(gagnant.compareTo(equipe1) != 0  && gagnant.compareTo(equipe2) != 0 )
			throw new ExceptionMetier("Le gagnant doit etre une des deux equipe de la rencontre !");
		
		rencontre.setGagnant(Integer.parseInt(gagnant));
		rencontre.setResultat(resultat);
				
		return rencontre;
	}
	
}
