package testy;

import java.util.ArrayList;

import modules.aukcje.Aukcja;
import modules.aukcje.AukcjaLista;
import modules.przedmioty.Przedmiot;
import modules.przedmioty.PrzedmiotFactory;

public class test2
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Aukcja au;
		AukcjaLista a_lista = new AukcjaLista();
		ArrayList<Object> aukcje;
		aukcje = a_lista.getAktywneAukcje();
		
		au = (Aukcja) aukcje.get(2);
		PrzedmiotFactory p_factory = new PrzedmiotFactory();
		p_factory.setId(au.getIdPrzedmiotu());
		
		
		
		au.setPrzedmiot((Przedmiot) p_factory.getObject());
		AukcjaLista al = new AukcjaLista();
		ArrayList<Object> aukcje_1 =  al.wyszukajAukcje("kaw");
		Aukcja asd;
		
		int size = aukcje_1.size();
		for(int i = 0; i < size; i++)
		{
			asd = (Aukcja) aukcje_1.get(i);
			System.out.println(asd.getNazwa());
		}
		
	}

}
