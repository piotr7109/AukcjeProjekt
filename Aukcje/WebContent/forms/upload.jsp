<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Upload</title>
</head>
<body>
		
		
		
        <form method="post" enctype="multipart/form-data" novalidate>
        <label class="control-label col-sm-4" for="nazwa_przedmiotu">Nazwa przedmiotu:</label>
			
				<input type="text" class="form-control" placeholder="Nazwa przedmiotu" name="nazwa_przedmiotu" value="asde" required="">
			
      
        <input type="hidden" value="0" name="mode" id="mode">
            Select file to upload: <input type="file" name="uploadFile" />
            <br/><br/>
            <input type="submit" value="Upload" />
           
        </form>

</body>
</html>