package hello.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("email")
public class GlobalProperties {
	
	public String username;
	public String password;
	public String host;
	public String from;
	public String myRuc;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getMyRuc() {
		return myRuc;
	}
	public void setMyRuc(String myRuc) {
		this.myRuc = myRuc;
	}
	
}
