package coucheAccesDB;

import java.sql.*;
import java.util.*;
import classeMetier.*;

public class EquipeDAO extends  BaseDAO<Equipe>
{
	public EquipeDAO(Connection sqlConn){ super(sqlConn);}

	
	/**
	 * Methode qui ajoute une equipe dans la DB 
	 * @param obj : est une equipe
	 */
	public void ajouter(Equipe obj) throws ExceptionAccesDB
	{
		try
		{
			SqlCmd = SqlConn.prepareCall("select max(idEquipe) + 1 FROM EQUIPE ");
			
			ResultSet sqlRes = SqlCmd.executeQuery();
			
			sqlRes.next();
			
			int numEquipe = sqlRes.getInt(1);  // getInt va prendre le max du tableau EQUIPE + 1  DONC ca sera le numero du new EQUIPE
			
			if(sqlRes.wasNull())
				numEquipe = 1;
			
			SqlCmd.close();
			
			SqlCmd = SqlConn.prepareCall("INSERT INTO EQUIPE VALUES(?,?,?,?)");
			
			SqlCmd.setInt(1, numEquipe);  
			SqlCmd.setString(2, obj.getNom());
			SqlCmd.setInt(3, obj.getJ1());
			SqlCmd.setInt(4, obj.getJ2());
			
			SqlCmd.executeUpdate();
			
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
	}
	
	/**
	 * Methode qui supprimer une equipe de la base de donne
	 * @param num    id de l'equipe a supprimer
	 */
	public void supprimerEquipe(int num) throws ExceptionAccesDB
	{
		try
		{
			SqlConn.setAutoCommit(false);
			
			SqlCmd = SqlConn.prepareCall("DELETE FROM EQUIPE WHERE idEquipe = ?");
			
			SqlCmd.setInt(1, num);
			
			SqlCmd.executeUpdate();
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
	}
	
	
	/**
	 * Methode qui lit dans la DB une equipe specifique
	 * @param num : le numero de la table
	 */
	public Equipe charger(int num) throws  ExceptionAccesDB
	{
		Equipe equipe = null;
		
		try
		{
			SqlCmd = SqlConn.prepareCall("SELECT nom, idJoueur1, idJoueur2 "
										+"FROM  EQUIPE "
										+"WHERE idEquipe = ? ");
			
			SqlCmd.setInt(1, num);
			
			ResultSet sqlRes = SqlCmd.executeQuery();
						
			if(sqlRes.next() == true)
			{
				equipe = new Equipe(num,  sqlRes.getString(1), sqlRes.getInt(2), sqlRes.getInt(3));
			}
			
			sqlRes.close();
			return equipe;
		}
		catch(Exception e)
		{
			throw new  ExceptionAccesDB(e.getMessage());
		}
	}
	
	/**
	 * Methode qui lit dans la DB toutes les equipes
	 */
	public List<Equipe> listerTous() throws ExceptionAccesDB
	{
		ArrayList<Equipe> list = new  ArrayList<Equipe>();
		
		try
		{
			SqlCmd = SqlConn.prepareCall( "SELECT idEquipe, nom, idJoueur1, idJoueur2 "
										+ "FROM EQUIPE "
										+ "ORDER BY idEquipe ASC ");
			
			ResultSet  sqlRes = SqlCmd.executeQuery(); 
			
			while(sqlRes.next() == true)
			{
				list.add(new Equipe(sqlRes.getInt(1),sqlRes.getString(2), sqlRes.getInt(3), sqlRes.getInt(4)));
			}
			sqlRes.close();
			
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
		
		return list;
	}
	
	
	/**
	 * Methode qui lit dans la DB l'equipe qui contient un joueur specifique, pour voir s'il est deja dans une equipe
	 * @param num  est le nombre d'equipe qui contienne l'un des deux joueurs
	 */
	public int listerEquipeSelonJoueur(int j1, int j2) throws ExceptionAccesDB
	{
		ArrayList<Equipe> list = new  ArrayList<Equipe>();
		int equipe = 0;
		
		try
		{
			SqlCmd = SqlConn.prepareCall( "SELECT idEquipe, nom, idJoueur1, idJoueur2 "
										+ "FROM EQUIPE "
										+ "WHERE idJoueur1 = ? OR idJoueur1 = ? OR idJoueur2 = ? OR idJoueur2 = ? ");
			SqlCmd.setInt(1, j1);
			SqlCmd.setInt(2, j2);
			SqlCmd.setInt(3, j1);
			SqlCmd.setInt(4, j2);
			
			ResultSet  sqlRes = SqlCmd.executeQuery(); 
			
			while(sqlRes.next() == true)
			{
				list.add(new Equipe(sqlRes.getInt(1),sqlRes.getString(2), sqlRes.getInt(3), sqlRes.getInt(4)));
			}
			sqlRes.close();
			
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
		equipe = list.size();
		
		return equipe;
	}
	

	/**
	 * Quand on veut supprimer un joeur il faut d'abord supprimer l'equipe 
	 * dans laquelle le joueur se trouve, cette methode supprime l'equipe 
	 * du joueur
	 * @param numJoueur numJoueur a rechercher
	 */
	public void supprimer(int numJoueur) throws ExceptionAccesDB
	{
		try
		{
			SqlConn.setAutoCommit(false);
			
			SqlCmd = SqlConn.prepareCall("DELETE FROM EQUIPE WHERE idJoueur1 = ? OR idJoueur2 = ?");
			
			SqlCmd.setInt(1, numJoueur);
			SqlCmd.setInt(2, numJoueur);
			
			SqlCmd.executeUpdate();
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
	}
}
