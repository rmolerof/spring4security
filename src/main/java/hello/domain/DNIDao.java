package hello.domain;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import hello.rucdnisearch.DNIVo;

@Document(collection = "dnis")
public class DNIDao {
	@Id 
	private ObjectId id;
	@Indexed(unique = true)
	private Date date;
	private String dni;
	private String nombre;
	private String paterno;
	private String materno;
	private String correoElectronico = "";
	private String bonusNumber = "";
	
	public DNIDao() {
		super();
	}
	
	public DNIDao(DNIVo dniVo) {
		this.date = new Date(dniVo.getDate().getTime());
		this.dni = new String(dniVo.getDni());
		this.correoElectronico = new String(dniVo.getCorreoElectronico());
		this.nombre = new String(dniVo.getNombre());
		this.paterno = new String(dniVo.getPaterno());
		this.materno = new String(dniVo.getMaterno());
		this.bonusNumber = new String(dniVo.getBonusNumber());
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPaterno() {
		return paterno;
	}
	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}
	public String getMaterno() {
		return materno;
	}
	public void setMaterno(String materno) {
		this.materno = materno;
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

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getBonusNumber() {
		return bonusNumber;
	}

	public void setBonusNumber(String bonusNumber) {
		this.bonusNumber = bonusNumber;
	}
	
	
}
