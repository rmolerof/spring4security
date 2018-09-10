package hello.businessModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hello.domain.GasPricesDao;

public class GasPricesVo {
	
	private String pumpAttendantNames;
	private Date date;
	private List<GasPrice> gasPrices;
	
	public GasPricesVo() {
		super();
	}

	public GasPricesVo(String pumpAttendantNames, Date date, List<GasPrice> gasPrices) {
		super();
		this.pumpAttendantNames = pumpAttendantNames;
		this.date = date;
		this.gasPrices = gasPrices;
	}
	
	public GasPricesVo(GasPricesDao gasPricesDao) {
		this.pumpAttendantNames = new String(null == gasPricesDao.getPumpAttendantNames() ? "": gasPricesDao.getPumpAttendantNames());
		this.date = new Date(gasPricesDao.getDate().getTime());
		this.gasPrices = new ArrayList<GasPrice>(gasPricesDao.getGasPrices());
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<GasPrice> getGasPrices() {
		return gasPrices;
	}

	public void setGasPrices(List<GasPrice> gasPrices) {
		this.gasPrices = gasPrices;
	}

	public String getPumpAttendantNames() {
		return pumpAttendantNames;
	}

	public void setPumpAttendantNames(String pumpAttendantNames) {
		this.pumpAttendantNames = pumpAttendantNames;
	}
	
	

}
