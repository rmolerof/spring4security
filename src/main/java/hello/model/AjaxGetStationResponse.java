package hello.model;

import java.util.List;

import hello.businessModel.Station;

public class AjaxGetStationResponse {
	
	String msg;
	List<Station> result;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Station> getResult() {
		return result;
	}
	public void setResult(List<Station> result) {
		this.result = result;
	}
	
}
