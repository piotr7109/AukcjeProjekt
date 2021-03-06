package modules.automaty;

import java.sql.ResultSet;
import java.sql.SQLException;

import modules.AbstractFactory;

public class AutomatFactory extends AbstractFactory
{
	public AutomatFactory()
	{
		super();
		tabela = "t_automaty";
		
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
}
