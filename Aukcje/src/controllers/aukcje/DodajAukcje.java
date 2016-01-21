package controllers.aukcje;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

//import org.apache.commons.io.FileUtils;

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
		return true;
	}
	
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		System.out.println(mode+"   "+request.getParameter("mode"));
		
    	switch(mode)
    	{
			case 0:
				html = String.format(this.getHtml(page_url), "", "", "", "", "", "");
				break;
			case 1:
				this.zapiszAukcje(getAukcjaFromRequest(request));
				html = this.getRightHtml();
				break;
			case 2:
				html = this.getRightHtml();
				break;
				
    	}
    	initServlet();
    }
	
	private void zapiszAukcje(Aukcja aukcja)
	{
		Przedmiot przedmiot = aukcja.getPrzedmiot();
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

		

		File source = new File(request.getParameter("zdjecie"));
		String nazwa_pliku = "zdjecie_"+ String.valueOf((int)(Math.random()*10000));
		File dest = new File("C:/images/" + nazwa_pliku +".jpg");


		try
		{
			FileUtils.copyFile(source,dest);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Przedmiot prz = new Przedmiot();
		prz.setNazwa(request.getParameter("nazwa_przedmiotu"));
		prz.setOpis(request.getParameter("opis"));
		System.out.println(nazwa_pliku);
		prz.setZdjecieSrc("C:/images/" + nazwa_pliku +".jpg");
		

		
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
		    case 1:

		    	html = "<script>window.location.replace('dodaj_aukcje?mode=2');</script>";
		    	break;
		    case 2:
		    	html = Komunikaty.getSukces("Twoje og³oszenie aukcyjne zosta³o pomyœlnie zapisane!");
		    	break;
    	}

    	return html;
    }
	
	
}