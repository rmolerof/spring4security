package webbonusgx;

public class BonusPointsOperationResponse {
    
	private String accumulatedPoints;
    private  String responseFlag;
    
	public BonusPointsOperationResponse(String accumulatedPoints, String responseFlag) {
		super();
		this.accumulatedPoints = accumulatedPoints;
		this.responseFlag = responseFlag;
	}
	
	public String getAccumulatedPoints() {
		return accumulatedPoints;
	}
	public void setAccumulatedPoints(String accumulatedPoints) {
		this.accumulatedPoints = accumulatedPoints;
	}
	public String getResponseFlag() {
		return responseFlag;
	}
	public void setResponseFlag(String responseFlag) {
		this.responseFlag = responseFlag;
	}

}
