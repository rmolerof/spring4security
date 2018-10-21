package hello.sunat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*import com.bean.CpeBean;
import com.bean.Cpe_DetalleBean;*/

import hello.model.InvoiceVo;

public class XmlSunat {
	/*private ApiComprobantSunat cpeSunat = new ApiComprobanteSunat;
	private CpeBean cpe;
	private Cpe_DetalleBean cpe_detalle;
	
	public submitReceipt(InvoiceVo invoiceVo) throws Exception {
		
		cpe = new CpeBean();
        //=================datos del comprobante=================
        cpe.setTIPO_OPERACION("");
        cpe.setTOTAL_GRAVADAS(invoiceVo.getSubTotal());
        cpe.setSUB_TOTAL(invoiceVo.getSubTotal());
        cpe.setTOTAL_IGV(invoiceVo.getTotalIGV());
        cpe.setTOTAL(invoiceVo.getTotal());
        cpe.setTOTAL_LETRAS("SETECIENTOS TREINTA Y SIETE CON 50/100 SOLES");
        cpe.setNRO_COMPROBANTE("B020-" + "188278");// generate autmatically
        cpe.setFECHA_DOCUMENTO(formatDate(invoiceVo.getDate()));// what format for date
        cpe.setCOD_TIPO_DOCUMENTO(invoiceVo.getBillType().equalsIgnoreCase("factura") ? "01": "03"); // boletas = 03, factura = 01
        cpe.setCOD_MONEDA("PEN");
        //==================datos del cliente===================
        //-- como separo estos datos
        cpe.setNRO_DOCUMENTO_CLIENTE("44791512");
        cpe.setRAZON_SOCIAL_CLIENTE("MIGUEL CHINCHAY");
        cpe.setTIPO_DOCUMENTO_CLIENTE("1");
        cpe.setDIRECCION_CLIENTE("HUAMPANI ALTO");
        cpe.setCIUDAD_CLIENTE("LIMA");
        cpe.setCORREO("miguel.peru.seo@gmail.com");
        cpe.setCOD_PAIS_CLIENTE("PE");
        //==================datos de la empresa===================
        cpe.setNRO_DOCUMENTO_EMPRESA(invoiceVo.getRucNumber());
        cpe.setTIPO_DOCUMENTO_EMPRESA("6");
        cpe.setNOMBRE_COMERCIAL_EMPRESA(invoiceVo.getClientName());
        cpe.setCODIGO_UBIGEO_EMPRESA("070104");
        cpe.setDIRECCION_EMPRESA(invoiceVo.getClientAddress());
        cpe.setDEPARTAMENTO_EMPRESA("LIMA");
        cpe.setPROVINCIA_EMPRESA("LIMA");
        cpe.setDISTRITO_EMPRESA("CHACLACAYO");
        cpe.setCODIGO_PAIS_EMPRESA("PE");
        cpe.setRAZON_SOCIAL_EMPRESA(invoiceVo.getClientName());
        //===================datos sunat==================
        cpe.setUSUARIO_SOL_EMPRESA("MODDATOS");
        cpe.setPASS_SOL_EMPRESA("moddatos");
        //==============datos del server==============
        cpe.setCONTRA("123456");
        cpe.setTIPO_PROCESO(3);//1=PRODUCCION,2=HOMOLOGACION,3=PRUEBA
        
        //==============datos de facturas x anticipo==============

        List<Cpe_DetalleBean> lstCpe_Detalle = new ArrayList<Cpe_DetalleBean>();

        cpe_Detalle = new Cpe_DetalleBean();
        cpe_Detalle.setITEM(1);
        cpe_Detalle.setUNIDAD_MEDIDA("NIU");
        cpe_Detalle.setCANTIDAD(1);
        cpe_Detalle.setPRECIO(invoiceVo.getSubTotal());
        cpe_Detalle.setIMPORTE(invoiceVo.getTotal());
        cpe_Detalle.setPRECIO_TIPO_CODIGO("02");
        cpe_Detalle.setIGV(invoiceVo.getTotalIGV());
        cpe_Detalle.setISC(0);
        cpe_Detalle.setCOD_TIPO_OPERACION("20");
        cpe_Detalle.setCODIGO("0001");
        cpe_Detalle.setDESCRIPCION("PRUEBA");
        cpe_Detalle.setPRECIO_SIN_IMPUESTO(invoiceVo.getSubTotal());
        lstCpe_Detalle.add(cpe_Detalle);
        //================CARGAMOS DETALLE EN LA CABECERA================
        cpe.setDetalle(lstCpe_Detalle);
        
        // llamar al metodo
        public int cpeSunat(cpe, lstCpe_Detalle);
        
        String rutaXMLCPE = "C:\\" + cpe.getNRO_DOCUMENTO_EMPRESA() + "-" + Cpe.getCOD_TIPO_DOCUMENTO() + cpe.getNRO_TIPO_DOCUMENTO() + cpe.getNRO_COMPROBANTE();
        
        if (cpe.getCOD_TIPO_DOCUMENTO().equals("01") || cpe.getCOD_TIPO_DOCUMENTO().equals("03")) {
        	cpeSunat.GENERARXMLCPE(rutaXMLCPE, cpe, lstCpe_Detalle);
        } else if (cpe.getCOD_TIPO_DOCUMENTO().equals("07")) {
        	cpeSunat.GENERARXML_NC(rutaXMLCPE, cpe, lstCpe_Detalle);
        } else if (cpe.getCOD_TIPO_DOCUMENTO().equals("08")){
        	cpeSunat.GENERARXML_ND(rutaXMLCPE, cpe, lstCpe_Detalle);
        }
        
	}
	
	private String formatDate(Date date) {
		String isoDatePattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

		return simpleDateFormat.format(date);
	} */
}
