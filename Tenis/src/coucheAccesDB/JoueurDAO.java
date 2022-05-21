package coucheAccesDB;

import java.sql.*;
import java.util.*;
import classeMetier.*;

public class JoueurDAO extends BaseDAO<Joueur>
{
	public JoueurDAO(Connection sqlConn){ super(sqlConn);}
	
	/**
	 * Methode qui ajoute dans la base de donne un joueur
	 * @param obj : joueur
	 */
	public void ajouter(Joueur obj) throws ExceptionAccesDB
	{
		try
		{
			SqlCmd = SqlConn.prepareCall("select max(idJoueur) + 1 FROM JOUEUR ");
			
			ResultSet sqlRes = SqlCmd.executeQuery();
			
			sqlRes.next();
			
			int numJoueur = sqlRes.getInt(1);  // getInt va prendre le max du tableau JOUEUR + 1  DONC ca sera le numero du new joueur
			
			if(sqlRes.wasNull())
				numJoueur = 1;
			
			SqlCmd.close();
			
			SqlCmd = SqlConn.prepareCall("INSERT INTO JOUEUR VALUES(?,?,?,?,?)");
			
			SqlCmd.setInt(1, numJoueur);  
			SqlCmd.setString(2, obj.getNom());
			SqlCmd.setString(3, obj.getPrenom());
			SqlCmd.setString(4, obj.getStyleJeu());
			SqlCmd.setString(5, obj.getPhoto());
			
			SqlCmd.executeUpdate();
			
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
	}
	
	/**
	 * Methode qui supprimer un joueur de la base de donne
	 * @param num    id du joueur a supprimer
	 */
	public void supprimer(int num) throws ExceptionAccesDB
	{
		try
		{
			SqlConn.setAutoCommit(false);
			
			SqlCmd = SqlConn.prepareCall("DELETE FROM JOUEUR WHERE idJoueur = ?");
			
			SqlCmd.setInt(1, num);
			
			SqlCmd.executeUpdate();
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
		
	}
	

	/**
	 * Methode qui lit dans la DB un joueur specifique
	 * @param num : le numero du joueur
	 */
	public Joueur charger(int num) throws  ExceptionAccesDB
	{
		Joueur joueur = null;
		
		try
		{
			SqlCmd = SqlConn.prepareCall("SELECT nom, prenom, styleJeu, photo "
										+"FROM  JOUEUR "
										+"WHERE idJoueur = ? ");
			
			SqlCmd.setInt(1, num);
			
			ResultSet sqlRes = SqlCmd.executeQuery();
						
			if(sqlRes.next() == true)
			{
				joueur = new Joueur(num, sqlRes.getString(1), sqlRes.getString(2), sqlRes.getString(3), sqlRes.getString(4));
			}
			
			sqlRes.close();
			return joueur;
		}
		catch(Exception e)
		{
			throw new  ExceptionAccesDB(e.getMessage());
		}
	}
	
	/**
	 * Methode qui lit dans la DB tous les joueurs
	 */
	public List<Joueur> listerTous() throws ExceptionAccesDB
	{
		ArrayList<Joueur> list = new  ArrayList<Joueur>();
		
		try
		{
			SqlCmd = SqlConn.prepareCall( "SELECT idJoueur, nom, prenom, styleJeu, photo "
										+ "FROM JOUEUR "
										+ "ORDER BY idJoueur ASC ");
			
			ResultSet  sqlRes = SqlCmd.executeQuery(); 
			
			while(sqlRes.next() == true)
			{
				list.add(new Joueur(sqlRes.getInt(1), sqlRes.getString(2), sqlRes.getString(3), sqlRes.getString(4), sqlRes.getString(5)));
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
