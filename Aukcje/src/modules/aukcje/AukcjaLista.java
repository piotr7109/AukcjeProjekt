package modules.aukcje;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.PostgreSQLJDBC;
import modules.AbstractLista;

public class AukcjaLista extends AbstractLista
{
	protected Object fetchObject(ResultSet rs) throws SQLException
	{
		
		AukcjaFactory a_factory = new AukcjaFactory();
		a_factory.setId(rs.getInt("id"));
		
		Aukcja aukcja = (Aukcja)a_factory.getObject();
		return aukcja;
	}
}
