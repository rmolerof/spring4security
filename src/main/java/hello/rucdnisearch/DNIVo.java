package hello.rucdnisearch;

import java.util.Date;

import hello.domain.DNIDao;

public class DNIVo {
	private Date date;
	private String dni;
	private String nombre;
	private String paterno;
	private String materno;
	private boolean status;
	
	public DNIVo() {
		super();
	}
	
	public DNIVo(DNIDao dniDao) {
		this.date = new Date(dniDao.getDate().getTime());
		this.dni = new String(dniDao.getDni());
		this.nombre = new String(dniDao.getNombre());
		this.paterno = new String(dniDao.getPaterno());
		this.materno = new String(dniDao.getMaterno());
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
	
	
}