package cron;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaLista;
import modules.automaty.Automat;
import modules.automaty.AutomatLista;
import modules.przebicia.Przebicie;
import modules.przebicia.PrzebicieFactory;
import modules.przebicia.PrzebicieLista;
import modules.uzytkownicy.UzytkownicyLista;
import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;

public class SprawdzFinalAukcji
{

	public static void sprawdz()
	{
		AukcjaLista lista_aukcji = new AukcjaLista();
		ArrayList<Object> aukcje = lista_aukcji.getList();
		int ile_aukcji = aukcje.size();
		java.util.Date date = new java.util.Date();
		Date sqlDate = new Date(date.getTime());


		PrzebicieFactory przebicie_factory = new PrzebicieFactory();

		for (int i = 0; i < ile_aukcji; i++)
		{
			Aukcja aukcja = (Aukcja) aukcje.get(i);
			Przebicie ostatnie_przebicie = (Przebicie) przebicie_factory.getOstatniePrzebicie(aukcja.getId());

			if (aukcja.getStan() == 'A' && aukcja.getDataZakonczenia().before(sqlDate))
			{
				if (ostatnie_przebicie == null)
				{
					aukcja.deleteAukcja();
				}
				else
				{
					Przebicie ostatnie_przebicie_ = (Przebicie)przebicie_factory.getOstatniePrzebicie(aukcja.getId());
					
					aukcja.setStan('X');
					aukcja.setIdUzytkownikaZakup(ostatnie_przebicie_.getIdUzytkownika());
					aukcja.setCenaKoncowa((int)ostatnie_przebicie_.getWartosc());
					aukcja.updateAukcja();
					UzytkownicyLista u_lista = new UzytkownicyLista();
					ArrayList<Object> uzytkownicy = u_lista.getUzytkownicyAukcja(aukcja.getId());					
					int size = uzytkownicy.size();
					
					for(int j=0; j< size; j++)
					{
						Uzytkownik uz = (Uzytkownik)uzytkownicy.get(j);
						Przebicie ost_przebicie = przebicie_factory.getOstatniePrzebicieUzytkownika(aukcja.getId(), uz.getId());
						
						int wartosc = (int)ost_przebicie.getWartosc();
						uz.setStanKonta(uz.getStanKonta()-wartosc);
						uz.setBicki();
					
					}
					aukcja.deletePrzebicia();
				}
			}
			else if (aukcja.getDataZakonczenia().before(getLastWeekDate()))
			{
				aukcja.deleteAukcja();
			}
		}

	}

	private static java.sql.Date getLastWeekDate()
	{
		java.util.Calendar cal = Calendar.getInstance();
		java.util.Date utilDate = new java.util.Date(); // your util date
		cal.setTime(utilDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-7);
		java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());
		return sqlDate;
	}
}
