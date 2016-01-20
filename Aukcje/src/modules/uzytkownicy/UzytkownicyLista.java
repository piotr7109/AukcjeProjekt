package modules.uzytkownicy;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
