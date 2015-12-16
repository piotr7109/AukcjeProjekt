package controllers.aukcje;

import main.ServletMain;
import modules.aukcje.Aukcja;

public class ListaAukcji extends ServletMain 
{
	protected Aukcja aukcje[];
	
	public ListaAukcji() {
		super();
		
		page_url = page_url = "views/ListaAukcji.html";
	}
}
