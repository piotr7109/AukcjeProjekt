package testy;

import modules.uzytkownicy.Uzytkownik;

public class TestUpdate
{
	
	
	
	public static void main(String[] args)
	{
		Uzytkownik uz = new Uzytkownik();
		uz.setId(111);
		uz.setLogin("login");
    	uz.setHaslo("haslo");
    	uz.setImie("imie");
    	uz.setNazwisko("nazwisko");
    	uz.setEmail("email");
    	uz.setAdres("adres");
    	
    	uz.setStanKonta(1500);
    	uz.setStatus("A".charAt(0));
    	uz.setBledneLogowania(0);
    	uz.updateUzytkownik();
	}
}
