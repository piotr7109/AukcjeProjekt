package controllers.aukcje;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.ServletMain;
import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaLista;

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
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	html = String.format(this.getHtml(page_url),  this.getRightHtml());
    	AukcjaLista a_lista = new AukcjaLista();
		aukcje = a_lista.getList();
		ile_aukcji = aukcje.size();
    	initServlet();
    }
	
	private String getRightHtml()
    {
    	String html ="";
    	Aukcja au;
    	for(int i=0; i< this.ile_aukcji; i++)
    	{
    		au = (Aukcja)aukcje.get(i);
    		html +="<tr>";
    		
    		html +=String.format("<td>%d</td>", (i+1));
    		html +=String.format("<td>%s</td>", au.getNazwa());
    		html +=String.format("<td>%s</td>", au.getDataZakonczenia());
    		html +=String.format("<td><a><span class='glyphicon glyphicon-search'></span></a></td>");
    		
    		html +="</tr>";
    	}


    	return html;
    }
}
