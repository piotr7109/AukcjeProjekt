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

public class SprawdzAutomaty
{

	public static void sprawdz()
	{
		AukcjaLista lista_aukcji = new AukcjaLista();
		ArrayList<Object> aukcje = lista_aukcji.getAktywneAukcje();
		int ile_aukcji = aukcje.size();

		PrzebicieFactory przebicie_factory = new PrzebicieFactory();

		AutomatLista lista_automatow = new AutomatLista();

		for (int i = 0; i < ile_aukcji; i++)
		{
			Aukcja aukcja = (Aukcja) aukcje.get(i);
			Przebicie ostatnie_przebicie = (Przebicie) przebicie_factory.getOstatniePrzebicie(aukcja.getId());

			ArrayList<Object> automaty = lista_automatow.getAutomatyByAukcjaId(aukcja.getId());
			int ile_automatow = automaty.size();

			for (int j = 0; j < ile_automatow; j++)
			{
				Automat automat = (Automat) automaty.get(j);
				if (automat.getIdUzytkownika() != ostatnie_przebicie.getIdUzytkownika())
				{
					if (automat.getMaxPrzebicie() >= ostatnie_przebicie.getWartosc())
					{
						Przebicie przebicie = new Przebicie();
						przebicie.setWartosc(ostatnie_przebicie.getWartosc() + 1);
						przebicie.setIdAukcji(aukcja.getId());
						przebicie.setIdUzytkownika(automat.getIdUzytkownika());

						Date current_date = new Date(Calendar.getInstance().getTime().getTime());
						przebicie.setDataPrzebicia(current_date);
						przebicie.insertPrzebicie();
					}
				}
			}

		}

	}
}
