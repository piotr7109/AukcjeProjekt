package modules.uzytkownicy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modules.AbstractLista;


public class UzytkownicyLista extends AbstractLista 
{
	public UzytkownicyLista() {
		table = "t_uzytkownicy";
	}
	
	protected Object fetchObject(ResultSet rs) throws SQLException
	{
		//id login haslo imie nazwisko email adres stan_konta status bledne_logowanie
		Uzytkownik u = new Uzytkownik();
		u.setId(rs.getInt("id"));
		u.setLogin(rs.getString("login"));
		u.setHaslo(rs.getString("haslo"));
		u.setImie(rs.getString("imie"));
		u.setNazwisko(rs.getString("nazwisko"));
		u.setAdres(rs.getString("adres"));
		u.setEmail(rs.getString("email"));
		u.setStanKonta(rs.getInt("stan_konta"));
		u.setStatus(rs.getString("status").charAt(0));
		u.setBledneLogowania(rs.getInt("bledne_logowania"));
		
		
		
		return u;
	}
	public ArrayList<Object> getUzytkownicyAukcja(int id_aukcji)
	{
		query = String.format("select DISTINCT u.* FROM t_uzytkownicy u INNER JOIN t_przebicia p ON u.id = p.id_uzytkownika  WHERE p.id_aukcji =%d", id_aukcji);
		ArrayList<Object> uz = (ArrayList<Object>) getList();
		return uz;
	}
}
