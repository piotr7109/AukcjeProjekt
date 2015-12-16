package testy;

import java.util.ArrayList;

import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaLista;
import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;

public class Test
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		AukcjaLista au = new AukcjaLista();
		ArrayList<Object> a = au.getList();
		Aukcja aukcja = (Aukcja)a.get(0);
		System.out.println(aukcja.getNazwa());

	}

}
