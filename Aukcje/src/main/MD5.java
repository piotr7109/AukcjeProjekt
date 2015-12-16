package main;

import java.math.BigInteger;
import java.security.MessageDigest;


public class MD5
{
	public static String getMD5(String chain)
	{
		try
		{
			MessageDigest md5_string = MessageDigest.getInstance("MD5"); 
			md5_string.update(chain.getBytes(), 0, chain.length());
			return new BigInteger(1, md5_string.digest()).toString(16);
		}
		catch(Exception e)
		{
			System.out.println("Problem z przetwarzaniem MD5");
		}
		return "";
	}
}
