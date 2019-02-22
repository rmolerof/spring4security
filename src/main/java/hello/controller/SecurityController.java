package hello.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import hello.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {
 
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String getCurrentUserName(Principal principal) {
        return principal.getName();
    }
    
    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    @ResponseBody
    public User getCurrentUser(Principal principal) {
        
    	User currentUser = new User();
    	currentUser.setName(principal.getName());
    	
    	Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    	List<String> roles = new ArrayList<String>();
    	
    	authorities.stream().map(authority -> {
    		roles.add(authority.getAuthority());
    		return roles;
    	}).collect(Collectors.toList());
    	
    	currentUser.setRoles(roles);
    	
    	return currentUser;
    }
}
