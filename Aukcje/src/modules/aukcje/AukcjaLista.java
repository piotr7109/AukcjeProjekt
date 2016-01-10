package modules.aukcje;

import java.sql.ResultSet;
import java.sql.SQLException;

import modules.AbstractLista;

public class AukcjaLista extends AbstractLista
{
	public AukcjaLista() {
		table = "t_aukcje";
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
		
		
		return aukcja;
	}
}
