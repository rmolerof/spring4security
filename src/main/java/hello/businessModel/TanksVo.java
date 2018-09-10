package hello.businessModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hello.domain.TanksDao;

public class TanksVo {
	private String pumpAttendantNames;
	private Date date;
	private List<Tank> tanks;
	
	public TanksVo() {
		super();
	}
	
	public TanksVo(String pumpAttendantNames, Date date, List<Tank> tanks) {
		this.pumpAttendantNames = pumpAttendantNames;
		this.date = date;
		this.tanks = tanks;
	}
	
	public TanksVo(TanksDao tanksDao) {
		this.pumpAttendantNames = new String(null == tanksDao.getPumpAttendantNames() ? "": tanksDao.getPumpAttendantNames());
		this.date = new Date(tanksDao.getDate().getTime());
		this.tanks = new ArrayList<Tank>(tanksDao.getTanks());
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
		
}
