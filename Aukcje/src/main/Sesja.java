package main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class Sesja 
{
	private String login;
	private char typ_uzytkownika;
	
	
	public String getLogin()
	{
		return login;
	}
	public void setLogin(String login)
	{
		this.login = login;
	}
	public int getIdUzytkownika(HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		return Integer.parseInt((String)session.getAttribute("id_uzytkownika"));
	}
	public char getTypUzytkownika()
	{
		return typ_uzytkownika;
	}
	public boolean czy_zalogowany(HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		String login = (String)session.getAttribute("login");
		this.login = login;
		if(login==null)
			return false;
		return true;
	}
	
}
