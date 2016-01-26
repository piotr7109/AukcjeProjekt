package modules.aukcje;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modules.AbstractLista;

public class AukcjaLista extends AbstractLista
{
	public AukcjaLista()
	{
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
		aukcja.setStan(rs.getString("stan").charAt(0));

		return aukcja;
	}
	public ArrayList<Object> getAktywneAukcje()
	{
		query = String.format("select * FROM t_aukcje where stan ='A' ");
		ArrayList<Object> au = (ArrayList<Object>) getList();
		return au;
	}
	
	
	public ArrayList<Object> getAukcjeUzytkownik(int id_uzytkownika)
	{
		query = String.format("select DISTINCT a.* from t_aukcje a INNER JOIN t_przebicia p ON p.id_aukcji = a.id  where p.id_uzytkownika =  %d", id_uzytkownika);
		ArrayList<Object> au = (ArrayList<Object>) getList();
		return au;
	}
	public ArrayList<Object> getAukcjeWygrane()
	{
		query = String.format("select * FROM t_aukcje where stan ='X'");
		ArrayList<Object> au = (ArrayList<Object>) getList();
		return au;
	}
}
