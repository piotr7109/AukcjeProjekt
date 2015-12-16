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
		
		UzytkownikFactory u_factory = new UzytkownikFactory();
		u_factory.setId(rs.getInt("id"));
		
		Uzytkownik uzytkownik = (Uzytkownik)u_factory.getObject();
		return uzytkownik;
	}
}
