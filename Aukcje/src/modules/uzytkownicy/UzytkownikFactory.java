package modules.uzytkownicy;


import modules.AbstractFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UzytkownikFactory extends AbstractFactory
{
	protected String login;
	
	public UzytkownikFactory()
	{
		super();
		id = 0;
		login="";
	}
	public void setId(int id)
	{
		this.id = id;
		query = String.format("SELECT * FROM t_uzytkownicy where id=%d OR login='%s'", id, login);
	}
	
	public void setLogin(String login)
	{
		this.login = login;
		query = String.format("SELECT * FROM t_uzytkownicy where id=%d OR login='%s'", id, login);
	}
	
	public Object fetchObject(ResultSet rs) throws SQLException
	{
		
		Uzytkownik uz = new Uzytkownik();
		uz.setId(rs.getInt("id"));
    	uz.setLogin(rs.getString("login"));
    	uz.setHaslo(rs.getString("haslo"));
    	uz.setImie(rs.getString("imie"));
    	uz.setNazwisko(rs.getString("nazwisko"));
    	uz.setEmail(rs.getString("email"));
    	uz.setAdres(rs.getString("adres"));
    	uz.setStanKonta(rs.getInt("stan_konta"));
    	uz.setStatus(rs.getString("status").charAt(0));
    	uz.setBledneLogowania(rs.getInt("bledne_logowania"));
		
		return uz;
	}
}
