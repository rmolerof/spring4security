package corp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@RequestMapping("inventory-control-page")
	public String inventoryControlPage(){
		return "inventory-control-page";
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
	
	@GetMapping("ajax")
	public String ajax(){
		return "ajax";
	}
	
	@GetMapping("reset-status-page")
	public String resetStatusPage(){
		return "reset-status-page";
	}
	
	@GetMapping("dashboard-table-summary-page")
	public String dashboardTableSummaryPage(){
		return "dashboard-table-summary-page";
	}
	
	@GetMapping("invoice-page")
	public String invoicePage(){
		return "invoice-page";
	}
	
	@GetMapping("invoice-table-page")
	public String invoiceTablePage(){
		return "invoice-table-page";
	}
	
	@GetMapping("invoice-viewer-page")
	public String invoiceViewerPage(){
		return "invoice-viewer-page";
	}
	
}
