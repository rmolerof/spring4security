package corp.businessModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import corp.domain.GasPricesDao;

public class GasPricesVo {
	
	private String pumpAttendantNames;
	private Date date;
	private List<GasPrice> gasPrices;
	private String saveOrUpdate;
	
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
		this.pumpAttendantNames = new String(null == gasPricesDao ? "": gasPricesDao.getPumpAttendantNames());
		this.date = null == gasPricesDao ? new Date(): new Date(gasPricesDao.getDate().getTime());
		this.gasPrices = new ArrayList<GasPrice>(null == gasPricesDao ? Arrays.asList(new GasPrice("d2", 0D, 0D), new GasPrice("g90", 0D, 0D), new GasPrice("g95", 0D, 0D)) : gasPricesDao.getGasPrices());
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

	public String getSaveOrUpdate() {
		return saveOrUpdate;
	}

	public void setSaveOrUpdate(String saveOrUpdate) {
		this.saveOrUpdate = saveOrUpdate;
	}
	
	

}
