package hello.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hello.model.AjaxResponseBody;
import hello.model.SearchCriteria;
import hello.model.User;
import hello.services.UserService;

@RestController
public class SearchController {

	UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/api/search")
	public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody SearchCriteria search, Errors errors){
		AjaxResponseBody result = new AjaxResponseBody();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		List<User> users = userService.findByUserNameOrEmail(search.getUsername());
		if(users.isEmpty()) {
			result.setMsg("no user found");
		} else {
			result.setMsg("success");
		}
		result.setResult(users);
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(result);
		
	}
}
