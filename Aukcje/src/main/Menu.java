package main;

public final class Menu
{

	public static String getMenuLogged(char typ_uzytkownika)
	{
		String nazwy[] = { "Lista aukcji", "Dodaj aukcjê", "Kup BICKi", "Edytuj dane", "Wygrane aukcje", "Wyloguj" };
		String serwisy[] = { "lista_aukcji", "dodaj_aukcje", "zakup_bickow", "edycja_danych", "wygrane", "logowanie?mode=10" };
		String ikony[] = { "list-alt", "plus", "euro", "user", "euro", "log-out" };
		String klasy[] = { "lista_aukcji", "dodaj_aukcje", "zakup_bickow", "edycja_danych", "wygrane", "wyloguj" };

		String html = "";

		int size = nazwy.length;
		for (int i = 0; i < size; i++)
		{
			html += String.format("<li id='%s'><a href='%s'><button type='button' class='btn user'><span class='glyphicon glyphicon-%s'></span> %s</button></a></li>", klasy[i], serwisy[i], ikony[i],
					nazwy[i]);
		}
		if (typ_uzytkownika == 'A')
		{
			html += getMenuAdmin();
		}

		return html;
	}

	private static String getMenuAdmin()
	{
		String html = "";

		html += "<li><a href='lista_uzytkownikow'><button type='button' class='btn btn-warning'><span class='glyphicon glyphicon-user'></span> Lista u¿ytkowników</button></a></li>";

		return html;
	}

	public static String getMenuUnlogged()
	{
		String nazwy[] = { "Logowanie", "Rejestracja", "Lista aukcji" };
		String serwisy[] = { "logowanie", "rejestracja", "lista_aukcji" };
		String ikony[] = { "log-in", "registration-mark", "list-alt" };
		String klasy[] = { "logowanie", "rejestracja", "lista_aukcji" };
		String html = "";

		int size = nazwy.length;
		for (int i = 0; i < size; i++)
		{
			html += String.format("<li id='%s'><a href='%s'><button type='button' class='btn user'><span class='glyphicon glyphicon-%s'></span> %s</button></a></li>", klasy[i], serwisy[i], ikony[i],
					nazwy[i]);
		}
		return html;
	}
}
