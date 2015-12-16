package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLJDBC 
{
	private String host = "localhost:5432";
	private String database = "aukcje";
	private String user = "postgres";
	private String password = "Admin123$";
	
	private Connection c = null;
	
	public Connection getC() 
	{
		return c;
	}


	public PostgreSQLJDBC()
	{
		try 
		{
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://"+host+"/"+database, user, password);
			System.out.println("Opened database successfully");
			
			
		} 
		catch ( Exception e ) 
		{
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
	    }
	    System.out.println("Operation done successfully");
		
		
	}
	public void queryOpertaion(String query) 
	{
		Statement stmt;
		try
		{
			stmt = c.createStatement();
			stmt.executeUpdate(query );
			stmt.close();
			c.close();

		}
		catch ( SQLException e ) 
		{
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
	    }
	}

	
	
	
}