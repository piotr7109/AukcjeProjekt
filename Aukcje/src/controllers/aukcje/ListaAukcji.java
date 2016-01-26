package controllers.aukcje;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.ServletMain;
import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaLista;
import modules.przedmioty.Przedmiot;
import modules.przedmioty.PrzedmiotFactory;

public class ListaAukcji extends ServletMain
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ArrayList<Object> aukcje;
	protected int ile_aukcji;

	public ListaAukcji()
	{
		super();
		page_url = "views/ListaAukcji.html";
	}

	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		AukcjaLista a_lista = new AukcjaLista();
		aukcje = a_lista.getAktywneAukcje();
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
			html += String.format("<td>%s</td>", "<img src = " + au.getPrzedmiot().getZdjecieSrc() + " width=\"300\" height=\"150\" border=\"3\" </img>");
			html += String.format("<td>%s</td>", au.getNazwa());
			html += String.format("<td>%s</td>", au.getDataZakonczenia());
			html += String.format("<td><a href=podglad_aukcji?id_aukcji=%d><span class='glyphicon glyphicon-search'></span></a></td>", au.getId());

			html += "</tr>";
		}

		return html;
	}
}
