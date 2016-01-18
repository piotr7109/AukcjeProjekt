package testy;

import java.util.ArrayList;

import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaLista;
import modules.przebicia.Przebicie;
import modules.przebicia.PrzebicieFactory;
import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;

public class Test
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		PrzebicieFactory p_factory = new PrzebicieFactory();
		Przebicie prz = p_factory.getOstatniePrzebicie(1);
		System.out.println(prz.getWartosc());

	}

}
