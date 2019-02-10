package hello.controller;

import java.util.Arrays;
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
import hello.model.AjaxEmailInvoiceResponse;
import hello.model.AjaxGetDniResponse;
import hello.model.AjaxGetInvoiceResponse;
import hello.model.AjaxGetInvoicesResponse;
import hello.model.AjaxGetPricesResponse;
import hello.model.AjaxGetRucResponse;
import hello.model.AjaxGetStationResponse;
import hello.model.AjaxGetStockResponse;
import hello.model.AjaxResponseBody;
import hello.model.DayDataCriteria;
import hello.model.InvoiceVo;
import hello.model.SearchCriteria;
import hello.model.SearchDateCriteria;
import hello.model.SearchDocIdCriteria;
import hello.model.SearchInvoiceCriteria;
import hello.model.SubmitInvoiceGroupCriteria;
import hello.model.User;
import hello.rucdnisearch.DNIVo;
import hello.rucdnisearch.RUCVo;
import hello.services.RucDniService;
import hello.services.UserService;
import hello.services.Utils;

@RestController
public class SearchController {

	UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	RucDniService rucDniService;
	@Autowired
	Utils utils;
	
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
		
		List<Station> users = userService.findLatestStationStatus(search.getDateEnd(), search.getDateBeg(), search.getBackDataCount());
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
			result.setMsg("No hay precios de productos en base de datos");
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
		Station station = userService.updateTanksToStation(tanksVoCriteria);
		
		if(null != station) {
			result.setMsg("Se actualizó Stock para la fecha " + tanksVoCriteria.getDate());
		} else {
			result.setMsg("Actualización de Stock falló");
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
		userService.updateGasPricesToStation(gasPricesVoCriteria);
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
		
		List<InvoiceVo> invoiceVos = userService.submitInvoice(invoiceVo);
		if(invoiceVos.isEmpty()) {
			result.setMsg("No se pudo remitir comprobante: " + invoiceVo);
		} else {
			result.setMsg("Comprobante remitido: " + invoiceVos.get(0).getInvoiceNumber());
		}
		result.setResult(invoiceVos);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/submitInvoicesToSunat")
	public ResponseEntity<?> submitInvoicesToSunat(@Valid @RequestBody SubmitInvoiceGroupCriteria submitInvoiceGroupCriteria, Errors errors){
		AjaxGetInvoiceResponse result = new AjaxGetInvoiceResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		List<InvoiceVo> invoiceVos = userService.submitInvoicesToSunat(submitInvoiceGroupCriteria.getProcessingType());
		
		if(invoiceVos.isEmpty()) {
			result.setMsg("No hay comprobantes para enviar a SUNAT.");
		} else {
			result.setMsg("Comprobante remitido: " + invoiceVos.get(0).getInvoiceNumber());
		}
		result.setResult(invoiceVos);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/getInvoicesSummaryData")
	public ResponseEntity<?> getInvoicesSummaryData(@Valid @RequestBody SearchDateCriteria search, Errors errors){
		AjaxGetInvoicesResponse result = new AjaxGetInvoicesResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		List<InvoiceVo> users = userService.findInvoicesSummaryData(search.getDateEnd(), search.getDateBeg());
		if(users.isEmpty()) {
			result.setMsg("No hay datos para la fecha: " + search.getDateEnd());
		} else {
			result.setMsg("Datos hallados");
		}
		result.setResult(users);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/findInvoice")
	public ResponseEntity<?> findInvoice(@Valid @RequestBody SearchInvoiceCriteria search, Errors errors){
		AjaxGetInvoicesResponse result = new AjaxGetInvoicesResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		List<InvoiceVo> users = userService.findInvoice(search.getInvoiceNumber());
		if(users.isEmpty()) {
			result.setMsg("No hay datos para la recibo Nro: " + search.getInvoiceNumber());
		} else {
			result.setMsg("Datos hallados");
		}
		result.setResult(users);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/deleteInvoice")
	public ResponseEntity<?> deleteInvoice(@Valid @RequestBody SearchInvoiceCriteria search, Errors errors){
		AjaxGetInvoicesResponse result = new AjaxGetInvoicesResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		String status = userService.deleteInvoice(search.getInvoiceNumber());
		if(status.equals("0")) {
			result.setMsg("No se pudo borrar recibo Nro " + search.getInvoiceNumber());
		} else {
			result.setMsg("Comprobante " + search.getInvoiceNumber() + " borrado");
		}
		result.setResult(null);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/findRuc")
	public ResponseEntity<?> findRuc(@Valid @RequestBody SearchDocIdCriteria search, Errors errors){
		AjaxGetRucResponse result = new AjaxGetRucResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		RUCVo users = rucDniService.findRUCDetails(search.getDocId());
		if(!users.isStatus()) {
			result.setMsg("No hay datos para la RUC Nro: " + search.getDocId());
		} else {
			result.setMsg("Datos hallados");
		}
		result.setResult(users);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/findDni")
	public ResponseEntity<?> findDni(@Valid @RequestBody SearchDocIdCriteria search, Errors errors){
		AjaxGetDniResponse result = new AjaxGetDniResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		DNIVo users = rucDniService.findDNIDetails(search.getDocId());
		if(!users.isStatus()) {
			result.setMsg("No hay datos para la DNI Nro: " + search.getDocId());
		} else {
			result.setMsg("Datos hallados");
		}
		result.setResult(users);
		
		return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/api/emailInvoice")
	public ResponseEntity<?> emailInvoice(@Valid @RequestBody SearchInvoiceCriteria search, Errors errors){
		AjaxEmailInvoiceResponse result = new AjaxEmailInvoiceResponse();
		
		if(errors.hasErrors()) {
			result.setMsg(errors.getAllErrors().stream().map(x->x.getDefaultMessage())
					.collect(Collectors.joining(",")));
		
			return ResponseEntity.badRequest().body(result);
		}
		
		// Generate XML
		String xmlPath = utils.generateXMLFromDB(search.getInvoiceNumber());
		
		// Generate PDF
		String pdfPath = utils.generateReport(search.getInvoiceNumber());
		
		// Save email
		utils.saveCustomerEmail(search.getClientEmailAddress(), search.getClientDocNumber(), search.getClientDocType());
		
		String to = search.getClientEmailAddress().toLowerCase();
		String from = "support@grifoslajoya.net";
		String subject = "GRIFO LA JOYA DE SANTA ISABEL E.I.R.L - " + search.getSelectedOption().toUpperCase() + ": " + search.getInvoiceNumber();
		String body = "Estimado Cliente. Buen día. Adjuntado está su comprobante: " + search.getSelectedOption().toUpperCase() + ": " + search.getInvoiceNumber();
		List<String> attachmentPaths = Arrays.asList(new String(xmlPath), new String(pdfPath));
		
		String response = utils.sendEmail(to, from, subject, body, attachmentPaths);
		
		if(response.equals("0")) {
			result.setMsg("No se pudo enviar email para comprobante: " + search.getInvoiceNumber());
		} else {
			result.setMsg("Email enviado para comprobante: " + search.getInvoiceNumber());
		}
		result.setResult(response);
		
		return ResponseEntity.ok(result);
		
	}
	
}
