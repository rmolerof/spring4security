package corp.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class Domain {
	
	@Id 
	private ObjectId id;
	
	@Indexed(unique = true)
	private String domain;
	
	private boolean displayAds;
	 
	private String first_name;
	
	private String last_name;
	
	@Override
	public String toString() {
		return "Domain [id=" + id + ", domain=" + domain + ", displayAds=" + displayAds + ", first_name=" + first_name
				+ ", last_name=" + last_name + "]";
	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
    public boolean isDisplayAds() {
        return displayAds;
    }

    public void setDisplayAds(boolean displayAds) {
        this.displayAds = displayAds;
    }

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

}
