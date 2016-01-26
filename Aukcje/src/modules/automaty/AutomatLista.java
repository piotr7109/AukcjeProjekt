package modules.automaty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modules.AbstractLista;

public class AutomatLista extends AbstractLista
{
	public AutomatLista()
	{
		table = "t_automaty";
		
	}
	
	protected Object fetchObject(ResultSet rs) throws SQLException
	{
		Automat au = new Automat();
		
		au.setId(rs.getInt("id"));
		au.setIdAukcji(rs.getInt("id_aukcji"));
		au.setIdUzytkownika(rs.getInt("id_uzytkownika"));
		au.setMaxPrzebicie(rs.getInt("max_przebicie"));
		
		return au;
	}
	public ArrayList<Object> getAutomatyByAukcjaId(int id_aukcji)
	{
		query = String.format("select * FROM t_automaty where id_aukcji =  %d", id_aukcji);
		ArrayList<Object> au = (ArrayList<Object>) getList();
		return au;
	}
}
