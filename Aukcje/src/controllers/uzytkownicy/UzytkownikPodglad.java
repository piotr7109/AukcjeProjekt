package controllers.uzytkownicy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.ServletMain;
import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;

public class UzytkownikPodglad extends ServletMain
{
	public UzytkownikPodglad()
	{
		super();
		page_url = "views/Uzytkownik.html";
	}

	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id_uzytkownika;
		if (request.getParameter("id_uzytkownika") == null)
		{
			id_uzytkownika = sesja.getIdUzytkownika(request);
		}
		else
		{
			id_uzytkownika = Integer.parseInt(request.getParameter("id_uzytkownika"));
		}
		UzytkownikFactory uzytkownik_factory = new UzytkownikFactory();
		uzytkownik_factory.setId(id_uzytkownika);

		Uzytkownik uzytkownik = (Uzytkownik) uzytkownik_factory.getObject();

		html = String.format(this.getHtml(page_url), uzytkownik.getId(), uzytkownik.getLogin(), uzytkownik.getImie(), uzytkownik.getNazwisko(), uzytkownik.getEmail());
		initServlet();
	}
}
