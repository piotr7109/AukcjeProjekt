package controllers.aukcje;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.ServletMain;
import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaFactory;

public class PodgladAukcji extends ServletMain 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_aukcji;
	
	public PodgladAukcji() 
	{
		super();
		page_url = "views/PodgladAukcji.html";
	}
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		id_aukcji = Integer.getInteger(request.getParameter("id_aukcji"));
		// pobraæ obiek z bazy
		AukcjaFactory a_factory = new AukcjaFactory();
		a_factory.setId(id_aukcji);
		Aukcja aukcja = (Aukcja)a_factory.getObject();
		
		
		
		
		
		html = this.getHtml(page_url);
    	initServlet();
    }
	
}
