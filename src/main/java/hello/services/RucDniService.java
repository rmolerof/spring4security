package hello.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.domain.DNIDao;
import hello.domain.DNIsRepository;
import hello.domain.RUCDao;
import hello.domain.RUCsRepository;
import hello.rucdnisearch.DNIVo;
import hello.rucdnisearch.RUCVo;

@Service
public class RucDniService {
	
	@Autowired
    private RUCsRepository rucsRepository;
	@Autowired
    private DNIsRepository dnisRepository;
	
	public RUCVo findRUCDetails(String ruc) {
		
		RUCVo rucVo = null;
		try {
			// search RUC in DB
			RUCDao rucDao = rucsRepository.findFirstByRuc(ruc);
			
			// if not Found, search in Sunat
			if (null == rucDao) {
				rucVo = getRUCWS(ruc);
				rucVo.setStatus(true);
				rucsRepository.save(new RUCDao(rucVo));
				return rucVo;
			}
			
			rucVo = new RUCVo(rucDao);
			rucVo.setStatus(true);
			return rucVo;
		} catch (Exception e) {
			e.printStackTrace();
			rucVo = new RUCVo();
			rucVo.setStatus(false);
			return rucVo;
		}
	}
	
	public DNIVo findDNIDetails(String dni) {
		
		DNIVo dniVo = null;
		try {
			// search DNI in DB
			DNIDao dniDao = dnisRepository.findFirstByDni(dni);
			
			// if not Found, search in Sunat
			if (null == dniDao || dniDao.getNombre().contains("EXPIRADO")) {
				if (null != dniDao && dniDao.getNombre().contains("EXPIRADO")) {
					dnisRepository.delete(dniDao);
				}
				
				dniVo = getDNIWS(dni);
				dniVo.setStatus(true);
				dnisRepository.save(new DNIDao(dniVo));
				return dniVo;
			}
			
			dniVo = new DNIVo(dniDao); 
			dniVo.setStatus(true);
			return dniVo;
		} catch (Exception e) {
			e.printStackTrace();
			dniVo = new DNIVo();
			dniVo.setStatus(false);
			return dniVo;
		}
	}
	
	public DNIVo getDNIWS(String dni) throws Exception {
		DNIVo dniVo = null;
		String Usuario = "20501568776";
		String Password = "ruc1234";
		String Documento = "DNI";
		String Nro_documento = dni;
		
		URL targetUrl = new URL(
				"https://www.facturacionelectronica.us/facturacion/controller/ws_consulta_rucdni_v2.php?usuario="
						+ Usuario + "&password=" + Password + "&documento=" + Documento + "&nro_documento="
						+ Nro_documento);
		HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
		httpConnection.setDoOutput(true);
		httpConnection.setRequestMethod("POST");
		httpConnection.setRequestProperty("Content-Type", "application/json");
		httpConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
		String input = "{}";// gson.toJson(Login);
		OutputStream outputStream = httpConnection.getOutputStream();
		outputStream.write(input.getBytes());
		outputStream.flush();
		if (httpConnection.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
		}

		BufferedReader responseBuffer = new BufferedReader(
				new InputStreamReader((httpConnection.getInputStream())));
		String output;
		StringBuilder cadenaJson = new StringBuilder("");
		
		while ((output = responseBuffer.readLine()) != null) {
			cadenaJson.append(output);
		}
		
		dniVo = new DNIVo();
		JSONObject obj = new JSONObject(cadenaJson.toString());
		dniVo.setDate(new Date());
		dniVo.setDni(dni);
		dniVo.setNombre(obj.getJSONObject("result").getString("Nombre"));
		dniVo.setPaterno(obj.getJSONObject("result").getString("Paterno"));
		dniVo.setMaterno(obj.getJSONObject("result").getString("Materno"));
		httpConnection.disconnect();
		
		return dniVo;
	}
	
	public RUCVo getRUCWS(String ruc) throws Exception {
		
		RUCVo rucVo = null;
		String Usuario = "20501568776";
		String Password = "ruc1234";
		String Documento = "RUC";
		String Nro_documento = ruc; 
		
		// Backup: https://api.sunat.cloud/ruc/" + %ruc_number%
		URL targetUrl = new URL(
				"https://www.facturacionelectronica.us/facturacion/controller/ws_consulta_rucdni_v2.php?usuario="
						+ Usuario + "&password=" + Password + "&documento=" + Documento + "&nro_documento="
						+ Nro_documento);
		HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
		httpConnection.setDoOutput(true);
		httpConnection.setRequestMethod("POST");
		httpConnection.setRequestProperty("Content-Type", "application/json");
		httpConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
		String input = "{}";
		OutputStream outputStream = httpConnection.getOutputStream();
		outputStream.write(input.getBytes());
		outputStream.flush();
		if (httpConnection.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
		}

		BufferedReader responseBuffer = new BufferedReader(
				new InputStreamReader((httpConnection.getInputStream())));
		String output;
		StringBuilder cadenaJson = new StringBuilder("");
		
		while ((output = responseBuffer.readLine()) != null) {
			cadenaJson.append(output);
		}
		
		rucVo = new RUCVo();
		JSONObject obj = new JSONObject(cadenaJson.toString());
		rucVo.setRuc(obj.getJSONObject("result").getString("RUC"));
		rucVo.setRazonSocial(obj.getJSONObject("result").getString("RazonSocial"));
		rucVo.setNombreComercial(obj.getJSONObject("result").getString("NombreComercial"));
		rucVo.setTipo(obj.getJSONObject("result").getString("Tipo"));
		rucVo.setEstado(obj.getJSONObject("result").getString("Estado"));
		rucVo.setCondicion(obj.getJSONObject("result").getString("Condicion"));
		rucVo.setDireccion(obj.getJSONObject("result").getString("Direccion").trim().replaceAll(" +", " "));
		rucVo.setDepartamento(true == obj.getJSONObject("result").isNull("departamento") ? "": obj.getJSONObject("result").getString("departamento"));
		rucVo.setProvincia(true == obj.getJSONObject("result").isNull("provincia") ? "": obj.getJSONObject("result").getString("provincia"));
		rucVo.setDistrito(true == obj.getJSONObject("result").isNull("distrito") ? "": obj.getJSONObject("result").getString("distrito"));
		rucVo.setInscripcion(true == obj.getJSONObject("result").isNull("Inscripcion") ? "": obj.getJSONObject("result").getString("Inscripcion"));
		rucVo.setActividadExterior(true == obj.getJSONObject("result").isNull("ActividadExterior") ? "": obj.getJSONObject("result").getString("ActividadExterior"));
		rucVo.setDireccionS(true == obj.getJSONObject("result").isNull("direccion") ? "": obj.getJSONObject("result").getString("direccion"));
		rucVo.setDate(new Date());
		rucVo.setStatus(true);
		
		httpConnection.disconnect();
		
		return rucVo;
	}
}
