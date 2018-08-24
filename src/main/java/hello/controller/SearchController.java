package hello.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hello.businessModel.Station;
import hello.model.AjaxGetStationResponse;
import hello.model.AjaxResponseBody;
import hello.model.DayDataCriteria;
import hello.model.SearchCriteria;
import hello.model.SearchDateCriteria;
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
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/getStationStatusByDates")
	public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody SearchDateCriteria search, Errors errors){
		AjaxGetStationResponse result = new AjaxGetStationResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		List<Station> users = userService.findStationStatusByDates(search.getDateEnd(), search.getDateBeg());
		if(users.isEmpty()) {
			result.setMsg("No hay datos para la fecha: " + search.getDateEnd());
		} else {
			result.setMsg("Datos hallados");
		}
		result.setResult(users);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/submitDayData")
	public ResponseEntity<?> submitDayData(@Valid @RequestBody DayDataCriteria dayDataCriteria, Errors errors){
		AjaxGetStationResponse result = new AjaxGetStationResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		List<Station> stations = userService.submitDayData(dayDataCriteria);
		if(stations.isEmpty()) {
			result.setMsg("No hay datos para la fecha: " + dayDataCriteria.getDate());
		} else {
			result.setMsg("Datos hallados");
		}
		result.setResult(stations);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/resetStatus")
	public ResponseEntity<?> resetStatus(@Valid @RequestBody DayDataCriteria dayDataCriteria, Errors errors){
		AjaxGetStationResponse result = new AjaxGetStationResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		List<Station> stations = userService.resetStatus(dayDataCriteria);
		if(stations.isEmpty()) {
			result.setMsg("No hay datos para la fecha: " + dayDataCriteria.getDate());
		} else {
			result.setMsg("Datos hallados");
		}
		result.setResult(stations);
		
		return ResponseEntity.ok(result);
		
	}
}
