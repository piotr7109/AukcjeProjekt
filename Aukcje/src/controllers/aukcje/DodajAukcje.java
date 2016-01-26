package controllers.aukcje;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

//import org.apache.commons.io.FileUtils;

import main.Komunikaty;
import main.ServletMain;
import modules.aukcje.Aukcja;
import modules.przedmioty.Przedmiot;

@MultipartConfig
public class DodajAukcje extends ServletMain
{
	private static final long serialVersionUID = 1L;

	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	private Aukcja aukcja;
	private Przedmiot przedmiot;

	public DodajAukcje()
	{
		super();
		page_url = "forms/DodajAukcjeForm.html";

	}

	protected boolean authRequired()
	{
		return true;
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

    	request.setCharacterEncoding("UTF-8");
    	this.request = request;
    	this.response = response;
    	html = String.format(this.getHtml(page_url),"","","","");
    	initServlet();
    	
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	request.setCharacterEncoding("UTF-8");
    	this.request = request;
    	this.response = response;
    	zapiszPlik();
    	Aukcja aukcja = getAukcjaFromRequest(request);
		if (aukcja == null)
		{
			mode = 0;
			html += this.getRightHtml();
		}
		else
		{
			System.out.println("mode 2");
			this.zapiszAukcje(aukcja);
			html = this.getRightHtml();
		}
    	initServlet();
    }
	
	/*public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		switch (mode)
		{
			case 1:
				System.out.println("mode 1");
				Aukcja aukcja = getAukcjaFromRequest(request);
				if (aukcja == null)
				{
					mode = 0;
					html += this.getRightHtml();
				}
				else
				{
					System.out.println("mode 2");
					this.zapiszAukcje(aukcja);
					html = this.getRightHtml();
				}
				break;
			case 2:
				html = this.getRightHtml();
				break;

		}
		initServlet();
	}*/

	private void zapiszAukcje(Aukcja aukcja)
	{
		Przedmiot przedmiot = aukcja.getPrzedmiot();
		przedmiot.insertPrzedmiot();
		int id_przedmiotu = przedmiot.getLastId();
		przedmiot.setId(id_przedmiotu);

		aukcja.setIdPrzedmiotu(id_przedmiotu);
		aukcja.setIdUzytkownika(sesja.getIdUzytkownika(this.request));
		java.util.Date date = new java.util.Date();
		Date sqlDate = new Date(date.getTime());
		aukcja.setDataRozpoczecia(sqlDate);

		aukcja.insertAukcja();

	}

	private Aukcja getAukcjaFromRequest(HttpServletRequest request)
	{
		Aukcja aukcja = new Aukcja();

		System.out.println("AAA" + request.getParameter("nazwa_aukcji"));
		if (request.getParameter("nazwa_aukcji").equals(""))
		{
			html = Komunikaty.getError("Wpisz nazwê");
			return null;
		}
		aukcja.setNazwa(request.getParameter("nazwa_aukcji"));

		if (request.getParameter("data_zakonczenia").equals(""))
		{
			html = Komunikaty.getError("Wpisz datê zakoñczenia");
			return null;
		}

		Date data_zakonczenia = Date.valueOf(request.getParameter("data_zakonczenia"));
		aukcja.setDataZakonczenia(data_zakonczenia);

		Przedmiot prz = new Przedmiot();
		if (request.getParameter("nazwa_przedmiotu").equals(""))
		{
			html = Komunikaty.getError("Wpisz nazwê dla przedmiotu");
			return null;
		}
		prz.setNazwa(request.getParameter("nazwa_przedmiotu"));
		prz.setOpis(request.getParameter("opis"));

	
		prz.setZdjecieSrc("");
		aukcja.setPrzedmiot(prz);
		System.out.println(
				aukcja.getId() + " " + aukcja.getIdUzytkownika() + " " + aukcja.getNazwa() + " " + aukcja.getIdPrzedmiotu() + " " + aukcja.getDataRozpoczecia() + " " + aukcja.getDataZakonczenia());
		System.out.println(aukcja.getPrzedmiot().getId() + " " + aukcja.getPrzedmiot().getLastId() + " " + aukcja.getPrzedmiot().getNazwa() + " " + aukcja.getPrzedmiot().getOpis());
		return aukcja;
	}

	private String zapiszPlik()
	{
		String nazwa_pliku = "";
		
		if (!ServletFileUpload.isMultipartContent(request))
//			{
//				PrintWriter writer = response.getWriter();
//				writer.println("Request does not contain upload data");
//				writer.flush();
//				return;
//			}

			// configures upload settings
			System.out.println("case 1");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(THRESHOLD_SIZE);
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(MAX_FILE_SIZE);
			upload.setSizeMax(MAX_REQUEST_SIZE);

			// constructs the directory path to store upload file
			String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
			// creates the directory if it does not exist
			File uploadDir = new File(uploadPath);
			System.out.println(uploadPath);
			if (!uploadDir.exists())
			{
				uploadDir.mkdir();
			}

			try
			{
				// parses the request's content to extract file data
				System.out.println("0");
				List formItems = upload.parseRequest(request);
				System.out.println("1");
				Iterator iter = formItems.iterator();
				System.out.println("2");
				// iterates over form's fields
				//System.out.println(formItems);
				while (iter.hasNext())
				{
					FileItem item = (FileItem) iter.next();
					// processes only fields that are not form fields
					if (!item.isFormField())
					{
						String fileName = new File(item.getName()).getName();
						String filePath = uploadPath + File.separator + fileName;
						File storeFile = new File(filePath);
						
						// saves the file on disk
						item.write(storeFile);
					}
					else{
						
						System.out.println("nie plik!!!");
					}
				}
				// request.setAttribute("message", "Upload has been done
				// successfully!");
			}
			catch (Exception ex)
			{
				System.err.println("blad");
				// request.setAttribute("message", "There was an error: " +
				// ex.getMessage());
			}
			
			
			html = Komunikaty.getSukces("DODANE!");
			
			//getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
		return nazwa_pliku;
	}

	private String getRightHtml()
	{
		String html = "";

		switch (mode)
		{
			case 0:
				html = String.format(this.getHtml(page_url), "", "", "", "");
				break;
			case 1:

				html = "<script>window.location.replace('dodaj_aukcje?mode=2');</script>";
				break;
			case 2:
				html = Komunikaty.getSukces("Twoje og³oszenie aukcyjne zosta³o pomyœlnie zapisane!");
				break;
		}

		return html;
	}

}