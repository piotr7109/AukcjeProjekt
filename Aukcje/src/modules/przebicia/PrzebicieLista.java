package modules.przebicia;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Generated;

import modules.AbstractLista;
import modules.uzytkownicy.Uzytkownik;

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
		prz.setWartosc(rs.getDouble("wartosc"));
		
		return prz;
	}

}
