package modules.aukcje;

import java.sql.ResultSet;
import java.sql.SQLException;

import modules.AbstractFactory;
import modules.przedmioty.Przedmiot;

public class AukcjaFactory extends AbstractFactory
{
	public AukcjaFactory()
	{
		super();
		tabela = "t_aukcje";

	}

	protected Object fetchObject(ResultSet rs) throws SQLException
	{
		Aukcja aukcja = new Aukcja();

		aukcja.setId(rs.getInt("id"));
		aukcja.setIdPrzedmiotu(rs.getInt("id_przedmiotu"));
		aukcja.setIdUzytkownika(rs.getInt("id_uzytkownika"));
		aukcja.setNazwa(rs.getString("nazwa"));
		aukcja.setDataRozpoczecia(rs.getDate("data_rozpoczecia"));
		aukcja.setDataZakonczenia(rs.getDate("data_zakonczenia"));
		aukcja.setStan(rs.getString("stan").charAt(0));
		
		return aukcja;
	}
	public Aukcja getLastInserted()
	{
		query = String.format("SELECT * from t_aukcje order by id DESC limit 1");
		Aukcja au = (Aukcja) getObject();
		return au;
	}

	

}
