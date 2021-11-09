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
		HashMap <String,Double> lista = c.getListaProductos();
		StringBuilder htmlResumen = new StringBuilder();
		Set <String> listaKeys = lista.keySet();
		
		
		if (sesion.isNew() || sesion == null || sesion.getAttribute("usuario")==null) {
			response.sendRedirect("/CarroDeLaCompra/HTML/Login.jsp");
		}
		else {
			Double cantidadTotalPagar = 0.0;
			for (String key : listaKeys) {
				String nombreProducto = key;
				Double precioProducto = lista.get(key);
				if(!request.getParameter(nombreProducto).equals("")) {
					sesion.setAttribute("carritoConProductos", true);
					int cantidad = Integer.parseInt(request.getParameter(nombreProducto));
					Double totalProducto = precioProducto * cantidad;
					htmlResumen.append("<p> "+ nombreProducto +"  x  "+ cantidad +"  =  "+ totalProducto +"eu </p>"); 
					cantidadTotalPagar += totalProducto;
				}
			}
			sesion.setAttribute("Resumen", htmlResumen);
			sesion.setAttribute("TotalPagar", cantidadTotalPagar);
			
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
