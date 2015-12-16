package modules.przedmioty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.PostgreSQLJDBC;

public class Przedmiot
{
	private int id;
	private String nazwa;
	private String opis;
	private String zdjecie_src;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getNazwa()
	{
		return nazwa;
	}
	public void setNazwa(String nazwa)
	{
		this.nazwa = nazwa;
	}
	public String getOpis()
	{
		return opis;
	}
	public void setOpis(String opis)
	{
		this.opis = opis;
	}
	public String getZdjecieSrc()
	{
		return zdjecie_src;
	}
	public void setZdjecieSrc(String zdjecie_src)
	{
		this.zdjecie_src = zdjecie_src;
	}
	
	public void insertPrzedmiot()
	{
		
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("INSERT INTO t_przedmioty(nazwa, opis, zdjecie_src)"
									+" VALUES ('%s','%s','%s')",
										nazwa, opis, zdjecie_src);
		pgsq.queryOpertaion(query);
	}
	
	public int getLastId()
	{
		int id_przedmiotu = 0;
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("SELECT id FROM t_przedmioty order by id desc limit 1");
		try
		{
			Statement stmt = pgsq.getC().createStatement();
			
			ResultSet rs = stmt.executeQuery( query );
			while ( rs.next() ) 
			{
				id_przedmiotu = rs.getInt("id");
	            
			}
			rs.close();
		}
		catch(SQLException e)
		{
			
		}
		return id_przedmiotu;
	}
	public void updatePrzedmiot()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("UPDATE t_przedmioty SET nazwa = '%s', opis = '%s', zdjecie_src = '%s' WHERE id=%d",
									nazwa, opis, zdjecie_src, id);
		pgsq.queryOpertaion(query);
	}
	public void deletePrzedmiot()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("DELETE FROM t_przedmioty WHERE id=%d", id);
		pgsq.queryOpertaion(query);
	}
	
	
	
	
}
