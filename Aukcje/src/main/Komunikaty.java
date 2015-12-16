package main;

public class Komunikaty
{
	
	public static String getWarning(String komunikat)
	{
		String html ="<div class='alert alert-warning' role='alert'><span aria-hidden='true' class='glyphicon glyphicon-alert'></span> "+komunikat+"</div>";
		
		return html;
	}
	public static String getSukces(String komunikat)
	{
		String html ="<div class='alert alert-success' role='alert'><span aria-hidden='true' class='glyphicon glyphicon-alert'></span> "+komunikat+"</div>";
		
		return html;
	}
	public static String getError(String komunikat)
	{
		String html ="<div class='alert alert-danger' role='alert'><span aria-hidden='true' class='glyphicon glyphicon-alert'></span> "+komunikat+"</div>";
		
		return html;
	}
}
