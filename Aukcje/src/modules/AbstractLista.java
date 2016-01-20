package modules;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.PostgreSQLJDBC;

abstract public class AbstractLista
{
	protected String table;
	protected String query;
	public AbstractLista()
	{
		query = "";
	}
	public ArrayList<Object> getList()
	{
		ArrayList<Object> objects = new ArrayList<Object>();
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		Statement stmt;
		Connection c = pgsq.getC(); 
		try
		{
			stmt = c.createStatement();
			if(query=="")
				query = String.format("SELECT * FROM %s", table);
			
			ResultSet rs = stmt.executeQuery( query );
			while ( rs.next() ) 
			{
				objects.add(this.fetchObject(rs));
			}
			rs.close();
			stmt.close();
			c.close();

		}
		catch ( Exception e ) 
		{
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
	    }
		
		
		return objects;
	}
	
	protected Object fetchObject(ResultSet rs) throws SQLException
	{
		return null;
	}
}
