package controllers.uzytkownicy;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.ServletMain;
import modules.uzytkownicy.UzytkownicyLista;
import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;

public class ListaUzytkownikow extends ServletMain 
{
	private static final long serialVersionUID = 1L;
	protected ArrayList<Object> uzytkownicy;
	protected int ile_uzytkownikow;
	
	public ListaUzytkownikow()  
	{
		super();
		
		page_url = "views/ListaUzytkownikow.html";    
	}
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		this.setUzytkownicy();
		switch(mode)
		{
			case 0:
				html = String.format(this.getHtml(page_url),  this.getRightHtml());
		    	
		    	break;
			case 10:
				Uzytkownik uz = new Uzytkownik();
				uz.setId(Integer.parseInt(request.getParameter("id_uzytkownika")));
				uz.deleteUzytkownik();
				html = "<script>window.location.replace('lista_uzytkownikow');</script>";
				break;
				
			case 20:
				UzytkownikFactory u_factory = new UzytkownikFactory();
				u_factory.setId(Integer.parseInt(request.getParameter("id_uzytkownika")));
				Uzytkownik uzytkownik = (Uzytkownik)u_factory.getObject();
				
				uzytkownik.setStatus('Z');
				uzytkownik.aktywujUzytkownika();
				
				html = "<script>window.location.replace('lista_uzytkownikow');</script>";
				break;
				
	    	
		}
		initServlet();
    	
    }
	protected boolean authRequired()
	{
		return true;
	}
	private void setUzytkownicy()
	{
		UzytkownicyLista u_lista = new UzytkownicyLista();
		uzytkownicy = u_lista.getList();
		ile_uzytkownikow = uzytkownicy.size();
	}
	private String getRightHtml()
    {
    	String html ="";
    	Uzytkownik uz;
    	
    	for(int i=0; i< this.ile_uzytkownikow; i++)
    	{
    		String operacje ="";
    		uz = (Uzytkownik)uzytkownicy.get(i);
    		html +="<tr>";
    		html +=String.format("<td>%d</td>", uz.getId());
    		html +=String.format("<td>%s</td>", uz.getLogin());
    		html +=String.format("<td>%s</td>", uz.getImie());
    		html +=String.format("<td>%s</td>", uz.getNazwisko());
    		html +=String.format("<td>%s</td>", uz.getEmail());
    		html +=String.format("<td>%s</td>", uz.getAdres());
    		html +=String.format("<td>%d</td>", uz.getStanKonta());
    		html +=String.format("<td>%c</td>", uz.getStatus());
    		html +=String.format("<td>%d</td>", uz.getBledneLogowania());
    		
    		
    		if(uz.getStatus() == 'X')
    		{
    			operacje +=String.format("<a href='lista_uzytkownikow?mode=20&id_uzytkownika=%d'><button class='btn btn-success'>Aktywuj</button></a><br>", uz.getId());
    		}
    		if(uz.getStatus() != 'A')
    		{
    			operacje +=String.format("<a href='lista_uzytkownikow?mode=10&id_uzytkownika=%d'><button class='btn btn-danger'>Usuñ</button></a><br>", uz.getId());
    		}
    		operacje +=String.format("<a href='edycja_danych?id_uzytkownika=%d'><button class='btn btn-warning'>Edycja</button></a>", uz.getId());
    		html +=String.format("<td>%s</td>", operacje);
    		
    		html +="</tr>";
    	}


    	return html;
    }
	
}
