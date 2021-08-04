package corp.sunat;

import com.bean.CpeBean;
import com.bean.Cpe_BajaBean;
import com.bean.Cpe_Baja_DetalleBean;
import com.bean.Cpe_DetalleBean;
import com.bean.Cpe_Resumen_BoletaBean;
import com.bean.Cpe_Resumen_Boleta_DetalleBean;
import com.bean.Cpe_RetencionBean;
import com.bean.Cpe_Retencion_DetalleBean;
import com.bean.Cpe_RrBean;
import com.bean.Cpe_Rr_DetalleBean;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class CPESunat
{
  private static CpeBean Cpe;
  private static Cpe_DetalleBean Cpe_Detalle;
  
  public static void main(String[] args)
  {
    Cpe = new CpeBean();
    Cpe.setTIPO_OPERACION("0101");
    Cpe.setTOTAL_GRAVADAS(625.0D);
    Cpe.setTOTAL_INAFECTA(0.0D);
    Cpe.setTOTAL_EXONERADAS(0.0D);
    Cpe.setTOTAL_GRATUITAS(0.0D);
    Cpe.setTOTAL_PERCEPCIONES(0.0D);
    Cpe.setTOTAL_RETENCIONES(0.0D);
    Cpe.setTOTAL_DETRACCIONES(0.0D);
    Cpe.setTOTAL_BONIFICACIONES(0.0D);
    Cpe.setTOTAL_DESCUENTO(0.0D);
    Cpe.setSUB_TOTAL(625.0D);
    Cpe.setPOR_IGV(18.0D);
    Cpe.setTOTAL_IGV(112.5D);
    Cpe.setTOTAL_ISC(0.0D);
    Cpe.setTOTAL_OTR_IMP(0.0D);
    Cpe.setTOTAL(737.5D);
    Cpe.setTOTAL_LETRAS("SETECIENTOS TREINTA Y SIETE CON 50/100 SOLES");
    Cpe.setNRO_GUIA_REMISION("");
    Cpe.setCOD_GUIA_REMISION("");
    Cpe.setNRO_OTR_COMPROBANTE("");
    Cpe.setCOD_OTR_COMPROBANTE("");
    Cpe.setNRO_COMPROBANTE("B020-8000");
    Cpe.setFECHA_DOCUMENTO("2018-10-11");
    Cpe.setFECHA_VTO("2018-10-11");
    Cpe.setCOD_TIPO_DOCUMENTO("03");
    Cpe.setCOD_MONEDA("PEN");
    Cpe.setNRO_DOCUMENTO_CLIENTE("44791512");
    Cpe.setRAZON_SOCIAL_CLIENTE("JOSE ZAMBRANO");
    Cpe.setTIPO_DOCUMENTO_CLIENTE("1");
    
    Cpe.setCOD_UBIGEO_CLIENTE("");
    Cpe.setDEPARTAMENTO_CLIENTE("");
    Cpe.setPROVINCIA_CLIENTE("");
    Cpe.setDISTRITO_CLIENTE("");
    
    Cpe.setCIUDAD_CLIENTE("LIMA");
    Cpe.setCOD_PAIS_CLIENTE("PE");
    Cpe.setNRO_DOCUMENTO_EMPRESA("20100066603");
    Cpe.setTIPO_DOCUMENTO_EMPRESA("6");
    Cpe.setNOMBRE_COMERCIAL_EMPRESA("CREV PERU COMERCIAL");
    Cpe.setCODIGO_UBIGEO_EMPRESA("070104");
    Cpe.setDIRECCION_EMPRESA("PSJ HUAMPANI");
    Cpe.setDEPARTAMENTO_EMPRESA("LIMA");
    Cpe.setPROVINCIA_EMPRESA("LIMA");
    Cpe.setDISTRITO_EMPRESA("CHACLACAYO");
    Cpe.setCODIGO_PAIS_EMPRESA("PE");
    Cpe.setRAZON_SOCIAL_EMPRESA("CREVPERU S.A.");
    Cpe.setUSUARIO_SOL_EMPRESA("MODDATOS");
    Cpe.setPASS_SOL_EMPRESA("moddatos");
    Cpe.setTIPO_PROCESO(3);
    Cpe.setCOD_RESPUESTA_SUNAT("");
    Cpe.setDESCRIPCION_RESPUESTA("");
    Cpe.setESTADO("V");
    
    List<Cpe_DetalleBean> lstCpe_Detalle = null;
    lstCpe_Detalle = new ArrayList();
    
    Cpe_Detalle = new Cpe_DetalleBean();
    Cpe_Detalle.setITEM(1);
    Cpe_Detalle.setUNIDAD_MEDIDA("NIU");
    Cpe_Detalle.setCANTIDAD(1.0D);
    Cpe_Detalle.setPRECIO(625.0D);
    Cpe_Detalle.setIMPORTE(625.0D);
    Cpe_Detalle.setPRECIO_TIPO_CODIGO("01");
    Cpe_Detalle.setIGV(112.5D);
    Cpe_Detalle.setISC(0.0D);
    Cpe_Detalle.setCOD_TIPO_OPERACION("10");
    Cpe_Detalle.setCODIGO("0001");
    Cpe_Detalle.setDESCRIPCION("PRUEBA");
    Cpe_Detalle.setPRECIO_SIN_IMPUESTO(625.0D);
    lstCpe_Detalle.add(Cpe_Detalle);
    int rta = GenerarXMLCPE("D:\\CPE\\BETA\\20100066603-03-B020-8000.XML", Cpe, lstCpe_Detalle);
  }
  
  public static int GenerarXML_NC(String RutaXml, CpeBean Cpe, List<Cpe_DetalleBean> lstCpe_Detalle)
  {
    try
    {
      String xmlCPE = "";
      
      xmlCPE = "<?xml version='1.0' encoding='UTF-8'?>\n<CreditNote xmlns='urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2' xmlns:cac='urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2' xmlns:cbc='urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2' xmlns:ccts='urn:un:unece:uncefact:documentation:2' xmlns:ds='http://www.w3.org/2000/09/xmldsig#' xmlns:ext='urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2' xmlns:qdt='urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2' xmlns:sac='urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1' xmlns:udt='urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>\n    <ext:UBLExtensions>\n        <ext:UBLExtension>\n            <ext:ExtensionContent>\n            </ext:ExtensionContent>\n        </ext:UBLExtension>\n    </ext:UBLExtensions>\n    <cbc:UBLVersionID>2.1</cbc:UBLVersionID>\n    <cbc:CustomizationID>2.0</cbc:CustomizationID>\n    <cbc:ID>" + Cpe.getNRO_COMPROBANTE() + "</cbc:ID>\n" + "    <cbc:IssueDate>" + Cpe.getFECHA_DOCUMENTO() + "</cbc:IssueDate>\n" + "    <cbc:IssueTime>00:00:00</cbc:IssueTime>\n" + "    <cbc:DocumentCurrencyCode>" + Cpe.getCOD_MONEDA() + "</cbc:DocumentCurrencyCode>\n";
      if (!Cpe.getNRO_OTR_COMPROBANTE().equals("")) {
        xmlCPE = xmlCPE + "\n" + "            <cac:OrderReference>\n" + "               <cbc:ID>" + Cpe.getNRO_OTR_COMPROBANTE() + "</cbc:ID>\n" + "            </cac:OrderReference>\n";
      }
      if (!Cpe.getNRO_GUIA_REMISION().equals("")) {
        xmlCPE = xmlCPE + "\n" + "            <cac:DespatchDocumentReference>\n" + "\t\t<cbc:ID>" + Cpe.getNRO_GUIA_REMISION() + "</cbc:ID>\n" + "\t\t<cbc:IssueDate>" + Cpe.getFECHA_GUIA_REMISION() + "</cbc:IssueDate>\n" + "\t\t<cbc:DocumentTypeCode listAgencyName='PE:SUNAT' listName='Tipo de Documento' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01'>" + Cpe.getCOD_GUIA_REMISION() + "</cbc:DocumentTypeCode>\n" + "            </cac:DespatchDocumentReference>\n";
      }
      if (Cpe.getFLG_REGU_ANTICIPO() == 1)
      {
        String tipodocRelacionado = "";
        if (Cpe.getCOD_TIPO_DOCUMENTO().equals("01")) {
          tipodocRelacionado = "02";
        } else {
          tipodocRelacionado = "03";
        }
        xmlCPE = xmlCPE + "<cac:AdditionalDocumentReference>\n" + "<cbc:ID>" + Cpe.getNRO_COMPROBANTE_REF_ANT() + "</cbc:ID>\n" + "<cbc:DocumentTypeCode listAgencyName='PE:SUNAT' listName='Documento Relacionado' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12'>" + tipodocRelacionado + "</cbc:DocumentTypeCode>\n" + "<cbc:DocumentStatusCode listName='Anticipo' listAgencyName='PE:SUNAT'>" + Cpe.getNRO_COMPROBANTE_REF_ANT() + "</cbc:DocumentStatusCode>\n" + "<cac:IssuerParty>\n" + "<cac:PartyIdentification>\n" + "<cbc:ID schemeID='" + Cpe.getTIPO_DOCUMENTO_EMP_REGU_ANT() + "' schemeName='Documento de Identidad' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'>" + Cpe.getNRO_DOCUMENTO_EMP_REGU_ANT() + "</cbc:ID>\n" + "</cac:PartyIdentification>\n" + "</cac:IssuerParty>\n" + "</cac:AdditionalDocumentReference>";
      }
      xmlCPE = xmlCPE + "    <cac:DiscrepancyResponse>\n" + "        <cbc:ReferenceID>" + Cpe.getNRO_DOCUMENTO_MODIFICA() + "</cbc:ReferenceID>\n" + "        <cbc:ResponseCode>" + Cpe.getCOD_TIPO_MOTIVO() + "</cbc:ResponseCode>\n" + "        <cbc:Description><![CDATA[" + Cpe.getDESCRIPCION_MOTIVO() + "]]></cbc:Description>\n" + "    </cac:DiscrepancyResponse>\n" + "    <cac:BillingReference>\n" + "        <cac:InvoiceDocumentReference>\n" + "            <cbc:ID>" + Cpe.getNRO_DOCUMENTO_MODIFICA() + "</cbc:ID>\n" + "            <cbc:DocumentTypeCode>" + Cpe.getTIPO_COMPROBANTE_MODIFICA() + "</cbc:DocumentTypeCode>\n" + "        </cac:InvoiceDocumentReference>\n" + "    </cac:BillingReference>\n" + "    <cac:Signature>\n" + "        <cbc:ID>IDSignST</cbc:ID>\n" + "        <cac:SignatoryParty>\n" + "            <cac:PartyIdentification>\n" + "                <cbc:ID>" + Cpe.getNRO_DOCUMENTO_EMPRESA() + "</cbc:ID>\n" + "            </cac:PartyIdentification>\n" + "            <cac:PartyName>\n" + "                <cbc:Name><![CDATA[" + Cpe.getRAZON_SOCIAL_EMPRESA() + "]]></cbc:Name>\n" + "            </cac:PartyName>\n" + "        </cac:SignatoryParty>\n" + "        <cac:DigitalSignatureAttachment>\n" + "            <cac:ExternalReference>\n" + "                <cbc:URI>#SignatureSP</cbc:URI>\n" + "            </cac:ExternalReference>\n" + "        </cac:DigitalSignatureAttachment>\n" + "    </cac:Signature>\n" + "    <cac:AccountingSupplierParty>\n" + "        <cac:Party>\n" + "            <cac:PartyIdentification>\n" + "                <cbc:ID schemeID='" + Cpe.getTIPO_DOCUMENTO_EMPRESA() + "' schemeName='SUNAT:Identificador de Documento de Identidad' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'>" + Cpe.getNRO_DOCUMENTO_EMPRESA() + "</cbc:ID>\n" + "            </cac:PartyIdentification>\n" + "            <cac:PartyName>\n" + "                <cbc:Name><![CDATA[" + Cpe.getNOMBRE_COMERCIAL_EMPRESA() + "]]></cbc:Name>\n" + "            </cac:PartyName>\n" + "            <cac:PartyLegalEntity>\n" + "<cbc:RegistrationName><![CDATA[" + Cpe.getRAZON_SOCIAL_EMPRESA() + "]]></cbc:RegistrationName>\n" + "                <cac:RegistrationAddress>\n" + "                    <cbc:AddressTypeCode>0000</cbc:AddressTypeCode>\n" + "                </cac:RegistrationAddress>\n" + "            </cac:PartyLegalEntity>\n" + "        </cac:Party>\n" + "    </cac:AccountingSupplierParty>\n" + "    <cac:AccountingCustomerParty>\n" + "        <cac:Party>\n" + "            <cac:PartyIdentification>\n" + "                <cbc:ID schemeID='" + Cpe.getTIPO_DOCUMENTO_CLIENTE() + "' schemeName='SUNAT:Identificador de Documento de Identidad' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'>" + Cpe.getNRO_DOCUMENTO_CLIENTE() + "</cbc:ID>\n" + "            </cac:PartyIdentification>\n" + "            <cac:PartyLegalEntity>\n" + "<cbc:RegistrationName><![CDATA[" + Cpe.getRAZON_SOCIAL_CLIENTE() + "]]></cbc:RegistrationName>\n" + "            </cac:PartyLegalEntity>\n" + "        </cac:Party>\n" + "    </cac:AccountingCustomerParty>\n";
      if (Cpe.getTOTAL_DETRACCIONES() > 0.0D) {
        xmlCPE = xmlCPE + "<cac:PaymentTerms>\n" + "<cbc:Amount currencyID='PEN'>1.42</cbc:Amount>\n" + "</cac:PaymentTerms>\n";
      }
      if (Cpe.getFLG_REGU_ANTICIPO() == 1) {
        xmlCPE = xmlCPE + "<cac:PrepaidPayment>\n" + "<cbc:ID schemeName='Anticipo' schemeAgencyName='PE:SUNAT'>" + Cpe.getNRO_COMPROBANTE_REF_ANT() + "</cbc:ID>\n" + "<cbc:PaidAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getMONTO_REGU_ANTICIPO() + "</cbc:PaidAmount>\n" + "</cac:PrepaidPayment>\n";
      }
      xmlCPE = xmlCPE + "<cac:TaxTotal>\n" + "        <cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_IGV() + "</cbc:TaxAmount>\n" + "        <cac:TaxSubtotal>\n" + "<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_GRAVADAS() + "</cbc:TaxableAmount>\n" + "<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_IGV() + "</cbc:TaxAmount>\n" + "            <cac:TaxCategory>\n" + "                <cac:TaxScheme>\n" + "                    <cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>1000</cbc:ID>\n" + "                    <cbc:Name>IGV</cbc:Name>\n" + "                    <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>\n" + "                </cac:TaxScheme>\n" + "            </cac:TaxCategory>\n" + "        </cac:TaxSubtotal>";
      if (Cpe.getTOTAL_ISC() > 0.0D) {
        xmlCPE = xmlCPE + "\n" + "                <cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_ISC() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_ISC() + "</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>S</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>2000</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>ISC</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>EXC</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      }
      if (Cpe.getTOTAL_EXPORTACION() > 0.0D) {
        xmlCPE = xmlCPE + "\n" + "                <cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_EXPORTACION() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0.00</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>G</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>9995</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>EXP</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>FRE</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      }
      if (Cpe.getTOTAL_GRATUITAS() > 0.0D) {
        xmlCPE = xmlCPE + "\n" + "                <cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_GRATUITAS() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0.00</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>Z</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>9996</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>GRA</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>FRE</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      }
      if (Cpe.getTOTAL_EXONERADAS() > 0.0D) {
        xmlCPE = xmlCPE + "\n" + "                <cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_EXONERADAS() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0.00</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>E</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>9997</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>EXO</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      }
      if (Cpe.getTOTAL_INAFECTA() > 0.0D) {
        xmlCPE = xmlCPE + "\n" + "                <cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_INAFECTA() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0.00</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>O</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>9998</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>INA</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>FRE</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      }
      if (Cpe.getTOTAL_OTR_IMP() > 0.0D) {
        xmlCPE = xmlCPE + "\n" + "                <cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_OTR_IMP() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_OTR_IMP() + "</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>S</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>9999</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>OTR</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>OTH</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      }
      xmlCPE = xmlCPE + "\n" + "       </cac:TaxTotal>\n" + "\t<cac:LegalMonetaryTotal>\n";
      if (Cpe.getFLG_REGU_ANTICIPO() == 1) {
        xmlCPE = xmlCPE + "<cbc:PrepaidAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getMONTO_REGU_ANTICIPO() + "</cbc:PrepaidAmount>\n" + "           <cbc:PayableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0.00</cbc:PayableAmount>\n";
      } else {
        xmlCPE = xmlCPE + "<cbc:PayableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL() + "</cbc:PayableAmount>\n";
      }
      xmlCPE = xmlCPE + "\t</cac:LegalMonetaryTotal>\n";
      for (int i = 0; i < lstCpe_Detalle.size(); i++)
      {
        Cpe_DetalleBean Cpe_Detalle = (Cpe_DetalleBean)lstCpe_Detalle.get(i);
        if ((Cpe_Detalle.getCOD_TIPO_OPERACION().equals("10")) || (Cpe_Detalle.getCOD_TIPO_OPERACION().equals("40"))) {
          xmlCPE = xmlCPE + "<cac:CreditNoteLine>\n" + "        <cbc:ID>" + Cpe_Detalle.getITEM() + "</cbc:ID>\n" + "<cbc:CreditedQuantity unitCode='" + Cpe_Detalle.getUNIDAD_MEDIDA() + "'>" + Cpe_Detalle.getCANTIDAD() + "</cbc:CreditedQuantity>\n" + "<cbc:LineExtensionAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:LineExtensionAmount>\n" + "        <cac:PricingReference>\n" + "            <cac:AlternativeConditionPrice>\n" + "<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "                <cbc:PriceTypeCode>" + Cpe_Detalle.getPRECIO_TIPO_CODIGO() + "</cbc:PriceTypeCode>\n" + "            </cac:AlternativeConditionPrice>\n" + "        </cac:PricingReference>\n" + "        <cac:TaxTotal>\n" + "<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIGV() + "</cbc:TaxAmount>\n" + "            <cac:TaxSubtotal>\n" + "<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:TaxableAmount>\n" + "<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIGV() + "</cbc:TaxAmount>\n" + "                <cac:TaxCategory>\n" + "                 <cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>E</cbc:ID>\n" + "                    <cbc:Percent>" + Cpe.getPOR_IGV() + "</cbc:Percent>\n" + "<cbc:TaxExemptionReasonCode>" + Cpe_Detalle.getCOD_TIPO_OPERACION() + "</cbc:TaxExemptionReasonCode>\n" + "                    <cac:TaxScheme>\n" + "                        <cbc:ID>1000</cbc:ID>\n" + "                        <cbc:Name>IGV</cbc:Name>\n" + "                        <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>\n" + "                    </cac:TaxScheme>\n" + "                </cac:TaxCategory>\n" + "            </cac:TaxSubtotal>\n" + "        </cac:TaxTotal>\n" + "        <cac:Item>\n" + "<cbc:Description><![CDATA[" + Cpe_Detalle.getDESCRIPCION() + "]]></cbc:Description>\n" + "            <cac:SellersItemIdentification>\n" + "                <cbc:ID><![CDATA[" + Cpe_Detalle.getCODIGO() + "]]></cbc:ID>\n" + "            </cac:SellersItemIdentification>\n" + "        </cac:Item>\n" + "        <cac:Price>\n" + "<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "        </cac:Price>\n" + "    </cac:CreditNoteLine>";
        }
        if (Cpe_Detalle.getCOD_TIPO_OPERACION().equals("20")) {
          xmlCPE = xmlCPE + "<cac:CreditNoteLine>\n" + "        <cbc:ID>" + Cpe_Detalle.getITEM() + "</cbc:ID>\n" + "<cbc:CreditedQuantity unitCode='" + Cpe_Detalle.getUNIDAD_MEDIDA() + "'>" + Cpe_Detalle.getCANTIDAD() + "</cbc:CreditedQuantity>\n" + "<cbc:LineExtensionAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:LineExtensionAmount>\n" + "        <cac:PricingReference>\n" + "            <cac:AlternativeConditionPrice>\n" + "<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "                <cbc:PriceTypeCode>" + Cpe_Detalle.getPRECIO_TIPO_CODIGO() + "</cbc:PriceTypeCode>\n" + "            </cac:AlternativeConditionPrice>\n" + "        </cac:PricingReference>\n" + "        <cac:TaxTotal>\n" + "<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxAmount>\n" + "            <cac:TaxSubtotal>\n" + "<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:TaxableAmount>\n" + "<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxAmount>\n" + "                <cac:TaxCategory>\n" + "                    <cbc:Percent>0</cbc:Percent>\n" + "<cbc:TaxExemptionReasonCode>" + Cpe_Detalle.getCOD_TIPO_OPERACION() + "</cbc:TaxExemptionReasonCode>\n" + "                    <cac:TaxScheme>\n" + "                        <cbc:ID>9997</cbc:ID>\n" + "                        <cbc:Name>EXO</cbc:Name>\n" + "                        <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>\n" + "                    </cac:TaxScheme>\n" + "                </cac:TaxCategory>\n" + "            </cac:TaxSubtotal>\n" + "        </cac:TaxTotal>\n" + "        <cac:Item>\n" + "<cbc:Description><![CDATA[" + Cpe_Detalle.getDESCRIPCION() + "]]></cbc:Description>\n" + "            <cac:SellersItemIdentification>\n" + "                <cbc:ID><![CDATA[" + Cpe_Detalle.getCODIGO() + "]]></cbc:ID>\n" + "            </cac:SellersItemIdentification>\n" + "        </cac:Item>\n" + "        <cac:Price>\n" + "<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "        </cac:Price>\n" + "    </cac:CreditNoteLine>";
        }
        if (Cpe_Detalle.getCOD_TIPO_OPERACION().equals("30")) {
          xmlCPE = xmlCPE + "<cac:CreditNoteLine>\n" + "        <cbc:ID>" + Cpe_Detalle.getITEM() + "</cbc:ID>\n" + "<cbc:CreditedQuantity unitCode='" + Cpe_Detalle.getUNIDAD_MEDIDA() + "'>" + Cpe_Detalle.getCANTIDAD() + "</cbc:CreditedQuantity>\n" + "<cbc:LineExtensionAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:LineExtensionAmount>\n" + "        <cac:PricingReference>\n" + "            <cac:AlternativeConditionPrice>\n" + "                <cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "                <cbc:PriceTypeCode listName='Tipo de Precio' listAgencyName='PE:SUNAT' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16'>02</cbc:PriceTypeCode>\n" + "            </cac:AlternativeConditionPrice>\n" + "        </cac:PricingReference>\n" + "        <cac:TaxTotal>\n" + "<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxAmount>\n" + "            <cac:TaxSubtotal>\n" + "<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxableAmount>\n" + "<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxAmount>\n" + "                <cac:TaxCategory>\n" + "\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>O</cbc:ID>\n" + "                    <cbc:Percent>0</cbc:Percent>\n" + "                    <cbc:TaxExemptionReasonCode listAgencyName='PE:SUNAT' listName='SUNAT:Codigo de Tipo de Afectaci�n del IGV' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07'>" + Cpe_Detalle.getCOD_TIPO_OPERACION() + "</cbc:TaxExemptionReasonCode>\n" + "                    <cac:TaxScheme>\n" + "                        <cbc:ID schemeID='UN/ECE 5153' schemeName='Tax Scheme Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>9998</cbc:ID>\n" + "                        <cbc:Name>INA</cbc:Name>\n" + "                        <cbc:TaxTypeCode>FRE</cbc:TaxTypeCode>\n" + "                    </cac:TaxScheme>\n" + "                </cac:TaxCategory>\n" + "            </cac:TaxSubtotal>\n" + "        </cac:TaxTotal>\n" + "        <cac:Item>\n" + "<cbc:Description><![CDATA[" + Cpe_Detalle.getDESCRIPCION() + "]]></cbc:Description>\n" + "            <cac:SellersItemIdentification>\n" + "                <cbc:ID><![CDATA[" + Cpe_Detalle.getCODIGO() + "]]></cbc:ID>\n" + "            </cac:SellersItemIdentification>\n" + "        </cac:Item>\n" + "        <cac:Price>\n" + "<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:PriceAmount>\n" + "        </cac:Price>\n" + "    </cac:CreditNoteLine>";
        }
        if (Cpe_Detalle.getCOD_TIPO_OPERACION().equals("31")) {
          xmlCPE = xmlCPE + "<cac:CreditNoteLine>\n" + "        <cbc:ID>" + Cpe_Detalle.getITEM() + "</cbc:ID>\n" + "<cbc:CreditedQuantity unitCode='" + Cpe_Detalle.getUNIDAD_MEDIDA() + "'>" + Cpe_Detalle.getCANTIDAD() + "</cbc:CreditedQuantity>\n" + "<cbc:LineExtensionAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:LineExtensionAmount>\n" + "        <cac:PricingReference>\n" + "            <cac:AlternativeConditionPrice>\n" + "<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "                <cbc:PriceTypeCode>02</cbc:PriceTypeCode>\n" + "            </cac:AlternativeConditionPrice>\n" + "        </cac:PricingReference>\n" + "        <cac:TaxTotal>\n" + "<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxAmount>\n" + "            <cac:TaxSubtotal>\n" + "<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:TaxableAmount>\n" + "<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxAmount>\n" + "                <cac:TaxCategory>\n" + "\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>Z</cbc:ID>\n" + "                    <cbc:Percent>0</cbc:Percent>\n" + "<cbc:TaxExemptionReasonCode>" + Cpe_Detalle.getCOD_TIPO_OPERACION() + "</cbc:TaxExemptionReasonCode>\n" + "                    <cac:TaxScheme>\n" + "                        <cbc:ID>9996</cbc:ID>\n" + "                        <cbc:Name>GRA</cbc:Name>\n" + "                        <cbc:TaxTypeCode>FRE</cbc:TaxTypeCode>\n" + "                    </cac:TaxScheme>\n" + "                </cac:TaxCategory>\n" + "            </cac:TaxSubtotal>\n" + "        </cac:TaxTotal>\n" + "        <cac:Item>\n" + "<cbc:Description><![CDATA[" + Cpe_Detalle.getDESCRIPCION() + "]]></cbc:Description>\n" + "            <cac:SellersItemIdentification>\n" + "                <cbc:ID><![CDATA[" + Cpe_Detalle.getCODIGO() + "]]></cbc:ID>\n" + "            </cac:SellersItemIdentification>\n" + "        </cac:Item>\n" + "        <cac:Price>\n" + "<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "        </cac:Price>\n" + "    </cac:CreditNoteLine>";
        }
      }
      xmlCPE = xmlCPE + "\n</CreditNote>";
      
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(xmlCPE)));
      
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();
      
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(RutaXml));
      transformer.transform(source, result);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 1;
  }
  
  public static int GenerarXML_ND(String RutaXml, CpeBean Cpe, List<Cpe_DetalleBean> lstCpe_Detalle)
  {
    try
    {
      String xmlCPE = "";
      
      xmlCPE = "<?xml version='1.0' encoding='UTF-8'?>\n<DebitNote xmlns='urn:oasis:names:specification:ubl:schema:xsd:DebitNote-2' xmlns:cac='urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2' xmlns:cbc='urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2' xmlns:ccts='urn:un:unece:uncefact:documentation:2' xmlns:ds='http://www.w3.org/2000/09/xmldsig#' xmlns:ext='urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2' xmlns:qdt='urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2' xmlns:sac='urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1' xmlns:udt='urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>\n    <ext:UBLExtensions>\n        <ext:UBLExtension>\n            <ext:ExtensionContent>\n            </ext:ExtensionContent>\n        </ext:UBLExtension>\n    </ext:UBLExtensions>\n    <cbc:UBLVersionID>2.1</cbc:UBLVersionID>\n    <cbc:CustomizationID>2.0</cbc:CustomizationID>\n    <cbc:ID>" + Cpe.getNRO_COMPROBANTE() + "</cbc:ID>\n" + "    <cbc:IssueDate>" + Cpe.getFECHA_DOCUMENTO() + "</cbc:IssueDate>\n" + "    <cbc:IssueTime>00:00:00</cbc:IssueTime>\n" + "    <cbc:DocumentCurrencyCode>" + Cpe.getCOD_MONEDA() + "</cbc:DocumentCurrencyCode>\n" + "    <cac:DiscrepancyResponse>\n" + "        <cbc:ReferenceID>" + Cpe.getNRO_DOCUMENTO_MODIFICA() + "</cbc:ReferenceID>\n" + "        <cbc:ResponseCode>" + Cpe.getCOD_TIPO_MOTIVO() + "</cbc:ResponseCode>\n" + "        <cbc:Description><![CDATA[" + Cpe.getDESCRIPCION_MOTIVO() + "]]></cbc:Description>\n" + "    </cac:DiscrepancyResponse>\n" + "    <cac:BillingReference>\n" + "        <cac:InvoiceDocumentReference>\n" + "            <cbc:ID>" + Cpe.getNRO_DOCUMENTO_MODIFICA() + "</cbc:ID>\n" + "            <cbc:DocumentTypeCode>" + Cpe.getTIPO_COMPROBANTE_MODIFICA() + "</cbc:DocumentTypeCode>\n" + "        </cac:InvoiceDocumentReference>\n" + "    </cac:BillingReference>\n" + "    <cac:Signature>\n" + "        <cbc:ID>IDSignST</cbc:ID>\n" + "        <cac:SignatoryParty>\n" + "            <cac:PartyIdentification>\n" + "                <cbc:ID>" + Cpe.getNRO_DOCUMENTO_EMPRESA() + "</cbc:ID>\n" + "            </cac:PartyIdentification>\n" + "            <cac:PartyName>\n" + "                <cbc:Name><![CDATA[" + Cpe.getRAZON_SOCIAL_EMPRESA() + "]]></cbc:Name>\n" + "            </cac:PartyName>\n" + "        </cac:SignatoryParty>\n" + "        <cac:DigitalSignatureAttachment>\n" + "            <cac:ExternalReference>\n" + "                <cbc:URI>#SignatureSP</cbc:URI>\n" + "            </cac:ExternalReference>\n" + "        </cac:DigitalSignatureAttachment>\n" + "    </cac:Signature>\n" + "    <cac:AccountingSupplierParty>\n" + "        <cac:Party>\n" + "            <cac:PartyIdentification>\n" + "                <cbc:ID schemeID='" + Cpe.getTIPO_DOCUMENTO_EMPRESA() + "' schemeName='SUNAT:Identificador de Documento de Identidad' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'>" + Cpe.getNRO_DOCUMENTO_EMPRESA() + "</cbc:ID>\n" + "            </cac:PartyIdentification>\n" + "            <cac:PartyName>\n" + "                <cbc:Name><![CDATA[" + Cpe.getNOMBRE_COMERCIAL_EMPRESA() + "]]></cbc:Name>\n" + "            </cac:PartyName>\n" + "            <cac:PartyLegalEntity>\n" + "                <cbc:RegistrationName><![CDATA[" + Cpe.getRAZON_SOCIAL_EMPRESA() + "]]></cbc:RegistrationName>\n" + "                <cac:RegistrationAddress>\n" + "                    <cbc:AddressTypeCode>0001</cbc:AddressTypeCode>\n" + "                </cac:RegistrationAddress>\n" + "            </cac:PartyLegalEntity>\n" + "        </cac:Party>\n" + "    </cac:AccountingSupplierParty>\n" + "    <cac:AccountingCustomerParty>\n" + "        <cac:Party>\n" + "            <cac:PartyIdentification>\n" + "                <cbc:ID schemeID='" + Cpe.getTIPO_DOCUMENTO_CLIENTE() + "' schemeName='SUNAT:Identificador de Documento de Identidad' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'>" + Cpe.getNRO_DOCUMENTO_CLIENTE() + "</cbc:ID>\n" + "            </cac:PartyIdentification>\n" + "            <cac:PartyLegalEntity>\n" + "<cbc:RegistrationName><![CDATA[" + Cpe.getRAZON_SOCIAL_CLIENTE() + "]]></cbc:RegistrationName>\n" + "            </cac:PartyLegalEntity>\n" + "        </cac:Party>\n" + "    </cac:AccountingCustomerParty>\n" + "    <cac:TaxTotal>\n" + "        <cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_IGV() + "</cbc:TaxAmount>\n" + "        <cac:TaxSubtotal>\n" + "<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_GRAVADAS() + "</cbc:TaxableAmount>\n" + "            <cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_IGV() + "</cbc:TaxAmount>\n" + "            <cac:TaxCategory>\n" + "                <cac:TaxScheme>\n" + "                    <cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>1000</cbc:ID>\n" + "                    <cbc:Name>IGV</cbc:Name>\n" + "                    <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>\n" + "                </cac:TaxScheme>\n" + "            </cac:TaxCategory>\n" + "        </cac:TaxSubtotal>\n" + "    </cac:TaxTotal>\n" + "    <cac:RequestedMonetaryTotal>\n" + "<cbc:PayableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL() + "</cbc:PayableAmount>\n" + "    </cac:RequestedMonetaryTotal>";
      for (int i = 0; i < lstCpe_Detalle.size(); i++)
      {
        Cpe_DetalleBean Cpe_Detalle = (Cpe_DetalleBean)lstCpe_Detalle.get(i);
        
        xmlCPE = xmlCPE + "\n" + "    <cac:DebitNoteLine>\n" + "        <cbc:ID>" + Cpe_Detalle.getITEM() + "</cbc:ID>\n" + "<cbc:DebitedQuantity unitCode='" + Cpe_Detalle.getUNIDAD_MEDIDA() + "'>" + Cpe_Detalle.getCANTIDAD() + "</cbc:DebitedQuantity>\n" + "<cbc:LineExtensionAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:LineExtensionAmount>\n" + "        <cac:PricingReference>\n" + "            <cac:AlternativeConditionPrice>\n" + "<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "<cbc:PriceTypeCode>" + Cpe_Detalle.getPRECIO_TIPO_CODIGO() + "</cbc:PriceTypeCode>\n" + "            </cac:AlternativeConditionPrice>\n" + "        </cac:PricingReference>\n" + "        <cac:TaxTotal>\t\t\n" + "<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIGV() + "</cbc:TaxAmount>\n" + "            <cac:TaxSubtotal>\n" + "                <cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:TaxableAmount>\n" + "                <cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIGV() + "</cbc:TaxAmount>\n" + "                <cac:TaxCategory>\n" + "                    <cbc:Percent>" + Cpe.getPOR_IGV() + "</cbc:Percent>\n" + "<cbc:TaxExemptionReasonCode>" + Cpe_Detalle.getCOD_TIPO_OPERACION() + "</cbc:TaxExemptionReasonCode>\n" + "                    <cac:TaxScheme>\n" + "                        <cbc:ID>1000</cbc:ID>\n" + "                        <cbc:Name>IGV</cbc:Name>\n" + "                        <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>\n" + "                    </cac:TaxScheme>\n" + "                </cac:TaxCategory>\n" + "            </cac:TaxSubtotal>\n" + "        </cac:TaxTotal>\n" + "\t\t\n" + "<cac:Item>\n" + "<cbc:Description><![CDATA[" + Cpe_Detalle.getDESCRIPCION() + "]]></cbc:Description>\n" + "            <cac:SellersItemIdentification>\n" + "                <cbc:ID><![CDATA[" + Cpe_Detalle.getCODIGO() + "]]></cbc:ID>\n" + "            </cac:SellersItemIdentification>\n" + "        </cac:Item>\n" + "<cac:Price>\n" + "<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "</cac:Price>\n" + "    </cac:DebitNoteLine>";
      }
      xmlCPE = xmlCPE + "</DebitNote>";
      
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(xmlCPE)));
      
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();
      
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(RutaXml));
      transformer.transform(source, result);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 1;
  }
  
  public static int GenerarXMLCPE(String RutaXml, CpeBean Cpe, List<Cpe_DetalleBean> lstCpe_Detalle)
  {
    try
    {
      String xmlCPE = "";
      
      xmlCPE = "<?xml version='1.0' encoding='utf-8'?>\n<Invoice xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:cac='urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2' xmlns:cbc='urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2' xmlns:ccts='urn:un:unece:uncefact:documentation:2' xmlns:ds='http://www.w3.org/2000/09/xmldsig#' xmlns:ext='urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2' xmlns:qdt='urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2' xmlns:udt='urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2' xmlns='urn:oasis:names:specification:ubl:schema:xsd:Invoice-2'>\n\t<ext:UBLExtensions>\n\t\t<ext:UBLExtension>\n\t\t\t<ext:ExtensionContent>\n\t\t\t</ext:ExtensionContent>\n\t\t</ext:UBLExtension>\n\t</ext:UBLExtensions>\n\t<cbc:UBLVersionID>2.1</cbc:UBLVersionID>\n\t<cbc:CustomizationID schemeAgencyName='PE:SUNAT'>2.0</cbc:CustomizationID>\n\t<cbc:ProfileID schemeName='Tipo de Operacion' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo51'>" + Cpe.getTIPO_OPERACION() + "</cbc:ProfileID>\n" + "\t<cbc:ID>" + Cpe.getNRO_COMPROBANTE() + "</cbc:ID>\n" + "\t<cbc:IssueDate>" + Cpe.getFECHA_DOCUMENTO() + "</cbc:IssueDate>\n" + "\t<cbc:IssueTime>00:00:00</cbc:IssueTime>\n" + "\t<cbc:DueDate>" + Cpe.getFECHA_VTO() + "</cbc:DueDate>\n" + "\t<cbc:InvoiceTypeCode listAgencyName='PE:SUNAT' listName='Tipo de Documento' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01' listID='" + Cpe.getTIPO_OPERACION() + "' name='Tipo de Operacion' listSchemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo51'>" + Cpe.getCOD_TIPO_DOCUMENTO() + "</cbc:InvoiceTypeCode>\n";
      if (!Cpe.getTOTAL_LETRAS().equals("")) {
        xmlCPE = xmlCPE + "\n" + "<cbc:Note languageLocaleID='1000'>" + Cpe.getTOTAL_LETRAS() + "</cbc:Note>\n";
      }
      xmlCPE = xmlCPE + "\n" + "       <cbc:DocumentCurrencyCode listID='ISO 4217 Alpha' listName='Currency' listAgencyName='United Nations Economic Commission for Europe'>" + Cpe.getCOD_MONEDA() + "</cbc:DocumentCurrencyCode>\n" + "            <cbc:LineCountNumeric>" + lstCpe_Detalle.size() + "</cbc:LineCountNumeric>\n";
      if (!Cpe.getNRO_OTR_COMPROBANTE().equals("")) {
        xmlCPE = xmlCPE + "\n" + "            <cac:OrderReference>\n" + "               <cbc:ID>" + Cpe.getNRO_OTR_COMPROBANTE() + "</cbc:ID>\n" + "            </cac:OrderReference>\n";
      }
      if (!Cpe.getNRO_GUIA_REMISION().equals("")) {
        xmlCPE = xmlCPE + "\n" + "            <cac:DespatchDocumentReference>\n" + "\t\t<cbc:ID>" + Cpe.getNRO_GUIA_REMISION() + "</cbc:ID>\n" + "\t\t<cbc:IssueDate>" + Cpe.getFECHA_GUIA_REMISION() + "</cbc:IssueDate>\n" + "\t\t<cbc:DocumentTypeCode listAgencyName='PE:SUNAT' listName='Tipo de Documento' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01'>" + Cpe.getCOD_GUIA_REMISION() + "</cbc:DocumentTypeCode>\n" + "            </cac:DespatchDocumentReference>\n";
      }
      if (Cpe.getFLG_REGU_ANTICIPO() == 1)
      {
        String tipodocRelacionado = "";
        if (Cpe.getCOD_TIPO_DOCUMENTO().equals("01")) {
          tipodocRelacionado = "02";
        } else {
          tipodocRelacionado = "03";
        }
        xmlCPE = xmlCPE + "<cac:AdditionalDocumentReference>\n" + "<cbc:ID>" + Cpe.getNRO_COMPROBANTE_REF_ANT() + "</cbc:ID>\n" + "<cbc:DocumentTypeCode listAgencyName='PE:SUNAT' listName='Documento Relacionado' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12'>" + tipodocRelacionado + "</cbc:DocumentTypeCode>\n" + "<cbc:DocumentStatusCode listName='Anticipo' listAgencyName='PE:SUNAT'>" + Cpe.getNRO_COMPROBANTE_REF_ANT() + "</cbc:DocumentStatusCode>\n" + "<cac:IssuerParty>\n" + "<cac:PartyIdentification>\n" + "<cbc:ID schemeID='" + Cpe.getTIPO_DOCUMENTO_EMP_REGU_ANT() + "' schemeName='Documento de Identidad' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'>" + Cpe.getNRO_DOCUMENTO_EMP_REGU_ANT() + "</cbc:ID>\n" + "</cac:PartyIdentification>\n" + "</cac:IssuerParty>\n" + "</cac:AdditionalDocumentReference>";
      }
      xmlCPE = xmlCPE + "\n" + "            <cac:Signature>\n" + "\t\t<cbc:ID>" + Cpe.getNRO_COMPROBANTE() + "</cbc:ID>\n" + "\t\t<cac:SignatoryParty>\n" + "\t\t\t<cac:PartyIdentification>\n" + "\t\t\t\t<cbc:ID>" + Cpe.getNRO_DOCUMENTO_EMPRESA() + "</cbc:ID>\n" + "\t\t\t</cac:PartyIdentification>\n" + "\t\t\t<cac:PartyName>\n" + "\t\t\t\t<cbc:Name>" + Cpe.getRAZON_SOCIAL_EMPRESA() + "</cbc:Name>\n" + "\t\t\t</cac:PartyName>\n" + "\t\t</cac:SignatoryParty>\n" + "\t\t<cac:DigitalSignatureAttachment>\n" + "\t\t\t<cac:ExternalReference>\n" + "\t\t\t\t<cbc:URI>#" + Cpe.getNRO_COMPROBANTE() + "</cbc:URI>\n" + "\t\t\t</cac:ExternalReference>\n" + "\t\t</cac:DigitalSignatureAttachment>\n" + "\t</cac:Signature>\n" + "\t<cac:AccountingSupplierParty>\n" + "\t\t<cac:Party>\n" + "\t\t\t<cac:PartyIdentification>\n" + "\t\t\t\t<cbc:ID schemeID='" + Cpe.getTIPO_DOCUMENTO_EMPRESA() + "' schemeName='Documento de Identidad' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'>" + Cpe.getNRO_DOCUMENTO_EMPRESA() + "</cbc:ID>\n" + "\t\t\t</cac:PartyIdentification>\n" + "\t\t\t<cac:PartyName>\n" + "\t\t\t\t<cbc:Name><![CDATA[" + Cpe.getNOMBRE_COMERCIAL_EMPRESA() + "]]></cbc:Name>\n" + "\t\t\t</cac:PartyName>\n" + "\t\t\t<cac:PartyTaxScheme>\n" + "\t\t\t\t<cbc:RegistrationName><![CDATA[" + Cpe.getRAZON_SOCIAL_EMPRESA() + "]]></cbc:RegistrationName>\n" + "\t\t\t\t<cbc:CompanyID schemeID='" + Cpe.getTIPO_DOCUMENTO_EMPRESA() + "' schemeName='SUNAT:Identificador de Documento de Identidad' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'>" + Cpe.getNRO_DOCUMENTO_EMPRESA() + "</cbc:CompanyID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='" + Cpe.getTIPO_DOCUMENTO_EMPRESA() + "' schemeName='SUNAT:Identificador de Documento de Identidad' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'>" + Cpe.getNRO_DOCUMENTO_EMPRESA() + "</cbc:ID>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:PartyTaxScheme>\n" + "\t\t\t<cac:PartyLegalEntity>\n" + "\t\t\t\t<cbc:RegistrationName><![CDATA[" + Cpe.getRAZON_SOCIAL_EMPRESA() + "]]></cbc:RegistrationName>\n" + "\t\t\t\t<cac:RegistrationAddress>\n" + "\t\t\t\t\t<cbc:ID schemeName='Ubigeos' schemeAgencyName='PE:INEI' />\n" + "\t\t\t\t\t<cbc:AddressTypeCode listAgencyName='PE:SUNAT' listName='Establecimientos anexos'>0000</cbc:AddressTypeCode>\n" + "\t\t\t\t\t<cbc:CityName><![CDATA[" + Cpe.getDEPARTAMENTO_EMPRESA() + "]]></cbc:CityName>\n" + "\t\t\t\t\t<cbc:CountrySubentity><![CDATA[" + Cpe.getPROVINCIA_EMPRESA() + "]]></cbc:CountrySubentity>\n" + "\t\t\t\t\t<cbc:District><![CDATA[" + Cpe.getDISTRITO_EMPRESA() + "]]></cbc:District>\n" + "\t\t\t\t\t<cac:AddressLine>\n" + "\t\t\t\t\t\t<cbc:Line><![CDATA[" + Cpe.getDIRECCION_EMPRESA() + "]]></cbc:Line>\n" + "\t\t\t\t\t</cac:AddressLine>\n" + "\t\t\t\t\t<cac:Country>\n" + "\t\t\t\t\t\t<cbc:IdentificationCode listID='ISO 3166-1' listAgencyName='United Nations Economic Commission for Europe' listName='Country'>" + Cpe.getCODIGO_PAIS_EMPRESA() + "</cbc:IdentificationCode>\n" + "\t\t\t\t\t</cac:Country>\n" + "\t\t\t\t</cac:RegistrationAddress>\n" + "\t\t\t</cac:PartyLegalEntity>\n" + "\t\t\t<cac:Contact>\n" + "\t\t\t\t<cbc:Name><![CDATA[" + Cpe.getCONTACTO_EMPRESA() + "]]></cbc:Name>\n" + "\t\t\t</cac:Contact>\n" + "\t\t</cac:Party>\n" + "\t</cac:AccountingSupplierParty>\n" + "\t<cac:AccountingCustomerParty>\n" + "\t\t<cac:Party>\n" + "\t\t\t<cac:PartyIdentification>\n" + "\t\t\t\t<cbc:ID schemeID='" + Cpe.getTIPO_DOCUMENTO_CLIENTE() + "' schemeName='Documento de Identidad' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'>" + Cpe.getNRO_DOCUMENTO_CLIENTE() + "</cbc:ID>\n" + "\t\t\t</cac:PartyIdentification>\n" + "\t\t\t<cac:PartyName>\n" + "\t\t\t\t<cbc:Name><![CDATA[" + Cpe.getRAZON_SOCIAL_CLIENTE() + "]]></cbc:Name>\n" + "\t\t\t</cac:PartyName>\n" + "\t\t\t<cac:PartyTaxScheme>\n" + "\t\t\t\t<cbc:RegistrationName><![CDATA[" + Cpe.getRAZON_SOCIAL_CLIENTE() + "]]></cbc:RegistrationName>\n" + "\t\t\t\t<cbc:CompanyID schemeID='" + Cpe.getTIPO_DOCUMENTO_CLIENTE() + "' schemeName='SUNAT:Identificador de Documento de Identidad' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'>" + Cpe.getNRO_DOCUMENTO_CLIENTE() + "</cbc:CompanyID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='" + Cpe.getTIPO_DOCUMENTO_CLIENTE() + "' schemeName='SUNAT:Identificador de Documento de Identidad' schemeAgencyName='PE:SUNAT' schemeURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06'>" + Cpe.getNRO_DOCUMENTO_CLIENTE() + "</cbc:ID>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:PartyTaxScheme>\n" + "\t\t\t<cac:PartyLegalEntity>\n" + "\t\t\t\t<cbc:RegistrationName><![CDATA[" + Cpe.getRAZON_SOCIAL_CLIENTE() + "]]></cbc:RegistrationName>\n" + "\t\t\t\t<cac:RegistrationAddress>\n" + "\t\t\t\t\t<cbc:ID schemeName='Ubigeos' schemeAgencyName='PE:INEI'>" + Cpe.getCOD_UBIGEO_CLIENTE() + "</cbc:ID>\n" + "\t\t\t\t\t<cbc:CityName><![CDATA[" + Cpe.getDEPARTAMENTO_CLIENTE() + "]]></cbc:CityName>\n" + "\t\t\t\t\t<cbc:CountrySubentity><![CDATA[" + Cpe.getPROVINCIA_CLIENTE() + "]]></cbc:CountrySubentity>\n" + "\t\t\t\t\t<cbc:District><![CDATA[" + Cpe.getDISTRITO_CLIENTE() + "]]></cbc:District>\n" + "\t\t\t\t\t<cac:AddressLine>\n" + "\t\t\t\t\t\t<cbc:Line><![CDATA[" + Cpe.getDIRECCION_CLIENTE() + "]]></cbc:Line>\n" + "\t\t\t\t\t</cac:AddressLine>\n" + "\t\t\t\t\t<cac:Country>\n" + "\t\t\t\t\t\t<cbc:IdentificationCode listID='ISO 3166-1' listAgencyName='United Nations Economic Commission for Europe' listName='Country'>" + Cpe.getCOD_PAIS_CLIENTE() + "</cbc:IdentificationCode>\n" + "\t\t\t\t\t</cac:Country>\n" + "\t\t\t\t</cac:RegistrationAddress>\n" + "\t\t\t</cac:PartyLegalEntity>\n" + "\t\t</cac:Party>\n" + "\t</cac:AccountingCustomerParty>\n";
      if (Cpe.getTOTAL_DETRACCIONES() > 0.0D) {
        xmlCPE = xmlCPE + "<cac:PaymentTerms>\n" + "<cbc:Amount currencyID='PEN'>1.42</cbc:Amount>\n" + "</cac:PaymentTerms>";
      }
      if (Cpe.getFLG_REGU_ANTICIPO() == 1) {
        xmlCPE = xmlCPE + "<cac:PrepaidPayment>\n" + "<cbc:ID schemeName='Anticipo' schemeAgencyName='PE:SUNAT'>" + Cpe.getNRO_COMPROBANTE_REF_ANT() + "</cbc:ID>\n" + "<cbc:PaidAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getMONTO_REGU_ANTICIPO() + "</cbc:PaidAmount>\n" + "</cac:PrepaidPayment>\n";
      }
      xmlCPE = xmlCPE + "<cac:TaxTotal>\n" + "\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_IGV() + "</cbc:TaxAmount>\n" + "\t\t<cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_GRAVADAS() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_IGV() + "</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>S</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>1000</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>IGV</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      if (Cpe.getTOTAL_ISC() > 0.0D) {
        xmlCPE = xmlCPE + "\n" + "                <cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_ISC() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_ISC() + "</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>S</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>2000</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>ISC</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>EXC</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      }
      if (Cpe.getTOTAL_EXPORTACION() > 0.0D) {
        xmlCPE = xmlCPE + "\n" + "                <cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_EXPORTACION() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0.00</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>G</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>9995</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>EXP</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>FRE</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      }
      if (Cpe.getTOTAL_GRATUITAS() > 0.0D) {
        xmlCPE = xmlCPE + "\n" + "                <cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_GRATUITAS() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0.00</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>Z</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>9996</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>GRA</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>FRE</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      }
      if (Cpe.getTOTAL_EXONERADAS() > 0.0D) {
        xmlCPE = xmlCPE + "\n" + "                <cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_EXONERADAS() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0.00</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>E</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>9997</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>EXO</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      }
      if (Cpe.getTOTAL_INAFECTA() > 0.0D) {
        xmlCPE = xmlCPE + "\n" + "                <cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_INAFECTA() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0.00</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>O</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>9998</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>INA</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>FRE</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      }
      if (Cpe.getTOTAL_OTR_IMP() > 0.0D) {
        xmlCPE = xmlCPE + "\n" + "                <cac:TaxSubtotal>\n" + "\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_OTR_IMP() + "</cbc:TaxableAmount>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL_OTR_IMP() + "</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>S</cbc:ID>\n" + "\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeAgencyID='6'>9999</cbc:ID>\n" + "\t\t\t\t\t<cbc:Name>OTR</cbc:Name>\n" + "\t\t\t\t\t<cbc:TaxTypeCode>OTH</cbc:TaxTypeCode>\n" + "\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t</cac:TaxCategory>\n" + "\t\t</cac:TaxSubtotal>";
      }
      xmlCPE = xmlCPE + "\n" + "       </cac:TaxTotal>\n" + "\t<cac:LegalMonetaryTotal>\n";
      if (Cpe.getFLG_REGU_ANTICIPO() == 1) {
        xmlCPE = xmlCPE + "<cbc:PrepaidAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getMONTO_REGU_ANTICIPO() + "</cbc:PrepaidAmount>\n" + "           <cbc:PayableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0.00</cbc:PayableAmount>\n";
      } else {
        xmlCPE = xmlCPE + "<cbc:PayableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe.getTOTAL() + "</cbc:PayableAmount>\n";
      }
      xmlCPE = xmlCPE + "\t</cac:LegalMonetaryTotal>\n";
      for (int i = 0; i < lstCpe_Detalle.size(); i++)
      {
        Cpe_DetalleBean Cpe_Detalle = (Cpe_DetalleBean)lstCpe_Detalle.get(i);
        if ((Cpe_Detalle.getCOD_TIPO_OPERACION().equals("10")) || (Cpe_Detalle.getCOD_TIPO_OPERACION().equals("40"))) {
          xmlCPE = xmlCPE + "<cac:InvoiceLine>\n" + "\t\t<cbc:ID>" + Cpe_Detalle.getITEM() + "</cbc:ID>\n" + "\t\t<cbc:InvoicedQuantity unitCode='" + Cpe_Detalle.getUNIDAD_MEDIDA() + "' unitCodeListID='UN/ECE rec 20' unitCodeListAgencyName='United Nations Economic Commission for Europe'>" + Cpe_Detalle.getCANTIDAD() + "</cbc:InvoicedQuantity>\n" + "\t\t<cbc:LineExtensionAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:LineExtensionAmount>\n" + "\t\t<cac:PricingReference>\n" + "\t\t\t<cac:AlternativeConditionPrice>\n" + "\t\t\t\t<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "\t\t\t\t<cbc:PriceTypeCode listName='Tipo de Precio' listAgencyName='PE:SUNAT' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16'>" + Cpe_Detalle.getPRECIO_TIPO_CODIGO() + "</cbc:PriceTypeCode>\n" + "\t\t\t</cac:AlternativeConditionPrice>\n" + "\t\t</cac:PricingReference>\n" + "\t\t<cac:TaxTotal>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIGV() + "</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxSubtotal>\n" + "\t\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:TaxableAmount>\n" + "\t\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIGV() + "</cbc:TaxAmount>\n" + "\t\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>S</cbc:ID>\n" + "\t\t\t\t\t<cbc:Percent>" + Cpe.getPOR_IGV() + "</cbc:Percent>\n" + "\t\t\t\t\t<cbc:TaxExemptionReasonCode listAgencyName='PE:SUNAT' listName='SUNAT:Codigo de Tipo de Afectaci�n del IGV' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07'>" + Cpe_Detalle.getCOD_TIPO_OPERACION() + "</cbc:TaxExemptionReasonCode>\n" + "\t\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeName='Tax Scheme Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>1000</cbc:ID>\n" + "\t\t\t\t\t\t<cbc:Name>IGV</cbc:Name>\n" + "\t\t\t\t\t\t<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>\n" + "\t\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t\t</cac:TaxCategory>\n" + "\t\t\t</cac:TaxSubtotal>\n" + "\t\t</cac:TaxTotal>\n" + "\t\t<cac:Item>\n" + "\t\t\t<cbc:Description><![CDATA[" + Cpe_Detalle.getDESCRIPCION() + "]]></cbc:Description>\n" + "\t\t\t<cac:SellersItemIdentification>\n" + "\t\t\t\t<cbc:ID><![CDATA[" + Cpe_Detalle.getCODIGO() + "]]></cbc:ID>\n" + "\t\t\t</cac:SellersItemIdentification>\n" + "\t\t</cac:Item>\n" + "\t\t<cac:Price>\n" + "\t\t\t<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO_SIN_IMPUESTO() + "</cbc:PriceAmount>\n" + "\t\t</cac:Price>\n" + "\t</cac:InvoiceLine>";
        }
        if (Cpe_Detalle.getCOD_TIPO_OPERACION().equals("20")) {
          xmlCPE = xmlCPE + "<cac:InvoiceLine>\n" + "\t\t<cbc:ID>" + Cpe_Detalle.getITEM() + "</cbc:ID>\n" + "\t\t<cbc:InvoicedQuantity unitCode='" + Cpe_Detalle.getUNIDAD_MEDIDA() + "' unitCodeListID='UN/ECE rec 20' unitCodeListAgencyName='United Nations Economic Commission for Europe'>" + Cpe_Detalle.getCANTIDAD() + "</cbc:InvoicedQuantity>\n" + "\t\t<cbc:LineExtensionAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:LineExtensionAmount>\n" + "\t\t<cac:PricingReference>\n" + "\t\t\t<cac:AlternativeConditionPrice>\n" + "\t\t\t\t<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "\t\t\t\t<cbc:PriceTypeCode listName='Tipo de Precio' listAgencyName='PE:SUNAT' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16'>" + Cpe_Detalle.getPRECIO_TIPO_CODIGO() + "</cbc:PriceTypeCode>\n" + "\t\t\t</cac:AlternativeConditionPrice>\n" + "\t\t</cac:PricingReference>\n" + "\t\t<cac:TaxTotal>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxSubtotal>\n" + "\t\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:TaxableAmount>\n" + "\t\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxAmount>\n" + "\t\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>E</cbc:ID>\n" + "\t\t\t\t\t<cbc:Percent>0</cbc:Percent>\n" + "\t\t\t\t\t<cbc:TaxExemptionReasonCode listAgencyName='PE:SUNAT' listName='SUNAT:Codigo de Tipo de Afectaci�n del IGV' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07'>" + Cpe_Detalle.getCOD_TIPO_OPERACION() + "</cbc:TaxExemptionReasonCode>\n" + "\t\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeName='Tax Scheme Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>9997</cbc:ID>\n" + "\t\t\t\t\t\t<cbc:Name>EXO</cbc:Name>\n" + "\t\t\t\t\t\t<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>\n" + "\t\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t\t</cac:TaxCategory>\n" + "\t\t\t</cac:TaxSubtotal>\n" + "\t\t</cac:TaxTotal>\n" + "\t\t<cac:Item>\n" + "\t\t\t<cbc:Description><![CDATA[" + Cpe_Detalle.getDESCRIPCION() + "]]></cbc:Description>\n" + "\t\t\t<cac:SellersItemIdentification>\n" + "\t\t\t\t<cbc:ID><![CDATA[" + Cpe_Detalle.getCODIGO() + "]]></cbc:ID>\n" + "\t\t\t</cac:SellersItemIdentification>\n" + "\t\t</cac:Item>\n" + "\t\t<cac:Price>\n" + "\t\t\t<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO_SIN_IMPUESTO() + "</cbc:PriceAmount>\n" + "\t\t</cac:Price>\n" + "\t</cac:InvoiceLine>";
        }
        if (Cpe_Detalle.getCOD_TIPO_OPERACION().equals("30")) {
          xmlCPE = xmlCPE + "<cac:InvoiceLine>\n" + "\t\t<cbc:ID>" + Cpe_Detalle.getITEM() + "</cbc:ID>\n" + "\t\t<cbc:InvoicedQuantity unitCode='" + Cpe_Detalle.getUNIDAD_MEDIDA() + "' unitCodeListID='UN/ECE rec 20' unitCodeListAgencyName='United Nations Economic Commission for Europe'>" + Cpe_Detalle.getCANTIDAD() + "</cbc:InvoicedQuantity>\n" + "\t\t<cbc:LineExtensionAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:LineExtensionAmount>\n" + "\t\t<cac:PricingReference>\n" + "\t\t\t<cac:AlternativeConditionPrice>\n" + "\t\t\t\t<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "\t\t\t\t<cbc:PriceTypeCode listName='Tipo de Precio' listAgencyName='PE:SUNAT' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16'>01</cbc:PriceTypeCode>\n" + "\t\t\t</cac:AlternativeConditionPrice>\n" + "\t\t</cac:PricingReference>\n" + "\t\t<cac:TaxTotal>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxSubtotal>\n" + "\t\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:TaxableAmount>\n" + "\t\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxAmount>\n" + "\t\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>O</cbc:ID>\n" + "\t\t\t\t\t<cbc:Percent>" + Cpe.getPOR_IGV() + "</cbc:Percent>\n" + "\t\t\t\t\t<cbc:TaxExemptionReasonCode listAgencyName='PE:SUNAT' listName='SUNAT:Codigo de Tipo de Afectaci�n del IGV' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07'>" + Cpe_Detalle.getCOD_TIPO_OPERACION() + "</cbc:TaxExemptionReasonCode>\n" + "\t\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeName='Tax Scheme Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>9998</cbc:ID>\n" + "\t\t\t\t\t\t<cbc:Name>INA</cbc:Name>\n" + "\t\t\t\t\t\t<cbc:TaxTypeCode>FRE</cbc:TaxTypeCode>\n" + "\t\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t\t</cac:TaxCategory>\n" + "\t\t\t</cac:TaxSubtotal>\n" + "\t\t</cac:TaxTotal>\n" + "\t\t<cac:Item>\n" + "\t\t\t<cbc:Description><![CDATA[" + Cpe_Detalle.getDESCRIPCION() + "]]></cbc:Description>\n" + "\t\t\t<cac:SellersItemIdentification>\n" + "\t\t\t\t<cbc:ID><![CDATA[" + Cpe_Detalle.getCODIGO() + "]]></cbc:ID>\n" + "\t\t\t</cac:SellersItemIdentification>\n" + "\t\t</cac:Item>\n" + "\t\t<cac:Price>\n" + "\t\t\t<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getPRECIO() + "</cbc:PriceAmount>\n" + "\t\t</cac:Price>\n" + "\t</cac:InvoiceLine>";
        }
        if (Cpe_Detalle.getCOD_TIPO_OPERACION().equals("31")) {
          xmlCPE = xmlCPE + "<cac:InvoiceLine>\n" + "\t\t<cbc:ID>" + Cpe_Detalle.getITEM() + "</cbc:ID>\n" + "\t\t<cbc:InvoicedQuantity unitCode='" + Cpe_Detalle.getUNIDAD_MEDIDA() + "' unitCodeListID='UN/ECE rec 20' unitCodeListAgencyName='United Nations Economic Commission for Europe'>" + Cpe_Detalle.getCANTIDAD() + "</cbc:InvoicedQuantity>\n" + "\t\t<cbc:LineExtensionAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:LineExtensionAmount>\n" + "\t\t<cac:PricingReference>\n" + "\t\t\t<cac:AlternativeConditionPrice>\n" + "\t\t\t\t<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:PriceAmount>\n" + "\t\t\t\t<cbc:PriceTypeCode listName='Tipo de Precio' listAgencyName='PE:SUNAT' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16'>02</cbc:PriceTypeCode>\n" + "\t\t\t</cac:AlternativeConditionPrice>\n" + "\t\t</cac:PricingReference>\n" + "\t\t<cac:TaxTotal>\n" + "\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxAmount>\n" + "\t\t\t<cac:TaxSubtotal>\n" + "\t\t\t\t<cbc:TaxableAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>" + Cpe_Detalle.getIMPORTE() + "</cbc:TaxableAmount>\n" + "\t\t\t\t<cbc:TaxAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:TaxAmount>\n" + "\t\t\t\t<cac:TaxCategory>\n" + "\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5305' schemeName='Tax Category Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>E</cbc:ID>\n" + "\t\t\t\t\t<cbc:Percent>" + Cpe.getPOR_IGV() + "</cbc:Percent>\n" + "\t\t\t\t\t<cbc:TaxExemptionReasonCode listAgencyName='PE:SUNAT' listName='SUNAT:Codigo de Tipo de Afectaci�n del IGV' listURI='urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07'>" + Cpe_Detalle.getCOD_TIPO_OPERACION() + "</cbc:TaxExemptionReasonCode>\n" + "\t\t\t\t\t<cac:TaxScheme>\n" + "\t\t\t\t\t\t<cbc:ID schemeID='UN/ECE 5153' schemeName='Tax Scheme Identifier' schemeAgencyName='United Nations Economic Commission for Europe'>9996</cbc:ID>\n" + "\t\t\t\t\t\t<cbc:Name>GRA</cbc:Name>\n" + "\t\t\t\t\t\t<cbc:TaxTypeCode>FRE</cbc:TaxTypeCode>\n" + "\t\t\t\t\t</cac:TaxScheme>\n" + "\t\t\t\t</cac:TaxCategory>\n" + "\t\t\t</cac:TaxSubtotal>\n" + "\t\t</cac:TaxTotal>\n" + "\t\t<cac:Item>\n" + "\t\t\t<cbc:Description><![CDATA[" + Cpe_Detalle.getDESCRIPCION() + "]]></cbc:Description>\n" + "\t\t\t<cac:SellersItemIdentification>\n" + "\t\t\t\t<cbc:ID><![CDATA[" + Cpe_Detalle.getCODIGO() + "]]></cbc:ID>\n" + "\t\t\t</cac:SellersItemIdentification>\n" + "\t\t</cac:Item>\n" + "\t\t<cac:Price>\n" + "\t\t\t<cbc:PriceAmount currencyID='" + Cpe.getCOD_MONEDA() + "'>0</cbc:PriceAmount>\n" + "\t\t</cac:Price>\n" + "\t</cac:InvoiceLine>";
        }
      }
      xmlCPE = xmlCPE + "</Invoice>";
      
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(xmlCPE)));
      
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();
      
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(RutaXml));
      transformer.transform(source, result);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 1;
  }
  
  public int GenerarXMLCPE_RT(String RutaXml, Cpe_RetencionBean Cpe_Retencion, List<Cpe_Retencion_DetalleBean> lstCpe_Retencion_Detalle)
  {
    DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
    try
    {
      DocumentBuilder icBuilder = icFactory.newDocumentBuilder();
      Document doc = icBuilder.newDocument();
      
      doc.setXmlVersion("1.0");
      Element mainRootElement = doc.createElementNS("urn:sunat:names:specification:ubl:peru:schema:xsd:Retention-1", "Retention");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ccts", "urn:un:unece:uncefact:documentation:2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:qdt", "urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:sac", "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:udt", "urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
      
      doc.appendChild(mainRootElement);
      
      Element UBLExtensions1 = doc.createElement("ext:UBLExtensions");
      Element UBLExtensionFirma = doc.createElement("ext:UBLExtension");
      Element ExtensionContentFirma = doc.createElement("ext:ExtensionContent");
      UBLExtensionFirma.appendChild(ExtensionContentFirma);
      UBLExtensions1.appendChild(UBLExtensionFirma);
      mainRootElement.appendChild(UBLExtensions1);
      
      Element UBLVersionID = doc.createElement("cbc:UBLVersionID");
      UBLVersionID.appendChild(doc.createTextNode("2.0"));
      mainRootElement.appendChild(UBLVersionID);
      
      Element CustomizationID = doc.createElement("cbc:CustomizationID");
      CustomizationID.appendChild(doc.createTextNode("1.0"));
      mainRootElement.appendChild(CustomizationID);
      
      Element Signature = doc.createElement("cac:Signature");
      Element IDSignature = doc.createElement("cbc:ID");
      IDSignature.appendChild(doc.createTextNode(Cpe_Retencion.getNRO_COMPROBANTE()));
      Signature.appendChild(IDSignature);
      
      Element SignatoryParty = doc.createElement("cac:SignatoryParty");
      
      Element PartyIdentification = doc.createElement("cac:PartyIdentification");
      Element ID_PartyIdentification = doc.createElement("cbc:ID");
      ID_PartyIdentification.appendChild(doc.createTextNode(Cpe_Retencion.getNRO_DOCUMENTO_EMPRESA()));
      PartyIdentification.appendChild(ID_PartyIdentification);
      SignatoryParty.appendChild(PartyIdentification);
      
      Element PartyName = doc.createElement("cac:PartyName");
      Element Name = doc.createElement("cbc:Name");
      Name.appendChild(doc.createCDATASection(Cpe_Retencion.getRAZON_SOCIAL_EMPRESA()));
      PartyName.appendChild(Name);
      SignatoryParty.appendChild(PartyName);
      
      Signature.appendChild(SignatoryParty);
      
      Element DigitalSignatureAttachment = doc.createElement("cac:DigitalSignatureAttachment");
      Element ExternalReference = doc.createElement("cac:ExternalReference");
      Element URI = doc.createElement("cbc:URI");
      URI.appendChild(doc.createTextNode(Cpe_Retencion.getNRO_COMPROBANTE()));
      ExternalReference.appendChild(URI);
      DigitalSignatureAttachment.appendChild(ExternalReference);
      Signature.appendChild(DigitalSignatureAttachment);
      
      mainRootElement.appendChild(Signature);
      
      Element IDNRO_DOCUMENTO = doc.createElement("cbc:ID");
      IDNRO_DOCUMENTO.appendChild(doc.createTextNode(Cpe_Retencion.getNRO_COMPROBANTE()));
      mainRootElement.appendChild(IDNRO_DOCUMENTO);
      
      Element IssueDate = doc.createElement("cbc:IssueDate");
      IssueDate.appendChild(doc.createTextNode(Cpe_Retencion.getFECHA_DOCUMENTO()));
      mainRootElement.appendChild(IssueDate);
      
      Element AgentParty = doc.createElement("cac:AgentParty");
      
      Element PartyIdentificationEmpresa = doc.createElement("cac:PartyIdentification");
      Element IDPartyEmpresa = doc.createElement("cbc:ID");
      IDPartyEmpresa.setAttribute("schemeID", Cpe_Retencion.getTIPO_DOCUMENTO_EMPRESA());
      IDPartyEmpresa.appendChild(doc.createTextNode(Cpe_Retencion.getNRO_DOCUMENTO_EMPRESA()));
      PartyIdentificationEmpresa.appendChild(IDPartyEmpresa);
      AgentParty.appendChild(PartyIdentificationEmpresa);
      
      Element PartyNameEmpresa = doc.createElement("cac:PartyName");
      Element NameEmpresa = doc.createElement("cbc:Name");
      NameEmpresa.appendChild(doc.createCDATASection(Cpe_Retencion.getNOMBRE_COMERCIAL_EMPRESA()));
      PartyNameEmpresa.appendChild(NameEmpresa);
      AgentParty.appendChild(PartyNameEmpresa);
      
      Element PostalAddress = doc.createElement("cac:PostalAddress");
      
      Element ID_PostalAddress = doc.createElement("cbc:ID");
      ID_PostalAddress.appendChild(doc.createTextNode(Cpe_Retencion.getCOD_UBIGEO_EMPRESA()));
      PostalAddress.appendChild(ID_PostalAddress);
      
      Element StreetName = doc.createElement("cbc:StreetName");
      StreetName.appendChild(doc.createCDATASection(Cpe_Retencion.getDIRECCION_EMPRESA()));
      PostalAddress.appendChild(StreetName);
      
      Element CitySubdivisionName = doc.createElement("cbc:CitySubdivisionName");
      CitySubdivisionName.appendChild(doc.createCDATASection(""));
      PostalAddress.appendChild(CitySubdivisionName);
      
      Element CityName = doc.createElement("cbc:CityName");
      CityName.appendChild(doc.createCDATASection(Cpe_Retencion.getDEPARTAMENTO_EMPRESA()));
      PostalAddress.appendChild(CityName);
      
      Element CountrySubentity = doc.createElement("cbc:CountrySubentity");
      CountrySubentity.appendChild(doc.createCDATASection(Cpe_Retencion.getPROVINCIA_EMPRESA()));
      PostalAddress.appendChild(CountrySubentity);
      
      Element District = doc.createElement("cbc:District");
      District.appendChild(doc.createCDATASection(Cpe_Retencion.getDISTRITO_EMPRESA()));
      PostalAddress.appendChild(District);
      
      Element Country = doc.createElement("cac:Country");
      Element IdentificationCode = doc.createElement("cbc:IdentificationCode");
      IdentificationCode.appendChild(doc.createTextNode(Cpe_Retencion.getCOD_PAIS_EMPRESA()));
      Country.appendChild(IdentificationCode);
      PostalAddress.appendChild(Country);
      
      AgentParty.appendChild(PostalAddress);
      
      Element PartyLegalEntity = doc.createElement("cac:PartyLegalEntity");
      Element RegistrationName = doc.createElement("cbc:RegistrationName");
      RegistrationName.appendChild(doc.createCDATASection(Cpe_Retencion.getRAZON_SOCIAL_EMPRESA()));
      PartyLegalEntity.appendChild(RegistrationName);
      AgentParty.appendChild(PartyLegalEntity);
      
      mainRootElement.appendChild(AgentParty);
      
      Element ReceiverParty = doc.createElement("cac:ReceiverParty");
      
      Element PartyIdentificationProveedor = doc.createElement("cac:PartyIdentification");
      Element IDPartyProveedor = doc.createElement("cbc:ID");
      IDPartyProveedor.setAttribute("schemeID", Cpe_Retencion.getTIPO_DOCUMENTO_PROVEEDOR());
      IDPartyProveedor.appendChild(doc.createTextNode(Cpe_Retencion.getNRO_DOCUMENTO_PROVEEDOR()));
      PartyIdentificationProveedor.appendChild(IDPartyProveedor);
      ReceiverParty.appendChild(PartyIdentificationProveedor);
      
      Element PartyNameProveedor = doc.createElement("cac:PartyName");
      Element NameProveedor = doc.createElement("cbc:Name");
      NameProveedor.appendChild(doc.createCDATASection(Cpe_Retencion.getNOMBRE_COMERCIAL_PROVEEDOR()));
      PartyNameProveedor.appendChild(NameProveedor);
      ReceiverParty.appendChild(PartyNameProveedor);
      
      Element PostalAddressProveedor = doc.createElement("cac:PostalAddress");
      
      Element ID_PostalAddressProveedor = doc.createElement("cbc:ID");
      ID_PostalAddressProveedor.appendChild(doc.createTextNode(Cpe_Retencion.getCOD_UBIGEO_PROVEEDOR()));
      PostalAddressProveedor.appendChild(ID_PostalAddressProveedor);
      
      Element StreetNameProveedor = doc.createElement("cbc:StreetName");
      StreetNameProveedor.appendChild(doc.createCDATASection(Cpe_Retencion.getDIRECCION_PROVEEDOR()));
      PostalAddressProveedor.appendChild(StreetNameProveedor);
      
      Element CitySubdivisionNameProveedor = doc.createElement("cbc:CitySubdivisionName");
      CitySubdivisionNameProveedor.appendChild(doc.createCDATASection(""));
      PostalAddressProveedor.appendChild(CitySubdivisionNameProveedor);
      
      Element CityNameProveedor = doc.createElement("cbc:CityName");
      CityNameProveedor.appendChild(doc.createCDATASection(Cpe_Retencion.getDEPARTAMENTO_PROVEEDOR()));
      PostalAddressProveedor.appendChild(CityNameProveedor);
      
      Element CountrySubentityProveedor = doc.createElement("cbc:CountrySubentity");
      CountrySubentityProveedor.appendChild(doc.createCDATASection(Cpe_Retencion.getPROVINCIA_PROVEEDOR()));
      PostalAddressProveedor.appendChild(CountrySubentityProveedor);
      
      Element DistrictProveedor = doc.createElement("cbc:District");
      DistrictProveedor.appendChild(doc.createCDATASection(Cpe_Retencion.getDISTRITO_PROVEEDOR()));
      PostalAddressProveedor.appendChild(DistrictProveedor);
      
      Element CountryProveedor = doc.createElement("cac:Country");
      Element IdentificationCodeProveedor = doc.createElement("cbc:IdentificationCode");
      IdentificationCodeProveedor.appendChild(doc.createTextNode(Cpe_Retencion.getPAIS_PROVEEDOR()));
      CountryProveedor.appendChild(IdentificationCodeProveedor);
      PostalAddressProveedor.appendChild(CountryProveedor);
      
      ReceiverParty.appendChild(PostalAddressProveedor);
      
      Element PartyLegalEntityProveedor = doc.createElement("cac:PartyLegalEntity");
      Element RegistrationNameProveedor = doc.createElement("cbc:RegistrationName");
      RegistrationNameProveedor.appendChild(doc.createCDATASection(Cpe_Retencion.getRAZON_SOCIAL_PROVEEDOR()));
      PartyLegalEntityProveedor.appendChild(RegistrationNameProveedor);
      ReceiverParty.appendChild(PartyLegalEntityProveedor);
      
      mainRootElement.appendChild(ReceiverParty);
      
      Element SUNATRetentionSystemCode = doc.createElement("sac:SUNATRetentionSystemCode");
      SUNATRetentionSystemCode.appendChild(doc.createTextNode(Cpe_Retencion.getTIPO_RETENCION()));
      mainRootElement.appendChild(SUNATRetentionSystemCode);
      
      Element SUNATRetentionPercent = doc.createElement("sac:SUNATRetentionPercent");
      SUNATRetentionPercent.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion.getPORCENTAJE_RETENCION())));
      mainRootElement.appendChild(SUNATRetentionPercent);
      
      Element Note = doc.createElement("cbc:Note");
      Note.appendChild(doc.createTextNode(Cpe_Retencion.getNOTA()));
      mainRootElement.appendChild(Note);
      
      Element TotalInvoiceAmount = doc.createElement("cbc:TotalInvoiceAmount");
      TotalInvoiceAmount.setAttribute("currencyID", Cpe_Retencion.getMONEDA());
      TotalInvoiceAmount.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion.getTOTAL_RETENCION())));
      mainRootElement.appendChild(TotalInvoiceAmount);
      
      Element SUNATTotalPaid = doc.createElement("sac:SUNATTotalPaid");
      SUNATTotalPaid.setAttribute("currencyID", Cpe_Retencion.getMONEDA());
      SUNATTotalPaid.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion.getNETO_RETENCION())));
      mainRootElement.appendChild(SUNATTotalPaid);
      for (int i = 0; i < lstCpe_Retencion_Detalle.size(); i++)
      {
        Cpe_Retencion_DetalleBean Cpe_Retencion_Detalle = (Cpe_Retencion_DetalleBean)lstCpe_Retencion_Detalle.get(i);
        Element SUNATRetentionDocumentReference = doc.createElement("sac:SUNATRetentionDocumentReference");
        
        Element IDCOMPROBANTE = doc.createElement("cbc:ID");
        IDCOMPROBANTE.setAttribute("schemeID", Cpe_Retencion_Detalle.getCOD_TIPO_DOCUMENTO());
        IDCOMPROBANTE.appendChild(doc.createTextNode(Cpe_Retencion_Detalle.getNRO_DOCUMENTO()));
        SUNATRetentionDocumentReference.appendChild(IDCOMPROBANTE);
        
        Element IssueDateCOMPROBANTE = doc.createElement("cbc:IssueDate");
        IssueDateCOMPROBANTE.appendChild(doc.createTextNode(Cpe_Retencion_Detalle.getFECHA_DOCUMENTO()));
        SUNATRetentionDocumentReference.appendChild(IssueDateCOMPROBANTE);
        
        Element TotalInvoiceAmountCOMPROBANTE = doc.createElement("cbc:TotalInvoiceAmount");
        TotalInvoiceAmountCOMPROBANTE.setAttribute("currencyID", Cpe_Retencion_Detalle.getCOD_MONEDA());
        TotalInvoiceAmountCOMPROBANTE.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion_Detalle.getMONTO_TOTAL())));
        SUNATRetentionDocumentReference.appendChild(TotalInvoiceAmountCOMPROBANTE);
        
        Element PaymentPAGO = doc.createElement("cac:Payment");
        
        Element IDPAGO = doc.createElement("cbc:ID");
        IDPAGO.appendChild(doc.createTextNode(Cpe_Retencion_Detalle.getNRO_DOC_PAGO()));
        PaymentPAGO.appendChild(IDPAGO);
        
        Element PaidAmount = doc.createElement("cbc:PaidAmount");
        PaidAmount.setAttribute("currencyID", Cpe_Retencion_Detalle.getCOD_MONEDA());
        PaidAmount.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion_Detalle.getMONTO_TOTAL())));
        PaymentPAGO.appendChild(PaidAmount);
        
        Element PaidDate = doc.createElement("cbc:PaidDate");
        PaidDate.appendChild(doc.createTextNode(Cpe_Retencion_Detalle.getFECHA_PAGO()));
        PaymentPAGO.appendChild(PaidDate);
        
        SUNATRetentionDocumentReference.appendChild(PaymentPAGO);
        
        Element SUNATRetentionInformation = doc.createElement("sac:SUNATRetentionInformation");
        
        Element SUNATRetentionAmount = doc.createElement("sac:SUNATRetentionAmount");
        SUNATRetentionAmount.setAttribute("currencyID", Cpe_Retencion_Detalle.getMONEDA_RETENIDA());
        SUNATRetentionAmount.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion_Detalle.getMONTO_RETENIDO())));
        SUNATRetentionInformation.appendChild(SUNATRetentionAmount);
        
        Element SUNATRetentionDate = doc.createElement("sac:SUNATRetentionDate");
        SUNATRetentionDate.appendChild(doc.createTextNode(Cpe_Retencion_Detalle.getFECHA_PAGO()));
        SUNATRetentionInformation.appendChild(SUNATRetentionDate);
        
        Element SUNATNetTotalPaid = doc.createElement("sac:SUNATNetTotalPaid");
        SUNATNetTotalPaid.setAttribute("currencyID", Cpe_Retencion_Detalle.getMONEDA_PAGO_NETO());
        SUNATNetTotalPaid.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion_Detalle.getMONTO_PAGO_NETO())));
        SUNATRetentionInformation.appendChild(SUNATNetTotalPaid);
        
        SUNATRetentionDocumentReference.appendChild(SUNATRetentionInformation);
        
        mainRootElement.appendChild(SUNATRetentionDocumentReference);
      }
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("encoding", "ISO-8859-1");
      DOMSource source = new DOMSource(doc);
      StreamResult console = new StreamResult(System.out);
      transformer.transform(source, console);
      
      DOMSource domSource = new DOMSource(doc);
      
      FileWriter out = new FileWriter(RutaXml);
      
      transformer.transform(domSource, new StreamResult(out));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 1;
  }
  
  public int GenerarXMLCPE_PC(String RutaXml, Cpe_RetencionBean Cpe_Retencion, List<Cpe_Retencion_DetalleBean> lstCpe_Retencion_Detalle)
  {
    DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
    try
    {
      DocumentBuilder icBuilder = icFactory.newDocumentBuilder();
      Document doc = icBuilder.newDocument();
      
      doc.setXmlVersion("1.0");
      Element mainRootElement = doc.createElementNS("urn:sunat:names:specification:ubl:peru:schema:xsd:Perception-1", "Perception");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ccts", "urn:un:unece:uncefact:documentation:2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:qdt", "urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:sac", "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:udt", "urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
      
      doc.appendChild(mainRootElement);
      
      Element UBLExtensions1 = doc.createElement("ext:UBLExtensions");
      Element UBLExtensionFirma = doc.createElement("ext:UBLExtension");
      Element ExtensionContentFirma = doc.createElement("ext:ExtensionContent");
      UBLExtensionFirma.appendChild(ExtensionContentFirma);
      UBLExtensions1.appendChild(UBLExtensionFirma);
      mainRootElement.appendChild(UBLExtensions1);
      
      Element UBLVersionID = doc.createElement("cbc:UBLVersionID");
      UBLVersionID.appendChild(doc.createTextNode("2.0"));
      mainRootElement.appendChild(UBLVersionID);
      
      Element CustomizationID = doc.createElement("cbc:CustomizationID");
      CustomizationID.appendChild(doc.createTextNode("1.0"));
      mainRootElement.appendChild(CustomizationID);
      
      Element Signature = doc.createElement("cac:Signature");
      Element IDSignature = doc.createElement("cbc:ID");
      IDSignature.appendChild(doc.createTextNode(Cpe_Retencion.getNRO_COMPROBANTE()));
      Signature.appendChild(IDSignature);
      
      Element SignatoryParty = doc.createElement("cac:SignatoryParty");
      
      Element PartyIdentification = doc.createElement("cac:PartyIdentification");
      Element ID_PartyIdentification = doc.createElement("cbc:ID");
      ID_PartyIdentification.appendChild(doc.createTextNode(Cpe_Retencion.getNRO_DOCUMENTO_EMPRESA()));
      PartyIdentification.appendChild(ID_PartyIdentification);
      SignatoryParty.appendChild(PartyIdentification);
      
      Element PartyName = doc.createElement("cac:PartyName");
      Element Name = doc.createElement("cbc:Name");
      Name.appendChild(doc.createCDATASection(Cpe_Retencion.getRAZON_SOCIAL_EMPRESA()));
      PartyName.appendChild(Name);
      SignatoryParty.appendChild(PartyName);
      
      Signature.appendChild(SignatoryParty);
      
      Element DigitalSignatureAttachment = doc.createElement("cac:DigitalSignatureAttachment");
      Element ExternalReference = doc.createElement("cac:ExternalReference");
      Element URI = doc.createElement("cbc:URI");
      URI.appendChild(doc.createTextNode(Cpe_Retencion.getNRO_COMPROBANTE()));
      ExternalReference.appendChild(URI);
      DigitalSignatureAttachment.appendChild(ExternalReference);
      Signature.appendChild(DigitalSignatureAttachment);
      
      mainRootElement.appendChild(Signature);
      
      Element IDNRO_DOCUMENTO = doc.createElement("cbc:ID");
      IDNRO_DOCUMENTO.appendChild(doc.createTextNode(Cpe_Retencion.getNRO_COMPROBANTE()));
      mainRootElement.appendChild(IDNRO_DOCUMENTO);
      
      Element IssueDate = doc.createElement("cbc:IssueDate");
      IssueDate.appendChild(doc.createTextNode(Cpe_Retencion.getFECHA_DOCUMENTO()));
      mainRootElement.appendChild(IssueDate);
      
      Element AgentParty = doc.createElement("cac:AgentParty");
      
      Element PartyIdentificationEmpresa = doc.createElement("cac:PartyIdentification");
      Element IDPartyEmpresa = doc.createElement("cbc:ID");
      IDPartyEmpresa.setAttribute("schemeID", Cpe_Retencion.getTIPO_DOCUMENTO_EMPRESA());
      IDPartyEmpresa.appendChild(doc.createTextNode(Cpe_Retencion.getNRO_DOCUMENTO_EMPRESA()));
      PartyIdentificationEmpresa.appendChild(IDPartyEmpresa);
      AgentParty.appendChild(PartyIdentificationEmpresa);
      
      Element PartyNameEmpresa = doc.createElement("cac:PartyName");
      Element NameEmpresa = doc.createElement("cbc:Name");
      NameEmpresa.appendChild(doc.createCDATASection(Cpe_Retencion.getNOMBRE_COMERCIAL_EMPRESA()));
      PartyNameEmpresa.appendChild(NameEmpresa);
      AgentParty.appendChild(PartyNameEmpresa);
      
      Element PostalAddress = doc.createElement("cac:PostalAddress");
      
      Element ID_PostalAddress = doc.createElement("cbc:ID");
      ID_PostalAddress.appendChild(doc.createTextNode(Cpe_Retencion.getCOD_UBIGEO_EMPRESA()));
      PostalAddress.appendChild(ID_PostalAddress);
      
      Element StreetName = doc.createElement("cbc:StreetName");
      StreetName.appendChild(doc.createCDATASection(Cpe_Retencion.getDIRECCION_EMPRESA()));
      PostalAddress.appendChild(StreetName);
      
      Element CitySubdivisionName = doc.createElement("cbc:CitySubdivisionName");
      CitySubdivisionName.appendChild(doc.createCDATASection(""));
      PostalAddress.appendChild(CitySubdivisionName);
      
      Element CityName = doc.createElement("cbc:CityName");
      CityName.appendChild(doc.createCDATASection(Cpe_Retencion.getDEPARTAMENTO_EMPRESA()));
      PostalAddress.appendChild(CityName);
      
      Element CountrySubentity = doc.createElement("cbc:CountrySubentity");
      CountrySubentity.appendChild(doc.createCDATASection(Cpe_Retencion.getPROVINCIA_EMPRESA()));
      PostalAddress.appendChild(CountrySubentity);
      
      Element District = doc.createElement("cbc:District");
      District.appendChild(doc.createCDATASection(Cpe_Retencion.getDISTRITO_EMPRESA()));
      PostalAddress.appendChild(District);
      
      Element Country = doc.createElement("cac:Country");
      Element IdentificationCode = doc.createElement("cbc:IdentificationCode");
      IdentificationCode.appendChild(doc.createTextNode(Cpe_Retencion.getCOD_PAIS_EMPRESA()));
      Country.appendChild(IdentificationCode);
      PostalAddress.appendChild(Country);
      
      AgentParty.appendChild(PostalAddress);
      
      Element PartyLegalEntity = doc.createElement("cac:PartyLegalEntity");
      Element RegistrationName = doc.createElement("cbc:RegistrationName");
      RegistrationName.appendChild(doc.createCDATASection(Cpe_Retencion.getRAZON_SOCIAL_EMPRESA()));
      PartyLegalEntity.appendChild(RegistrationName);
      AgentParty.appendChild(PartyLegalEntity);
      
      mainRootElement.appendChild(AgentParty);
      
      Element ReceiverParty = doc.createElement("cac:ReceiverParty");
      
      Element PartyIdentificationProveedor = doc.createElement("cac:PartyIdentification");
      Element IDPartyProveedor = doc.createElement("cbc:ID");
      IDPartyProveedor.setAttribute("schemeID", Cpe_Retencion.getTIPO_DOCUMENTO_PROVEEDOR());
      IDPartyProveedor.appendChild(doc.createTextNode(Cpe_Retencion.getNRO_DOCUMENTO_PROVEEDOR()));
      PartyIdentificationProveedor.appendChild(IDPartyProveedor);
      ReceiverParty.appendChild(PartyIdentificationProveedor);
      
      Element PartyNameProveedor = doc.createElement("cac:PartyName");
      Element NameProveedor = doc.createElement("cbc:Name");
      NameProveedor.appendChild(doc.createCDATASection(Cpe_Retencion.getNOMBRE_COMERCIAL_PROVEEDOR()));
      PartyNameProveedor.appendChild(NameProveedor);
      ReceiverParty.appendChild(PartyNameProveedor);
      
      Element PostalAddressProveedor = doc.createElement("cac:PostalAddress");
      
      Element ID_PostalAddressProveedor = doc.createElement("cbc:ID");
      ID_PostalAddressProveedor.appendChild(doc.createTextNode(Cpe_Retencion.getCOD_UBIGEO_PROVEEDOR()));
      PostalAddressProveedor.appendChild(ID_PostalAddressProveedor);
      
      Element StreetNameProveedor = doc.createElement("cbc:StreetName");
      StreetNameProveedor.appendChild(doc.createCDATASection(Cpe_Retencion.getDIRECCION_PROVEEDOR()));
      PostalAddressProveedor.appendChild(StreetNameProveedor);
      
      Element CitySubdivisionNameProveedor = doc.createElement("cbc:CitySubdivisionName");
      CitySubdivisionNameProveedor.appendChild(doc.createCDATASection(""));
      PostalAddressProveedor.appendChild(CitySubdivisionNameProveedor);
      
      Element CityNameProveedor = doc.createElement("cbc:CityName");
      CityNameProveedor.appendChild(doc.createCDATASection(Cpe_Retencion.getDEPARTAMENTO_PROVEEDOR()));
      PostalAddressProveedor.appendChild(CityNameProveedor);
      
      Element CountrySubentityProveedor = doc.createElement("cbc:CountrySubentity");
      CountrySubentityProveedor.appendChild(doc.createCDATASection(Cpe_Retencion.getPROVINCIA_PROVEEDOR()));
      PostalAddressProveedor.appendChild(CountrySubentityProveedor);
      
      Element DistrictProveedor = doc.createElement("cbc:District");
      DistrictProveedor.appendChild(doc.createCDATASection(Cpe_Retencion.getDISTRITO_PROVEEDOR()));
      PostalAddressProveedor.appendChild(DistrictProveedor);
      
      Element CountryProveedor = doc.createElement("cac:Country");
      Element IdentificationCodeProveedor = doc.createElement("cbc:IdentificationCode");
      IdentificationCodeProveedor.appendChild(doc.createTextNode(Cpe_Retencion.getPAIS_PROVEEDOR()));
      CountryProveedor.appendChild(IdentificationCodeProveedor);
      PostalAddressProveedor.appendChild(CountryProveedor);
      
      ReceiverParty.appendChild(PostalAddressProveedor);
      
      Element PartyLegalEntityProveedor = doc.createElement("cac:PartyLegalEntity");
      Element RegistrationNameProveedor = doc.createElement("cbc:RegistrationName");
      RegistrationNameProveedor.appendChild(doc.createCDATASection(Cpe_Retencion.getRAZON_SOCIAL_PROVEEDOR()));
      PartyLegalEntityProveedor.appendChild(RegistrationNameProveedor);
      ReceiverParty.appendChild(PartyLegalEntityProveedor);
      
      mainRootElement.appendChild(ReceiverParty);
      
      Element SUNATPerceptionSystemCode = doc.createElement("sac:SUNATPerceptionSystemCode");
      SUNATPerceptionSystemCode.appendChild(doc.createTextNode(Cpe_Retencion.getTIPO_RETENCION()));
      mainRootElement.appendChild(SUNATPerceptionSystemCode);
      
      Element SUNATPerceptionPercent = doc.createElement("sac:SUNATPerceptionPercent");
      SUNATPerceptionPercent.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion.getPORCENTAJE_RETENCION())));
      mainRootElement.appendChild(SUNATPerceptionPercent);
      
      Element Note = doc.createElement("cbc:Note");
      Note.appendChild(doc.createTextNode(Cpe_Retencion.getNOTA()));
      mainRootElement.appendChild(Note);
      
      Element TotalInvoiceAmount = doc.createElement("cbc:TotalInvoiceAmount");
      TotalInvoiceAmount.setAttribute("currencyID", Cpe_Retencion.getMONEDA());
      TotalInvoiceAmount.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion.getTOTAL_RETENCION())));
      mainRootElement.appendChild(TotalInvoiceAmount);
      
      Element SUNATTotalCashed = doc.createElement("sac:SUNATTotalCashed");
      SUNATTotalCashed.setAttribute("currencyID", Cpe_Retencion.getMONEDA());
      SUNATTotalCashed.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion.getNETO_RETENCION())));
      mainRootElement.appendChild(SUNATTotalCashed);
      for (int i = 0; i < lstCpe_Retencion_Detalle.size(); i++)
      {
        Cpe_Retencion_DetalleBean Cpe_Retencion_Detalle = (Cpe_Retencion_DetalleBean)lstCpe_Retencion_Detalle.get(i);
        Element SUNATPerceptionDocumentReference = doc.createElement("sac:SUNATPerceptionDocumentReference");
        
        Element IDCOMPROBANTE = doc.createElement("cbc:ID");
        IDCOMPROBANTE.setAttribute("schemeID", Cpe_Retencion_Detalle.getCOD_TIPO_DOCUMENTO());
        IDCOMPROBANTE.appendChild(doc.createTextNode(Cpe_Retencion_Detalle.getNRO_DOCUMENTO()));
        SUNATPerceptionDocumentReference.appendChild(IDCOMPROBANTE);
        
        Element IssueDateCOMPROBANTE = doc.createElement("cbc:IssueDate");
        IssueDateCOMPROBANTE.appendChild(doc.createTextNode(Cpe_Retencion_Detalle.getFECHA_DOCUMENTO()));
        SUNATPerceptionDocumentReference.appendChild(IssueDateCOMPROBANTE);
        
        Element TotalInvoiceAmountCOMPROBANTE = doc.createElement("cbc:TotalInvoiceAmount");
        TotalInvoiceAmountCOMPROBANTE.setAttribute("currencyID", Cpe_Retencion_Detalle.getCOD_MONEDA());
        TotalInvoiceAmountCOMPROBANTE.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion_Detalle.getMONTO_TOTAL())));
        SUNATPerceptionDocumentReference.appendChild(TotalInvoiceAmountCOMPROBANTE);
        
        Element PaymentPAGO = doc.createElement("cac:Payment");
        
        Element IDPAGO = doc.createElement("cbc:ID");
        IDPAGO.appendChild(doc.createTextNode(Cpe_Retencion_Detalle.getNRO_DOC_PAGO()));
        PaymentPAGO.appendChild(IDPAGO);
        
        Element PaidAmount = doc.createElement("cbc:PaidAmount");
        PaidAmount.setAttribute("currencyID", Cpe_Retencion_Detalle.getCOD_MONEDA());
        PaidAmount.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion_Detalle.getMONTO_TOTAL())));
        PaymentPAGO.appendChild(PaidAmount);
        
        Element PaidDate = doc.createElement("cbc:PaidDate");
        PaidDate.appendChild(doc.createTextNode(Cpe_Retencion_Detalle.getFECHA_PAGO()));
        PaymentPAGO.appendChild(PaidDate);
        
        SUNATPerceptionDocumentReference.appendChild(PaymentPAGO);
        
        Element SUNATPerceptionInformation = doc.createElement("sac:SUNATPerceptionInformation");
        
        Element SUNATPerceptionAmount = doc.createElement("sac:SUNATPerceptionAmount");
        SUNATPerceptionAmount.setAttribute("currencyID", Cpe_Retencion_Detalle.getMONEDA_RETENIDA());
        SUNATPerceptionAmount.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion_Detalle.getMONTO_RETENIDO())));
        SUNATPerceptionInformation.appendChild(SUNATPerceptionAmount);
        
        Element SUNATRetentionDate = doc.createElement("sac:SUNATPerceptionDate");
        SUNATRetentionDate.appendChild(doc.createTextNode(Cpe_Retencion_Detalle.getFECHA_PAGO()));
        SUNATPerceptionInformation.appendChild(SUNATRetentionDate);
        
        Element SUNATNetTotalCashed = doc.createElement("sac:SUNATNetTotalCashed");
        SUNATNetTotalCashed.setAttribute("currencyID", Cpe_Retencion_Detalle.getMONEDA_PAGO_NETO());
        SUNATNetTotalCashed.appendChild(doc.createTextNode(Double.toString(Cpe_Retencion_Detalle.getMONTO_PAGO_NETO())));
        SUNATPerceptionInformation.appendChild(SUNATNetTotalCashed);
        
        SUNATPerceptionDocumentReference.appendChild(SUNATPerceptionInformation);
        
        mainRootElement.appendChild(SUNATPerceptionDocumentReference);
      }
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("encoding", "ISO-8859-1");
      DOMSource source = new DOMSource(doc);
      StreamResult console = new StreamResult(System.out);
      transformer.transform(source, console);
      
      DOMSource domSource = new DOMSource(doc);
      
      FileWriter out = new FileWriter(RutaXml);
      
      transformer.transform(domSource, new StreamResult(out));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 1;
  }
  
  public int GenerarXMLCPE_RR_BAJA(String RutaXml, Cpe_RrBean Cpe_Rr, List<Cpe_Rr_DetalleBean> lstCpe_Rr_Detalle)
  {
    DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
    try
    {
      DocumentBuilder icBuilder = icFactory.newDocumentBuilder();
      Document doc = icBuilder.newDocument();
      
      doc.setXmlVersion("1.0");
      Element mainRootElement = doc.createElementNS("urn:sunat:names:specification:ubl:peru:schema:xsd:VoidedDocuments-1", "VoidedDocuments");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:sac", "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
      doc.appendChild(mainRootElement);
      
      Element UBLExtensions1 = doc.createElement("ext:UBLExtensions");
      Element UBLExtensionFirma = doc.createElement("ext:UBLExtension");
      Element ExtensionContentFirma = doc.createElement("ext:ExtensionContent");
      UBLExtensionFirma.appendChild(ExtensionContentFirma);
      UBLExtensions1.appendChild(UBLExtensionFirma);
      mainRootElement.appendChild(UBLExtensions1);
      
      Element UBLVersionID = doc.createElement("cbc:UBLVersionID");
      UBLVersionID.appendChild(doc.createTextNode("2.0"));
      mainRootElement.appendChild(UBLVersionID);
      
      Element CustomizationID = doc.createElement("cbc:CustomizationID");
      CustomizationID.appendChild(doc.createTextNode("1.0"));
      mainRootElement.appendChild(CustomizationID);
      
      Element IDNRO_DOCUMENTO = doc.createElement("cbc:ID");
      IDNRO_DOCUMENTO.appendChild(doc.createTextNode(Cpe_Rr.getCODIGO() + "-" + Cpe_Rr.getSERIE() + "-" + Cpe_Rr.getSECUENCIA()));
      mainRootElement.appendChild(IDNRO_DOCUMENTO);
      
      Element ReferenceDate = doc.createElement("cbc:ReferenceDate");
      ReferenceDate.appendChild(doc.createTextNode(Cpe_Rr.getFECHA_REFERENCIA()));
      mainRootElement.appendChild(ReferenceDate);
      
      Element IssueDate = doc.createElement("cbc:IssueDate");
      IssueDate.appendChild(doc.createTextNode(Cpe_Rr.getFECHA_DOCUMENTO()));
      mainRootElement.appendChild(IssueDate);
      
      Element Signature = doc.createElement("cac:Signature");
      Element IDSignature = doc.createElement("cbc:ID");
      IDSignature.appendChild(doc.createTextNode("IDSignKG"));
      Signature.appendChild(IDSignature);
      
      Element SignatoryParty = doc.createElement("cac:SignatoryParty");
      
      Element PartyIdentification = doc.createElement("cac:PartyIdentification");
      Element ID_PartyIdentification = doc.createElement("cbc:ID");
      ID_PartyIdentification.appendChild(doc.createTextNode(Cpe_Rr.getNRO_DOCUMENTO_EMPRESA()));
      PartyIdentification.appendChild(ID_PartyIdentification);
      SignatoryParty.appendChild(PartyIdentification);
      
      Element PartyName = doc.createElement("cac:PartyName");
      Element Name = doc.createElement("cbc:Name");
      Name.appendChild(doc.createTextNode(Cpe_Rr.getRAZON_SOCIAL()));
      PartyName.appendChild(Name);
      SignatoryParty.appendChild(PartyName);
      
      Signature.appendChild(SignatoryParty);
      
      Element DigitalSignatureAttachment = doc.createElement("cac:DigitalSignatureAttachment");
      Element ExternalReference = doc.createElement("cac:ExternalReference");
      Element URI = doc.createElement("cbc:URI");
      URI.appendChild(doc.createTextNode("#" + Cpe_Rr.getSERIE() + "-" + Cpe_Rr.getSECUENCIA()));
      
      ExternalReference.appendChild(URI);
      DigitalSignatureAttachment.appendChild(ExternalReference);
      Signature.appendChild(DigitalSignatureAttachment);
      
      mainRootElement.appendChild(Signature);
      
      Element AccountingSupplierParty = doc.createElement("cac:AccountingSupplierParty");
      
      Element CustomerAssignedAccountID = doc.createElement("cbc:CustomerAssignedAccountID");
      CustomerAssignedAccountID.appendChild(doc.createTextNode(Cpe_Rr.getNRO_DOCUMENTO_EMPRESA()));
      AccountingSupplierParty.appendChild(CustomerAssignedAccountID);
      
      Element AdditionalAccountID = doc.createElement("cbc:AdditionalAccountID");
      AdditionalAccountID.appendChild(doc.createTextNode(Cpe_Rr.getTIPO_DOCUMENTO()));
      AccountingSupplierParty.appendChild(AdditionalAccountID);
      
      Element Party = doc.createElement("cac:Party");
      Element PartyLegalEntity = doc.createElement("cac:PartyLegalEntity");
      Element RegistrationName = doc.createElement("cbc:RegistrationName");
      RegistrationName.appendChild(doc.createTextNode(Cpe_Rr.getRAZON_SOCIAL()));
      PartyLegalEntity.appendChild(RegistrationName);
      Party.appendChild(PartyLegalEntity);
      
      AccountingSupplierParty.appendChild(Party);
      mainRootElement.appendChild(AccountingSupplierParty);
      for (int i = 0; i < lstCpe_Rr_Detalle.size(); i++)
      {
        Cpe_Rr_DetalleBean Cpe_Rr_Detalle = (Cpe_Rr_DetalleBean)lstCpe_Rr_Detalle.get(i);
        Element VoidedDocumentsLine = doc.createElement("sac:VoidedDocumentsLine");
        
        Element LineID = doc.createElement("cbc:LineID");
        LineID.appendChild(doc.createTextNode(Integer.toString(Cpe_Rr_Detalle.getITEM())));
        VoidedDocumentsLine.appendChild(LineID);
        
        Element DocumentTypeCode = doc.createElement("cbc:DocumentTypeCode");
        DocumentTypeCode.appendChild(doc.createTextNode(Cpe_Rr_Detalle.getCOD_TIPO_DOCUMENTO()));
        VoidedDocumentsLine.appendChild(DocumentTypeCode);
        
        Element DocumentSerialID = doc.createElement("sac:DocumentSerialID");
        DocumentSerialID.appendChild(doc.createTextNode(Cpe_Rr_Detalle.getSERIE()));
        VoidedDocumentsLine.appendChild(DocumentSerialID);
        
        Element DocumentNumberID = doc.createElement("sac:DocumentNumberID");
        DocumentNumberID.appendChild(doc.createTextNode(Cpe_Rr_Detalle.getNUMERO()));
        VoidedDocumentsLine.appendChild(DocumentNumberID);
        
        Element VoidReasonDescription = doc.createElement("sac:VoidReasonDescription");
        VoidReasonDescription.appendChild(doc.createTextNode(Cpe_Rr_Detalle.getDESCRIPCION()));
        VoidedDocumentsLine.appendChild(VoidReasonDescription);
        
        mainRootElement.appendChild(VoidedDocumentsLine);
      }
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("encoding", "ISO-8859-1");
      DOMSource source = new DOMSource(doc);
      StreamResult console = new StreamResult(System.out);
      transformer.transform(source, console);
      
      DOMSource domSource = new DOMSource(doc);
      
      FileWriter out = new FileWriter(RutaXml);
      
      transformer.transform(domSource, new StreamResult(out));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 1;
  }
  
  public int GenerarXMLBAJA(String RutaXml, Cpe_BajaBean Cpe_Baja, List<Cpe_Baja_DetalleBean> lstCpe_BajaDetalle)
  {
    DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
    try
    {
      DocumentBuilder icBuilder = icFactory.newDocumentBuilder();
      Document doc = icBuilder.newDocument();
      
      doc.setXmlVersion("1.0");
      Element mainRootElement = doc.createElementNS("urn:sunat:names:specification:ubl:peru:schema:xsd:VoidedDocuments-1", "VoidedDocuments");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:sac", "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
      doc.appendChild(mainRootElement);
      
      Element UBLExtensions1 = doc.createElement("ext:UBLExtensions");
      Element UBLExtensionFirma = doc.createElement("ext:UBLExtension");
      Element ExtensionContentFirma = doc.createElement("ext:ExtensionContent");
      UBLExtensionFirma.appendChild(ExtensionContentFirma);
      UBLExtensions1.appendChild(UBLExtensionFirma);
      mainRootElement.appendChild(UBLExtensions1);
      
      Element UBLVersionID = doc.createElement("cbc:UBLVersionID");
      UBLVersionID.appendChild(doc.createTextNode("2.0"));
      mainRootElement.appendChild(UBLVersionID);
      
      Element CustomizationID = doc.createElement("cbc:CustomizationID");
      CustomizationID.appendChild(doc.createTextNode("1.0"));
      mainRootElement.appendChild(CustomizationID);
      
      Element IDNRO_DOCUMENTO = doc.createElement("cbc:ID");
      IDNRO_DOCUMENTO.appendChild(doc.createTextNode(Cpe_Baja.getCODIGO() + "-" + Cpe_Baja.getSERIE() + "-" + Cpe_Baja.getSECUENCIA()));
      mainRootElement.appendChild(IDNRO_DOCUMENTO);
      
      Element ReferenceDate = doc.createElement("cbc:ReferenceDate");
      ReferenceDate.appendChild(doc.createTextNode(Cpe_Baja.getFECHA_REFERENCIA()));
      mainRootElement.appendChild(ReferenceDate);
      
      Element IssueDate = doc.createElement("cbc:IssueDate");
      IssueDate.appendChild(doc.createTextNode(Cpe_Baja.getFECHA_BAJA()));
      mainRootElement.appendChild(IssueDate);
      
      Element Signature = doc.createElement("cac:Signature");
      Element IDSignature = doc.createElement("cbc:ID");
      IDSignature.appendChild(doc.createTextNode("IDSignKG"));
      Signature.appendChild(IDSignature);
      
      Element SignatoryParty = doc.createElement("cac:SignatoryParty");
      
      Element PartyIdentification = doc.createElement("cac:PartyIdentification");
      Element ID_PartyIdentification = doc.createElement("cbc:ID");
      ID_PartyIdentification.appendChild(doc.createTextNode(Cpe_Baja.getNRO_DOCUMENTO_EMPRESA()));
      PartyIdentification.appendChild(ID_PartyIdentification);
      SignatoryParty.appendChild(PartyIdentification);
      
      Element PartyName = doc.createElement("cac:PartyName");
      Element Name = doc.createElement("cbc:Name");
      Name.appendChild(doc.createTextNode(Cpe_Baja.getRAZON_SOCIAL()));
      PartyName.appendChild(Name);
      SignatoryParty.appendChild(PartyName);
      
      Signature.appendChild(SignatoryParty);
      
      Element DigitalSignatureAttachment = doc.createElement("cac:DigitalSignatureAttachment");
      Element ExternalReference = doc.createElement("cac:ExternalReference");
      Element URI = doc.createElement("cbc:URI");
      URI.appendChild(doc.createTextNode("#" + Cpe_Baja.getSERIE() + "-" + Cpe_Baja.getSECUENCIA()));
      
      ExternalReference.appendChild(URI);
      DigitalSignatureAttachment.appendChild(ExternalReference);
      Signature.appendChild(DigitalSignatureAttachment);
      
      mainRootElement.appendChild(Signature);
      
      Element AccountingSupplierParty = doc.createElement("cac:AccountingSupplierParty");
      
      Element CustomerAssignedAccountID = doc.createElement("cbc:CustomerAssignedAccountID");
      CustomerAssignedAccountID.appendChild(doc.createTextNode(Cpe_Baja.getNRO_DOCUMENTO_EMPRESA()));
      AccountingSupplierParty.appendChild(CustomerAssignedAccountID);
      
      Element AdditionalAccountID = doc.createElement("cbc:AdditionalAccountID");
      AdditionalAccountID.appendChild(doc.createTextNode(Cpe_Baja.getTIPO_DOCUMENTO()));
      AccountingSupplierParty.appendChild(AdditionalAccountID);
      
      Element Party = doc.createElement("cac:Party");
      Element PartyLegalEntity = doc.createElement("cac:PartyLegalEntity");
      Element RegistrationName = doc.createElement("cbc:RegistrationName");
      RegistrationName.appendChild(doc.createCDATASection(Cpe_Baja.getRAZON_SOCIAL()));
      PartyLegalEntity.appendChild(RegistrationName);
      Party.appendChild(PartyLegalEntity);
      
      AccountingSupplierParty.appendChild(Party);
      mainRootElement.appendChild(AccountingSupplierParty);
      for (int i = 0; i < lstCpe_BajaDetalle.size(); i++)
      {
        Cpe_Baja_DetalleBean Cpe_BajaDetalle = (Cpe_Baja_DetalleBean)lstCpe_BajaDetalle.get(i);
        Element VoidedDocumentsLine = doc.createElement("sac:VoidedDocumentsLine");
        
        Element LineID = doc.createElement("cbc:LineID");
        LineID.appendChild(doc.createTextNode(Integer.toString(Cpe_BajaDetalle.getITEM())));
        VoidedDocumentsLine.appendChild(LineID);
        
        Element DocumentTypeCode = doc.createElement("cbc:DocumentTypeCode");
        DocumentTypeCode.appendChild(doc.createTextNode(Cpe_BajaDetalle.getTIPO_COMPROBANTE()));
        VoidedDocumentsLine.appendChild(DocumentTypeCode);
        
        Element DocumentSerialID = doc.createElement("sac:DocumentSerialID");
        DocumentSerialID.appendChild(doc.createTextNode(Cpe_BajaDetalle.getSERIE()));
        VoidedDocumentsLine.appendChild(DocumentSerialID);
        
        Element DocumentNumberID = doc.createElement("sac:DocumentNumberID");
        DocumentNumberID.appendChild(doc.createTextNode(Cpe_BajaDetalle.getNUMERO()));
        VoidedDocumentsLine.appendChild(DocumentNumberID);
        
        Element VoidReasonDescription = doc.createElement("sac:VoidReasonDescription");
        VoidReasonDescription.appendChild(doc.createCDATASection(Cpe_BajaDetalle.getDESCRIPCION()));
        VoidedDocumentsLine.appendChild(VoidReasonDescription);
        
        mainRootElement.appendChild(VoidedDocumentsLine);
      }
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("encoding", "ISO-8859-1");
      DOMSource source = new DOMSource(doc);
      StreamResult console = new StreamResult(System.out);
      transformer.transform(source, console);
      
      DOMSource domSource = new DOMSource(doc);
      
      FileWriter out = new FileWriter(RutaXml);
      
      transformer.transform(domSource, new StreamResult(out));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 1;
  }
  
  public int GenerarXMLRESUMEN(String RutaXml, Cpe_Resumen_BoletaBean Cpe_Resumen_Boleta, List<Cpe_Resumen_Boleta_DetalleBean> lstCpe_Resumen_Boleta_Detalle)
  {
    DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
    try
    {
      DocumentBuilder icBuilder = icFactory.newDocumentBuilder();
      Document doc = icBuilder.newDocument();
      
      doc.setXmlVersion("1.0");
      Element mainRootElement = doc.createElementNS("urn:sunat:names:specification:ubl:peru:schema:xsd:SummaryDocuments-1", "SummaryDocuments");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ds", "http://www.w3.org/2000/09/xmldsig#");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:sac", "urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:qdt", "urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2");
      mainRootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:udt", "urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2");
      
      doc.appendChild(mainRootElement);
      
      Element UBLExtensions1 = doc.createElement("ext:UBLExtensions");
      Element UBLExtensionFirma = doc.createElement("ext:UBLExtension");
      Element ExtensionContentFirma = doc.createElement("ext:ExtensionContent");
      UBLExtensionFirma.appendChild(ExtensionContentFirma);
      UBLExtensions1.appendChild(UBLExtensionFirma);
      mainRootElement.appendChild(UBLExtensions1);
      
      Element UBLVersionID = doc.createElement("cbc:UBLVersionID");
      UBLVersionID.appendChild(doc.createTextNode("2.0"));
      mainRootElement.appendChild(UBLVersionID);
      
      Element CustomizationID = doc.createElement("cbc:CustomizationID");
      CustomizationID.appendChild(doc.createTextNode("1.1"));
      mainRootElement.appendChild(CustomizationID);
      
      Element IDNRO_DOCUMENTO = doc.createElement("cbc:ID");
      IDNRO_DOCUMENTO.appendChild(doc.createTextNode(Cpe_Resumen_Boleta.getCODIGO() + "-" + Cpe_Resumen_Boleta.getSERIE() + "-" + Cpe_Resumen_Boleta.getSECUENCIA()));
      mainRootElement.appendChild(IDNRO_DOCUMENTO);
      
      Element ReferenceDate = doc.createElement("cbc:ReferenceDate");
      ReferenceDate.appendChild(doc.createTextNode(Cpe_Resumen_Boleta.getFECHA_REFERENCIA()));
      mainRootElement.appendChild(ReferenceDate);
      
      Element IssueDate = doc.createElement("cbc:IssueDate");
      IssueDate.appendChild(doc.createTextNode(Cpe_Resumen_Boleta.getFECHA_DOCUMENTO()));
      mainRootElement.appendChild(IssueDate);
      
      Element Signature = doc.createElement("cac:Signature");
      Element IDSignature = doc.createElement("cbc:ID");
      IDSignature.appendChild(doc.createTextNode(Cpe_Resumen_Boleta.getCODIGO() + "-" + Cpe_Resumen_Boleta.getSERIE() + "-" + Cpe_Resumen_Boleta.getSECUENCIA()));
      Signature.appendChild(IDSignature);
      
      Element SignatoryParty = doc.createElement("cac:SignatoryParty");
      
      Element PartyIdentification = doc.createElement("cac:PartyIdentification");
      Element ID_PartyIdentification = doc.createElement("cbc:ID");
      ID_PartyIdentification.appendChild(doc.createTextNode(Cpe_Resumen_Boleta.getNRO_DOCUMENTO_EMPRESA()));
      PartyIdentification.appendChild(ID_PartyIdentification);
      SignatoryParty.appendChild(PartyIdentification);
      
      Element PartyName = doc.createElement("cac:PartyName");
      Element Name = doc.createElement("cbc:Name");
      Name.appendChild(doc.createCDATASection(Cpe_Resumen_Boleta.getRAZON_SOCIAL()));
      PartyName.appendChild(Name);
      SignatoryParty.appendChild(PartyName);
      
      Signature.appendChild(SignatoryParty);
      
      Element DigitalSignatureAttachment = doc.createElement("cac:DigitalSignatureAttachment");
      Element ExternalReference = doc.createElement("cac:ExternalReference");
      Element URI = doc.createElement("cbc:URI");
      URI.appendChild(doc.createTextNode(Cpe_Resumen_Boleta.getCODIGO() + "-" + Cpe_Resumen_Boleta.getSERIE() + "-" + Cpe_Resumen_Boleta.getSECUENCIA()));
      
      ExternalReference.appendChild(URI);
      DigitalSignatureAttachment.appendChild(ExternalReference);
      Signature.appendChild(DigitalSignatureAttachment);
      
      mainRootElement.appendChild(Signature);
      
      Element AccountingSupplierParty = doc.createElement("cac:AccountingSupplierParty");
      
      Element CustomerAssignedAccountID = doc.createElement("cbc:CustomerAssignedAccountID");
      CustomerAssignedAccountID.appendChild(doc.createTextNode(Cpe_Resumen_Boleta.getNRO_DOCUMENTO_EMPRESA()));
      AccountingSupplierParty.appendChild(CustomerAssignedAccountID);
      
      Element AdditionalAccountID = doc.createElement("cbc:AdditionalAccountID");
      AdditionalAccountID.appendChild(doc.createTextNode(Cpe_Resumen_Boleta.getTIPO_DOCUMENTO()));
      AccountingSupplierParty.appendChild(AdditionalAccountID);
      
      Element Party = doc.createElement("cac:Party");
      Element PartyLegalEntity = doc.createElement("cac:PartyLegalEntity");
      Element RegistrationName = doc.createElement("cbc:RegistrationName");
      RegistrationName.appendChild(doc.createCDATASection(Cpe_Resumen_Boleta.getRAZON_SOCIAL()));
      PartyLegalEntity.appendChild(RegistrationName);
      Party.appendChild(PartyLegalEntity);
      
      AccountingSupplierParty.appendChild(Party);
      mainRootElement.appendChild(AccountingSupplierParty);
      for (int i = 0; i < lstCpe_Resumen_Boleta_Detalle.size(); i++)
      {
        Cpe_Resumen_Boleta_DetalleBean Cpe_Resumen_Boleta_Detalle = (Cpe_Resumen_Boleta_DetalleBean)lstCpe_Resumen_Boleta_Detalle.get(i);
        Element SummaryDocumentsLine = doc.createElement("sac:SummaryDocumentsLine");
        
        Element LineID = doc.createElement("cbc:LineID");
        LineID.appendChild(doc.createTextNode(Integer.toString(Cpe_Resumen_Boleta_Detalle.getITEM())));
        SummaryDocumentsLine.appendChild(LineID);
        
        Element DocumentTypeCode = doc.createElement("cbc:DocumentTypeCode");
        DocumentTypeCode.appendChild(doc.createTextNode(Cpe_Resumen_Boleta_Detalle.getTIPO_COMPROBANTE()));
        SummaryDocumentsLine.appendChild(DocumentTypeCode);
        
        Element comprobanteID = doc.createElement("cbc:ID");
        comprobanteID.appendChild(doc.createTextNode(Cpe_Resumen_Boleta_Detalle.getNRO_COMPROBANTE()));
        SummaryDocumentsLine.appendChild(comprobanteID);
        
        Element AccountingCustomerParty = doc.createElement("cac:AccountingCustomerParty");
        
        Element CustomerAssignedAccountID_DET = doc.createElement("cbc:CustomerAssignedAccountID");
        CustomerAssignedAccountID_DET.appendChild(doc.createTextNode(Cpe_Resumen_Boleta_Detalle.getNRO_DOCUMENTO()));
        AccountingCustomerParty.appendChild(CustomerAssignedAccountID_DET);
        
        Element AdditionalAccountID_DET = doc.createElement("cbc:AdditionalAccountID");
        AdditionalAccountID_DET.appendChild(doc.createTextNode(Cpe_Resumen_Boleta_Detalle.getTIPO_DOCUMENTO()));
        AccountingCustomerParty.appendChild(AdditionalAccountID_DET);
        
        SummaryDocumentsLine.appendChild(AccountingCustomerParty);
        if ((Cpe_Resumen_Boleta_Detalle.getTIPO_COMPROBANTE().equals("07")) || 
          (Cpe_Resumen_Boleta_Detalle.getTIPO_COMPROBANTE().equals("08")))
        {
          Element BillingReference = doc.createElement("cac:BillingReference");
          Element InvoiceDocumentReference = doc.createElement("cac:InvoiceDocumentReference");
          
          Element ID_DOCUMENTO_REF = doc.createElement("cbc:ID");
          ID_DOCUMENTO_REF.appendChild(doc.createTextNode(Cpe_Resumen_Boleta_Detalle.getNRO_COMPROBANTE_REF()));
          InvoiceDocumentReference.appendChild(ID_DOCUMENTO_REF);
          
          Element DocumentTypeCode_REF = doc.createElement("cbc:DocumentTypeCode");
          DocumentTypeCode_REF.appendChild(doc.createTextNode(Cpe_Resumen_Boleta_Detalle.getTIPO_COMPROBANTE_REF()));
          InvoiceDocumentReference.appendChild(DocumentTypeCode_REF);
          
          BillingReference.appendChild(InvoiceDocumentReference);
          SummaryDocumentsLine.appendChild(BillingReference);
        }
        Element Status = doc.createElement("cac:Status");
        Element ConditionCode = doc.createElement("cbc:ConditionCode");
        ConditionCode.appendChild(doc.createTextNode(Cpe_Resumen_Boleta_Detalle.getSTATU()));
        Status.appendChild(ConditionCode);
        SummaryDocumentsLine.appendChild(Status);
        if (Cpe_Resumen_Boleta_Detalle.getGRAVADA() > 0.0D)
        {
          Element TotalAmount = doc.createElement("sac:TotalAmount");
          TotalAmount.setAttribute("currencyID", Cpe_Resumen_Boleta_Detalle.getCOD_MONEDA());
          TotalAmount.appendChild(doc.createTextNode(Double.toString(Cpe_Resumen_Boleta_Detalle.getTOTAL())));
          SummaryDocumentsLine.appendChild(TotalAmount);
          
          Element BillingPaymentGravado = doc.createElement("sac:BillingPayment");
          
          Element PaidAmountGravado = doc.createElement("cbc:PaidAmount");
          PaidAmountGravado.setAttribute("currencyID", Cpe_Resumen_Boleta_Detalle.getCOD_MONEDA());
          PaidAmountGravado.appendChild(doc.createTextNode(Double.toString(Cpe_Resumen_Boleta_Detalle.getGRAVADA())));
          BillingPaymentGravado.appendChild(PaidAmountGravado);
          
          Element InstructionIDGravado = doc.createElement("cbc:InstructionID");
          InstructionIDGravado.appendChild(doc.createTextNode("01"));
          BillingPaymentGravado.appendChild(InstructionIDGravado);
          
          SummaryDocumentsLine.appendChild(BillingPaymentGravado);
        }
        if (Cpe_Resumen_Boleta_Detalle.getEXONERADO() > 0.0D)
        {
          Element BillingPaymentExonerado = doc.createElement("sac:BillingPayment");
          
          Element PaidAmountExonerado = doc.createElement("cbc:PaidAmount");
          PaidAmountExonerado.setAttribute("currencyID", Cpe_Resumen_Boleta_Detalle.getCOD_MONEDA());
          PaidAmountExonerado.appendChild(doc.createTextNode(Double.toString(Cpe_Resumen_Boleta_Detalle.getEXONERADO())));
          BillingPaymentExonerado.appendChild(PaidAmountExonerado);
          
          Element InstructionIDExonerado = doc.createElement("cbc:InstructionID");
          InstructionIDExonerado.appendChild(doc.createTextNode("02"));
          BillingPaymentExonerado.appendChild(InstructionIDExonerado);
          
          SummaryDocumentsLine.appendChild(BillingPaymentExonerado);
        }
        if (Cpe_Resumen_Boleta_Detalle.getINAFECTO() > 0.0D)
        {
          Element BillingPaymentInafecto = doc.createElement("sac:BillingPayment");
          
          Element PaidAmountInafecto = doc.createElement("cbc:PaidAmount");
          PaidAmountInafecto.setAttribute("currencyID", Cpe_Resumen_Boleta_Detalle.getCOD_MONEDA());
          PaidAmountInafecto.appendChild(doc.createTextNode(Double.toString(Cpe_Resumen_Boleta_Detalle.getINAFECTO())));
          BillingPaymentInafecto.appendChild(PaidAmountInafecto);
          
          Element InstructionIDInafecto = doc.createElement("cbc:InstructionID");
          InstructionIDInafecto.appendChild(doc.createTextNode("03"));
          BillingPaymentInafecto.appendChild(InstructionIDInafecto);
          
          SummaryDocumentsLine.appendChild(BillingPaymentInafecto);
        }
        if (Cpe_Resumen_Boleta_Detalle.getEXPORTACION() > 0.0D)
        {
          Element BillingPaymentExportacion = doc.createElement("sac:BillingPayment");
          
          Element PaidAmountExportacion = doc.createElement("cbc:PaidAmount");
          PaidAmountExportacion.setAttribute("currencyID", Cpe_Resumen_Boleta_Detalle.getCOD_MONEDA());
          PaidAmountExportacion.appendChild(doc.createTextNode(Double.toString(Cpe_Resumen_Boleta_Detalle.getEXPORTACION())));
          BillingPaymentExportacion.appendChild(PaidAmountExportacion);
          
          Element InstructionIDExportacion = doc.createElement("cbc:InstructionID");
          InstructionIDExportacion.appendChild(doc.createTextNode("04"));
          BillingPaymentExportacion.appendChild(InstructionIDExportacion);
          
          SummaryDocumentsLine.appendChild(BillingPaymentExportacion);
        }
        if (Cpe_Resumen_Boleta_Detalle.getGRATUITAS() > 0.0D)
        {
          Element BillingPaymentGratuitas = doc.createElement("sac:BillingPayment");
          
          Element PaidAmountGratuitas = doc.createElement("cbc:PaidAmount");
          PaidAmountGratuitas.setAttribute("currencyID", Cpe_Resumen_Boleta_Detalle.getCOD_MONEDA());
          PaidAmountGratuitas.appendChild(doc.createTextNode(Double.toString(Cpe_Resumen_Boleta_Detalle.getGRATUITAS())));
          BillingPaymentGratuitas.appendChild(PaidAmountGratuitas);
          
          Element InstructionIDGratuitas = doc.createElement("cbc:InstructionID");
          InstructionIDGratuitas.appendChild(doc.createTextNode("05"));
          BillingPaymentGratuitas.appendChild(InstructionIDGratuitas);
          
          SummaryDocumentsLine.appendChild(BillingPaymentGratuitas);
        }
        if (Cpe_Resumen_Boleta_Detalle.getMONTO_CARGO_X_ASIG() > 0.0D)
        {
          String StrChargeIndicator = "true";
          if (Cpe_Resumen_Boleta_Detalle.getCARGO_X_ASIGNACION() == 1) {
            StrChargeIndicator = "true";
          } else {
            StrChargeIndicator = "false";
          }
          Element AllowanceCharge = doc.createElement("cac:AllowanceCharge");
          
          Element ChargeIndicator = doc.createElement("cbc:ChargeIndicator");
          ChargeIndicator.appendChild(doc.createTextNode(StrChargeIndicator));
          AllowanceCharge.appendChild(ChargeIndicator);
          
          Element AmountChargeIndicator = doc.createElement("cbc:Amount");
          AmountChargeIndicator.setAttribute("currencyID", Cpe_Resumen_Boleta_Detalle.getCOD_MONEDA());
          AmountChargeIndicator.appendChild(doc.createTextNode(Double.toString(Cpe_Resumen_Boleta_Detalle.getMONTO_CARGO_X_ASIG())));
          AllowanceCharge.appendChild(AmountChargeIndicator);
          
          SummaryDocumentsLine.appendChild(AllowanceCharge);
        }
        if (Cpe_Resumen_Boleta_Detalle.getISC() > 0.0D)
        {
          Element TaxTotalISC = doc.createElement("cac:TaxTotal");
          
          Element TaxAmountISC = doc.createElement("cbc:TaxAmount");
          TaxAmountISC.setAttribute("currencyID", Cpe_Resumen_Boleta_Detalle.getCOD_MONEDA());
          TaxAmountISC.appendChild(doc.createTextNode(Double.toString(Cpe_Resumen_Boleta_Detalle.getISC())));
          TaxTotalISC.appendChild(TaxAmountISC);
          
          Element TaxSubtotalISC = doc.createElement("cac:TaxSubtotal");
          
          Element TaxAmountISC_TaxSubtotal = doc.createElement("cbc:TaxAmount");
          TaxAmountISC_TaxSubtotal.setAttribute("currencyID", Cpe_Resumen_Boleta_Detalle.getCOD_MONEDA());
          TaxAmountISC_TaxSubtotal.appendChild(doc.createTextNode(Double.toString(Cpe_Resumen_Boleta_Detalle.getISC())));
          TaxSubtotalISC.appendChild(TaxAmountISC_TaxSubtotal);
          
          Element TaxCategoryISC = doc.createElement("cac:TaxCategory");
          Element TaxSchemeISC = doc.createElement("cac:TaxScheme");
          
          Element IDISC = doc.createElement("cbc:ID");
          IDISC.appendChild(doc.createTextNode("2000"));
          TaxSchemeISC.appendChild(IDISC);
          
          Element NameISC = doc.createElement("cbc:Name");
          NameISC.appendChild(doc.createTextNode("ISC"));
          TaxSchemeISC.appendChild(NameISC);
          
          Element TaxTypeCodeISC = doc.createElement("cbc:TaxTypeCode");
          TaxTypeCodeISC.appendChild(doc.createTextNode("EXC"));
          TaxSchemeISC.appendChild(TaxTypeCodeISC);
          
          TaxCategoryISC.appendChild(TaxSchemeISC);
          TaxSubtotalISC.appendChild(TaxCategoryISC);
          TaxTotalISC.appendChild(TaxSubtotalISC);
          
          SummaryDocumentsLine.appendChild(TaxTotalISC);
        }
        Element TaxTotalIGV = doc.createElement("cac:TaxTotal");
        
        Element TaxAmountIGV = doc.createElement("cbc:TaxAmount");
        TaxAmountIGV.setAttribute("currencyID", Cpe_Resumen_Boleta_Detalle.getCOD_MONEDA());
        TaxAmountIGV.appendChild(doc.createTextNode(Double.toString(Cpe_Resumen_Boleta_Detalle.getIGV())));
        TaxTotalIGV.appendChild(TaxAmountIGV);
        
        Element TaxSubtotalIGV = doc.createElement("cac:TaxSubtotal");
        
        Element TaxAmountIGV_TaxSubtotal = doc.createElement("cbc:TaxAmount");
        TaxAmountIGV_TaxSubtotal.setAttribute("currencyID", Cpe_Resumen_Boleta_Detalle.getCOD_MONEDA());
        TaxAmountIGV_TaxSubtotal.appendChild(doc.createTextNode(Double.toString(Cpe_Resumen_Boleta_Detalle.getIGV())));
        TaxSubtotalIGV.appendChild(TaxAmountIGV_TaxSubtotal);
        
        Element TaxCategoryIGV = doc.createElement("cac:TaxCategory");
        Element TaxSchemeIGV = doc.createElement("cac:TaxScheme");
        
        Element IDIGV = doc.createElement("cbc:ID");
        IDIGV.appendChild(doc.createTextNode("1000"));
        TaxSchemeIGV.appendChild(IDIGV);
        
        Element NameIGV = doc.createElement("cbc:Name");
        NameIGV.appendChild(doc.createTextNode("IGV"));
        TaxSchemeIGV.appendChild(NameIGV);
        
        Element TaxTypeCodeIGV = doc.createElement("cbc:TaxTypeCode");
        TaxTypeCodeIGV.appendChild(doc.createTextNode("VAT"));
        TaxSchemeIGV.appendChild(TaxTypeCodeIGV);
        
        TaxCategoryIGV.appendChild(TaxSchemeIGV);
        TaxSubtotalIGV.appendChild(TaxCategoryIGV);
        TaxTotalIGV.appendChild(TaxSubtotalIGV);
        
        SummaryDocumentsLine.appendChild(TaxTotalIGV);
        mainRootElement.appendChild(SummaryDocumentsLine);
      }
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("encoding", "ISO-8859-1");
      DOMSource source = new DOMSource(doc);
      StreamResult console = new StreamResult(System.out);
      transformer.transform(source, console);
      
      DOMSource domSource = new DOMSource(doc);
      
      FileWriter out = new FileWriter(RutaXml);
      
      transformer.transform(domSource, new StreamResult(out));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 1;
  }
}

