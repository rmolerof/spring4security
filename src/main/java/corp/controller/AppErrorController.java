package corp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class AppErrorController implements ErrorController {

	private final static String ERROR_PATH = "/error";
	private boolean includeStackTrace = false; 
	
	@Autowired
    private ErrorAttributes errorAttributes;
	
/*	@RequestMapping(value = ERROR_PATH)
	public String error() {
		return "Error handling";
	}*/
	
	@RequestMapping(value = ERROR_PATH)
    ErrorJson error(HttpServletRequest request, HttpServletResponse response) {
	    // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring. 
	    // Here we just define response body.
		// change false to 
		
	    return new ErrorJson(response.getStatus(), getErrorAttributes(request, includeStackTrace));
    }
	
	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}
