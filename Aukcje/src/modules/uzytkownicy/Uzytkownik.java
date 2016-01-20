package modules.uzytkownicy;

import main.PostgreSQLJDBC;;

public class Uzytkownik 
{
	private int id;
	private String login;
	private String haslo;
	private String imie;
	private String nazwisko;
	private String email;
	private String adres;
	private int stan_konta;
	private char status;   // A- admin, X - rejestracja niepotwierdzona, Z - w pe³ni zarejestrowany
	private int bledne_logowania;
	
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getLogin()
	{
		return login;
	}
	public void setLogin(String login)
	{
		this.login = login;
	}
	public String getHaslo()
	{
		return haslo;
	}
	public void setHaslo(String haslo)
	{
		this.haslo = haslo;
	}
	public String getImie()
	{
		return imie;
	}
	public void setImie(String imie)
	{
		this.imie = imie;
	}
	public String getNazwisko()
	{
		return nazwisko;
	}
	public void setNazwisko(String nazwisko)
	{
		this.nazwisko = nazwisko;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getAdres()
	{
		return adres;
	}
	public void setAdres(String adres)
	{
		this.adres = adres;
	}
	public int getStanKonta()
	{
		return stan_konta;
	}
	public void setStanKonta(int stan_konta)
	{
		this.stan_konta = stan_konta;
	}
	public char getStatus()
	{
		return status;
	}
	public void setStatus(char status)
	{
		this.status = status;
	}
	public int getBledneLogowania()
	{
		return bledne_logowania;
	}
	public void setBledneLogowania(int bledne_logowania)
	{
		this.bledne_logowania = bledne_logowania;
	}
	public void insertUzytkownik()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("INSERT INTO t_uzytkownicy(login, haslo, imie, nazwisko, email, adres," +
						"stan_konta, status, bledne_logowania) VALUES" +
						"('%s', MD5('%s'), '%s', '%s', '%s', '%s', %d, '%c', %d)",
						login, haslo, imie, nazwisko, email, adres, stan_konta, status, bledne_logowania);
		pgsq.queryOpertaion(query);
	}
	public void updateUzytkownik()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("UPDATE t_uzytkownicy SET login='%s', haslo=MD5('%s'), imie='%s'," +
									"nazwisko='%s', email='%s', adres='%s', stan_konta=%d, status='%c', " +
									"bledne_logowania=%d WHERE id=%d",
									login, haslo, imie, nazwisko, email, adres, stan_konta, status,
									bledne_logowania, id);
		pgsq.queryOpertaion(query);
	}
	public void deleteUzytkownik()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("DELETE FROM t_uzytkownicy WHERE id=%d", id);
		pgsq.queryOpertaion(query);
	}
	
	public void dodajBicki()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("UPDATE t_uzytkownicy set stan_konta = %d where id=%d", stan_konta, id);
		pgsq.queryOpertaion(query);
	}
	public void aktywujUzytkownika()
	{
		PostgreSQLJDBC pgsq = new PostgreSQLJDBC();
		String query = String.format("UPDATE t_uzytkownicy set status = '%c' where id=%d", status, id);
		pgsq.queryOpertaion(query);
	}
	
	
	
	
}
