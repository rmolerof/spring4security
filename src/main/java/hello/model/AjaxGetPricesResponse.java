package hello.model;

import java.util.List;

import hello.businessModel.GasPricesVo;

public class AjaxGetPricesResponse {
	
	String msg;
	List<GasPricesVo> result;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<GasPricesVo> getResult() {
		return result;
	}
	public void setResult(List<GasPricesVo> result) {
		this.result = result;
	}
	
}
