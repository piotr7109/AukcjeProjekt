package controllers.aukcje;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Komunikaty;
import main.ServletMain;
import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaFactory;

public class PodgladAukcji extends ServletMain 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Aukcja aukcja;
	
	public PodgladAukcji() 
	{
		super();
		page_url = "views/PodgladAukcji.html";
	}
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		int id_aukcji = Integer.parseInt(request.getParameter("id_aukcji"));
			
		
		AukcjaFactory a_factory = new AukcjaFactory();
		a_factory.setId(id_aukcji);
		aukcja = (Aukcja)a_factory.getObject();
		if(testy() == "AUKCJA_NIE_ISTNIEJE")
		{
			html = Komunikaty.getError("Aukcja o podanym identyfikatorze nie istnieje!");
		}
		else
		{
			
			getRightHtml();
		}
    	initServlet();
    }
	private void getRightHtml()
	{
		html = String.format(this.getHtml(page_url), this.aukcja.getNazwa(),
				"http://www.dfv.pl/tl_files/dfv/Poradniki/Szkola_dfv/Jak_zrobi%C5%82em_to_zdj%C4%99cie/Zabaewa/P1352dfv.jpg", this.aukcja.getDataRozpoczecia());
	}
	private String testy()
	{
		
		if(aukcja == null)
		{
			return "AUKCJA_NIE_ISTNIEJE";
		}
		return "";
	}
	
}
