package modules.automaty;

import main.PostgreSQLJDBC;

public class Automat
{
	private int id, id_aukcji, id_uzytkownika, max_przebicie;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getIdAukcji()
	{
		return id_aukcji;
	}

	public void setIdAukcji(int id_aukcji)
	{
		this.id_aukcji = id_aukcji;
	}

	public int getIdUzytkownika()
	{
		return id_uzytkownika;
	}

	public void setIdUzytkownika(int id_uzytkownika)
	{
		this.id_uzytkownika = id_uzytkownika;
	}

	public int getMaxPrzebicie()
	{
		return max_przebicie;
	}

	public void setMaxPrzebicie(int max_przebicie)
	{
		this.max_przebicie = max_przebicie;
	}

	public void insert()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("INSERT INTO t_automaty( id_aukcji, id_uzytkownika, max_przebicie) VALUES(%d, %d, %d)", id_aukcji, id_uzytkownika, max_przebicie);
		pgsq.queryOpertaion(query);
	}

	public void deleteByAukcjaAndUzytkownikId()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("DELETE FROM t_automaty WHERE id_aukcji=%d AND id_uzytkownika=%d", id_aukcji, id_uzytkownika);
		pgsq.queryOpertaion(query);
	}

}
