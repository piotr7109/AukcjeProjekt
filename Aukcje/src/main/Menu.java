package main;

public final class Menu 
{
	
	
	private static String getMenuIn()
	{
		String html ="";
		html +="<div class='btn-group col-md-1 col-sm-1 col-xs-1 button-menu '>";
    	html +="<button type='button' class='btn btn-info dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>";
    	html +=" Menu <span class='caret'></span>";
		html +="</button>";
		html +="<ul class='dropdown-menu main-dropdown-menu'>";
		return html;
	}
	private static String getMenuOut()
	{
		String html ="";
		html +="</ul>";
		html +="</div>";
		return html;
	}
	
	public static String getMenuLogged(char typ_uzytkownika)
	{
		String html ="";
    	html +=getMenuIn();
    	html +="<li><a href='lista_aukcji'><span style='color:red' class='glyphicon glyphicon-plus'></span> Lista aukcji</a></li>";
    	html +="<li><a href='dodaj_aukcje'><span style='color:red' class='glyphicon glyphicon-plus'></span> Dodaj Aukcjê</a></li>";
    	html +="<li><a href='zakup_bickow'><span style='color:red' class='glyphicon glyphicon-plus'></span> Zakup BICKi</a></li>";
    	
		html +="<li role='separator' class='divider'></li>";
		html +="<li><a href='edycja_danych'><span style='color:red' class='glyphicon glyphicon-plus'></span> Edytuj swoje dane</a></li>";
		
		if(typ_uzytkownika== 'A')
		{
			html += getMenuAdmin();
		}
		
	    html +="<li><a href='logowanie?mode=10'><span style='color:red' class='glyphicon glyphicon-log-out'></span> Wyloguj</a></li>";
	    
	    html +=getMenuOut();
    	return html;
	}
	private static String getMenuAdmin()
	{
		String html ="";
		
		html +="<li><a href='lista_uzytkownikow'><span style='color:red' class='glyphicon glyphicon-user'></span> Lista U¿ytkowników</a></li>";
		
		return html;
	}
	public static String getMenuUnlogged()
	{
		String html ="";
    	html +=getMenuIn();
    	html +="<li><a href='lista_aukcji'><span style='color:red' class='glyphicon glyphicon-plus'></span> Lista aukcji</a></li>";
    	html +="<li><a href='rejestracja'><span style='color:red' class='glyphicon glyphicon-log-in'></span> Rejestracja</a></li>"; 
		html +="<li role='separator' class='divider'></li>";
		html +="<li><a href='logowanie'><span style='color:red' class='glyphicon glyphicon-log-in'></span> Zaloguj</a></li>"; 
	    html +=getMenuOut();
    	return html;
	}
}
