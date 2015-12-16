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
	
	public static String getMenuLogged()
	{
		String html ="";
    	html +=getMenuIn();
    	html +="<li><a href='lista_aukcji'><span style='color:red' class='glyphicon glyphicon-plus'></span> Lista aukcji</a></li>";
    	html +="<li><a href='dodaj_aukcje'><span style='color:red' class='glyphicon glyphicon-plus'></span> Dodaj Aukcję</a></li>";
		html +="<li role='separator' class='divider'></li>";
		html +="<li><a href='lista_użytkownikow'><span style='color:red' class='glyphicon glyphicon-user'></span> Lista Użytkowników</a></li>";
	    html +="<li><a href='logowanie?mode=10'><span style='color:red' class='glyphicon glyphicon-log-out'></span> Wyloguj</a></li>";
	    
	    html +=getMenuOut();
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
