package hello.rucdnisearch;

import java.util.Date;

import hello.domain.RUCDao;

public class RUCVo {
	private String ruc;
	private String razonSocial;
	private String nombreComercial;
	private String correoElectronico = "";
	private String tipo;
	private String estado;
	private String condicion;
	private String direccion;
	private String departamento;
	private String provincia;
	private String distrito;
	private String inscripcion;
	private String actividadExterior;
	private String direccionS;
	private Date date;
	private boolean status;
	
	public RUCVo() {
		super();
	}
	
	public RUCVo(RUCDao rucDao) {
		this.ruc = new String(rucDao.getRuc());
		this.razonSocial = new String(rucDao.getRazonSocial());
		this.nombreComercial = new String(rucDao.getNombreComercial());
		this.correoElectronico = new String(rucDao.getCorreoElectronico());
		this.tipo = new String(rucDao.getTipo());
		this.estado = new String(rucDao.getEstado());
		this.condicion = new String(rucDao.getCondicion());
		this.direccion = new String(rucDao.getDireccion());
		this.departamento = new String(rucDao.getDepartamento());
		this.provincia = new String(rucDao.getProvincia());
		this.distrito = new String(rucDao.getDistrito());
		this.inscripcion = new String(rucDao.getInscripcion());
		this.actividadExterior = new String(rucDao.getActividadExterior());
		this.direccionS = new String(rucDao.getDireccionS());
		this.date = new Date(rucDao.getDate().getTime());
	}

	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCondicion() {
		return condicion;
	}
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public String getInscripcion() {
		return inscripcion;
	}
	public void setInscripcion(String inscripcion) {
		this.inscripcion = inscripcion;
	}
	public String getActividadExterior() {
		return actividadExterior;
	}
	public void setActividadExterior(String actividadExterior) {
		this.actividadExterior = actividadExterior;
	}
	public String getDireccionS() {
		return direccionS;
	}
	public void setDireccionS(String direccionS) {
		this.direccionS = direccionS;
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

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	
}
