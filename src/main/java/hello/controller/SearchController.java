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

import hello.businessModel.GasPricesVo;
import hello.businessModel.Station;
import hello.businessModel.TanksVo;
import hello.model.AjaxGetInvoiceResponse;
import hello.model.AjaxGetPricesResponse;
import hello.model.AjaxGetStationResponse;
import hello.model.AjaxGetStockResponse;
import hello.model.AjaxResponseBody;
import hello.model.DayDataCriteria;
import hello.model.InvoiceVo;
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
		
		List<Station> users = userService.findLatestStationStatus(search.getDateEnd(), search.getDateBeg());
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
		
		List<Station> stations = null;
		if(dayDataCriteria.getSaveOrUpdate().equals("save")) {
			stations = userService.submitDayData(dayDataCriteria);
		} else {
			stations = userService.updateLatestDayData(dayDataCriteria);
		}
		
		if(stations.isEmpty()) {
			result.setMsg("No hay datos para la fecha: " + dayDataCriteria.getDate());
		} else {
			result.setMsg("Datos hallados");
		}
		result.setResult(stations);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/getLatestStock")
	public ResponseEntity<?> getLatestStockViaAjax(@Valid @RequestBody SearchDateCriteria search, Errors errors){
		AjaxGetStockResponse result = new AjaxGetStockResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		List<TanksVo> tanksVo = userService.findStockByDates(search.getDateEnd(), search.getDateBeg());
		if(tanksVo.isEmpty()) {
			result.setMsg("No hay stock para la fecha: " + search.getDateEnd());
		} else {
			result.setMsg("Datos hallados");
		}
		result.setResult(tanksVo);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/getLatestPrices")
	public ResponseEntity<?> getLatestPricesViaAjax(@Valid @RequestBody SearchDateCriteria search, Errors errors){
		AjaxGetPricesResponse result = new AjaxGetPricesResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		List<GasPricesVo> gasPricesVo = userService.findPricesByDates(search.getDateEnd(), search.getDateBeg());
		if(gasPricesVo.isEmpty()) {
			result.setMsg("No hay prices para la fecha: " + search.getDateEnd());
		} else {
			result.setMsg("Datos hallados");
		}
		result.setResult(gasPricesVo);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/submitTanksVo")
	public ResponseEntity<?> submitTanksVo(@Valid @RequestBody TanksVo tanksVoCriteria, Errors errors){
		AjaxGetStockResponse result = new AjaxGetStockResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		List<TanksVo> TanksVos = userService.submitTanksVo(tanksVoCriteria, tanksVoCriteria.getSaveOrUpdate());
		if(TanksVos.isEmpty()) {
			result.setMsg("No hay Stock para la fecha: " + tanksVoCriteria.getDate());
		} else {
			result.setMsg("Datos hallados");
		}
		result.setResult(TanksVos);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/submitGasPricesVo")
	public ResponseEntity<?> submitGasPricesVo(@Valid @RequestBody GasPricesVo gasPricesVoCriteria, Errors errors){
		AjaxGetPricesResponse result = new AjaxGetPricesResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		List<GasPricesVo> gasPricesVos = userService.submitGasPricesVo(gasPricesVoCriteria, gasPricesVoCriteria.getSaveOrUpdate());
		if(gasPricesVos.isEmpty()) {
			result.setMsg("No hay Stock para la fecha: " + gasPricesVoCriteria.getDate());
		} else {
			result.setMsg("Datos hallados");
		}
		result.setResult(gasPricesVos);
		
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
	
	@PostMapping("/api/getStationSummaryData")
	public ResponseEntity<?> getStationSummaryData(@Valid @RequestBody SearchDateCriteria search, Errors errors){
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
	
	@PostMapping("/api/submitInvoiceVo")
	public ResponseEntity<?> submitInvoiceVo(@Valid @RequestBody InvoiceVo invoiceVo, Errors errors){
		AjaxGetInvoiceResponse result = new AjaxGetInvoiceResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		List<InvoiceVo> users = userService.submitInvoice(invoiceVo);
		if(users.isEmpty()) {
			result.setMsg("No se pudeo enviar recibo");
		} else {
			result.setMsg("Recibo enviado");
		}
		result.setResult(users);
		
		return ResponseEntity.ok(result);
		
	}
}
