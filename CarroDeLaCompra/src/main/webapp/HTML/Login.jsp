<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
		<title>Log In</title>
		<link rel="styleSheet" type="text/css" href="../css/style.css">
	</head>
	<body>
	<div class = "form">
	
		<form action= "/CarroDeLaCompra/catalogo" method="POST" id="formulario">
            <label for="usuario"> Usuario: </label><input type="text" name="usuario" id="usuario">
            <label for="contraseña"> Contraseña: </label><input type="text" name="contrasena" id="contrasena">
            <input type="submit" class="boton" value="Iniciar Sesión"> 
		</form>
		</div>
		
		<% if(session.getAttribute("errorLog") == "true") {%>
			<p id="error">Error, usuario o contraseña erroneos</p>
		<%}%>
	</body>
</html>