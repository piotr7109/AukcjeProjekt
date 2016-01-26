package modules.przebicia;

import java.sql.ResultSet;
import java.sql.SQLException;

import modules.AbstractFactory;

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

	public Przebicie getOstatniePrzebicie(int id_aukcji)
	{
		query = String.format("SELECT * from t_przebicia where id_aukcji = %s order by id DESC limit 1", id_aukcji);
		Przebicie prz = (Przebicie) getObject();
		return prz;
	}
	public Przebicie getOstatniePrzebicieUzytkownika(int id_aukcji, int id_uzytkownika)
	{
		query = String.format("SELECT * from t_przebicia where id_aukcji = %d AND id_uzytkownika = %d order by data_przebicia DESC limit 1", id_aukcji, id_uzytkownika);
		Przebicie prz = (Przebicie) getObject();
		return prz;
	}

}
