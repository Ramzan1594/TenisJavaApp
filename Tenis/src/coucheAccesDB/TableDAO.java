package coucheAccesDB;

import java.sql.*;
import java.util.*;
import classeMetier.*;

public class TableDAO extends BaseDAO<Table>
{
	public TableDAO(Connection sqlConn){ super(sqlConn);}
	
	/**
	 * Methode qui lit dans la DB une table specifique
	 * @param num : le numero de la table
	 */
	public Table charger(int num) throws  ExceptionAccesDB
	{
		Table table = null;
		
		try
		{
			SqlCmd = SqlConn.prepareCall("SELECT position "
										+"FROM  TABLEE "
										+"WHERE idTable = ? ");
			
			SqlCmd.setInt(1, num);
			
			ResultSet sqlRes = SqlCmd.executeQuery();
						
			if(sqlRes.next() == true)
			{
				table = new Table(num, sqlRes.getInt(1));
			}
			
			sqlRes.close();
			return table;
		}
		catch(Exception e)
		{
			throw new  ExceptionAccesDB(e.getMessage());
		}
	}
	
	/**
	 * Methode qui lit dans la DB tous les tables
	 */
	public List<Table> listerTous() throws ExceptionAccesDB
	{
		ArrayList<Table> list = new  ArrayList<Table>();
		
		try
		{
			SqlCmd = SqlConn.prepareCall( "SELECT idTable, position "
										+ "FROM TABLEE "
										+ "ORDER BY idTable ASC");
			
			ResultSet  sqlRes = SqlCmd.executeQuery(); 
			
			while(sqlRes.next() == true)
			{
				list.add(new Table(sqlRes.getInt(1), sqlRes.getInt(2)));
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
