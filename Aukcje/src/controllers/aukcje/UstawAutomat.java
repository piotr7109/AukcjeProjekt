package controllers.aukcje;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Komunikaty;
import main.ServletMain;
import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaLista;
import modules.automaty.Automat;
import modules.automaty.AutomatFactory;
import modules.przebicia.Przebicie;
import modules.przebicia.PrzebicieFactory;
import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;

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
				if (test() == "BRAK_FUNDUSZY")
				{
					html = Komunikaty.getWarning("Niestety nie masz wystarczaj¹co du¿o œrodów na koncie. " + "Mo¿e to wynikaæ z udzia³u w innych aukcjach.<br>"
							+ "<a href='zakup_bickow'>Uzupe³nij swoje konto, aby braæ udzia³ w aukcji.</a>");

					html += String.format(this.getHtml(page_url), id_aukcji);
					mode = 0;
				}
				else
				{
					zapiszAutomat();
					html = String.format("<script>window.location.replace('podglad_aukcji?id_aukcji=%d');</script>", id_aukcji);
				}
				break;

			default:
				break;
		}

		initServlet();
	}

	private String test()
	{
		AukcjaLista a_lista = new AukcjaLista();
		ArrayList<Object> aukcje = a_lista.getAukcjeUzytkownik(sesja.getIdUzytkownika(request));
		int size = aukcje.size();

		int wartosc = 0;
		PrzebicieFactory p_factory = new PrzebicieFactory();
		for (int i = 0; i < size; i++)
		{
			Aukcja au = (Aukcja) aukcje.get(i);
			if (au.getId() != id_aukcji)
			{
				Przebicie p = p_factory.getOstatniePrzebicieUzytkownika(au.getId(), sesja.getIdUzytkownika(request));
				wartosc += p.getWartosc();
			}
		}
		wartosc += Integer.parseInt(request.getParameter("kwota"));
		UzytkownikFactory u_factory = new UzytkownikFactory();
		u_factory.setId(sesja.getIdUzytkownika(request));
		Uzytkownik uz = (Uzytkownik) u_factory.getObject();
		if (wartosc > uz.getStanKonta())
		{
			return "BRAK_FUNDUSZY";
		}
		return "";
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
