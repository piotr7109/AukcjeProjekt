package controllers.aukcje;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Komunikaty;
import main.ServletMain;
import modules.aukcje.Aukcja;
import modules.przedmioty.Przedmiot;

public class DodajAukcje extends ServletMain
{
	private static final long serialVersionUID = 1L;
	
	private Aukcja aukcja;
	private Przedmiot przedmiot;

	public DodajAukcje() 
    {
    	super();
    	page_url = "forms/DodajAukcjeForm.html";
    	

    }
	
	protected boolean authRequired()
	{
		return false;
	}
	
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	switch(mode)
    	{
			case 0:
				html = String.format(this.getHtml(page_url), "", "", "", "", "", "");
				break;
			case 1:
				this.aukcja = this.getAukcjaFromRequest(request);
		    	this.przedmiot = this.aukcja.getPrzedmiot();
		    	html = this.getRightHtml();
				break;
			case 2:
				this.zapiszAukcje(getAukcjaFromRequest(request));
				html = this.getRightHtml();
				break;
			case 3:
				html = this.getRightHtml();
				break;
				
    	}
    	initServlet();
    }
	
	private void zapiszAukcje(Aukcja aukcja)
	{
		Przedmiot przedmiot = aukcja.getPrzedmiot();
		przedmiot.setZdjecieSrc("/");
		przedmiot.insertPrzedmiot();
		int id_przedmiotu = przedmiot.getLastId();
		przedmiot.setId(id_przedmiotu);
				
		aukcja.setIdPrzedmiotu(id_przedmiotu);
		aukcja.setIdUzytkownika(sesja.getIdUzytkownika(this.request));
		java.util.Date date = new java.util.Date();
		Date sqlDate = new Date(date.getTime());
		aukcja.setDataRozpoczecia(sqlDate);
		
		aukcja.insertAukcja();
		
		
	}
	
	private Aukcja getAukcjaFromRequest(HttpServletRequest request)
	{
		Aukcja aukcja = new Aukcja();
		aukcja.setNazwa(request.getParameter("nazwa_aukcji"));
		
		Date data_zakonczenia = Date.valueOf(request.getParameter("data_zakonczenia"));
		aukcja.setDataZakonczenia(data_zakonczenia);

		Przedmiot prz = new Przedmiot();
		prz.setNazwa(request.getParameter("nazwa_przedmiotu"));
		prz.setOpis(request.getParameter("opis"));
		
		///zapis pliku na serwer
		
		//request.getParameter("zdjecie");
		
		//
		aukcja.setPrzedmiot(prz);
		return aukcja;
	}
	private String getRightHtml()
    {
    	String html ="";
    	
    	switch(mode)
    	{
		    case 0:
		    	html = String.format(this.getHtml(page_url),  "", "", "", "");
				break;
				
		    case 1: //wyœwietl zablokowane
		    	
		    	html = String.format(this.getHtml(page_url), aukcja.getNazwa(), aukcja.getDataZakonczenia(), 
		    										przedmiot.getNazwa(), przedmiot.getOpis());
		    	html +="<script src='form_helper.js'></script>";
		    	break;
		    	
		    case 2:

		    	html = "<script>window.location.replace('dodaj_aukcje?mode=3');</script>";
		    	break;
		    case 3:
		    	html = Komunikaty.getSukces("Twoje og³oszenie aukcyjne zosta³o pomyœlnie zapisane!");
		    	break;
    	}

    	return html;
    }
	
	
}
