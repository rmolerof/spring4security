package hello.model;

import java.util.List;

public class AjaxGetInvoicesResponse {
	
	String msg;
	List<InvoiceVo> result;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<InvoiceVo> getResult() {
		return result;
	}
	public void setResult(List<InvoiceVo> result) {
		this.result = result;
	}
	
}
