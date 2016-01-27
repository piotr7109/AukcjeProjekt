package controllers.aukcje;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

//import org.apache.commons.io.FileUtils;

import main.Komunikaty;
import main.ServletMain;
import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaFactory;
import modules.przedmioty.Przedmiot;

@MultipartConfig
public class DodajAukcje extends ServletMain
{
	private static final long serialVersionUID = 1L;

	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

    	request.setCharacterEncoding("UTF-8");
    	this.request = request;
    	this.response = response;
    	html = String.format(this.getHtml(page_url),"","","","");
    	initServlet();
    	
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	request.setCharacterEncoding("UTF-8");
    	this.request = request;
    	this.response = response;
    	
    	Aukcja aukcja = getAukcjaFromRequest(request);
		if (aukcja == null)
		{
			mode = 0;
			html += this.getRightHtml();
		}
		else
		{
			System.out.println("mode 2");
			this.zapiszAukcje(aukcja);
			html = "<script>window.location.replace('upload_servlet');</script>";
		}
		
		
		AukcjaFactory a_factory = new AukcjaFactory();
		Aukcja ost_aukcja = a_factory.getLastInserted();
		zapiszIdAukcjiDoSesji(ost_aukcja.getId());
		
    	initServlet();
    }
	
	private void zapiszIdAukcjiDoSesji(int id_aukcji)
	{
		HttpSession session=request.getSession();
		session.setAttribute("id_aukcji", id_aukcji);
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
		aukcja.setStan('A');

		aukcja.insertAukcja();

	}

	private Aukcja getAukcjaFromRequest(HttpServletRequest request)
	{
		Aukcja aukcja = new Aukcja();

		System.out.println("AAA" + request.getParameter("nazwa_aukcji"));
		if (request.getParameter("nazwa_aukcji").equals(""))
		{
			html = Komunikaty.getError("Wpisz nazwê");
			return null;
		}
		aukcja.setNazwa(request.getParameter("nazwa_aukcji"));

		if (request.getParameter("data_zakonczenia").equals(""))
		{
			html = Komunikaty.getError("Wpisz datê zakoñczenia");
			return null;
		}

		Date data_zakonczenia = Date.valueOf(request.getParameter("data_zakonczenia"));
		aukcja.setDataZakonczenia(data_zakonczenia);

		Przedmiot prz = new Przedmiot();
		if (request.getParameter("nazwa_przedmiotu").equals(""))
		{
			html = Komunikaty.getError("Wpisz nazwê dla przedmiotu");
			return null;
		}
		prz.setNazwa(request.getParameter("nazwa_przedmiotu"));
		prz.setOpis(request.getParameter("opis"));

	
		prz.setZdjecieSrc("");
		aukcja.setPrzedmiot(prz);
		System.out.println(
				aukcja.getId() + " " + aukcja.getIdUzytkownika() + " " + aukcja.getNazwa() + " " + aukcja.getIdPrzedmiotu() + " " + aukcja.getDataRozpoczecia() + " " + aukcja.getDataZakonczenia());
		System.out.println(aukcja.getPrzedmiot().getId() + " " + aukcja.getPrzedmiot().getLastId() + " " + aukcja.getPrzedmiot().getNazwa() + " " + aukcja.getPrzedmiot().getOpis());
		return aukcja;
	}


	private String getRightHtml()
	{
		String html = "";

		switch (mode)
		{
			case 0:
				html = String.format(this.getHtml(page_url), "", "", "", "");
				break;
			case 1:

				html = "<script>window.location.replace('dodaj_aukcje?mode=2');</script>";
				break;
			case 2:
				
				break;
		}

		return html;
	}

}