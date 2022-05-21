package coucheAccesDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public abstract class BaseDAO<T>
{
	protected Connection SqlConn;
	protected PreparedStatement SqlCmd;
	
	/**
	 * Constructeur 
	 * @param SqlConn connexion a la base de donnee
	 */
	
	public BaseDAO(Connection sqlConn)
	{
		SqlConn = sqlConn;
	}
	
	/*
	 * methode dont le comportement doit etre definie dans les sous-classe DAO
	 */
	public T charger(int i) throws ExceptionAccesDB{return null;}
	
	public List<T> chargerTous(int i) throws ExceptionAccesDB{return null;}
		
	public void ajouter(T obj) throws ExceptionAccesDB{}
	
	public void modifier(T obj) throws ExceptionAccesDB{}
	
	public void supprimer(T obj) throws ExceptionAccesDB{} 
	
	public List<T> listerTous() throws ExceptionAccesDB{return null;}
	
}
