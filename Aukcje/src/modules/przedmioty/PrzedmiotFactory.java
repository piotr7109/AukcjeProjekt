package modules.przedmioty;

import java.sql.ResultSet;
import java.sql.SQLException;

import modules.AbstractFactory;

public class PrzedmiotFactory extends AbstractFactory
{
	public PrzedmiotFactory()
	{
		super();
		tabela = "t_przedmioty";
		
	}
	
	protected Object fetchObject(ResultSet rs) throws SQLException
	{
		Przedmiot przedmiot = new Przedmiot();
		przedmiot.setId(rs.getInt("id"));
		przedmiot.setNazwa(rs.getString("nazwa"));
		przedmiot.setOpis(rs.getString("opis"));
		przedmiot.setZdjecieSrc(rs.getString("zdjecie_src"));
		
		return przedmiot;
	}
	
	
}
