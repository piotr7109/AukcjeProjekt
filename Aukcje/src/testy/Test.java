package testy;

import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;

public class Test
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		UzytkownikFactory factory = new UzytkownikFactory();
		factory.setLogin("admin");
		Uzytkownik uz = (Uzytkownik)factory.getObject();
		
		System.out.println(uz.getNazwisko());

	}

}
