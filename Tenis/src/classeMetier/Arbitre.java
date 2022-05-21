package classeMetier;

public class Arbitre 
{
	private int IdArbitre;
	private String Nom, Prenom;
	private int AnneeExperience;
	
	//Propriete get pour avoir les valeurs des attributs
	public int getId(){				return IdArbitre;}
	public String getNom(){			return Nom;}
	public String getPrenom(){		return Prenom;}
	public int getAnneeExperience(){return AnneeExperience;}
	
	
	//Propriete set pour attribuer et modifie les valeurs des attributs
	public void setId(int id){ 					IdArbitre = id;}
	public void setNom(String nom){ 			Nom = nom;}
	public void setPrenom(String prenom){ 	Prenom = prenom;}
	public void setAnneeExperience(int exp){	AnneeExperience = exp;}
	
	
	//REDEFINITION DE LA METHODE TOSTRING   
	@Override
	public String toString(){ return Nom + " " + Prenom;}
	
	
	//Constructeur
	public Arbitre(){}    //par defaut
	
	public Arbitre(Arbitre a)
	{
		IdArbitre		= a.IdArbitre;
		Nom				= a.Nom;
		Prenom	 		= a.Prenom;
		AnneeExperience = a.AnneeExperience;
	}
	
	public Arbitre(int id, String nom, String prenom, int anneeExp)
	{
		IdArbitre		= id;
		Nom				= nom;
		Prenom			= prenom;
		AnneeExperience = anneeExp;
	}

}
