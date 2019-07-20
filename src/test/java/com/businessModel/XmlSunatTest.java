package com.businessModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.MarshalException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.bean.CpeBean;
import com.bean.Cpe_DetalleBean;

import apiclienteenviosunat.ApiClienteEnvioSunat;
import corp.Application;
import corp.domain.InvoicesRepository;
import corp.model.InvoiceVo;
import corp.services.ApplicationService;
import corp.services.GlobalProperties;
import corp.sunat.XmlSunat;
import firmacpesunat.FirmaCPESunat;
import generadorxmlcpe.CPESunat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class XmlSunatTest {
	
	public static CpeBean cpe = null;
    public static Cpe_DetalleBean cpe_Detalle = null;
    
    @Autowired
	private InvoicesRepository invoicesRepository;
	@Autowired
	private GlobalProperties globalProperties;
	@Autowired
	ApplicationService userService;
    
    public static void main(String[] args) throws Exception {
    	
    	//processInvoice();
    	//conexionCPEConsultaTicketTest();
    	conexionCPEStatusCDRTest();
    	// To recover hash CPE
    	/*String RutaArchivo = "C:\\Users\\mecam\\xmlsSunat\\";
    	String NombreCPE = "20501568776-03-B001-00000038";
    	System.out.println(valorXML(RutaArchivo + NombreCPE, "", "DigestValue"));*/
    }
	
    public static void processInvoice() throws FileNotFoundException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, KeyStoreException, CertificateException, ParserConfigurationException, SAXException, MarshalException, IOException, Exception {
		
		// Factura
		InvoiceVo invoiceVo2 = new InvoiceVo();
		invoiceVo2.setInvoiceNumber("F020-8000");
		invoiceVo2.setDate(new Date());
		invoiceVo2.setClientAddress("319 Oakmont Dr");
		invoiceVo2.setClientName("Molfer Technologies EIRL");
		invoiceVo2.setInvoiceType("01");// Factura
		invoiceVo2.setClientDocType("6"); //RUC
		invoiceVo2.setClientDocNumber("10203040501"); // RUC NUMBER
		invoiceVo2.setGalsD2(20D);
		invoiceVo2.setPriceD2(10D); // includes IGV
		invoiceVo2.setSolesD2(200D);
		invoiceVo2.setGalsG90(10D);
		invoiceVo2.setPriceG90(10D);
		invoiceVo2.setSolesG90(100D);
		invoiceVo2.setTotalVerbiage(XmlSunat.Convertir(invoiceVo2.getTotal().toString(), true, "PEN"));
		//invoiceVo2.setTotalVerbiage("TRESCIENTOS CICUENTA Y CUATRO CON 00/100 SOLES");
		/*invoiceVo.setGalsG95(0D);
		invoiceVo.setPriceG95(11D);
		invoiceVo.setSolesG95(0D);*/
		
		// Boleta
		InvoiceVo invoiceVo = new InvoiceVo();
		invoiceVo.setInvoiceNumber("B020-2000");
		invoiceVo.setDate(new Date());
		invoiceVo.setClientAddress("319 Oakmont Dr");
		invoiceVo.setClientName("Rudy Molero");
		invoiceVo.setInvoiceType("03");// Boleta
		invoiceVo.setClientDocType("1");//DNI
		invoiceVo.setClientDocNumber("43664470"); // DNI NUMBER
		invoiceVo.setGalsD2(10D);
		invoiceVo.setPriceD2(10D);// Includes IGuserHomeDir
		invoiceVo.setSolesD2(100D);
		invoiceVo.setTotalVerbiage(XmlSunat.Convertir(invoiceVo.getTotal().toString(), true, "PEN"));
		/*invoiceVo.setGalsG90(0D);
		invoiceVo.setPriceG90(10D);
		invoiceVo.setSolesG90(0D);
		invoiceVo.setGalsG95(0D);
		invoiceVo.setPriceG95(11D);
		invoiceVo.setSolesG95(0D);*/
		
		// Nota de Crédito
		InvoiceVo invoiceVo3 = new InvoiceVo();
		invoiceVo3.setInvoiceNumber("F020-80000001");
		invoiceVo3.setDate(new Date());
		invoiceVo3.setClientAddress("319 Oakmont Dr");
		invoiceVo3.setClientName("Molfer Technologies EIRL");
		invoiceVo3.setInvoiceType("07");// Factura
		invoiceVo3.setClientDocType("6"); //RUC
		invoiceVo3.setClientDocNumber("10203040501"); // RUC NUMBER
		invoiceVo3.setGalsD2(20D);
		invoiceVo3.setPriceD2(10D); // includes IGV
		invoiceVo3.setSolesD2(200D);
		invoiceVo3.setGalsG90(10D);
		invoiceVo3.setPriceG90(10D);
		invoiceVo3.setSolesG90(100D);
		invoiceVo3.setTotalVerbiage(XmlSunat.Convertir(invoiceVo3.getTotal().toString(), true, "PEN"));
		invoiceVo3.setInvoiceNumberModified("F020-80000009");
		invoiceVo3.setInvoiceTypeModified("01");
		invoiceVo3.setMotiveCd("01");
		invoiceVo3.setMotiveCdDescription("ANULACION DE LA OPERACION");
		
	/*	// Factura
		XmlSunat.invokeSunat(invoiceVo2);
		XmlSunat.firma(invoiceVo2);
		XmlSunat.envio(invoiceVo2);
		
		// Boleta
		XmlSunat.invokeSunat(invoiceVo);
		XmlSunat.firma(invoiceVo);
		XmlSunat.envio(invoiceVo);*/
		
		// Nota de credito
		XmlSunat.invokeSunat(invoiceVo3, System.getProperty("user.home"));
		XmlSunat.firma(invoiceVo3, System.getProperty("user.home"), "FIRMABETA.pfx", "123456");
		XmlSunat.envio(invoiceVo3, System.getProperty("user.home"), "https://e-beta.sunat.gob.pe:443/ol-ti-itcpfegem-beta/billService", "MODDATOS", "moddatos");
	}
	
	public static void conexionCPEConsultaTicketTest() {
		
		String ruc = "20501568776";
		String UsuarioSol = "LAJOYA40";
		String PassSol = "Lajoya@4";
		String rucCliente = "20501568776";
		String tipoDocumento = "01";
		String serie = "F001";
		String numero = "108";
		String RutaWS = "https://e-factura.sunat.gob.pe/ol-ti-itcpfegem/billService";
		
		ApiClienteEnvioSunat.ConexionCPEConsultaEstadoFactura(ruc, UsuarioSol, PassSol, rucCliente, tipoDocumento, serie, numero, RutaWS);
	}
	
	public static void conexionCPEStatusCDRTest() throws SAXException, IOException, ParserConfigurationException {
		
		String ruc = "20501568776";
		String UsuarioSol = "LAJOYA40";
		String PassSol = "Lajoya@4";
		String tipoDocumento = "";
		String nro_comprobante = "B001-00011088";
		if (nro_comprobante.charAt(0) == 'F') {
			tipoDocumento = "01";
		} else {
			tipoDocumento = "03";
		}
		String RutaWS = "https://e-factura.sunat.gob.pe/ol-it-wsconscpegem/billConsultService";
		String NombreCPE = "20501568776-" + tipoDocumento + "-" + nro_comprobante ;
		String NombreCDR = "R-" + NombreCPE;
		String RutaArchivo = "C:\\Users\\mecam\\xmlsSunat\\CDR\\";
		
		String response = ApiClienteEnvioSunat.ConexionCPEStatusCDR(ruc, UsuarioSol, PassSol, tipoDocumento, nro_comprobante, NombreCPE,  NombreCDR,  RutaArchivo, RutaWS);
		System.out.println("\n" + response);
		
		//String RutaArchivo = "C:\\Users\\mecam\\xmlsSunat\\";
    	//String NombreCPE = "20501568776-03-B001-00000038";
    	//System.out.println("Hash CPE: " + valorXML(RutaArchivo + NombreCPE, "", "DigestValue"));
		
	}
	
	@Test
	public void consultInvoiceTest() {
		
		InvoiceVo invoiceVo = new InvoiceVo(invoicesRepository.findFirstByInvoiceNumberAndSunatStatus("B001-00003790", ApplicationService.PENDING_STATUS));
		String rutaArchivo = XmlSunat.getValidatedPath("C:\\Users\\mecam\\xmlsSunat\\CDR\\");
		
		XmlSunat.consultInvoice(invoiceVo, rutaArchivo, globalProperties);
	}
	
    public static String valorXML(String rutaArchivo, String Nspace, String TagName) throws SAXException, IOException, ParserConfigurationException {
        String rta = "";
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(rutaArchivo + ".XML"), "ISO-8859-1");//origen
            doc.getDocumentElement().normalize();
            if (Nspace.length() > 0) {
                rta = doc.getDocumentElement().getElementsByTagNameNS("*", TagName).item(0).getTextContent();//cbc:ResponseCode
            } else {
                rta = doc.getDocumentElement().getElementsByTagName(TagName).item(0).getTextContent();
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return rta;
    }
	
	public static void firma() throws FileNotFoundException, NoSuchAlgorithmException,
			InvalidAlgorithmParameterException, ParserConfigurationException, SAXException, MarshalException,
			KeyStoreException, IOException, CertificateException, Exception {

		int flg_firma = 0;// (1=factura,boleta,nc,nd)<====>(0=retencion, percepcion)

		String rutaXML = "C:\\sunat\\20501568776-01-F020-8000";
		String rutaFirma = "C:\\Users\\mecam\\.m2\\repository\\sunat\\FIRMABETA.pfx";
		String passFirma = "123456";

		// String nomArchivo = "20100078792-20-R002-41372";
		int Result = FirmaCPESunat.Signature(flg_firma, rutaXML, rutaFirma, passFirma);
		System.out.println(Result);
	}
	
	public static void envio() {
        String ruc = "20501568776";
        String UsuSol = "MODDATOS";// pruebas de sunat
        String PassSol = "moddatos";// password de prueba de sunat
        String NombreCPE = "20501568776-01-F020-8000"; // xml firmado
        String NombreCDR = "R-20501568776-01-F020-8000"; // respuesta
        String RutaArchivo = "C:\\sunat\\";
        String RutaWS = "https://e-beta.sunat.gob.pe:443/ol-ti-itcpfegem-beta/billService";
        //PRODUCCION=https://e-factura.sunat.gob.pe/ol-ti-itcpfegem/billService
        //BETA=https://e-beta.sunat.gob.pe:443/ol-ti-itcpfegem-beta/billService
        String sunatResponse = ApiClienteEnvioSunat.ConexionCPE(ruc, UsuSol, PassSol, NombreCPE, NombreCDR, RutaArchivo, RutaWS);
        System.out.println("\nsunatResponse: " + sunatResponse);
	}
	
	// OBLIGATORIO
	public static void invokeSunat() {
	    cpe = new CpeBean();
	    cpe.setTIPO_OPERACION("0101");// OBLIGATORIO
	    cpe.setTOTAL_GRAVADAS(625);// OBLIGATORIO IGUAL A SUB_TOTAL
	    cpe.setTOTAL_INAFECTA(0);
	    cpe.setTOTAL_EXONERADAS(0);
	    cpe.setTOTAL_GRATUITAS(0);
	    cpe.setTOTAL_PERCEPCIONES(0);
	    cpe.setTOTAL_RETENCIONES(0);
	    cpe.setTOTAL_DETRACCIONES(0);
	    cpe.setTOTAL_BONIFICACIONES(0);
	    cpe.setTOTAL_DESCUENTO(0);
	    cpe.setSUB_TOTAL(625);// OBLIGATORIO
	    cpe.setPOR_IGV(18);//UBL2.1 // OBLIGATORIO
	    cpe.setTOTAL_IGV(112.50);// OBLIGATORIO
	    cpe.setTOTAL_ISC(0);// NO 
	    cpe.setTOTAL_OTR_IMP(0);// NO
	    cpe.setTOTAL(737.50);// OBLIGATORIO
	    cpe.setTOTAL_LETRAS("SETECIENTOS TREINTA Y SIETE CON 50/100 SOLES"); // OBLIGATORIO
	    cpe.setNRO_GUIA_REMISION("");
	    cpe.setCOD_GUIA_REMISION("");
	    cpe.setNRO_OTR_COMPROBANTE("");
	    cpe.setCOD_OTR_COMPROBANTE("");
	    cpe.setNRO_COMPROBANTE("B020-8000");// OBLIGATORIO
	    cpe.setFECHA_DOCUMENTO("2018-10-11");// OBLIGATORIO YYYY-MM-DD
	    cpe.setFECHA_VTO("2018-10-11");//// OBLIGATORIO FECHA DE VENCIMIENTO IGUAL A FECHA DE DOCUMENTO PARA BOLETA Y FACTURA
	    cpe.setCOD_TIPO_DOCUMENTO("03");//01=factura, 03=boleta, 07=nota credito, 08=nota debito
	    cpe.setCOD_MONEDA("PEN");
	    
	    cpe.setNRO_DOCUMENTO_CLIENTE("44791512"); // OBLIGATORIO PARA CLIENTES CON COMPRAS >= 700 CASO CONTRARIO "00000000", RUC PARA FACTURA
	    cpe.setRAZON_SOCIAL_CLIENTE("JOSE ZAMBRANO"); // PARA COMPRAS < 700 "CLIENTES VARIOS"
	    cpe.setTIPO_DOCUMENTO_CLIENTE("1");//1=DNI, 6=RUC 
	    //================================
	    cpe.setCOD_UBIGEO_CLIENTE("");//NUEVO UBL2.1 PARA FACTURA O BOLETA LO PUEDO ENVIAR EN BLANCO, CODIGO POSTAL   
	    cpe.setDEPARTAMENTO_CLIENTE("");//NUEVO UBL2.1   
	    cpe.setPROVINCIA_CLIENTE("");//NUEVO UBL2.1   
	    cpe.setDISTRITO_CLIENTE("");//NUEVO UBL2.1 
	    //===============================
	    cpe.setCIUDAD_CLIENTE("LIMA");
	    cpe.setCOD_PAIS_CLIENTE("PE");
	    cpe.setNRO_DOCUMENTO_EMPRESA("20100066603"); // MI RUC
	    cpe.setTIPO_DOCUMENTO_EMPRESA("6");
	    cpe.setNOMBRE_COMERCIAL_EMPRESA("CREV PERU COMERCIAL");// PONER RAZON SOCIAL SI NO ESTA DISPONIBLE EN SUNAT 
	    cpe.setCODIGO_UBIGEO_EMPRESA("150103"); // ate  (anterior 070104)
	    cpe.setDIRECCION_EMPRESA("AV. MIGUEL GRAU MZA. B LOTE. 1-2 (COSTADO DE REVISION TECNICA-GRIFO PRIMAX)");
	    cpe.setDEPARTAMENTO_EMPRESA("LIMA");
	    cpe.setPROVINCIA_EMPRESA("LIMA");
	    cpe.setDISTRITO_EMPRESA("ATE");
	    cpe.setCODIGO_PAIS_EMPRESA("PE");
	    cpe.setRAZON_SOCIAL_EMPRESA("LA JOYA DE SANTA ISABEL E.I.R.L.");
	    
	    /*Cpe.setUSUARIO_SOL_EMPRESA("MODDATOS"); // OBTENER DE GERENTE
	    Cpe.setPASS_SOL_EMPRESA("moddatos");//OBETENER DE GERENT
	    Cpe.setTIPO_PROCESO(3);//1=PRODUCCION,2=HOMOLOGACION,3=PRUEBA
	    Cpe.setCOD_RESPUESTA_SUNAT("");
	    Cpe.setDESCRIPCION_RESPUESTA("");
	    Cpe.setESTADO("V");*/
	
	    // DETALLE DE CADA PRODUCTO VENDIDO, CASO DE ABAJO UN SOLO PRODUCTO
	    List<Cpe_DetalleBean> lstCpe_Detalle = new ArrayList<Cpe_DetalleBean>();
	
	    cpe_Detalle = new Cpe_DetalleBean();
	    cpe_Detalle.setITEM(1); // PONDER CONSEQU
	    cpe_Detalle.setUNIDAD_MEDIDA("ZZ");
	    cpe_Detalle.setCANTIDAD(1);
	    cpe_Detalle.setPRECIO(625.00);
	    cpe_Detalle.setIMPORTE(625.00);//PRECIO X CANTIDAD
	    cpe_Detalle.setPRECIO_TIPO_CODIGO("01");
	    cpe_Detalle.setIGV(112.50);
	    cpe_Detalle.setISC(0);
	    cpe_Detalle.setCOD_TIPO_OPERACION("10");
	    cpe_Detalle.setCODIGO("0001");
	    cpe_Detalle.setDESCRIPCION("PRUEBA");
	    cpe_Detalle.setPRECIO_SIN_IMPUESTO(625.00);
	    lstCpe_Detalle.add(cpe_Detalle);
	    
	    int rta = CPESunat.GenerarXMLCPE("C:\\sunat\\BETA\\20100066603-03-B020-8000.XML", cpe, lstCpe_Detalle);
	}	   
}