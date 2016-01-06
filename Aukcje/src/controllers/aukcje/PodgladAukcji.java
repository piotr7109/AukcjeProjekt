package controllers.aukcje;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.ServletMain;

public class PodgladAukcji extends ServletMain 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PodgladAukcji() 
	{
		super();
		page_url = "views/PodgladAukcji.html";
	}
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		html = this.getHtml(page_url);
    	initServlet();
    }
	
}
