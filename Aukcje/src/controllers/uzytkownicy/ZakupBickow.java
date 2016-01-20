package controllers.uzytkownicy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Komunikaty;
import main.ServletMain;
import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;

public class ZakupBickow extends ServletMain
{


	public ZakupBickow()
	{
		super();
		page_url = "forms/ZakupBickowForm.html";
	}

	@Override
	protected boolean authRequired()
	{
		return true;
	}

	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		switch (mode)
		{
			case 0:
				
				break;
			case 1:
				int kwota = Integer.parseInt(request.getParameter("kwota"));
				uzupelnijKonto(kwota);
				break;
			case 2:
				
				break;
				

		}
		html = this.getRightHtml();
		initServlet();
	}
	
	private void uzupelnijKonto(int kwota)
	{
		int id_uzytkownika = sesja.getIdUzytkownika(request);
		
		UzytkownikFactory u_factory = new UzytkownikFactory();
		u_factory.setId(id_uzytkownika);
		Uzytkownik uz = (Uzytkownik) u_factory.getObject();
		
		uz.setStanKonta(uz.getStanKonta()+kwota);
		uz.dodajBicki();
		
	}

	private String getRightHtml()
	{
		String html = "";
		switch (mode)
		{
			case 0:
				html = String.format(this.getHtml(page_url));
				break;
			case 1:

				html = "<script>window.location.replace('zakup_bickow?mode=2');</script>";
				break;
			case 2:
				html = Komunikaty.getSukces("Twoje konto zosta³o uzupe³nione! ¯yczymy mi³ych zakupów!");
				break;
		}

		return html;
	}
}
