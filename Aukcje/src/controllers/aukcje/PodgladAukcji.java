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
import modules.przebicia.PrzebicieFactory;
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
	private Przebicie przebicie;
	private double aktualna_cena;
	private PrzebicieLista przebicie_lista;

	public PodgladAukcji()
	{
		super();
		page_url = "views/PodgladAukcji.html";
	}

	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id_aukcji = Integer.parseInt(request.getParameter("id_aukcji"));

		AukcjaFactory a_factory = new AukcjaFactory();
		a_factory.setId(id_aukcji);
		aukcja = (Aukcja) a_factory.getObject();

		PrzedmiotFactory p_factory = new PrzedmiotFactory();
		p_factory.setId(aukcja.getIdPrzedmiotu());
		aukcja.setPrzedmiot((Przedmiot) p_factory.getObject());
		
		PrzebicieFactory przebicie_factory = new PrzebicieFactory();
		Przebicie ostatnie_przebicie = przebicie_factory.getOstatniePrzebicie(id_aukcji);

		if (testy() == "AUKCJA_NIE_ISTNIEJE")
		{
			html = Komunikaty.getError("Aukcja o podanym identyfikatorze nie istnieje!");
		}
		else
		{

			html = getRightHtml();
		}
		if (mode == 1)
		{
			this.przebicie = getPrzebicieFromRequest(request);
			if (przebicie.getWartosc() > aktualna_cena)
			{
				przebicie.insertPrzebicie();
				aktualna_cena = przebicie.getWartosc();
			}
			else
			{
				html = Komunikaty.getWarning("Podana cena jest zbyt niska, minimalne przebicie: " + aktualna_cena + 1);
			}
			mode = 0;
			// pobiera z formularza i ustawiæ przebicie
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

	private String getRightHtml()
	{
		String html = "";
		Przedmiot przedmiot = aukcja.getPrzedmiot();
		html = String.format(this.getHtml(page_url), this.aukcja.getNazwa(), przedmiot.getNazwa(), "/img/obr_1.jpg", "aktualna_cena", "ostatnie_przebicie", aukcja.getDataZakonczenia(), aukcja.getId(),
				przedmiot.getOpis());
		return html;
	}

	private String testy()
	{

		if (aukcja == null)
		{
			return "AUKCJA_NIE_ISTNIEJE";
		}
		return "";
	}

}
