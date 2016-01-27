package modules.przebicia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modules.AbstractLista;


public class PrzebicieLista extends AbstractLista
{
	public PrzebicieLista() {
		table = "t_przebicia";
	}
	
	protected Object fetchObject(ResultSet rs) throws SQLException
	{
		Przebicie prz = new Przebicie();
		prz.setId(rs.getInt("id"));
		prz.setIdAukcji(rs.getInt("id_aukcji"));
		prz.setIdUzytkownika(rs.getInt("id_uzytkownika"));
		prz.setDataPrzebicia(rs.getDate("data_przebicia"));
		prz.setWartosc(rs.getInt("wartosc"));
		
		return prz;
	}
	public ArrayList<Object> getPrzebiciaAukcja(int id_aukcji)
	{
		query = String.format("select * FROM t_przebicia where id_aukcji=%d", id_aukcji);
		ArrayList<Object> prz = (ArrayList<Object>) getList();
		return prz;
	}
}
