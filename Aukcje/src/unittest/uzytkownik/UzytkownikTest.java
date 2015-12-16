package unittest.uzytkownik;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import main.MD5;
import modules.uzytkownicy.Uzytkownik;
import modules.uzytkownicy.UzytkownikFactory;

public class UzytkownikTest
{
	Uzytkownik user;

	@Before
	public void setUp() throws Exception
	{
		user = new Uzytkownik();
		// Id auto-increment in DB
		user.setLogin("TESTER");
		user.setHaslo("TESTPASS");
		user.setImie("TESTERNAME");
		user.setNazwisko("TESTERLASTNAME");
		user.setEmail("tester@test.com");
		user.setStanKonta(100);
		user.setStatus('Z');
		user.setBledneLogowania(0);
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testInsertUzytkownik()
	{
		user.insertUzytkownik();
		UzytkownikFactory uf = new UzytkownikFactory();
		uf.setLogin(user.getLogin());
		Uzytkownik user2 = (Uzytkownik) uf.getObject();

		Assert.assertEquals(user.getLogin(), user2.getLogin());
		Assert.assertEquals(MD5.getMD5(user.getHaslo()), user2.getHaslo());
		Assert.assertEquals(user.getImie(), user2.getImie());
		Assert.assertEquals(user.getNazwisko(), user2.getNazwisko());
		Assert.assertEquals(user.getEmail(), user2.getEmail());
		Assert.assertEquals(user.getStanKonta(), user2.getStanKonta());
		Assert.assertEquals(user.getStatus(), user2.getStatus());
		Assert.assertEquals(user.getBledneLogowania(), user2.getBledneLogowania());

	}

	@Test
	public void testUpdateUzytkownik()
	{
		user.insertUzytkownik();

		UzytkownikFactory uf = new UzytkownikFactory();
		uf.setLogin(user.getLogin());
		user = (Uzytkownik) uf.getObject();
		// zmiana wartoœci
		user.setLogin("TESTER2");
		user.setHaslo("TESTPASS2");
		user.setImie("TESTERNAME2");
		user.setNazwisko("TESTERLASTNAME2");
		user.setEmail("tester2@test.com");
		user.setStanKonta(200);
		user.setStatus('A');
		user.setBledneLogowania(1);

		user.updateUzytkownik();

		uf.setLogin(user.getLogin());

		Uzytkownik user2 = (Uzytkownik) uf.getObject();
		Assert.assertNotNull(user);
		Assert.assertNotNull(user2);

		Assert.assertEquals(user.getLogin(), user2.getLogin());
		Assert.assertEquals(MD5.getMD5(user.getHaslo()), user2.getHaslo());
		Assert.assertEquals(user.getImie(), user2.getImie());
		Assert.assertEquals(user.getNazwisko(), user2.getNazwisko());
		Assert.assertEquals(user.getEmail(), user2.getEmail());
		Assert.assertEquals(user.getStanKonta(), user2.getStanKonta());
		Assert.assertEquals(user.getStatus(), user2.getStatus());
		Assert.assertEquals(user.getBledneLogowania(), user2.getBledneLogowania());
	}

	@Test
	public void testDeleteUzytkownik()
	{
		user.insertUzytkownik();
		UzytkownikFactory uf = new UzytkownikFactory();

		uf.setLogin(user.getLogin());
		user = (Uzytkownik) uf.getObject();

		user.deleteUzytkownik();

		uf.setLogin("");
		
		Assert.assertNull(uf.getObject());
	}

}