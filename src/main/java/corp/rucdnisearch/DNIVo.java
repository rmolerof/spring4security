package corp.rucdnisearch;

import java.util.Date;

import corp.domain.DNIDao;

public class DNIVo {
	private Date date;
	private String dni;
	private String nombre;
	private String paterno;
	private String materno;
	private String correoElectronico = "";
	private String bonusNumber = "";
	private String direccion = "";
	private boolean status;
	
	public DNIVo() {
		super();
	}
	
	public DNIVo(DNIDao dniDao) {
		this.date = new Date(dniDao.getDate().getTime());
		this.dni = new String(dniDao.getDni());
		this.correoElectronico = new String(dniDao.getCorreoElectronico());
		this.nombre = new String(dniDao.getNombre());
		this.paterno = new String(dniDao.getPaterno());
		this.materno = new String(dniDao.getMaterno());
		this.bonusNumber = new String(dniDao.getBonusNumber());
		this.direccion = new String(dniDao.getDireccion());
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
}
