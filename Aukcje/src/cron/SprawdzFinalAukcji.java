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

public class SprawdzFinalAukcji
{

	public static void sprawdz()
	{
		AukcjaLista lista_aukcji = new AukcjaLista();
		ArrayList<Object> aukcje = lista_aukcji.getList();
		int ile_aukcji = aukcje.size();

		PrzebicieFactory przebicie_factory = new PrzebicieFactory();


		for (int i = 0; i < ile_aukcji; i++)
		{
			Aukcja aukcja = (Aukcja) aukcje.get(i);
			Przebicie ostatnie_przebicie = (Przebicie) przebicie_factory.getOstatniePrzebicie(aukcja.getId());
			
		}

	}
}
