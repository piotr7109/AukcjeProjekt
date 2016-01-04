package controllers.logowanie;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.Komunikaty;
import main.MD5;
import main.ServletMain;
import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;


public class Logowanie extends ServletMain
{
    private static final long serialVersionUID = 1L;
        
    public Logowanie()
    {
    	super();
    	page_url = "forms/LoginForm.html";
    	

    }
    /*
     * Czy autoryzacja jest potrzebna
     */
    protected boolean authRequired()
    {
    	return false;
    }
    /*
     * W tej funkcji nale¿y wykonaæ wszystkie dzia³ania na GET i POST
     */
    public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	switch(mode)
    	{
			case 0:
				html = this.getHtml(page_url);
				break;
			case 1:
				if(checkLoginParameters(request) == "NIEPOPRAWNY_LOGIN")
				{
					html = Komunikaty.getError("Login nie istnieje w systemie!");
					html+="<a href=logowanie>Spróbuj ponownie</a>";
				}
				else if(checkLoginParameters(request) == "NIEPOPRAWNE_HASLO")
				{
					html = Komunikaty.getError("Podane has³o nie pasuje do loginu!");
					html+="<a href=logowanie>Spróbuj ponownie</a>";
				}
				else if(checkLoginParameters(request) == "NIEAKTYWNY")
				{
					html = Komunikaty.getError("Jesteœ zarejestrowany, ale Twoje konto nie jest wci¹¿ aktywne.");
					html+="<a href=logowanie>Spróbuj ponownie</a>";
				}
				else
				{
					HttpSession session=request.getSession();
					session.setAttribute("id_uzytkownika", checkLoginParameters(request));
					session.setAttribute("login", request.getParameter("login"));
					html = Komunikaty.getSukces("Zosta³eœ poprawnie zalogowany!");
				}
				break;
			case 10:
				HttpSession session=request.getSession();
				session.invalidate();
				html = Komunikaty.getWarning("Zosta³eœ wylogowany!");
				break;
    	}
    	initServlet();
    }
    private String checkLoginParameters(HttpServletRequest request)
    {
    	String login = request.getParameter("login");
    	String haslo = request.getParameter("haslo");
    	
    	UzytkownikFactory uz_factory =new UzytkownikFactory();
    	uz_factory.setLogin(login);
    	Uzytkownik uz = (Uzytkownik)uz_factory.getObject();
    	if(uz == null)
    	{
    		return "NIEPOPRAWNY_LOGIN";
    	}
    	if(!uz.getHaslo().equals( MD5.getMD5(haslo)))
    	{
    		return "NIEPOPRAWNE_HASLO";
    	}
    	if(uz.getStatus()=='X')
    	{
    		return "NIEAKTYWNY";
    	}
    	
    	return uz.getId()+"";
    }
    
    
    
}