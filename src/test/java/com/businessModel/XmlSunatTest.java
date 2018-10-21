package com.businessModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bean.CpeBean;
import com.bean.Cpe_DetalleBean;
import generadorxmlcpe.CPESunat;

import hello.model.InvoiceVo;
import hello.sunat.XmlSunat;

public class XmlSunatTest {
	
	public static CpeBean Cpe = null;
    public static Cpe_DetalleBean Cpe_Detalle = null;

	public static void main(String[] args) {
		InvoiceVo invoiceVo = new InvoiceVo();
		invoiceVo.setDate(new Date());
		invoiceVo.setClientAddress("");
		invoiceVo.setClientName("");
		
		XmlSunat xmlSunat = new XmlSunat();
		
		invokeSunat();
		
	}
	
	// OBLIGATORIO
	public static void invokeSunat() {
	    Cpe = new CpeBean();
	    Cpe.setTIPO_OPERACION("0101");// OBLIGATORIO
	    Cpe.setTOTAL_GRAVADAS(625);// OBLIGATORIO IGUAL A SUB_TOTAL
	    Cpe.setTOTAL_INAFECTA(0);
	    Cpe.setTOTAL_EXONERADAS(0);
	    Cpe.setTOTAL_GRATUITAS(0);
	    Cpe.setTOTAL_PERCEPCIONES(0);
	    Cpe.setTOTAL_RETENCIONES(0);
	    Cpe.setTOTAL_DETRACCIONES(0);
	    Cpe.setTOTAL_BONIFICACIONES(0);
	    Cpe.setTOTAL_DESCUENTO(0);
	    Cpe.setSUB_TOTAL(625);// OBLIGATORIO
	    Cpe.setPOR_IGV(18);//UBL2.1 // OBLIGATORIO
	    Cpe.setTOTAL_IGV(112.50);// OBLIGATORIO
	    Cpe.setTOTAL_ISC(0);// NO 
	    Cpe.setTOTAL_OTR_IMP(0);// NO
	    Cpe.setTOTAL(737.50);// OBLIGATORIO
	    Cpe.setTOTAL_LETRAS("SETECIENTOS TREINTA Y SIETE CON 50/100 SOLES"); // OBLIGATORIO
	    Cpe.setNRO_GUIA_REMISION("");
	    Cpe.setCOD_GUIA_REMISION("");
	    Cpe.setNRO_OTR_COMPROBANTE("");
	    Cpe.setCOD_OTR_COMPROBANTE("");
	    Cpe.setNRO_COMPROBANTE("B020-8000");// OBLIGATORIO
	    Cpe.setFECHA_DOCUMENTO("2018-10-11");// OBLIGATORIO YYYY-MM-DD
	    Cpe.setFECHA_VTO("2018-10-11");//// OBLIGATORIO FECHA DE VENCIMIENTO IGUAL A FECHA DE DOCUMENTO PARA BOLETA Y FACTURA
	    Cpe.setCOD_TIPO_DOCUMENTO("03");//01=factura, 03=boleta, 07=nota credito, 08=nota debito
	    Cpe.setCOD_MONEDA("PEN");
	    
	    Cpe.setNRO_DOCUMENTO_CLIENTE("44791512"); // OBLIGATORIO PARA CLIENTES CON COMPRAS >= 700 CASO CONTRARIO "00000000", RUC PARA FACTURA
	    Cpe.setRAZON_SOCIAL_CLIENTE("JOSE ZAMBRANO"); // PARA COMPRAS < 700 "CLIENTES VARIOS"
	    Cpe.setTIPO_DOCUMENTO_CLIENTE("1");//1=DNI, 6=RUC 
	    //================================
	    Cpe.setCOD_UBIGEO_CLIENTE("");//NUEVO UBL2.1 PARA FACTURA O BOLETA LO PUEDO ENVIAR EN BLANCO, CODIGO POSTAL   
	    Cpe.setDEPARTAMENTO_CLIENTE("");//NUEVO UBL2.1   
	    Cpe.setPROVINCIA_CLIENTE("");//NUEVO UBL2.1   
	    Cpe.setDISTRITO_CLIENTE("");//NUEVO UBL2.1 
	    //===============================
	    Cpe.setCIUDAD_CLIENTE("LIMA");
	    Cpe.setCOD_PAIS_CLIENTE("PE");
	    Cpe.setNRO_DOCUMENTO_EMPRESA("20100066603"); // MI RUC
	    Cpe.setTIPO_DOCUMENTO_EMPRESA("6");
	    Cpe.setNOMBRE_COMERCIAL_EMPRESA("CREV PERU COMERCIAL");// PONER RAZON SOCIAL SI NO ESTA DISPONIBLE EN SUNAT 
	    Cpe.setCODIGO_UBIGEO_EMPRESA("150103"); // ate  (anterior 070104)
	    Cpe.setDIRECCION_EMPRESA("AV. MIGUEL GRAU MZA. B LOTE. 1-2 (COSTADO DE REVISION TECNICA-GRIFO PRIMAX)");
	    Cpe.setDEPARTAMENTO_EMPRESA("LIMA");
	    Cpe.setPROVINCIA_EMPRESA("LIMA");
	    Cpe.setDISTRITO_EMPRESA("ATE");
	    Cpe.setCODIGO_PAIS_EMPRESA("PE");
	    Cpe.setRAZON_SOCIAL_EMPRESA("LA JOYA DE SANTA ISABEL E.I.R.L.");
	    
	    /*Cpe.setUSUARIO_SOL_EMPRESA("MODDATOS"); // OBTENER DE GERENTE
	    Cpe.setPASS_SOL_EMPRESA("moddatos");//OBETENER DE GERENT
	    Cpe.setTIPO_PROCESO(3);//1=PRODUCCION,2=HOMOLOGACION,3=PRUEBA
	    Cpe.setCOD_RESPUESTA_SUNAT("");
	    Cpe.setDESCRIPCION_RESPUESTA("");
	    Cpe.setESTADO("V");*/
	
	    // DETALLE DE CADA PRODUCTO VENDIDO, CASO DE ABAJO UN SOLO PRODUCTO
	    List<Cpe_DetalleBean> lstCpe_Detalle = null;
	    lstCpe_Detalle = new ArrayList<Cpe_DetalleBean>();
	
	    Cpe_Detalle = new Cpe_DetalleBean();
	    Cpe_Detalle.setITEM(1); // PONDER CONSEQU
	    Cpe_Detalle.setUNIDAD_MEDIDA("ZZ");
	    Cpe_Detalle.setCANTIDAD(1);
	    Cpe_Detalle.setPRECIO(625.00);
	    Cpe_Detalle.setIMPORTE(625.00);//PRECIO X CANTIDAD
	    Cpe_Detalle.setPRECIO_TIPO_CODIGO("01");
	    Cpe_Detalle.setIGV(112.50);
	    Cpe_Detalle.setISC(0);
	    Cpe_Detalle.setCOD_TIPO_OPERACION("10");
	    Cpe_Detalle.setCODIGO("0001");
	    Cpe_Detalle.setDESCRIPCION("PRUEBA");
	    Cpe_Detalle.setPRECIO_SIN_IMPUESTO(625.00);
	    lstCpe_Detalle.add(Cpe_Detalle);
	    
	    int rta = CPESunat.GenerarXMLCPE("C:\\sunat\\BETA\\20100066603-03-B020-8000.XML", Cpe, lstCpe_Detalle);
	}	   
}