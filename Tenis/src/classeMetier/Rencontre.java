package classeMetier;

public class Rencontre 
{
	private int IdRencontre;
	private String Phase , Equipe1, Equipe2, ArbitreString, GagnantString;
	private int E1, E2, Arbitre, Table;
	private int Gagnant;
	private String Resultat;
	
	//Propriete get pour avoir les valeurs des attributs
	public int getId(){		 return IdRencontre;}
	public String getPhase(){return Phase;}
	public int getE1(){		 return E1;}
	public int getE2(){		 return E2;}
	public int getGagnant(){ return Gagnant;}
	public int getArbitre(){ return Arbitre;}
	public int getTable(){   return Table;}
	public String getResultat(){return Resultat;}
	
	public String getEquipe1(){return Equipe1;}
	public String getEquipe2(){return Equipe2;}
	public String getArbitreString(){return ArbitreString;}
	public String getGagnantString(){return GagnantString;}
	
	
	
	//Propriete set pour attribuer et modifie les valeurs des attributs
	public void setId(int id){			IdRencontre = id;}
	public void setPhase(String phase){	Phase = phase;}
	public void setE1(int e1){			E1 = e1;}
	public void setE2(int e2){			E2 = e2;}
	public void setGagnant(int g){		Gagnant = g;}
	public void setArbitre(int a){		Arbitre = a;}
	public void setTable(int t){        Table = t;}
	public void setResultat(String resultat){	Resultat = resultat;}
	
	public void setEquipe1(String e1){	Equipe1 = e1;}
	public void setEquipe2(String e2){	Equipe2 = e2;}
	public void setArbitreString(String a){	ArbitreString = a;}
	public void setGagnantString(String g){	GagnantString = g;}
	
	//REDEFINITION DE LA METHODE TOSTRING   
	@Override
	public String toString(){ return IdRencontre + "." + Phase + "    /EQUIPES : " + E1 + " - " + E2 + "    /Arbitres : " + Arbitre + "    /Table : " + Table + 
									"    /Vainceur :" + Gagnant +	"    /Resultat : " + Resultat;}
	
		
		
	//Constructeur
	public Rencontre(){}    //par defaut
		
	public Rencontre(Rencontre r)
	{
		IdRencontre = r.IdRencontre;
		Phase		= r.Phase;
		E1			= r.E1;
		E2			= r.E2;
		Arbitre		= r.Arbitre;
		Table		= r.Table;
		Gagnant 	= r.Gagnant;
		Resultat	= r.Resultat;
	}
	
	public Rencontre(int id, String phase, int e1, int e2, int arbitre, int table, int gagnant, String resultat)
	{
		IdRencontre= id;
		Phase	= phase;
		E1		= e1;
		E2		= e2;
		Arbitre = arbitre;
		Table	= table;
		Gagnant = gagnant;
		Resultat= resultat;
	}
	
	public Rencontre(int id, String phase, String e1, String e2, String arbitre, int table, String gagnant, String resultat)
	{
		IdRencontre= id;
		Phase	= phase;
		Equipe1		= e1;
		Equipe2		= e2;
		ArbitreString = arbitre;
		Table	= table;
		GagnantString = gagnant;
		Resultat= resultat;
	}

}
