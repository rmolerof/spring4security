package corp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import corp.businessModel.GasPrice;
import corp.businessModel.GasPricesVo;

@Document(collection = "gasPrices")
public class GasPricesDao {
	
	@Id 
	private ObjectId id;
	private String pumpAttendantNames;
	@Indexed(unique = true)
	private Date date;
	private List<GasPrice> gasPrices;

	public GasPricesDao() {
		super();
	}
	
	public GasPricesDao(ObjectId id, String pumpAttendantNames, Date date, List<GasPrice> gasPrices) {
		super();
		this.pumpAttendantNames = pumpAttendantNames;
		this.id = id;
		this.date = date;
		this.gasPrices = gasPrices;
	}
	
	public GasPricesDao(GasPricesVo gasPricesVo) {
		this.id = new ObjectId();
		this.pumpAttendantNames = new String(gasPricesVo.getPumpAttendantNames());
		this.date = new Date(gasPricesVo.getDate().getTime());
		this.gasPrices = new ArrayList<GasPrice>(gasPricesVo.getGasPrices());
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
