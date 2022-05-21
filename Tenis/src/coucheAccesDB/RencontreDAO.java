package coucheAccesDB;

import java.sql.*;
import java.util.*;
import classeMetier.*;

public class RencontreDAO extends BaseDAO<Rencontre>
{
	public RencontreDAO(Connection sqlConn){ super(sqlConn);}
	
	
	/**
	 * Methode qui ajoute une equipe dans la DB 
	 * @param obj : est une equipe
	 */
	public void ajouter(Rencontre obj) throws ExceptionAccesDB
	{
		try
		{
			SqlCmd = SqlConn.prepareCall("select max(idRencontre) + 1 FROM RENCONTRE ");
			
			ResultSet sqlRes = SqlCmd.executeQuery();
			
			sqlRes.next();
			
			int numRencontre = sqlRes.getInt(1);  // getInt va prendre le max du tableau RENCONTRE + 1  DONC ca sera le numero du new RENCONTRE
			
			if(sqlRes.wasNull())
				numRencontre = 1;
			
			SqlCmd.close();
			
			SqlCmd = SqlConn.prepareCall("INSERT INTO RENCONTRE VALUES(?,?,?,?,?,?,?,?)");
			
			SqlCmd.setInt(1, numRencontre);  
			SqlCmd.setString(2, obj.getPhase());
			SqlCmd.setInt(3, obj.getE1());
			SqlCmd.setInt(4, obj.getE2());
			SqlCmd.setInt(5, obj.getArbitre());
			SqlCmd.setInt(6, obj.getTable());
			SqlCmd.setInt(7, obj.getGagnant());
			SqlCmd.setString(8, obj.getResultat());
			
			SqlCmd.executeUpdate();
			
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
	}
	
	
	/**
	 * Methode qui met ajour une rencontre de la DB
	 */
	public void modifier(Rencontre obj) throws ExceptionAccesDB
	{
		try
		{	
			SqlCmd = SqlConn.prepareCall("UPDATE RENCONTRE "
										+"SET   phase = ?, idEquipe1 = ?, idEquipe2 = ?, idArbitre = ?, idTable = ?, idGagnant = ?, resultat = ? "
										+"WHERE idRencontre = ?");
			
		 
			SqlCmd.setString(1, obj.getPhase());
			SqlCmd.setInt(2, obj.getE1());
			SqlCmd.setInt(3, obj.getE2());
			SqlCmd.setInt(4, obj.getArbitre());
			SqlCmd.setInt(5, obj.getTable());
			SqlCmd.setInt(6, obj.getGagnant());
			SqlCmd.setString(7, obj.getResultat());
			
			SqlCmd.setInt(8, obj.getId());  			
			
			SqlCmd.executeUpdate();
			
			SqlCmd.close();
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
	}
	
	
	
	/**
	 * Methode qui supprime toutes les rencontre  de la base de donne
	 */
	public void supprimerTous() throws ExceptionAccesDB
	{
		try
		{
			SqlConn.setAutoCommit(false);
			
			SqlCmd = SqlConn.prepareCall("DELETE FROM RENCONTRE ");
			
			
			SqlCmd.executeUpdate();
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
	}
	
	/**
	 * Methode qui lit dans la DB un rencontre specifique
	 * @param num : le numero de la rencontre
	 */
	public Rencontre charger(int num) throws  ExceptionAccesDB
	{
		Rencontre rencontre = null;
		
		try
		{
			SqlCmd = SqlConn.prepareCall("SELECT idRencontre, phase, idEquipe1, idEquipe2, idArbitre, idTable, idGagnant, resultat "
										+"FROM  RENCONTRE "
										+"WHERE idRencontre = ? ");
			
			SqlCmd.setInt(1, num);
			
			ResultSet sqlRes = SqlCmd.executeQuery();
						
			if(sqlRes.next() == true)
			{
				rencontre = new Rencontre(num, sqlRes.getString(2), sqlRes.getInt(3), sqlRes.getInt(4), sqlRes.getInt(5), sqlRes.getInt(6), sqlRes.getInt(7), sqlRes.getString(8));
			}
			
			sqlRes.close();
			return rencontre;
		}
		catch(Exception e)
		{
			throw new  ExceptionAccesDB(e.getMessage());
		}
	}
	
	/**
	 * Methode qui lit dans la DB les rencontre d'une equipe
	 * @param num : numero de l'equipe
	 */
	public List<Rencontre> chargerTous(int num) throws  ExceptionAccesDB
	{
		ArrayList<Rencontre> list = new  ArrayList<Rencontre>();
		
		try
		{
			SqlCmd = SqlConn.prepareCall("SELECT idRencontre, phase, idEquipe1, idEquipe2, idArbitre, idTable, idGagnant, resultat "
										+"FROM  RENCONTRE "
										+"WHERE idEquipe1 = ? OR idEquipe2 = ? ");
			
			SqlCmd.setInt(1, num);
			SqlCmd.setInt(2, num);
			
			ResultSet sqlRes = SqlCmd.executeQuery();
						
			while(sqlRes.next() == true)
			{
				list.add(new Rencontre(sqlRes.getInt(1),sqlRes.getString(2), sqlRes.getInt(3), sqlRes.getInt(4), sqlRes.getInt(5), sqlRes.getInt(6), sqlRes.getInt(7),
							sqlRes.getString(8)));
			}
			sqlRes.close();
			
		}
		catch(Exception e)
		{
			throw new  ExceptionAccesDB(e.getMessage());
		}
		return list;
	}
	
	
	
	/**
	 * Methode qui lit dans la DB toutes les rencontres
	 */
	public List<Rencontre> listerTous() throws ExceptionAccesDB
	{
		ArrayList<Rencontre> list = new  ArrayList<Rencontre>();
		
		try
		{
			SqlCmd = SqlConn.prepareCall( "SELECT idRencontre, phase, idEquipe1, idEquipe2, idArbitre, idTable, idGagnant, resultat "
										+ "FROM RENCONTRE ");
			
			ResultSet  sqlRes = SqlCmd.executeQuery(); 
			
			while(sqlRes.next() == true)
			{
				list.add(new Rencontre(sqlRes.getInt(1),sqlRes.getString(2), sqlRes.getInt(3), sqlRes.getInt(4), sqlRes.getInt(5), sqlRes.getInt(6), sqlRes.getInt(7),
							sqlRes.getString(8)));
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
