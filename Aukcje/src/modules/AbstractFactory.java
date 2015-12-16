package modules;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.PostgreSQLJDBC;

abstract public class AbstractFactory
{
	protected int id;
	protected String query;
	protected String tabela;

	public AbstractFactory()
	{
		query ="";
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	public Object getObject()
	{
		Object object = null;
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		Statement stmt;
		Connection c = pgsq.getC(); 
		try
		{
			stmt = c.createStatement();
			if(query=="")
				query = String.format("SELECT * FROM %s where id=%d", tabela, id);
			
			ResultSet rs = stmt.executeQuery( query );
			System.out.println(query);
			while ( rs.next() ) 
			{
				object = fetchObject(rs);
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
		
		
		return object;
	}
	protected Object fetchObject(ResultSet rs) throws SQLException
	{
		return null;
	}
}
