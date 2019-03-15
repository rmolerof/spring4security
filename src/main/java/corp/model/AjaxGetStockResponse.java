package corp.model;

import java.util.List;

import corp.businessModel.TanksVo;

public class AjaxGetStockResponse {
	
	String msg;
	List<TanksVo> result;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<TanksVo> getResult() {
		return result;
	}
	public void setResult(List<TanksVo> result) {
		this.result = result;
	}
	
}
