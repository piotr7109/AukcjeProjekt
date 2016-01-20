package controllers.uzytkownicy;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Komunikaty;
import main.ServletMain;
import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;

public class EdycjaDanych extends ServletMain
{
	private static final long serialVersionUID = 1L;
	private UzytkownikFactory u_factory;
	private int id_uzytkownikaa;
	private Uzytkownik uz, temp_user;

	public EdycjaDanych()
	{
		super();
		page_url = "forms/EdycjaDanych.html";
		u_factory = new UzytkownikFactory();

	}
	/*
	 * W tej funkcji nale¿y wykonaæ wszystkie dzia³ania na GET i POST
	 */
	protected boolean authRequired()
	{
		return true;
	}
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getParameter("id_uzytkownika") == null)
		{
			id_uzytkownikaa = sesja.getIdUzytkownika(request);
		}
		else
		{
			id_uzytkownikaa = Integer.parseInt(request.getParameter("id_uzytkownika"));
		}
		u_factory.setId(id_uzytkownikaa);

		switch (mode)
		{
			case 0:
				this.uz = (Uzytkownik) u_factory.getObject();
				temp_user = uz;
				html = this.getRightHtml();
				break;

			case 1: // wyœwietl zablokowane

				this.uz = this.getUzytkownikFromRequest(request);
				html = this.getRightHtml();
				break;

			case 2:
				zapiszDaneUzytkownika(this.getUzytkownikFromRequest(request));
				html = this.getRightHtml();
				break;

			case 3:
				html = this.getRightHtml();
				break;
		}
		initServlet();
	}

	private String getRightHtml()
	{
		String html = "";

		switch (mode)
		{
			case 0:
				html = String.format(this.getHtml(page_url), uz.getLogin(), "", uz.getImie(), uz.getNazwisko(), uz.getEmail(), uz.getAdres());
				break;

			case 1: // wyœwietl zablokowane

				html = String.format(this.getHtml(page_url), uz.getLogin(), uz.getHaslo(), uz.getImie(), uz.getNazwisko(), uz.getEmail(), uz.getAdres());
				html += "<script src='form_helper.js'></script>";
				break;

			case 2:
				html = "<script>window.location.replace('edycja_danych?id_uzytkownika=" + id_uzytkownikaa + "&mode=3');</script>";
				break;
			case 3:
				html = Komunikaty.getSukces("Dane zosta³y zmienione pomyœlnie");
				break;
		}

		return html;
	}

	

	private void zapiszDaneUzytkownika(Uzytkownik uzyt)
	{
		uzyt.setId(id_uzytkownikaa);
		uzyt.setStanKonta(this.temp_user.getStanKonta());
		uzyt.setStatus(this.temp_user.getStatus());
		uzyt.setBledneLogowania(this.temp_user.getBledneLogowania());
		uzyt.updateUzytkownik();
	}

	private Uzytkownik getUzytkownikFromRequest(HttpServletRequest request)
	{
		Uzytkownik uzyt = new Uzytkownik();

		uzyt.setLogin(request.getParameter("login"));
		uzyt.setHaslo(request.getParameter("haslo"));
		uzyt.setImie(request.getParameter("imie"));
		uzyt.setNazwisko(request.getParameter("nazwisko"));
		uzyt.setEmail(request.getParameter("email"));
		uzyt.setAdres(request.getParameter("adres"));

		return uzyt;
	}

}