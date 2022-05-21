package classeMetier;

public class Table 
{
	private int IdTable, Position;
	
	//Propriete get pour avoir les valeurs des attributs
	public int getId(){ 		return IdTable;}
	public int getPosition(){ 	return Position;}
	
	//Propriete set pour attribuer et modifie les valeurs des attributs
	public void setId(int id){				IdTable = id;}
	public void setPosition(int position){	Position = position;}
	
	//REDEFINITION DE LA METHODE TOSTRING   
	@Override
	public String toString(){ return IdTable + " " + Position;}
		
		
	//Constructeur
	public Table(){}    //par defaut
		
	public Table(Table t)
	{
		IdTable	 = t.IdTable;
		Position = t.Position;
	}
	
	public Table(int id, int position)
	{
		IdTable  = id;
		Position = position;
	}
}
