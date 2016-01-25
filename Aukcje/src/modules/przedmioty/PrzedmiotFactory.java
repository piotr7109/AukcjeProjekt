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
	public Przedmiot getLastInserted()
	{
		query = String.format("SELECT * from t_przedmioty order by id DESC limit 1");
		Przedmiot prz = (Przedmiot) getObject();
		return prz;
	}
	
	
}
