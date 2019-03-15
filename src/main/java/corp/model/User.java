package corp.model;

import java.util.Map;

public class User {

    String name;
    String password;
    String email;
    Map<String, Boolean> roles;

    public User() {
    	super();
    }
    
    public User(String name, Map<String, Boolean> roles) {
    	this.name = name;
    	this.roles = roles;
    }
    
    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, Boolean> getRoles() {
		return roles;
	}

	public void setRoles(Map<String, Boolean> roles) {
		this.roles = roles;
	}
    
}
