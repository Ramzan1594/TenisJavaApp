package coucheAccesDB;

import java.sql.*;
import java.util.*;
import classeMetier.*;

public class ArbitreDAO extends  BaseDAO<Arbitre>
{

	public ArbitreDAO(Connection sqlConn){ super(sqlConn);}
	
	/**
	 * Methode qui lit dans la DB un arbitre specifique
	 * @param num : le numero de l'arbitre
	 */
	public Arbitre charger(int num) throws  ExceptionAccesDB
	{
		Arbitre arbitre = null;
		
		try
		{
			SqlCmd = SqlConn.prepareCall("SELECT nom, prenom, anneExperience "
										+"FROM  ARBITRE "
										+"WHERE idArbitre = ? ");
			
			SqlCmd.setInt(1, num);
			
			ResultSet sqlRes = SqlCmd.executeQuery();
						
			if(sqlRes.next() == true)
			{
				arbitre = new Arbitre(num, sqlRes.getString(1), sqlRes.getString(2), sqlRes.getInt(3));
			}
			
			sqlRes.close();
			return arbitre;
		}
		catch(Exception e)
		{
			throw new  ExceptionAccesDB(e.getMessage());
		}
	}
	
	public List<Arbitre> listerTous() throws ExceptionAccesDB
	{
		ArrayList<Arbitre> list = new  ArrayList<Arbitre>();
		
		try
		{
			SqlCmd = SqlConn.prepareCall( "SELECT idArbitre, nom, prenom, anneExperience "
										+ "FROM ARBITRE "
										+ "ORDER BY idArbitre ASC ");
			
			ResultSet  sqlRes = SqlCmd.executeQuery(); 
			
			while(sqlRes.next() == true)
			{
				list.add(new Arbitre(sqlRes.getInt(1), sqlRes.getString(2), sqlRes.getString(3), sqlRes.getInt(4)));
			}
			sqlRes.close();
			
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
		
		return list;
	}

}
