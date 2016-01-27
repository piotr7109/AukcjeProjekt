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
		aukcje = a_lista.getAukcjeWygrane();
		ile_aukcji = aukcje.size();
		html = String.format(this.getHtml(page_url), this.getRightHtml());

		initServlet();
	}

	private String getRightHtml()
	{
		String html = "";
		Aukcja au;
		PrzebicieFactory przebicie_factory = new PrzebicieFactory();

		for (int i = 0; i < this.ile_aukcji; i++)
		{

			au = (Aukcja) aukcje.get(i);
			Przebicie ostatnie_przebicie = (Przebicie) przebicie_factory.getOstatniePrzebicie(au.getId());
			if (ostatnie_przebicie.getIdUzytkownika() != sesja.getIdUzytkownika(request))
				continue;
			PrzedmiotFactory p_factory = new PrzedmiotFactory();
			p_factory.setId(au.getIdPrzedmiotu());
			au.setPrzedmiot((Przedmiot) p_factory.getObject());

			html += "<tr>";

			html += String.format("<td>%d</td>", (i + 1));
			html += String.format("<td>%s</td>", "<img src = " + au.getPrzedmiot().getZdjecieSrc() + " width=\"300\" height=\"150\" border=\"3\" </img>");
			html += String.format("<td>%s</td>", au.getNazwa());
			html += String.format("<td>%s</td>", au.getDataZakonczenia());
			html += String.format("<td>%s</td>", ostatnie_przebicie.getWartosc());
			html += "</tr>";
		}

		return html;
	}
}
