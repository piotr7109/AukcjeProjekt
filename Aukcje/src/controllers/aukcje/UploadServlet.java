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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import main.Komunikaty;
import main.ServletMain;
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

	public UploadServlet()
	{
		// TODO Auto-generated constructor stub
		super();
		page_url = "forms/upload.html";
	}

	/**
	 * handles file upload via HTTP POST method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

    	request.setCharacterEncoding("UTF-8");
    	this.request = request;
    	this.response = response;
    	System.out.println("aaaaa");
		html = String.format(this.getHtml(page_url));
		System.out.println("bbbbb");
    	initServlet();
    	
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	request.setCharacterEncoding("UTF-8");
    	this.request = request;
    	this.response = response;
    	
    	
    	
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
			initServlet();
			
    }
	
	
	
	
}