/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.rucdnisearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author jose
 */
public class TestDni {

    public static void main(String[] args) {            
		String Usuario = "10447915125";
		String Password = "xxxxx";
		String Documento = "DNI";
		String Nro_documento = "43664470";
		
		try {
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
			String cadenaJson = "";
			System.out.println("Output from Server:\n");
			while ((output = responseBuffer.readLine()) != null) {
				System.out.println(output);
				cadenaJson = output;
			}

			// BufferedReader reader = request.getReader();
			// Gson gson = new Gson();
			// CpeRespuestaBean CpeRespuesta = gson.fromJson(cadenaJson,
			// CpeRespuestaBean.class);
			// System.out.println(CpeRespuesta.getDes_msj_sunat());

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
