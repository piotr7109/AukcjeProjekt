package modules.aukcje;

import java.sql.Date;

import main.PostgreSQLJDBC;
import modules.przedmioty.Przedmiot;

public class Aukcja
{
	private int id;
	private int id_uzytkownika;
	private int id_przedmiotu;
	private Przedmiot przedmiot;
	private String nazwa;
	private Date data_rozpoczecia;
	private Date data_zakonczenia;
	private char stan;
	
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
	public Przedmiot getPrzedmiot()
	{
		return przedmiot;
	}
	public void setPrzedmiot(Przedmiot przedmiot)
	{
		this.przedmiot = przedmiot;
	}
	public String getNazwa()
	{
		return nazwa;
	}
	public void setNazwa(String nazwa)
	{
		this.nazwa = nazwa;
	}
	public Date getDataRozpoczecia()
	{
		return data_rozpoczecia;
	}
	public void setDataRozpoczecia(Date data_rozpoczecia)
	{
		this.data_rozpoczecia = data_rozpoczecia;
	}
	public Date getDataZakonczenia()
	{
		return data_zakonczenia;
	}
	public void setDataZakonczenia(Date data_zakonczenia)
	{
		this.data_zakonczenia = data_zakonczenia;
	}
	public int getIdPrzedmiotu()
	{
		return id_przedmiotu;
	}
	public void setIdPrzedmiotu(int id_przedmiotu)
	{
		this.id_przedmiotu = id_przedmiotu;
	}
	
	
	public char getStan()
	{
		return stan;
	}
	public void setStan(char stan)
	{
		this.stan = stan;
	}
	
	public void insertAukcja()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("INSERT INTO t_aukcje(id_uzytkownika, id_przedmiotu, nazwa, data_rozpoczecia, data_zakonczenia, stan)"
									+" VALUES (%d, %d, '%s','%s','%s', '%c')",
						id_uzytkownika, przedmiot.getId(), nazwa, data_rozpoczecia, data_zakonczenia, stan);
		pgsq.queryOpertaion(query);
	}
	public void updateAukcja()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("UPDATE t_aukcje SET id_uzytkownika = %d, id_przedmiotu = %d," +
									"nazwa = '%s', data_rozpoczecia = '%s', data_zakonczenia = '%s' stan='%c' WHERE id=%d",
									id_uzytkownika, przedmiot.getId(),nazwa, data_rozpoczecia, data_zakonczenia, id, stan);
		pgsq.queryOpertaion(query);
	}
	public void deleteAukcja()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("DELETE FROM t_aukcje WHERE id=%d", id);
		pgsq.queryOpertaion(query);
		
		query = String.format("DELETE FROM t_przebicia WHERE id_aukcji=%d", id);
		pgsq.queryOpertaion(query);
		
		query = String.format("DELETE FROM t_automaty WHERE id_aukcji=%d", id);
		pgsq.queryOpertaion(query);
	}
	
	
	

}
