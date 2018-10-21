package hello.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	
	@RequestMapping("/")
	public String home0(){
		return "home";
	}
	
	@RequestMapping("home")
	public String home1(){
		return "home";
	}
	
	@RequestMapping("admin")
	public String admin(){
		return "admin";
	}
	
	@RequestMapping("user")
	public String user(){
		return "user";
	}
	
	@RequestMapping("about")
	public String about(){
		return "about";
	}
	
	@RequestMapping("login")
    public String login() {
        return "login";
    }
	
	@RequestMapping("403")
	public String error403(){
		return "error/403";
	}
	
}
