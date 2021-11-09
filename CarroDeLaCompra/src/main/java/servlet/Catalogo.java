package servlet;

import java.util.HashMap;

public class Catalogo {
	private HashMap <String,Double> listaProductos;

	
	public Catalogo() {
		this.listaProductos = new HashMap <>();
		this.listaProductos.put("Sudadera", 25.99);
		this.listaProductos.put("Camiseta", 15.50);
		this.listaProductos.put("Rinonera", 6.99);
		this.listaProductos.put("Vaqueros", 30.25);
		this.listaProductos.put("Gorro", 5.55);
		this.listaProductos.put("Botines", 22.00);
	}


	public HashMap<String,Double> getListaProductos() {
		return listaProductos;
	}
	
}
