package hello.sunat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.crypto.MarshalException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.bean.CpeBean;
import com.bean.Cpe_DetalleBean;

import apiclienteenviosunat.ApiClienteEnvioSunat;
import firmacpesunat.FirmaCPESunat;
import generadorxmlcpe.CPESunat;

/*import com.bean.CpeBean;
import com.bean.Cpe_DetalleBean;*/

import hello.model.InvoiceVo;

public class XmlSunat {
	
	public static final String myRUC = "20501568776";
	private static final String[] UNIDADES = { "", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve " };
	private static final String[] DECENAS  = { "diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
											   "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ", "cincuenta ", "sesenta ",
											   "setenta ", "ochenta ", "noventa " };
	private static final String[] CENTENAS = { "", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ",
											   "quinientos ", "seiscientos ", "setecientos ", "ochocientos ", "novecientos " };
//	private static final String userHomeDir = System.getProperty("user.home");
	private static final String userHomeDir = "/home/ec2-user"; 
	
	// OBLIGATORIO
	public static int invokeSunat(InvoiceVo invoiceVo) throws Exception {
		CpeBean cpe = new CpeBean();
	    cpe.setTIPO_OPERACION("0101");// OBLIGATORIO 0101 venta interna (grifo), venta itinerante
	    cpe.setTOTAL_GRAVADAS(invoiceVo.getSubTotal());// OBLIGATORIO IGUAL A SUB_TOTAL
	    cpe.setTOTAL_INAFECTA(0);
	    cpe.setTOTAL_EXONERADAS(0);
	    cpe.setTOTAL_GRATUITAS(0);
	    cpe.setTOTAL_PERCEPCIONES(0);
	    cpe.setTOTAL_RETENCIONES(0);
	    cpe.setTOTAL_DETRACCIONES(0);
	    cpe.setTOTAL_BONIFICACIONES(0);
	    cpe.setTOTAL_DESCUENTO(invoiceVo.getDiscount());
	    cpe.setSUB_TOTAL(invoiceVo.getSubTotal());// OBLIGATORIO
	    cpe.setPOR_IGV(18);//UBL2.1 // OBLIGATORIO
	    cpe.setTOTAL_IGV(invoiceVo.getTotalIGV());// OBLIGATORIO
	    cpe.setTOTAL_ISC(0);// NO 
	    cpe.setTOTAL_OTR_IMP(0);// NO
	    cpe.setTOTAL(invoiceVo.getTotal());// OBLIGATORIO
	    cpe.setTOTAL_LETRAS(invoiceVo.getTotalVerbiage()); // OBLIGATORIO
	    cpe.setNRO_GUIA_REMISION("");
	    cpe.setCOD_GUIA_REMISION("");
	    cpe.setNRO_OTR_COMPROBANTE("");
	    cpe.setCOD_OTR_COMPROBANTE("");
	    cpe.setNRO_COMPROBANTE(invoiceVo.getInvoiceNumber());// OBLIGATORIO  ????
	    cpe.setFECHA_DOCUMENTO(formatDate(invoiceVo.getDate()));// OBLIGATORIO YYYY-MM-DD
	    cpe.setFECHA_VTO(formatDate(invoiceVo.getDate()));//// OBLIGATORIO FECHA DE VENCIMIENTO IGUAL A FECHA DE DOCUMENTO PARA BOLETA Y FACTURA
	    cpe.setCOD_TIPO_DOCUMENTO(invoiceVo.getInvoiceType());//01=factura, 03=boleta, 07=nota credito, 08=nota debito
	    cpe.setCOD_MONEDA("PEN");
	    
	  //=================REFERENCIA DE LA NOTA DE CREDITO================
        cpe.setTIPO_COMPROBANTE_MODIFICA(invoiceVo.getInvoiceTypeModified());//01=FACTURA, 03=BOLETA
		cpe.setNRO_DOCUMENTO_MODIFICA(invoiceVo.getInvoiceNumberModified());
		cpe.setCOD_TIPO_MOTIVO(invoiceVo.getMotiveCd());//CATALOGO 09 SEGUN SUNAT
		cpe.setDESCRIPCION_MOTIVO(invoiceVo.getMotiveCdDescription());//CATALOGO 09 SEGUN SUNAT
        //==============FIN DE LA REFERENCIA NOTA 
	    
	    cpe.setNRO_DOCUMENTO_CLIENTE(invoiceVo.getClientDocNumber()); // OBLIGATORIO PARA CLIENTES CON COMPRAS >= 700 CASO CONTRARIO "00000000", RUC PARA FACTURA
	    cpe.setRAZON_SOCIAL_CLIENTE(invoiceVo.getClientName()); // PARA COMPRAS < 700 "CLIENTES VARIOS"
	    cpe.setTIPO_DOCUMENTO_CLIENTE(invoiceVo.getClientDocType());//1=DNI, 6=RUC 
	    //================================
	    cpe.setCOD_UBIGEO_CLIENTE("");//NUEVO UBL2.1 PARA FACTURA O BOLETA LO PUEDO ENVIAR EN BLANCO, CODIGO POSTAL   
	    cpe.setDEPARTAMENTO_CLIENTE("");//NUEVO UBL2.1   
	    cpe.setPROVINCIA_CLIENTE("");//NUEVO UBL2.1   
	    cpe.setDISTRITO_CLIENTE("");//NUEVO UBL2.1 
	    //===============================
	    cpe.setCIUDAD_CLIENTE("LIMA");
	    cpe.setCOD_PAIS_CLIENTE("PE");
	    cpe.setNRO_DOCUMENTO_EMPRESA(myRUC); // MI RUC
	    cpe.setTIPO_DOCUMENTO_EMPRESA("6");// RUC
	    cpe.setNOMBRE_COMERCIAL_EMPRESA("LA JOYA DE SANTA ISABEL E.I.R.L.");// PONER RAZON SOCIAL SI NO ESTA DISPONIBLE EN SUNAT 
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
	    
	    int itemCount = 0;
	    if (invoiceVo.getGalsD2() != 0D) {
	    	itemCount++;
	    	Cpe_DetalleBean cpe_Detalle = new Cpe_DetalleBean();
		    cpe_Detalle.setITEM(itemCount); // PONDER CONSEQU
		    cpe_Detalle.setUNIDAD_MEDIDA("ZZ");
		    cpe_Detalle.setCANTIDAD(invoiceVo.getGalsD2());//hasta 5 decimales
		    cpe_Detalle.setPRECIO(roundTwo(invoiceVo.getPriceD2() / 1.18D));
		    cpe_Detalle.setIMPORTE(roundTwo(invoiceVo.getSolesD2() / 1.18D));//PRECIO X CANTIDAD
		    cpe_Detalle.setPRECIO_TIPO_CODIGO("01");// ????? 01: afectado por igv, 02: venta gratuita
		    cpe_Detalle.setIGV(roundTwo(invoiceVo.getSolesD2() / 1.18 * 0.18));
		    cpe_Detalle.setISC(0);
		    cpe_Detalle.setCOD_TIPO_OPERACION("10");// 10: grabado (01 tipo codigo), 31 (gratuito), 20 (exonerado), inefectas 30
		    cpe_Detalle.setCODIGO("01");//codigo interno
		    cpe_Detalle.setDESCRIPCION("MAX-D BIODIESEL B.A (UV)");//nombre interno
		    cpe_Detalle.setPRECIO_SIN_IMPUESTO(cpe_Detalle.getIMPORTE());
		    lstCpe_Detalle.add(cpe_Detalle);
	    } 
	    if (invoiceVo.getGalsG90() != 0D) {
	    	itemCount++;
	    	Cpe_DetalleBean cpe_Detalle = new Cpe_DetalleBean();
		    cpe_Detalle.setITEM(itemCount); // PONDER CONSEQU
		    cpe_Detalle.setUNIDAD_MEDIDA("ZZ");
		    cpe_Detalle.setCANTIDAD(invoiceVo.getGalsG90());//hasta 5 decimales
		    cpe_Detalle.setPRECIO(roundTwo(invoiceVo.getPriceG90() / 1.18D));
		    cpe_Detalle.setIMPORTE(roundTwo(invoiceVo.getSolesG90() / 1.18));//PRECIO X CANTIDAD
		    cpe_Detalle.setPRECIO_TIPO_CODIGO("01");// ????? 01: afectado por igv, 02: venta gratuita
		    cpe_Detalle.setIGV(roundTwo(invoiceVo.getSolesG90() / 1.18 * 0.18));
		    cpe_Detalle.setISC(0);
		    cpe_Detalle.setCOD_TIPO_OPERACION("10");
		    cpe_Detalle.setCODIGO("02");
		    cpe_Detalle.setDESCRIPCION("GASOHOL PRIMAX 90");
		    cpe_Detalle.setPRECIO_SIN_IMPUESTO(cpe_Detalle.getIMPORTE());
		    lstCpe_Detalle.add(cpe_Detalle);
	    } 
	    if (invoiceVo.getGalsG95() != 0D) {
	    	itemCount++;
	    	Cpe_DetalleBean cpe_Detalle = new Cpe_DetalleBean();
		    cpe_Detalle.setITEM(itemCount); // PONDER CONSEQU
		    cpe_Detalle.setUNIDAD_MEDIDA("ZZ");
		    cpe_Detalle.setCANTIDAD(invoiceVo.getGalsG95());//hasta 5 decimales
		    cpe_Detalle.setPRECIO(roundTwo(invoiceVo.getPriceG95() / 1.18D));
		    cpe_Detalle.setIMPORTE(roundTwo(invoiceVo.getSolesG95() / 1.18D));//PRECIO X CANTIDAD
		    cpe_Detalle.setPRECIO_TIPO_CODIGO("01");// ????? 01: afectado por igv, 02: venta gratuita
		    cpe_Detalle.setIGV(roundTwo(invoiceVo.getSolesG95() / 1.18 * 0.18));
		    cpe_Detalle.setISC(0);
		    cpe_Detalle.setCOD_TIPO_OPERACION("10");
		    cpe_Detalle.setCODIGO("03");
		    cpe_Detalle.setDESCRIPCION("GASOHOL PRIMAX 95");
		    cpe_Detalle.setPRECIO_SIN_IMPUESTO(cpe_Detalle.getIMPORTE());
		    lstCpe_Detalle.add(cpe_Detalle);
	    }
	    
	    String rutaXMLCPE = userHomeDir + "/xmlsSunat/" + myRUC + "-" + invoiceVo.getInvoiceType() + "-" + invoiceVo.getInvoiceNumber() + ".XML";
	    boolean alreadyExists = new File(rutaXMLCPE).exists();
	    
	    // Create path if basePath doesn't exist
	    if(!alreadyExists){
			(new File(rutaXMLCPE)).getParentFile().mkdirs();
		}
	    
	    if (invoiceVo.getInvoiceType().equalsIgnoreCase("07")) {
	    	return CPESunat.GenerarXML_NC(rutaXMLCPE, cpe, lstCpe_Detalle);
	    } else {
	    	return CPESunat.GenerarXMLCPE(rutaXMLCPE, cpe, lstCpe_Detalle);
	    }
	    
	}
	
	public static void firma(InvoiceVo invoiceVo) throws FileNotFoundException, NoSuchAlgorithmException,
			InvalidAlgorithmParameterException, ParserConfigurationException, SAXException, MarshalException,
			KeyStoreException, IOException, CertificateException, Exception {

		int flg_firma = 0;// (1=factura,boleta,nc,nd)<====>(0=retencion, percepcion)

		String rutaXML = userHomeDir + "/xmlsSunat/" + myRUC + "-" + invoiceVo.getInvoiceType() + "-" + invoiceVo.getInvoiceNumber();
		String rutaFirma = userHomeDir + "/xmlsSunat/signatureSunat/FIRMABETA.pfx";
		boolean alreadyExists = new File(rutaFirma).exists();
	    
	    // Create path if basePath doesn't exist
	    if(!alreadyExists){
			(new File(rutaFirma)).getParentFile().mkdirs();
		}
		String passFirma = "123456";

		// String nomArchivo = "20100078792-20-R002-41372";
		int signingResult = FirmaCPESunat.Signature(flg_firma, rutaXML, rutaFirma, passFirma);
		System.out.println(signingResult == 1 ? "Invoice XML for " + invoiceVo.getInvoiceNumber() + " signed": "Invoice XML for " + invoiceVo.getInvoiceNumber() + " NOT signed");
	}
	
	/**
	 * PRODUCCION=https://e-factura.sunat.gob.pe/ol-ti-itcpfegem/billService
	 * BETA=https://e-beta.sunat.gob.pe:443/ol-ti-itcpfegem-beta/billService
	 */
	public static String envio(InvoiceVo invoiceVo) {
		String UsuSol = "MODDATOS";// pruebas de sunat
		String PassSol = "moddatos";// password de prueba de sunat
		String NombreCPE = myRUC + "-" + invoiceVo.getInvoiceType() + "-" + invoiceVo.getInvoiceNumber();
		String NombreCDR = "R-" + NombreCPE; // respuesta
		String RutaArchivo = userHomeDir + "/xmlsSunat/";
		String RutaWS = "https://e-beta.sunat.gob.pe:443/ol-ti-itcpfegem-beta/billService";
		String sunatResponse = ApiClienteEnvioSunat.ConexionCPE(myRUC, UsuSol, PassSol, NombreCPE, NombreCDR, RutaArchivo, RutaWS);
		invoiceVo.setInvoiceHash(sunatResponse.substring(sunatResponse.lastIndexOf("|") + 1, sunatResponse.length()));
		invoiceVo.setSunatErrorStr(sunatResponse);
		System.out.println("\nSubmit to SUNAT response for " + invoiceVo.getInvoiceNumber() + ": " + sunatResponse);
		System.out.println("hash: " + invoiceVo.getInvoiceHash());
		
		return sunatResponse;
	}
	
	private static String formatDate(Date date) {
		String isoDatePattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

		return simpleDateFormat.format(date);
	}
	
	public static String rightPadZeros(String str, int num) {
		return String.format("%1$-" + num + "s", str).replace(' ', '0');
	}
	
    public static String Convertir(String numero, boolean mayusculas, String moneda) {
        moneda = moneda.replace("PEN", "SOLES");
        moneda = moneda.replace("USD", "DOLARES AMERICANOS");
        moneda = moneda.replace("EUR", "EUROS");
        String literal = "";
        String parte_decimal;
        //si el numero utiliza (.) en lugar de (,) -> se reemplaza
        numero = numero.replace(".", ",");
        //si el numero no tiene parte decimal, se le agrega ,00
        if (numero.indexOf(",") == -1) {
            numero = numero + ",00";
        }
        //se valida formato de entrada -> 0,00 y 999 999 999,00
        if (Pattern.matches("\\d{1,9},\\d{1,2}", numero)) {
            //se divide el numero 0000000,00 -> entero y decimal
            String Num[] = numero.split(",");
            //de da formato al numero decimal
            parte_decimal = "CON " + rightPadZeros(Num[1], 2) + "/100 " + moneda;
            //se convierte el numero a literal
            if (Integer.parseInt(Num[0]) == 0) {//si el valor es cero
                literal = "cero ";
            } else if (Integer.parseInt(Num[0]) > 999999) {//si es millon
                literal = getMillones(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 999) {//si es miles
                literal = getMiles(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 99) {//si es centena
                literal = getCentenas(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 9) {//si es decena
                literal = getDecenas(Num[0]);
            } else {//sino unidades -> 9
                literal = getUnidades(Num[0]);
            }
            //devuelve el resultado en mayusculas o minusculas
            if (mayusculas) {
                return (literal + parte_decimal).toUpperCase().trim();
            } else {
                return (literal + parte_decimal).trim();
            }
        } else {//error, no se puede convertir
            return literal = null;
        }
    }

    /* funciones para convertir los numeros a literales */
    private static String getUnidades(String numero) {// 1 - 9
        //si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9
        String num = numero.substring(numero.length() - 1);
        return UNIDADES[Integer.parseInt(num)];
    }

    private static String getDecenas(String num) {// 99                        
        int n = Integer.parseInt(num);
        if (n < 10) {//para casos como -> 01 - 09
            return getUnidades(num);
        } else if (n > 19) {//para 20...99
            String u = getUnidades(num);
            if (u.equals("")) { //para 20,30,40,50,60,70,80,90
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8];
            } else {
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u;
            }
        } else {//numeros entre 11 y 19
            return DECENAS[n - 10];
        }
    }

    private static String getCentenas(String num) {// 999 o 099
        if (Integer.parseInt(num) > 99) {//es centena
            if (Integer.parseInt(num) == 100) {//caso especial
                return " cien ";
            } else {
                return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
            }
        } else {//por Ej. 099 
            //se quita el 0 antes de convertir a decenas
            return getDecenas(Integer.parseInt(num) + "");
        }
    }

    private static String getMiles(String numero) {// 999 999
        //obtiene las centenas
        String c = numero.substring(numero.length() - 3);
        //obtiene los miles
        String m = numero.substring(0, numero.length() - 3);
        String n = "";
        //se comprueba que miles tenga valor entero
        if (Integer.parseInt(m) > 0) {
            n = getCentenas(m);
            return n + "mil " + getCentenas(c);
        } else {
            return "" + getCentenas(c);
        }

    }

    private static String getMillones(String numero) { //000 000 000        
        //se obtiene los miles
        String miles = numero.substring(numero.length() - 6);
        //se obtiene los millones
        String millon = numero.substring(0, numero.length() - 6);
        String n = "";
        if (millon.length() > 1) {
            n = getCentenas(millon) + "millones ";
        } else {
            n = getUnidades(millon) + "millon ";
        }
        return n + getMiles(miles);
    }
    
    public static double roundTwo(double amt) {
		return Math.round(amt * 100.0) / 100.0;
	}
}