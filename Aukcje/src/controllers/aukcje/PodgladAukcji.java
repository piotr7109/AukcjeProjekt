package controllers.aukcje;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Komunikaty;
import main.PostgreSQLJDBC;
import main.ServletMain;
import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaFactory;
import modules.przebicia.Przebicie;
import modules.przebicia.PrzebicieLista;
import modules.przedmioty.Przedmiot;
import modules.przedmioty.PrzedmiotFactory;

public class PodgladAukcji extends ServletMain 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Aukcja aukcja;
	private int wartosc_przebicia;
	private Przebicie przebicie, przebicie_temp;
	private int aktualna_cena;
	private PrzebicieLista przebicie_lista;
	private PostgreSQLJDBC postgre;
	private Date data_przebicia;
	
	public PodgladAukcji() 
	{
		super();
		page_url = "views/PodgladAukcji.html";
	}
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		int id_aukcji = Integer.parseInt(request.getParameter("id_aukcji"));
		
		getOstatniePrzebicie();
		this.data_przebicia = przebicie_temp.getDataPrzebicia();
		this.aktualna_cena = przebicie_temp.getWartosc();
		
		AukcjaFactory a_factory = new AukcjaFactory();
		a_factory.setId(id_aukcji);
		aukcja = (Aukcja)a_factory.getObject();
		
		PrzedmiotFactory p_factory = new PrzedmiotFactory();
		p_factory.setId(aukcja.getIdPrzedmiotu());
		aukcja.setPrzedmiot((Przedmiot)p_factory.getObject());
		
		if(testy() == "AUKCJA_NIE_ISTNIEJE")
		{
			html = Komunikaty.getError("Aukcja o podanym identyfikatorze nie istnieje!");
		}
		else
		{
			
			getRightHtml(); 
		}
		if(mode == 1)
		{
			this.przebicie = getPrzebicieFromRequest(request);
			if(przebicie.getWartosc()>aktualna_cena)
			{
				przebicie.insertPrzebicie();
				aktualna_cena=przebicie.getWartosc();
			}
			else
				html=Komunikaty.getWarning("Podana cena jest zbyt niska, minimalne przebicie: " + aktualna_cena+1);
			mode = 0;
			//pobiera z formularza i ustawiæ przebicie 
			// przekierowanie w argumencie podg¹d aukcji?id_aukcji = 
			// sprawdziæ czy jest zalogowany
			
		}
    	initServlet();
    }
	private Przebicie getPrzebicieFromRequest(HttpServletRequest request)
	{
		wartosc_przebicia = Integer.parseInt(request.getParameter("wartosc_przebicia"));
		Przebicie prz = new Przebicie();
		
		prz.setWartosc(wartosc_przebicia);
		
		prz.setIdAukcji(Integer.parseInt(request.getParameter("id_aukcji")));
		
		prz.setIdUzytkownika(aukcja.getIdUzytkownika());
		
		Date current_date = new Date(Calendar.getInstance().getTime().getTime());
		prz.setDataPrzebicia(current_date);
		
		
		
		return prz;
	}
	private void getRightHtml()
	{
		
		Przedmiot przedmiot = aukcja.getPrzedmiot();
		html = String.format(this.getHtml(page_url), this.aukcja.getNazwa(), przedmiot.getNazwa(),
				"/img/obr_1.jpg", 
				"aktualna_cena","ostatnie_przebicie",aukcja.getDataZakonczenia(), aukcja.getId(), 
				 przedmiot.getOpis());
	}
	private String testy()
	{
		
		if(aukcja == null)
		{
			return "AUKCJA_NIE_ISTNIEJE";
		}
		return "";
	}
	private void getOstatniePrzebicie()
	{
		
		przebicie_temp = new Przebicie();
		String query = "";
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		Statement stmt;
		Connection c = pgsq.getC(); 
		try
		{
			stmt = c.createStatement();
			if(query=="");
				query = String.format("SELECT * from t_przebicia where id_aukcji = "+ request.getParameter("id_aukcji") +" order by wartosc DESC limit 1");
		
			ResultSet rs = stmt.executeQuery( query );
			if(rs.next())
			{
				przebicie_temp.setWartosc(rs.getInt("wartosc"));
				przebicie_temp.setDataPrzebicia(Date.valueOf(rs.getString("data_przebicia")));
			}
			rs.close();
			stmt.close();
			c.close();

		}
		catch ( Exception e ) 
		{
			System.err.println(e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
	    }
	}
	
}
