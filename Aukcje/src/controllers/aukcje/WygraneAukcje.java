package controllers.aukcje;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.ServletMain;
import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaLista;
import modules.przebicia.Przebicie;
import modules.przebicia.PrzebicieFactory;
import modules.przedmioty.Przedmiot;
import modules.przedmioty.PrzedmiotFactory;

public class WygraneAukcje extends ServletMain
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ArrayList<Object> aukcje;
	protected int ile_aukcji;

	public WygraneAukcje()
	{
		super();
		page_url = "views/WygraneAukcje.html";
	}

	@Override
	protected boolean authRequired()
	{
		return true;
	}

	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		AukcjaLista a_lista = new AukcjaLista();
		aukcje = a_lista.getAukcjeWygrane(sesja.getIdUzytkownika(request));
		ile_aukcji = aukcje.size();
		html = String.format(this.getHtml(page_url), this.getRightHtml());

		initServlet();
	}

	private String getRightHtml()
	{
		String html = "";
		Aukcja au;

		for (int i = 0; i < this.ile_aukcji; i++)
		{

			au = (Aukcja) aukcje.get(i);
			PrzedmiotFactory p_factory = new PrzedmiotFactory();
			p_factory.setId(au.getIdPrzedmiotu());
			au.setPrzedmiot((Przedmiot) p_factory.getObject());

			html += "<tr>";

			html += String.format("<td>%d</td>", (i + 1));
			html += String.format("<td>%s</td>", String.format("<img src ='%s' class='lista_aukcji_img'  border='3' >", au.getPrzedmiot().getZdjecieSrc()));
			html += String.format("<td>%s</td>", au.getNazwa());
			html += String.format("<td>%s</td>", au.getDataZakonczenia());
			html += String.format("<td>%s</td>", au.getCenaKoncowa());
			html += "</tr>";
		}

		return html;
	}
}
