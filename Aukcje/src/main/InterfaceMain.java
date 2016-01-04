package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface InterfaceMain 
{
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException ;
}
