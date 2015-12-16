package modules.aukcje;

import java.sql.ResultSet;
import java.sql.SQLException;

import modules.AbstractLista;

public class AukcjaLista extends AbstractLista
{
	public AukcjaLista() {
		table = "t_aukcje";
	}
	
	protected Object fetchObject(ResultSet rs) throws SQLException
	{
		
		AukcjaFactory a_factory = new AukcjaFactory();
		a_factory.setId(rs.getInt("id"));
		
		Aukcja aukcja = (Aukcja)a_factory.getObject();
		return aukcja;
	}
}
