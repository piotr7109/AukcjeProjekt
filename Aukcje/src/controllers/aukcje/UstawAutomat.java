package controllers.aukcje;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.ServletMain;
import modules.automaty.Automat;
import modules.automaty.AutomatFactory;

public class UstawAutomat extends ServletMain
{

	private int id_aukcji;
	public UstawAutomat()
	{
		super();
		page_url = "forms/UstawAutomatForm.html";
		
	}
	@Override
	protected boolean authRequired()
	{
		return true;
	}
	
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		id_aukcji = Integer.parseInt(request.getParameter("id_aukcji"));
		
		switch (mode)
		{
			case 0:
				html = String.format(this.getHtml(page_url), id_aukcji);
				break;
			case 1:
				zapiszAutomat();
				html = String.format("<script>window.location.replace('podglad_aukcji?id_aukcji=%d');</script>", id_aukcji);
				break;
				
			default:
				break;
		}
		
		initServlet();
    }
	private void zapiszAutomat()
	{
		Automat au = new Automat();
		au.setIdAukcji(id_aukcji);
		au.setIdUzytkownika(sesja.getIdUzytkownika(request));
		au.setMaxPrzebicie(Integer.parseInt(request.getParameter("kwota")));
		
		au.deleteByAukcjaAndUzytkownikId();
		au.insert();
	}
}
