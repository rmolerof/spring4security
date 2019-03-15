package corp.businessModel;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import corp.domain.TanksDao;

public class TanksVo {
	private String pumpAttendantNames;
	private Date date;
	private String shiftDate;
	private List<Tank> tanks;
	private String saveOrUpdate;
	public String supplierRUC;
	public String truckDriverName;
	public String truckPlateNumber;
	public boolean delivery;
	
	public TanksVo() {
		super();
	}
	
	public TanksVo(String pumpAttendantNames, Date date, List<Tank> tanks) {
		this.pumpAttendantNames = pumpAttendantNames;
		this.date = date;
		this.tanks = tanks;
	}

	public TanksVo(TanksDao tanksDao) {
		this.pumpAttendantNames = new String(null == tanksDao ? "": tanksDao.getPumpAttendantNames());
		this.date = null == tanksDao ? new Date(): new Date(tanksDao.getDate().getTime());
		this.tanks = new LinkedList<Tank>(null == tanksDao ? Arrays.asList(new Tank(1L, "d2", 0D, 0D), new Tank(2L, "g90", 0D, 0D), new Tank(3L, "g95", 0D, 0D)): tanksDao.getTanks());
		this.supplierRUC = new String(null == tanksDao || null == tanksDao.getSupplierRUC() ? "": tanksDao.getSupplierRUC());
		this.truckDriverName = new String(null == tanksDao || null == tanksDao.getTruckDriverName() ? "": tanksDao.getTruckDriverName());
		this.truckPlateNumber = new String(null == tanksDao || null == tanksDao.getTruckPlateNumber() ? "": tanksDao.getTruckPlateNumber());
		this.delivery = null == tanksDao ? true: tanksDao.isDelivery();
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Tank> getTanks() {
		return tanks;
	}
	public void setTanks(List<Tank> tanks) {
		this.tanks = tanks;
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

	public String getSupplierRUC() {
		return supplierRUC;
	}

	public void setSupplierRUC(String supplierRUC) {
		this.supplierRUC = supplierRUC;
	}

	public String getTruckDriverName() {
		return truckDriverName;
	}

	public void setTruckDriverName(String truckDriverName) {
		this.truckDriverName = truckDriverName;
	}

	public String getTruckPlateNumber() {
		return truckPlateNumber;
	}

	public void setTruckPlateNumber(String truckPlateNumber) {
		this.truckPlateNumber = truckPlateNumber;
	}

	public boolean isDelivery() {
		return delivery;
	}

	public void setDelivery(boolean delivery) {
		this.delivery = delivery;
	}

	public String getShiftDate() {
		return shiftDate;
	}

	public void setShiftDate(String shiftDate) {
		this.shiftDate = shiftDate;
	}

}
