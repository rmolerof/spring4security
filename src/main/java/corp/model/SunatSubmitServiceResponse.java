package corp.model;

public class SunatSubmitServiceResponse {
	private String responseCode = "";
	private String connectivityStatus = "";
	private String responseDetailMsg = "";
	private String hashCdr = "";
	private String hashCpe = "";

	public SunatSubmitServiceResponse(String sunatSubmitServiceResponseMsg) {
		super();
		
		if (sunatSubmitServiceResponseMsg.length() > 1) {
			String[] sunatSubmitServiceResponseArray = sunatSubmitServiceResponseMsg.split("\\|");
			
			for (int responseIndex = 0; responseIndex < sunatSubmitServiceResponseArray.length; responseIndex++) {
				
				switch (responseIndex) {
					case 0: 
						this.responseCode = sunatSubmitServiceResponseArray[0];
						break;
					case 1:
						this.connectivityStatus = sunatSubmitServiceResponseArray[1];
						break;
					case 2:
						this.responseDetailMsg = sunatSubmitServiceResponseArray[2];
						break;
					case 3:
						this.hashCdr = sunatSubmitServiceResponseArray[3];
						break;
					case 4:
						this.hashCpe = sunatSubmitServiceResponseArray[4];
						break;	
				}
			}
		}
		
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getConnectivityStatus() {
		return connectivityStatus;
	}

	public void setConnectivityStatus(String connectivityStatus) {
		this.connectivityStatus = connectivityStatus;
	}

	public String getResponseDetailMsg() {
		return responseDetailMsg;
	}

	public void setResponseDetailMsg(String responseDetailMsg) {
		this.responseDetailMsg = responseDetailMsg;
	}

	public String getHashCdr() {
		return hashCdr;
	}

	public void setHashCdr(String hashCdr) {
		this.hashCdr = hashCdr;
	}

	public String getHashCpe() {
		return hashCpe;
	}

	public void setHashCpe(String hashCpe) {
		this.hashCpe = hashCpe;
	}

	@Override
	public String toString() {
		return "SunatSubmitServiceResponse [responseCode=" + responseCode + ", connectivityStatus=" + connectivityStatus
				+ ", responseDetailMsg=" + responseDetailMsg + ", hashCdr=" + hashCdr + ", hashCpe=" + hashCpe + "]";
	}
	
}
