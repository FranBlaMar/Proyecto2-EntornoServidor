package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Factura
 */
@WebServlet("/Factura")
public class Factura extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Factura() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession sesion = request.getSession();
		
		//Comprobacion de que se ha iniciado sesion
		if (sesion.isNew() || sesion == null || sesion.getAttribute("usuario")==null) {
			response.sendRedirect("/CarroDeLaCompra/HTML/Login.jsp");
		}
		else {
			//Creamos un stringbuilder y le añadimos los datos de los productos que pasamos por la sesion.
			StringBuilder htmlResumen = new StringBuilder(sesion.getAttribute("Resumen").toString());
			
			//Obtenemos el tipo de envio que ha seleccionado el usuario
			String envio = request.getParameter("envio");
			int precioEnvio;
			//Calculamos el iva de la suma de los precios de los productos comprados
			Double totalPagar = (Double) sesion.getAttribute("TotalPagar") * 1.21;
			//Vemos que tipo de envio es y le añadimos el precio correspondiente
			if(envio.equals("Estandar")) {
				precioEnvio = 2;
			}
			else {
				precioEnvio = 5;
			}
			//Calculamos la cantidad total que tiene que pagar el usuario
			Double precioFinalFactura = totalPagar + precioEnvio;
			//Escribimos el html con toda la informacion de la factura
			out.write("<!DOCTYPE html>\n"
					+ "<html lang=\"en\">\n"
					+ "<head>\n"
					+ "    <title> Factura </title>\n"
					+ "<link rel=\"styleSheet\" type=\"text/css\" href=\"css/style.css\">"
					+ "</head>\n"
					+ "<body>\n"
					+ "<div class=\"factura\">"
					+ "<div class= \"datosFactura\">"
					+ "<h1> ¡Gracias por su compra! </h1>"
					+ "<div class=\"cuadradoFactura\">"
					+ htmlResumen
					+ "<p> IVA = 21% </p>"
					+ "<p> Envio "+envio+"  =  "+ precioEnvio +"eu</p>"
					+ "<div class=\"total\">"
					+ "<p> Total a Pagar = "+ Math.round(precioFinalFactura*100.00)/100.00 +"eu</p>"
					+ "</div>"
					+ "<form method=\"GET\" action=\"/CarroDeLaCompra/InvalidarSesion\">"
					+ "<input type=\"submit\" class=\"boton\" value=\"Cerrar Sesion\" />"
					+ "</div>"
					+ "</div>"
					+"</div>"
					+ "</form>"
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
