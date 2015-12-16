package controllers.rejestracja;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Komunikaty;
import main.ServletMain;
import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;

public class Rejestracja extends ServletMain
{
    private static final long serialVersionUID = 1L;
        
    
    private Uzytkownik uz;
    public Rejestracja()
    {
    	super();
    	page_url = "forms/RejestracjaForm.html";

    }
    /*
     * W tej funkcji nale¿y wykonaæ wszystkie dzia³ania na GET i POST
     */

    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	switch(mode)
    	{
		    case 0:
		    	html = this.getRightHtml();
				break;
				
		    case 1: //wyœwietl zablokowane
		    	this.uz = this.getUzytkownikFromRequest(request);
		    	html = this.getRightHtml();
		    	break;
		    	
		    case 2:
		    	if(!this.zapiszDaneUzytkownika(this.getUzytkownikFromRequest(request)))
		    	{
		    		html =Komunikaty.getError("Ten login jest ju¿ zajêty, wybierz inny!");
		    		mode = 0;
		    		html += String.format(this.getHtml(page_url), uz.getLogin(),
		    				uz.getHaslo(), uz.getImie(), uz.getNazwisko(), uz.getEmail(), uz.getAdres());
		    	}
		    	else
		    	{
		    		html = this.getRightHtml();
		    	}
		    	break;
		    case 3:
		    	html = this.getRightHtml();
		    	break;
		}
    	initServlet();
    }
    
    
    private String getRightHtml()
    {
    	String html ="";
    	
    	switch(mode)
    	{
		    case 0:
		    	html = String.format(this.getHtml(page_url), "", "", "", "", "", "");
				break;
				
		    case 1: //wyœwietl zablokowane
		    	
		    	html = String.format(this.getHtml(page_url), uz.getLogin(),
		    				uz.getHaslo(), uz.getImie(), uz.getNazwisko(), uz.getEmail(), uz.getAdres());
		    	html +="<script src='form_helper.js'></script>";
		    	break;
		    	
		    case 2:

		    	html = "<script>window.location.replace('rejestracja?mode=3');</script>";
		    	break;
		    case 3:
		    	html = Komunikaty.getSukces("Zosta³eœ pomyœlnie zarejestrowany! Aby aktywowaæ konto, skontaktuj siê z administracj¹.");
		    	break;
    	}

    	return html;
    }
    private boolean zapiszDaneUzytkownika(Uzytkownik uz)
    {
    	UzytkownikFactory uz_factory = new UzytkownikFactory();
    	uz_factory.setLogin(uz.getLogin());
    	if((Uzytkownik)uz_factory.getObject()!=null)
    	{
    		System.out.println("B³¹d");
    		return false;
    	}
    	else
    	{
	    	uz.setStanKonta(0);
	    	uz.setStatus('X');
	    	uz.setBledneLogowania(0);
	    	uz.insertUzytkownik();
	    	return true;
    	}
    	
    }
    private Uzytkownik getUzytkownikFromRequest(HttpServletRequest request)
    {
    	Uzytkownik uz = new Uzytkownik();
    	uz.setLogin(request.getParameter("login"));
    	uz.setHaslo(request.getParameter("haslo"));
    	uz.setImie(request.getParameter("imie"));
    	uz.setNazwisko(request.getParameter("nazwisko"));
    	uz.setEmail(request.getParameter("email"));
    	uz.setAdres(request.getParameter("adres"));
    	
    	return uz;
    }
    
}