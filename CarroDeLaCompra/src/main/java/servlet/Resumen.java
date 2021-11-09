package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Resumen
 */
@WebServlet("/Resumen")
public class Resumen extends HttpServlet { 
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Resumen() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession sesion = request.getSession();
		
		//Creamos el objeto catalogo
		Catalogo c =  new Catalogo();	
		//Recuperamos el array de productos del catalogo y creamos un stringbuilder para guardar el texto que vamos a mostrar en el html
		HashMap <String,Double> lista = c.getListaProductos();
		StringBuilder htmlResumen = new StringBuilder();
		
		//Obtenemos el set de keys del hasmap
		Set <String> listaKeys = lista.keySet();
		
		
		//Comprobacion de que se ha iniciado sesion
		if (sesion.isNew() || sesion == null || sesion.getAttribute("usuario")==null) {
			response.sendRedirect("/CarroDeLaCompra/HTML/Login.jsp");
		}
		else {
			//Creamos la variable donde vamos a ir sumando los precios de los productos comprados
			Double cantidadTotalPagar = 0.0;
			//Recorremos todas las keys del hasmap, que son los nombres de los productos
			for (String key : listaKeys) {
				String nombreProducto = key;
				Double precioProducto = lista.get(key);
				//Si el usuario no ha introducido un valor en los campos de cantidad, significa que no quiere comprar ese producto, por lo cual ignoramos el producto
				//y no se añade al resumen
				if(!request.getParameter(nombreProducto).equals("")) {
					//Obtenemos la cantidad del producto que desea comprar
					int cantidad = Integer.parseInt(request.getParameter(nombreProducto));
					//Calculamos el precio total a pagar por ese producto y su cantidad
					Double totalProducto = precioProducto * cantidad;
					//añadimos los datos al stringBuilder que escribiremos mas adelante
					htmlResumen.append("<p> "+ nombreProducto +"  x  "+ cantidad +"  =  "+ totalProducto +"eu </p>");
					//sumanos la cantidad a pagar de ese producto a la vriable donde calculamos el total a pagar por todos los productos
					cantidadTotalPagar += totalProducto;
				}
			}
			//Pasamos por sesion el stringbuilder con los datos de los productos que va a comprar
			sesion.setAttribute("Resumen", htmlResumen);
			//Pasamos por la sesion la cantidad total que tendra que pagar el usurio por los productos que ha comprado
			sesion.setAttribute("TotalPagar", cantidadTotalPagar);
			
			//Escribimos la informacion del resumen
			out.write("<!DOCTYPE html>\n"
				+ "<html lang=\"en\">\n"
				+ "<head>\n"
				+ "    <title>Resumen</title>\n"
				+ "<link rel=\"styleSheet\" type=\"text/css\" href=\"css/style.css\">"
				+ "</head>\n"
				+ "<body>\n"
				+ "<div class=\"resumen\">"
				+ "<div class=\"envio\">"
				+ "<h1> Resumen Pedido </h1>"
				+ htmlResumen
				+ "<form action=\"/CarroDeLaCompra/Factura\" method=\"GET\" id=\"formularioResumen\">"
				+ "<div>"
				+ "<label for=\"envioEstandar\"> Envio Estandar ---- 2eu</label>"
				+ "<input type=\"radio\" name=\"envio\" value=\"Estandar\"checked>"
				+ "<label for=\"envioEstandar\"> Envio Express ---- 5eu</label>"
				+ "<input type=\"radio\" name=\"envio\" value=\"Express\">"
				+ "</div>"
				+ "<input type=\"submit\" class=\"boton\" value=\"Confirmar Compra\"/>"
				+ "</form>"
				+ "</div>"
				+ "</div>"
				+ "</body>\n"
				+ "</html>");
		}
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
