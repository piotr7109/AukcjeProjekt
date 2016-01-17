package modules.przebicia;

import java.sql.ResultSet;
import java.sql.SQLException;

import modules.AbstractFactory;
import modules.aukcje.Aukcja;

public class PrzebicieFactory extends AbstractFactory
{
	public PrzebicieFactory()
	{
		super();
		tabela = "t_przbicia";
		
	}
	
	protected Object fetchObject(ResultSet rs) throws SQLException
	{
		Przebicie p = new Przebicie();
		
		p.setId(rs.getInt("id"));
		p.setIdAukcji(rs.getInt("id_aukcji"));
		p.setIdUzytkownika(rs.getInt("id_uzytkownika"));
		p.setWartosc(rs.getDouble("wartosc"));
		p.setDataPrzebicia(rs.getDate("data_przebicia"));
		
		return p;
	}
	
	
	
}
