package hello.controller;

import java.security.Principal;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.model.User;

@Controller
public class SecurityController {
 
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String getCurrentUserName(Principal principal) {
        return principal.getName();
    }
    
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    @ResponseBody
    public User getCurrentUser(Principal principal) {
        
    	User currentUser = new User();
    	currentUser.setName(principal.getName());
    	
    	Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    	
    	Map<String, Boolean> roles = authorities.stream().map(authority -> 
    		new AbstractMap.SimpleEntry<>(authority.getAuthority(), Boolean.TRUE)
    	).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    	
    	currentUser.setRoles(roles);
    	
    	return currentUser;
    }
}
