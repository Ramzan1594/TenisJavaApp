package classeMetier;

public class Equipe  
{
	private int IdEquipe;
	private String Nom, Joueur1, Joueur2;
	private int J1, J2;
	
	//Propriete get pour avoir les valeurs des attributs
	public int getId(){		return IdEquipe;}
	public String getNom(){	return Nom;}
	public int getJ1(){return J1;}
	public int getJ2(){return J2;}
	public String getJoueur1(){return Joueur1;}
	public String getJoueur2(){return Joueur2;}
	
	
	//Propriete set pour attribuer et modifie les valeurs des attributs
	public void setId(int id){		IdEquipe = id;}
	public void setNom(String nom){	Nom = nom;}
	public void setJ1(int j1){		J1 = j1;}
	public void setJ2(int j2){		J2 = j2;}
	public void setJoueur1(String j1){		Joueur1 = j1;}
	public void setJoueur2(String j2){		Joueur2 = j2;}
	
	
	//REDEFINITION DE LA METHODE TOSTRING   
	@Override
	public String toString(){ return  IdEquipe + "."+Nom + " : " + J1 + " et " + J2;}
		
		
	//Constructeur
	public Equipe(){}    //par defaut
		
	public Equipe(Equipe e)
	{
		IdEquipe= e.IdEquipe;
		Nom		= e.Nom;
		J1		= e.J1;
		J2		= e.J2;
	}
	
	public Equipe(int id, String nom, int j1, int j2)
	{
		IdEquipe= id;
		Nom		= nom;
		J1		= j1;
		J2		= j2;
	}
	
	public Equipe(int id, String nom, String j1, String j2)
	{
		IdEquipe= id;
		Nom		= nom;
		Joueur1		= j1;
		Joueur2		= j2;
	}
	

}
