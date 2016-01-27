package controllers.aukcje;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hamcrest.core.IsSame;

import main.Komunikaty;
import main.PostgreSQLJDBC;
import main.ServletMain;
import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaFactory;
import modules.aukcje.AukcjaLista;
import modules.przebicia.Przebicie;
import modules.przebicia.PrzebicieFactory;
import modules.przebicia.PrzebicieLista;
import modules.przedmioty.Przedmiot;
import modules.przedmioty.PrzedmiotFactory;
import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;

public class PodgladAukcji extends ServletMain
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Aukcja aukcja;
	private int wartosc_przebicia;
	private String data_ostatniego_przebicia;
	private int aktualna_cena;

	public PodgladAukcji()
	{
		super();
		page_url = "views/PodgladAukcji.html";
	}

	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		html = "";

		int id_aukcji = Integer.parseInt(request.getParameter("id_aukcji"));

		AukcjaFactory a_factory = new AukcjaFactory();
		a_factory.setId(id_aukcji);
		aukcja = (Aukcja) a_factory.getObject();

		if (testy() == "AUKCJA_NIE_ISTNIEJE")
		{
			html = Komunikaty.getError("Aukcja o podanym identyfikatorze nie istnieje!");
		}
		else if (testy() == "AUKCJA_ZAKONCZONA")
		{
			html = Komunikaty.getError("Aukcja zosta³a zakoñczona!");
		}
		else
		{
			PrzedmiotFactory p_factory = new PrzedmiotFactory();
			p_factory.setId(aukcja.getIdPrzedmiotu());
			aukcja.setPrzedmiot((Przedmiot) p_factory.getObject());

			PrzebicieFactory przebicie_factory = new PrzebicieFactory();
			Przebicie ostatnie_przebicie = przebicie_factory.getOstatniePrzebicie(id_aukcji);
			if (ostatnie_przebicie == null)
			{
				aktualna_cena = 0;
				data_ostatniego_przebicia = "---";
			}
			else
			{
				aktualna_cena = ostatnie_przebicie.getWartosc();
				data_ostatniego_przebicia = ostatnie_przebicie.getDataPrzebicia().toString();
			}
			if (mode == 1)
			{

				if (!this.sprawdzSesje())
				{
					html = Komunikaty.getWarning("Zaloguj siê, aby licytowaæ ");
				}
				else
				{
					Przebicie przebicie = getPrzebicieFromRequest(request);
					String przebicieTest = this.testyPrzebicie(przebicie);

					if (przebicieTest == "NISKA_CENA")
					{
						html = Komunikaty.getWarning("Podana cena jest zbyt niska, minimalne przebicie: " + (aktualna_cena+1));
					}
					else if (przebicieTest == "BRAK_FUNDUSZY")
					{
						html = Komunikaty.getWarning("Niestety nie masz wystarczaj¹co du¿o œrodów na koncie. " + "Mo¿e to wynikaæ z udzia³u w innych aukcjach.<br>"
								+ "<a href='zakup_bickow'>Uzupe³nij swoje konto, aby braæ udzia³ w aukcji.</a>");
					}
					else if (przebicieTest == "BRAK_FUNDUSZY_PRZESY£KA")
					{
						html = Komunikaty.getWarning("Niestety nie masz wystarczaj¹co du¿o œrodów na koncie. " + "Mo¿e to wynikaæ z udzia³u w innych aukcjach (pamiêtaj o kosztach wysy³ki).<br>"
								+ "<a href='zakup_bickow'>Uzupe³nij swoje konto, aby braæ udzia³ w aukcji.</a>");
					}
					else
					{
						przebicie.insertPrzebicie();
						aktualna_cena = przebicie.getWartosc();
					}
				}

				mode = 0;
				// pobiera z formularza i ustawiæ przebicie
				// przekierowanie w argumencie podg¹d aukcji?id_aukcji =
				// sprawdziæ czy jest zalogowany

			}
			html += getRightHtml();
			if ((request.getParameter("tryb") != null) && request.getParameter("tryb").equals("usun"))
			{

				html = usunAukcjeHtml(aukcja);
				html += "<script>window.location.replace('lista_aukcji');</script>";
			}
		}

		initServlet();
	}

	private Przebicie getPrzebicieFromRequest(HttpServletRequest request)
	{
		wartosc_przebicia = Integer.parseInt(request.getParameter("wartosc_przebicia"));
		Przebicie prz = new Przebicie();

		prz.setWartosc(wartosc_przebicia);
		prz.setIdAukcji(Integer.parseInt(request.getParameter("id_aukcji")));
		prz.setIdUzytkownika(sesja.getIdUzytkownika(request));

		Date current_date = new Date(Calendar.getInstance().getTime().getTime());
		prz.setDataPrzebicia(current_date);

		return prz;
	}

	private String testyPrzebicie(Przebicie prz)
	{
		if (prz.getWartosc() <= aktualna_cena)
		{
			return "NISKA_CENA";
		}
		else
		{
			AukcjaLista a_lista = new AukcjaLista();
			ArrayList<Object> aukcje = a_lista.getAukcjeUzytkownik(sesja.getIdUzytkownika(request));
			int size = aukcje.size();

			int wartosc = 0;
			PrzebicieFactory p_factory = new PrzebicieFactory();
			for (int i = 0; i < size; i++)
			{
				Aukcja au = (Aukcja) aukcje.get(i);
				if (au.getId() != aukcja.getId())
				{
					Przebicie p = p_factory.getOstatniePrzebicieUzytkownika(au.getId(), sesja.getIdUzytkownika(request));
					wartosc += p.getWartosc();
				}
			}
			wartosc += prz.getWartosc();
			UzytkownikFactory u_factory = new UzytkownikFactory();
			u_factory.setId(sesja.getIdUzytkownika(request));
			Uzytkownik uz = (Uzytkownik) u_factory.getObject();
			if (wartosc + 10 > uz.getStanKonta())
			{
				if (wartosc > uz.getStanKonta())
				{
					return "BRAK_FUNDUSZY";
				}
				else
					return "BRAK_FUNDUSZY_PRZESY£KA";
			}
		}
		return "";
	}

	private String testy()
	{

		if (aukcja == null)
		{
			return "AUKCJA_NIE_ISTNIEJE";
		}
		if ((aukcja.getStan() == 'X'))
		{
			return "AUKCJA_ZAKONCZONA";
		}
		return "";
	}

	private String getRightHtml()
	{
		String html = "";
		Przedmiot przedmiot = aukcja.getPrzedmiot();
		html = String.format(this.getHtml(page_url), this.aukcja.getNazwa(), przedmiot.getNazwa(), przedmiot.getZdjecieSrc(), aktualna_cena + " pkt BICK", data_ostatniego_przebicia,
				aukcja.getDataZakonczenia(), aukcja.getId(), przedmiot.getOpis(), "", aukcja.getId());
		return html;
	}

	private String usunAukcjeHtml(Aukcja a)
	{
		String html = "";

		PrzedmiotFactory p_factory = new PrzedmiotFactory();
		p_factory.setId(a.getIdPrzedmiotu());
		a.setPrzedmiot((Przedmiot) p_factory.getObject());
		a.getPrzedmiot().deletePrzedmiot();
		a.deleteAukcja();
		String komunikat = Komunikaty.getInfo("Aukcja zosta³a usuniêta");

		html += komunikat;

		return html;
	}

}
