<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Aukcje</title>
<link href="bootstrap/bootstrap.css" rel="stylesheet" />
<link href="bootstrap/bootstrap-theme.css" rel="stylesheet" />
<link href="jquery/jquery-ui.css" rel="stylesheet" />
<link rel="stylesheet" href="style.css"/>
<script src="jquery/jquery-1.10.2.js"></script>
<script src="bootstrap/bootstrap.js"></script>
<script src="tinymce/js/tinymce/tinymce.min.js"></script>
<script src="js/edytor_tinymce.js"></script>
<script src="jquery/jquery-ui.js"></script>
<script src="modeIncrement.js"></script>
</head>
<body>
	<div class='container'>
		<div class="panel panel-primary">
			<div class="panel-heading jg-panel">
				<div class='col-md-11 col-sm-11 col-xs-11'>
					<h4>
					<span class="glyphicon glyphicon-euro"></span> 
					Aukcje
					</h4>
				</div>
				<%=request.getAttribute("menu")  %>
			</div>
			<div class="panel-body">
				<%=request.getAttribute("html")  %>
			</div>
		</div>
	</div>
</body>
</html>