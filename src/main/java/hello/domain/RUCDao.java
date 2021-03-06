package hello.domain;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import hello.rucdnisearch.RUCVo;

@Document(collection = "rucs")
public class RUCDao {
	@Id 
	private ObjectId id;
	@Indexed(unique = true)
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
	private String bonusNumber = "";
	
	public RUCDao() {
		super();
	}
	
	public RUCDao(RUCVo rucVo) {
		this.ruc = new String(rucVo.getRuc());
		this.razonSocial = new String(rucVo.getRazonSocial());
		this.nombreComercial = new String(rucVo.getNombreComercial());
		this.correoElectronico = new String(rucVo.getCorreoElectronico());
		this.tipo = new String(rucVo.getTipo());
		this.estado = new String(rucVo.getEstado());
		this.condicion = new String(rucVo.getCondicion());
		this.direccion = new String(rucVo.getDireccion());
		this.departamento = new String(rucVo.getDepartamento());
		this.provincia = new String(rucVo.getProvincia());
		this.distrito = new String(rucVo.getDistrito());
		this.inscripcion = new String(rucVo.getInscripcion());
		this.actividadExterior = new String(rucVo.getActividadExterior());
		this.direccionS = new String(rucVo.getDireccionS());
		this.date = new Date(rucVo.getDate().getTime());
		this.bonusNumber = new String(rucVo.getBonusNumber());
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
