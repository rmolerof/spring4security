package hello.model;

import java.util.List;

public class User {

    String name;
    String password;
    String email;
    List<String> roles;

    public User() {
    	super();
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
    
    
}
