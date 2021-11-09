package servlet;

import java.util.HashMap;

public class Users {
    private final HashMap <String,String> users = new HashMap<>();
    
    
    public Users() {
    	users.put("FRAN","FRANBL");
    	users.put("USER","USER");
    	users.put("123","123");
    	users.put("JORGE","JORGE");
    	users.put("USUARIO","CONTRASEÑA");
    	users.put("PRUEBA","PRUEBA");
    }

    /**
     * 
     * @return hashmap de los usuarios
     */
	public HashMap<String, String> getUsers() {
		return users;
	}
    
}
