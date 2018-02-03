package hello.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import hello.model.User;

@Service
public class UserService {

	private List<User> users;
	
	public List<User> findByUserNameOrEmail(String username){
		if(username.equals("current")) {
			// Retrieving actual users
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			return new ArrayList<User>(Arrays.asList(new User(authentication.getName(), "*********", authentication.getAuthorities().toString())));
		} else {
			List<User> result = users.stream().
					filter(x -> x.getUsername().equalsIgnoreCase(username)).
					collect(Collectors.toList());
			return result;
		}
		
	}
	
	@PostConstruct
	private void initDataForTesting() {
		users = new ArrayList<User>();
        
        User user1 = new User("mkyong", "password111", "mkyong@yahoo.com");
        User user2 = new User("yflow", "password222", "yflow@yahoo.com");
        User user3 = new User("laplap", "password333", "mkyong@yahoo.com");
        
        users.add(user1);
        users.add(user2);
        users.add(user3);
	}
}
