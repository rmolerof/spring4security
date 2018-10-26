package hello.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import hello.businessModel.Tank;
import hello.businessModel.TanksVo;

@Document(collection = "tanks")
public class TanksDao {
	
	@Id 
	private ObjectId id;
	private String pumpAttendantNames;
	@Indexed(unique = true)
	private Date date;
	private List<Tank> tanks;
	public String supplierRUC;
	public String truckDriverName;
	public String truckPlateNumber;
	public boolean delivery;

	public TanksDao() {
		super();
	}
	
	public TanksDao(ObjectId id, String pumpAttendantNames, Date date, List<Tank> tanks) {
		super();
		this.id = id;
		this.pumpAttendantNames = pumpAttendantNames;
		this.date = date;
		this.tanks = tanks;
	}
	
	public TanksDao(TanksVo tanksVo) {
		this.id = new ObjectId();
		this.pumpAttendantNames = new String(tanksVo.getPumpAttendantNames());
		this.date = new Date(tanksVo.getDate().getTime());
		this.tanks = new ArrayList<Tank>(tanksVo.getTanks());
		this.supplierRUC = new String(tanksVo.getSupplierRUC());
		this.truckDriverName = new String(tanksVo.getTruckDriverName());
		this.truckPlateNumber = new String(tanksVo.getTruckPlateNumber());
		this.delivery = tanksVo.isDelivery();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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
	
}
