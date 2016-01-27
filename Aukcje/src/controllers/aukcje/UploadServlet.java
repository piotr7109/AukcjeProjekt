package controllers.aukcje;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import main.Komunikaty;
import main.ServletMain;
import modules.przedmioty.Przedmiot;
import modules.przedmioty.PrzedmiotFactory;

@MultipartConfig
/**
 * A Java servlet that handles file upload from client.
 * 
 * @author www.codejava.net
 */

public class UploadServlet extends ServletMain
{
	private static final long serialVersionUID = 1L;

	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	private int id_aukcji;

	public UploadServlet()
	{
		// TODO Auto-generated constructor stub
		super();
		page_url = "forms/upload.html";
	}

	/**
	 * handles file upload via HTTP POST method
	 */
	@Override
	protected boolean authRequired()
	{
		return true;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		id_aukcji = (int) session.getAttribute("id_aukcji");

		request.setCharacterEncoding("UTF-8");
		this.request = request;
		this.response = response;
		html = String.format(this.getHtml(page_url));
		initServlet();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		id_aukcji = (int) session.getAttribute("id_aukcji");
		
		request.setCharacterEncoding("UTF-8");
		this.request = request;
		this.response = response;
		PrzedmiotFactory p_factory = new PrzedmiotFactory();
		Przedmiot przedmiot = p_factory.getLastInserted();


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
			// System.out.println(formItems);
			while (iter.hasNext())
			{
				FileItem item = (FileItem) iter.next();
				// processes only fields that are not form fields
				if (!item.isFormField())
				{
					String fileName = new File(item.getName()).getName();
					String destFileName = setNazwa(fileName);
					String filePath = uploadPath + File.separator + destFileName;
					File storeFile = new File(filePath);
					if(destFileName!=null)
					{
						przedmiot.setZdjecieSrc("upload/" + destFileName);
						item.write(storeFile);
					}
					else
					przedmiot.setZdjecieSrc("upload/Yoda.png");
					System.out.println(przedmiot.getZdjecieSrc());
					System.out.println("ID przedmiotu to: " + przedmiot.getId());
					// saves the file on disk
					
				}
				else
				{

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

		zapiszPrzedmiot(przedmiot);
		html = Komunikaty.getSukces("Aukcja zosta³a pomyœlnie dodana <br><a href='podglad_aukcji?id_aukcji='");

		// getServletContext().getRequestDispatcher("/message.jsp").forward(request,
		// response);
		initServlet();

	}

	public void zapiszPrzedmiot(Przedmiot p)
	{
		p.updatePrzedmiot();
	}

	private String setNazwa(String input)
	{
		String ext = FilenameUtils.getExtension(input);
		System.out.println("EXTENSION : " + ext);
		if (sprawdzRozszerzenie(ext))
		{
			double a = Math.random() * 100000;
			String output = String.format("%05d", (int) a);
			output += "." + ext;
			return output;
		}
		else
		{
			return null;
		}
	}

	private boolean sprawdzRozszerzenie(String extension)
	{
		if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("bmp"))
			return true;
		else
			return false;
	}

}