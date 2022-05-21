package classeMetier;

public class Joueur 
{
	private int IdJoueur;
	private String Nom, Prenom;
	private String StyleJeu, Photo;
	
	//Propriete get pour avoir les valeurs des attributs
	public int getId(){			return IdJoueur;}
	public String getNom(){		return Nom;}
	public String getPrenom(){	return Prenom;}
	public String getStyleJeu(){return StyleJeu;}
	public String getPhoto(){	return Photo;}
	
	//Propriete set pour attribuer et modifie les valeurs des attributs
	public void setId(int id){ 				IdJoueur = id;}
	public void setNom(String nom){ 		Nom = nom;}
	public void setPrenom(String prenom){Prenom = prenom;}
	public void setStyleJeu(String style){StyleJeu = style;}
	public void setPhoto(String photo){	 Photo = photo;}
	
	
	
	//REDEFINITION DE LA METHODE TOSTRING   
	@Override
	public String toString(){ return Nom + " " + Prenom;}
	
	
	
	//Constructeur
	public Joueur(){}    //par defaut
	
	public Joueur(Joueur j)
	{
		IdJoueur= j.IdJoueur;
		Nom		= j.Nom;
		Prenom	= j.Prenom;
		StyleJeu= j.StyleJeu;
		Photo	= j.Photo;
	}
	
	public Joueur(int id, String nom, String prenom, String style, String photo)
	{
		IdJoueur= id;
		Nom		= nom;
		Prenom	= prenom;
		StyleJeu= style;
		Photo	= photo;
	}
	
	

}
