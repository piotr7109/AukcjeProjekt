package modules.przebicia;

import java.sql.Date;

import main.PostgreSQLJDBC;

public class Przebicie
{
	private int id, id_uzytkownika, id_aukcji;
	private int wartosc;
	private Date data_przebicia;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getIdUzytkownika()
	{
		return id_uzytkownika;
	}

	public void setIdUzytkownika(int id_uzytkownika)
	{
		this.id_uzytkownika = id_uzytkownika;
	}

	public int getIdAukcji()
	{
		return id_aukcji;
	}

	public void setIdAukcji(int id_aukcji)
	{
		this.id_aukcji = id_aukcji;
	}

	public int getWartosc()
	{
		return wartosc;
	}

	public void setWartosc(int wartosc)
	{
		this.wartosc = wartosc;
	}

	public Date getDataPrzebicia()
	{
		return data_przebicia;
	}

	public void setDataPrzebicia(Date data_przebicia)
	{
		this.data_przebicia = data_przebicia;
	}

	public void insertPrzebicie()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format(
				"INSERT INTO t_przebicia(wartosc, data_przebicia, id_uzytkownika, id_aukcji) VALUES('%s', '%s', %d, %d)",
				wartosc, data_przebicia, id_uzytkownika, id_aukcji);
		pgsq.queryOpertaion(query);
	}
}
