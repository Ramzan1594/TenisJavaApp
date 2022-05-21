package coucheAccesDB;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.SQLException;

public class FabriqueDAO 
{
	private static FabriqueDAO instance = new FabriqueDAO(); //variable stockant l'unique instance de la fabrique
	private Connection  SqlConn = null;                      //variable stockant la connexion a la DB
	
	/**
	 * Le consructeur est privee pour que la classe FabriqueDAO ne soit pas instancie de l'exterieur
	 */
	private FabriqueDAO(){}
	
	/**
	 * Methode quiretourne l'unique instance de la fabrique
	 * @return l'instance  de la couche metier
	 */
	public static FabriqueDAO getInstance()
	{
		return instance;
	}
	
	/**
	 * Methode qui cree la connexion avec la DB
	 */
	public void creeConnexion() throws ExceptionAccesDB
	{
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			SqlConn = DriverManager.getConnection(  "jdbc:sqlserver://localhost:1433;" +
													"database=Tournoi2;" +
													"user=genial;" +
													"password=super;");
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
	}
	
	/**
	 * Methode qui debute une transaction
	 */
	public  void debuterTransaction() throws ExceptionAccesDB
	{
		try
		{
			SqlConn.setAutoCommit(false);
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
	}
	
	/**
	 * Methode qui valide une transaction
	 */
	public void validerTransaction() throws ExceptionAccesDB
	{
		try
		{
			SqlConn.commit();
			SqlConn.setAutoCommit(true);
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
	}
	
	/**
	 * Methode qui annule une transaction
	 */
	public void annulerTransaction() throws ExceptionAccesDB
	{
		try
		{
			SqlConn.rollback();
			SqlConn.setAutoCommit(true);
		}
		catch(Exception e)
		{
			throw new ExceptionAccesDB(e.getMessage());
		}
	}
	
	/**
	 * Methode qui fourni une instance d'ARBITRE
	 * @return instance ARBITRE
	 */
	public ArbitreDAO getArbitreDAO()
	{
		return new ArbitreDAO(SqlConn);
	}
	
	/**
	 * Methode qui fourni une instance d'EQUIPE
	 * @return instance EQUIPE
	 */
	public EquipeDAO getEquipeDAO()
	{
		return new EquipeDAO(SqlConn);
	}
	
	/**
	 * Methode qui fourni une instance d'JOUEUR
	 * @return instance JOUEUR
	 */
	public JoueurDAO getJoueurDAO()
	{
		return new JoueurDAO(SqlConn);
	}
	
	/**
	 * Methode qui fourni une instance d'RENCONTRE
	 * @return instance RENCONTRE
	 */
	public RencontreDAO getRencontreDAO()
	{
		return new RencontreDAO(SqlConn);
	}
	
	/**
	 * Methode qui fourni une instance d'TABLE
	 * @return instance TABLE
	 */
	public TableDAO getTableDAO()
	{
		return new TableDAO(SqlConn);
	}
}
