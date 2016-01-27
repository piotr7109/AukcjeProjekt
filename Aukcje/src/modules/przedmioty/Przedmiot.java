package modules.przedmioty;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		PreparedStatement stmt = null;
		Connection c = pgsq.getC();
		try
		{
			
			stmt = c.prepareStatement("INSERT INTO t_przedmioty(nazwa, opis, zdjecie_src) values (?, ?, ?)");
			stmt.setString(1, nazwa);
			stmt.setString(2, opis);
			stmt.setString(3, zdjecie_src);
			stmt.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close();
				}
			}
			catch (Exception e)
			{
				// log this error
			}
			try
			{
				if (c != null)
				{
					c.close();
				}
			}
			catch (Exception e)
			{
				// log this error
			}
		}
	}

	public int getLastId()
	{
		int id_przedmiotu = 0;
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("SELECT id FROM t_przedmioty order by id desc limit 1");
		try
		{
			Statement stmt = pgsq.getC().createStatement();

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				id_przedmiotu = rs.getInt("id");

			}
			rs.close();
		}
		catch (SQLException e)
		{

		}
		return id_przedmiotu;
	}

	public void updatePrzedmiot()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("UPDATE t_przedmioty SET nazwa = '%s', zdjecie_src = '%s' WHERE id=%d", nazwa, zdjecie_src, id);
		pgsq.queryOpertaion(query);
	}

	public void deletePrzedmiot()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("DELETE FROM t_przedmioty WHERE id=%d", id);
		pgsq.queryOpertaion(query);
	}

}
