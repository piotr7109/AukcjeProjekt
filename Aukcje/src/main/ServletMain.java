package main;
 

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletMain extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    protected Sesja sesja;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String page_url;
    protected final String host ="http://localhost:8080/Aukcje/";
    protected int mode;
    protected String html;
        
    public ServletMain()
    {
    	sesja = new Sesja();
    	page_url = "forms/LoginForm.html";
    	

    }
    protected void initServlet() throws ServletException, IOException
    {
    	
        RequestDispatcher view;
        view = request.getRequestDispatcher("/Template.jsp"); 
        request.setCharacterEncoding("UTF-8");
        if( !this.sprawdzSesje() ) //nieautoryzowany dostęp
    	{
        	request.setAttribute("menu", this.getHtmlMenuUnlogged());
    	}
        else
        {
        	request.setAttribute("menu", this.getHtmlMenuLogged());
        }
        if(this.authRequired() && !this.sprawdzSesje())
        {
        	request.setAttribute("html", this.getHtmlNiezalogowany());
        }
        else
        {
        	html+="<script>var mode="+mode+"</script>";
        	request.setAttribute("html", html);
        }
        view.forward(request, response);
        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

    	request.setCharacterEncoding("UTF-8");
    	this.request = request;
    	this.response = response;
    	if(request.getParameter("mode") == null)
    	{
    		this.mode = 0;
    	}
    	else
    	{
    		this.mode = Integer.parseInt(request.getParameter("mode"));
    	}
    	
    	doRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	request.setCharacterEncoding("UTF-8");
    	this.request = request;
    	this.response = response;
    	if(request.getParameter("mode") == null)
    	{
    		this.mode = 0;
    	}
    	else
    	{
    		this.mode = Integer.parseInt(request.getParameter("mode"));
    	}
    	doRequest(request, response);
    }
    
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	initServlet();
    }
    
    
    
    
    protected boolean sprawdzSesje()
    {
    	return sesja.czy_zalogowany(this.request);
    }
    protected String getHtmlNiezalogowany()
    {
    	return Komunikaty.getError("Nie masz dostępu do tej zawartości!");
    }
    protected String getHtmlMenuUnlogged()
    {
    	String html ="";
    	html +=Menu.getMenuUnlogged();
    	
    	return html;
    	
    }
    protected String getHtmlMenuLogged()
    {
    	String html ="";
    	html +=Menu.getMenuLogged();
    	
    	return html;
    	
    }
    /*
     * Czy autoryzacja jest potrzebna
     */
    protected boolean authRequired()
    {
    	return false;
    }
    /*
     * W tej funkcji należy wykonać wszystkie działania na GET i POST
     */
    protected void doGetServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	
    }
    protected void doPostServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	
    }
    
    /*
     * tutaj należy wpisać wartość htmla
     */
    public String getHtml(String urlToRead)
    {
    	
    	String content = null;
    	URLConnection connection = null;
    	try {
    	  connection =  new URL(host+urlToRead).openConnection();
    	  Scanner scanner = new Scanner(connection.getInputStream());
    	  scanner.useDelimiter("\\Z");
    	  content = scanner.next();
    	  scanner.close();
    	}catch ( Exception ex ) {
    	    ex.printStackTrace();
    	}
    	
    	return content;
	}
    
}