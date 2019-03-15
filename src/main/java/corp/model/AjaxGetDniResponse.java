package corp.model;

import corp.rucdnisearch.DNIVo;

public class AjaxGetDniResponse {
	
	String msg;
	DNIVo result;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public DNIVo getResult() {
		return result;
	}
	public void setResult(DNIVo result) {
		this.result = result;
	}
	
}
