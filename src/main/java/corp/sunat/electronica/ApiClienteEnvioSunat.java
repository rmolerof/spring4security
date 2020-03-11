package corp.sunat.electronica;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import javax.mail.internet.MimeUtility;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import corp.sunat.XmlSunat;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ApiClienteEnvioSunat
{
  private static Logger logger = LogManager.getLogger(ApiClienteEnvioSunat.class);
	
  public static void main(String[] args)
  {
    String ruc = "20502258011";
    String UsuSol = "MODDATOS";
    String PassSol = "moddatos";
    String NombreCPE = "20502258011-01-F001-00000011";
    String NombreCDR = "R-20502258011-01-F001-00000011";
    String RutaArchivo = "D:\\CPE\\BETA\\";
    String RutaWS = "https://e-beta.sunat.gob.pe:443/ol-ti-itcpfegem-beta/billService";
    
    ConexionCPE(ruc, UsuSol, PassSol, NombreCPE, NombreCDR, RutaArchivo, RutaWS);
  }
  
  public static String ConexionCPE(String ruc, String UsuarioSol, String PassSol, String NombreCPE, String NombreCDR, String RutaArchivo, String RutaWS)
  {
    String[] Retorno = new String[5];
    try
    {
      SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
      SOAPConnection soapConnection = soapConnectionFactory.createConnection();
      
      URL url = new URL(new URL(RutaWS), "", new URLStreamHandler()
      {
        protected URLConnection openConnection(URL url)
          throws IOException
        {
          URL target = new URL(url.toString());
          URLConnection connection = target.openConnection();
          
          connection.setConnectTimeout(10000);
          connection.setReadTimeout(60000);
          return connection;
        }
      });
      SOAPMessage soapResponse = soapConnection.call(CPEsendBill(ruc, UsuarioSol, PassSol, NombreCPE, RutaArchivo), url);
      
      printSOAPResponse(soapResponse);
      
      SOAPBody body = soapResponse.getSOAPBody();
      
      String str1 = "";
      if ((NombreCPE.substring(12, 14).equals("RA")) || (NombreCPE.substring(12, 14).equals("RC")))
      {
        NodeList Rta = body.getElementsByTagName("ticket");
        if (Rta.getLength() > 0)
        {
          str1 = body.getElementsByTagName("ticket").item(0).getTextContent();
          
          Retorno[0] = "1";
          Retorno[1] = "0";
          Retorno[2] = ("Ticket Nro: " + str1);
          Retorno[3] = "";
          Retorno[4] = "";
        }
        else
        {
          Retorno[0] = "0";
          Retorno[1] = body.getElementsByTagName("faultcode").item(0).getTextContent();
          Retorno[2] = body.getElementsByTagName("faultstring").item(0).getTextContent();
          Retorno[3] = "";
          Retorno[4] = "";
        }
      }
      else
      {
        NodeList Rta = body.getElementsByTagName("applicationResponse");
        if (Rta.getLength() > 0)
        {
          str1 = body.getElementsByTagName("applicationResponse").item(0).getTextContent();
          decode(str1, RutaArchivo + NombreCDR);
          extrac_unzip(RutaArchivo + NombreCDR + ".zip", RutaArchivo);
          
          Retorno[0] = "1";
          Retorno[1] = valorXML(RutaArchivo + NombreCDR, "*", "ResponseCode");
          Retorno[2] = valorXML(RutaArchivo + NombreCDR, "*", "Description").toUpperCase();
          Retorno[3] = valorXML(RutaArchivo + NombreCDR, "", "DigestValue");
          Retorno[4] = valorXML(RutaArchivo + NombreCPE, "", "DigestValue");
        }
        else
        {
          Retorno[0] = "0";
          Retorno[1] = body.getElementsByTagName("faultcode").item(0).getTextContent();
          Retorno[2] = body.getElementsByTagName("faultstring").item(0).getTextContent();
          Retorno[3] = "";
          Retorno[4] = "";
        }
      }
      soapConnection.close();
    }
    catch (Exception e)
    {
      File fichero;
      File ficheroRta;
      File ficheroDummy;
      Retorno[0] = "0";
      Retorno[1] = "0";
      Retorno[2] = ("Error Conectarse a la SUNAT: " + e.getMessage());
      Retorno[3] = "";
      Retorno[4] = "";
    }
    finally
    {
      File fichero = new File(RutaArchivo + NombreCPE + ".zip");
      fichero.delete();
      
      File ficheroRta = new File(RutaArchivo + NombreCDR + ".zip");
      ficheroRta.delete();
      
      File ficheroDummy = new File(RutaArchivo + "dummy");
      ficheroDummy.delete();
    }
    return Retorno[0] + "|" + Retorno[1] + "|" + Retorno[2] + "|" + Retorno[3] + "|" + Retorno[4];
  }
  
  public static String ConexionCPE_RTPC(String ruc, String UsuarioSol, String PassSol, String NombreCPE, String NombreCDR, String RutaArchivo, String RutaWS)
  {
    String[] Retorno = new String[5];
    try
    {
      SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
      SOAPConnection soapConnection = soapConnectionFactory.createConnection();
      
      URL url = new URL(new URL(RutaWS), "", new URLStreamHandler()
      {
        protected URLConnection openConnection(URL url)
          throws IOException
        {
          URL target = new URL(url.toString());
          URLConnection connection = target.openConnection();
          
          connection.setConnectTimeout(10000);
          connection.setReadTimeout(60000);
          return connection;
        }
      });
      SOAPMessage soapResponse = soapConnection.call(CPEsendBill(ruc, UsuarioSol, PassSol, NombreCPE, RutaArchivo), url);
      
      printSOAPResponse(soapResponse);
      
      SOAPBody body = soapResponse.getSOAPBody();
      
      String str1 = "";
      if ((NombreCPE.substring(12, 14).equals("RA")) || (NombreCPE.substring(12, 14).equals("RC")) || (NombreCPE.substring(12, 14).equals("RR")))
      {
        NodeList Rta = body.getElementsByTagName("ticket");
        if (Rta.getLength() > 0)
        {
          str1 = body.getElementsByTagName("ticket").item(0).getTextContent();
          
          Retorno[0] = "1";
          Retorno[1] = "0";
          Retorno[2] = ("Ticket Nro: " + str1);
          Retorno[3] = "";
          Retorno[4] = "";
        }
        else
        {
          Retorno[0] = "0";
          Retorno[1] = body.getElementsByTagName("faultstring").item(0).getTextContent();
          Retorno[2] = body.getElementsByTagName("message").item(0).getTextContent();
          Retorno[3] = "";
          Retorno[4] = "";
        }
      }
      else
      {
        NodeList Rta = body.getElementsByTagName("applicationResponse");
        if (Rta.getLength() > 0)
        {
          str1 = body.getElementsByTagName("applicationResponse").item(0).getTextContent();
          
          decode(str1, RutaArchivo + NombreCDR);
          extrac_unzip(RutaArchivo + NombreCDR + ".zip", RutaArchivo);
          Retorno[0] = "1";
          Retorno[1] = valorXML(RutaArchivo + NombreCDR, "*", "ResponseCode");
          Retorno[2] = valorXML(RutaArchivo + NombreCDR, "*", "Description").toUpperCase();
          Retorno[3] = valorXML(RutaArchivo + NombreCDR, "", "DigestValue");
          Retorno[4] = valorXML(RutaArchivo + NombreCPE, "", "DigestValue");
        }
        else
        {
          Retorno[0] = "0";
          Retorno[1] = body.getElementsByTagName("faultstring").item(0).getTextContent();
          Retorno[2] = body.getElementsByTagName("message").item(0).getTextContent();
          Retorno[3] = "";
          Retorno[4] = "";
        }
      }
      soapConnection.close();
    }
    catch (Exception e)
    {
      File fichero;
      File ficheroRta;
      File ficheroDummy;
      Retorno[0] = "0";
      Retorno[1] = "0";
      Retorno[2] = ("Error Conectarse a la SUNAT: " + e.getMessage());
      Retorno[3] = "";
      Retorno[4] = "";
    }
    finally
    {
      File fichero = new File(RutaArchivo + NombreCPE + ".zip");
      fichero.delete();
      
      File ficheroRta = new File(RutaArchivo + NombreCDR + ".zip");
      ficheroRta.delete();
      
      File ficheroDummy = new File(RutaArchivo + "dummy");
      ficheroDummy.delete();
    }
    return Retorno[0] + "|" + Retorno[1] + "|" + Retorno[2] + "|" + Retorno[3] + "|" + Retorno[4];
  }
  
  public static SOAPMessage CPEsendBill(String ruc, String UsuarioSol, String PassSol, String NombreCPE, String RutaCPE)
    throws Exception
  {
    MessageFactory messageFactory = MessageFactory.newInstance();
    SOAPMessage soapMessage = messageFactory.createMessage();
    
    SOAPPart soapPart = soapMessage.getSOAPPart();
    
    String serverURI_ser = "http://service.sunat.gob.pe";
    String serverURI_wsse = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    
    SOAPEnvelope envelope = soapPart.getEnvelope();
    envelope.setPrefix("soapenv");
    envelope.addNamespaceDeclaration("ser", serverURI_ser);
    envelope.addNamespaceDeclaration("wsse", serverURI_wsse);
    
    SOAPHeader Header = envelope.getHeader();
    Header.setPrefix("soapenv");
    
    SOAPElement Security = Header.addChildElement("Security", "wsse");
    SOAPElement UsernameToken = Security.addChildElement("UsernameToken", "wsse");
    
    SOAPElement Username = UsernameToken.addChildElement("Username", "wsse").addTextNode(ruc + UsuarioSol);
    SOAPElement Password = UsernameToken.addChildElement("Password", "wsse").addTextNode(PassSol);
    
    SOAPBody soapBody = envelope.getBody();
    soapBody.setPrefix("soapenv");
    
    SOAPElement sendBill = null;
    if ((NombreCPE.substring(12, 14).equals("RA")) || (NombreCPE.substring(12, 14).equals("RC")) || (NombreCPE.substring(12, 14).equals("RR"))) {
      sendBill = soapBody.addChildElement("sendSummary", "ser");
    } else {
      sendBill = soapBody.addChildElement("sendBill", "ser");
    }
    SOAPElement fileName = sendBill.addChildElement("fileName").addTextNode(NombreCPE + ".zip");
    
    String ZIP64 = encode(RutaCPE + NombreCPE + ".zip");
    SOAPElement contentFile = sendBill.addChildElement("contentFile").addTextNode(ZIP64);
    
    soapMessage.saveChanges();
    
    soapMessage.writeTo(System.out);
    return soapMessage;
  }
  
  private static SOAPMessage ConsultaTicket(String ruc, String UsuarioSol, String PassSol, String NroTicket)
    throws Exception
  {
    MessageFactory messageFactory = MessageFactory.newInstance();
    SOAPMessage soapMessage = messageFactory.createMessage();
    SOAPPart soapPart = soapMessage.getSOAPPart();
    
    String serverURI_ser = "http://service.sunat.gob.pe";
    String serverURI_wsse = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    
    SOAPEnvelope envelope = soapPart.getEnvelope();
    envelope.setPrefix("soapenv");
    envelope.addNamespaceDeclaration("ser", serverURI_ser);
    envelope.addNamespaceDeclaration("wsse", serverURI_wsse);
    
    SOAPHeader Header = envelope.getHeader();
    Header.setPrefix("soapenv");
    
    SOAPElement Security = Header.addChildElement("Security", "wsse");
    SOAPElement UsernameToken = Security.addChildElement("UsernameToken", "wsse");
    
    SOAPElement Username = UsernameToken.addChildElement("Username", "wsse").addTextNode(ruc + UsuarioSol);
    SOAPElement Password = UsernameToken.addChildElement("Password", "wsse").addTextNode(PassSol);
    
    SOAPBody soapBody = envelope.getBody();
    soapBody.setPrefix("soapenv");
    
    SOAPElement getStatus = soapBody.addChildElement("getStatus", "ser");
    SOAPElement ticket = getStatus.addChildElement("ticket").addTextNode(NroTicket);
    
    soapMessage.saveChanges();
    
    System.out.print("Request SOAP Message = ");
    soapMessage.writeTo(System.out);
    return soapMessage;
  }
  
  public static String ConexionCPEConsultaTicket(String ruc, String UsuarioSol, String PassSol, String NroTicket, String NombreCPE, String NombreCDR, String RutaArchivo, String RutaWS)
  {
    String[] Retorno = new String[5];
    try
    {
      SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
      SOAPConnection soapConnection = soapConnectionFactory.createConnection();
      
      URL url = new URL(new URL(RutaWS), "", new URLStreamHandler()
      {
        protected URLConnection openConnection(URL url)
          throws IOException
        {
          URL target = new URL(url.toString());
          URLConnection connection = target.openConnection();
          
          connection.setConnectTimeout(10000);
          connection.setReadTimeout(60000);
          return connection;
        }
      });
      SOAPMessage soapResponse = soapConnection.call(ConsultaTicket(ruc, UsuarioSol, PassSol, NroTicket), url);
      
      printSOAPResponse(soapResponse);
      
      SOAPBody body = soapResponse.getSOAPBody();
      
      String msjRta = "";
      NodeList Rta = body.getElementsByTagName("statusCode");
      if (Rta.getLength() > 0)
      {
        int codigoRta = Integer.parseInt(body.getElementsByTagName("statusCode").item(0).getTextContent());
        msjRta = body.getElementsByTagName("content").item(0).getTextContent();
        if ((codigoRta >= 0) && (codigoRta <= 99))
        {
          decode(msjRta, RutaArchivo + NombreCDR);
          extrac_unzip(RutaArchivo + NombreCDR + ".zip", RutaArchivo);
          if (codigoRta == 0)
          {
            Retorno[0] = "1";
            Retorno[1] = valorXML(RutaArchivo + NombreCDR, "*", "ResponseCode");
            Retorno[2] = valorXML(RutaArchivo + NombreCDR, "*", "Description").toUpperCase();
            Retorno[3] = valorXML(RutaArchivo + NombreCDR, "", "DigestValue");
            Retorno[4] = valorXML(RutaArchivo + NombreCPE, "", "DigestValue");
          }
          else
          {
            Retorno[0] = "0";
            Retorno[1] = valorXML(RutaArchivo + NombreCDR, "*", "ResponseCode");
            Retorno[2] = valorXML(RutaArchivo + NombreCDR, "*", "Description").toUpperCase();
            Retorno[3] = valorXML(RutaArchivo + NombreCDR, "", "DigestValue");
            Retorno[4] = valorXML(RutaArchivo + NombreCPE, "", "DigestValue");
          }
        }
        else
        {
          Retorno[0] = "0";
          Retorno[1] = Integer.toString(codigoRta);
          Retorno[2] = msjRta;
          Retorno[3] = "";
          Retorno[4] = "";
        }
      }
      else
      {
        Retorno[0] = "0";
        Retorno[1] = "0000";
        Retorno[2] = "Error desconocido";
        Retorno[3] = "";
        Retorno[4] = "";
      }
      soapConnection.close();
    }
    catch (Exception e)
    {
      File ficheroRta;
      File ficheroDummy;
      Retorno[0] = "0";
      Retorno[1] = "0000";
      Retorno[2] = ("Error Conectarse a la SUNAT: " + e.getMessage());
      Retorno[3] = "";
      Retorno[4] = "";
    }
    finally
    {
      File ficheroRta = new File(RutaArchivo + NombreCDR + ".zip");
      ficheroRta.delete();
      
      File ficheroDummy = new File(RutaArchivo + NombreCDR + "dummy");
      ficheroDummy.delete();
    }
    return Retorno[0] + "|" + Retorno[1] + "|" + Retorno[2] + "|" + Retorno[3] + "|" + Retorno[4];
  }
  
  private static SOAPMessage ConsultaEstadoFactura(String ruc, String UsuarioSol, String PassSol, String rucCliente, String tipoDocumento, String serie, String numero)
    throws Exception
  {
    MessageFactory messageFactory = MessageFactory.newInstance();
    SOAPMessage soapMessage = messageFactory.createMessage();
    SOAPPart soapPart = soapMessage.getSOAPPart();
    
    String serverURI_ser = "http://service.sunat.gob.pe";
    String serverURI_wsse = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    
    SOAPEnvelope envelope = soapPart.getEnvelope();
    envelope.setPrefix("soapenv");
    envelope.addNamespaceDeclaration("ser", serverURI_ser);
    envelope.addNamespaceDeclaration("wsse", serverURI_wsse);
    
    SOAPHeader Header = envelope.getHeader();
    Header.setPrefix("soapenv");
    
    SOAPElement Security = Header.addChildElement("Security", "wsse");
    SOAPElement UsernameToken = Security.addChildElement("UsernameToken", "wsse");
    
    SOAPElement Username = UsernameToken.addChildElement("Username", "wsse").addTextNode(ruc + UsuarioSol);
    SOAPElement Password = UsernameToken.addChildElement("Password", "wsse").addTextNode(PassSol);
    
    SOAPBody soapBody = envelope.getBody();
    soapBody.setPrefix("soapenv");
    
    SOAPElement getStatus = soapBody.addChildElement("getStatus", "ser");
    SOAPElement rucComprobante = getStatus.addChildElement("rucComprobante").addTextNode(rucCliente);
    SOAPElement tipoComprobante = getStatus.addChildElement("tipoComprobante").addTextNode(tipoDocumento);
    SOAPElement serieComprobante = getStatus.addChildElement("serieComprobante").addTextNode(serie);
    SOAPElement numeroComprobante = getStatus.addChildElement("numeroComprobante").addTextNode(numero);
    
    soapMessage.saveChanges();
    
    soapMessage.writeTo(System.out);
    return soapMessage;
  }
  
  public static String ConexionCPEConsultaEstadoFactura(String ruc, String UsuarioSol, String PassSol, String rucCliente, String tipoDocumento, String serie, String numero, String RutaWS)
  {
    String[] Retorno = new String[5];
    try
    {
      SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
      SOAPConnection soapConnection = soapConnectionFactory.createConnection();
      
      URL url = new URL(new URL(RutaWS), "", new URLStreamHandler()
      {
        protected URLConnection openConnection(URL url)
          throws IOException
        {
          URL target = new URL(url.toString());
          URLConnection connection = target.openConnection();
          
          connection.setConnectTimeout(10000);
          connection.setReadTimeout(60000);
          return connection;
        }
      });
      SOAPMessage soapResponse = soapConnection.call(ConsultaEstadoFactura(ruc, UsuarioSol, PassSol, rucCliente, tipoDocumento, serie, numero), url);
      
      printSOAPResponse(soapResponse);
      
      SOAPBody body = soapResponse.getSOAPBody();
      
      String codigoRta = "";
      String msjRta = "";
      NodeList Rta = body.getElementsByTagName("statusCode");
      if (Rta.getLength() > 0)
      {
        codigoRta = body.getElementsByTagName("statusCode").item(0).getTextContent();
        msjRta = body.getElementsByTagName("statusMessage").item(0).getTextContent();
        
        Retorno[0] = "1";
        Retorno[1] = codigoRta;
        Retorno[2] = msjRta;
        Retorno[3] = "";
        Retorno[4] = "";
      }
      else
      {
        Retorno[0] = "0";
        Retorno[1] = "0000";
        Retorno[2] = "Error desconocido";
        Retorno[3] = "";
        Retorno[4] = "";
      }
      soapConnection.close();
    }
    catch (Exception e)
    {
      e = 
      
        e;Retorno[0] = "0";Retorno[1] = "0000";Retorno[2] = ("Error Conectarse a la SUNAT: " + e.getMessage());Retorno[3] = "";Retorno[4] = "";
    }
    finally {}
    return Retorno[0] + "|" + Retorno[1] + "|" + Retorno[2] + "|" + Retorno[3] + "|" + Retorno[4];
  }
  
  private static SOAPMessage CPEsendBill_StatusCDR(String ruc, String UsuarioSol, String PassSol, String tipo_comprobante, String nro_Comprobante)
    throws Exception
  {
    String[] comprobante = nro_Comprobante.split("-");
    String serie = comprobante[0];
    String numero = comprobante[1];
    
    MessageFactory messageFactory = MessageFactory.newInstance();
    SOAPMessage soapMessage = messageFactory.createMessage();
    SOAPPart soapPart = soapMessage.getSOAPPart();
    
    String serverURI_ser = "http://service.sunat.gob.pe";
    String serverURI_wsse = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    String serverURI_xsi = "http://www.w3.org/2001/XMLSchema-instance";
    String serverURI_xsd = "http://www.w3.org/2001/XMLSchema";
    String serverURI_enc = "http://schemas.xmlsoap.org/soap/encoding/";
    String serverURI_env = "http://schemas.xmlsoap.org/soap/encoding/";
    
    SOAPEnvelope envelope = soapPart.getEnvelope();
    envelope.setPrefix("soapenv");
    envelope.addNamespaceDeclaration("m", serverURI_ser);
    envelope.addNamespaceDeclaration("SOAP-ENC", serverURI_enc);
    envelope.addNamespaceDeclaration("wsse", serverURI_wsse);
    envelope.addNamespaceDeclaration("xsi", serverURI_xsi);
    envelope.addNamespaceDeclaration("xsd", serverURI_xsd);
    
    SOAPHeader Header = envelope.getHeader();
    Header.addNamespaceDeclaration("soapenv", serverURI_env);
    Header.setPrefix("soapenv");
    
    SOAPElement Security = Header.addChildElement("Security", "wsse");
    SOAPElement UsernameToken = Security.addChildElement("UsernameToken", "wsse");
    
    SOAPElement Username = UsernameToken.addChildElement("Username", "wsse").addTextNode(ruc + UsuarioSol);
    SOAPElement Password = UsernameToken.addChildElement("Password", "wsse").addTextNode(PassSol);
    
    SOAPBody soapBody = envelope.getBody();
    soapBody.setPrefix("soapenv");
    
    SOAPElement getStatusCdr = soapBody.addChildElement("getStatusCdr", "m");
    getStatusCdr.addNamespaceDeclaration("m", "xmlns:m='http://service.sunat.gob.pe'");
    
    SOAPElement rucComprobante = getStatusCdr.addChildElement("rucComprobante").addTextNode(ruc);
    SOAPElement tipoComprobante = getStatusCdr.addChildElement("tipoComprobante").addTextNode(tipo_comprobante);
    SOAPElement serieComprobante = getStatusCdr.addChildElement("serieComprobante").addTextNode(serie);
    SOAPElement numeroComprobante = getStatusCdr.addChildElement("numeroComprobante").addTextNode(numero);
    
    soapMessage.saveChanges();
    
    soapMessage.writeTo(System.out);
    
    return soapMessage;
  }
  
  public static String ConexionCPEStatusCDR(String ruc, String UsuarioSol, String PassSol, String tipoDocumento, String nro_comprobante, String NombreCPE, String NombreCDR, String RutaArchivo, String RutaWS)
  {
    String[] Retorno = new String[5];
    try
    {
      SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
      SOAPConnection soapConnection = soapConnectionFactory.createConnection();
      
      URL url = new URL(new URL(RutaWS), "", new URLStreamHandler()
      {
        protected URLConnection openConnection(URL url)
          throws IOException
        {
          URL target = new URL(url.toString());
          URLConnection connection = target.openConnection();
          
          connection.setConnectTimeout(10000);
          connection.setReadTimeout(60000);
          return connection;
        }
      });
      SOAPMessage soapResponse = soapConnection.call(CPEsendBill_StatusCDR(ruc, UsuarioSol, PassSol, tipoDocumento, nro_comprobante), url);
      
      printSOAPResponse(soapResponse);
      
      SOAPBody body = soapResponse.getSOAPBody();
      
      String codigoRta = "";
      String msjRta = "";
      String str1 = "";
      NodeList Rta = body.getElementsByTagName("statusCode");
      if (Rta.getLength() > 0)
      {
        codigoRta = body.getElementsByTagName("statusCode").item(0).getTextContent();
        msjRta = body.getElementsByTagName("statusMessage").item(0).getTextContent();
        if (codigoRta.equals("0004"))
        {
          str1 = body.getElementsByTagName("content").item(0).getTextContent();
          decode(str1, RutaArchivo + NombreCDR);
          extrac_unzip(RutaArchivo + NombreCDR + ".zip", RutaArchivo);
          
          Retorno[0] = "1";
          Retorno[1] = valorXML(RutaArchivo + NombreCDR, "*", "ResponseCode");
          Retorno[2] = valorXML(RutaArchivo + NombreCDR, "*", "Description").toUpperCase();
          Retorno[3] = valorXML(RutaArchivo + NombreCDR, "", "DigestValue");
          Retorno[4] = valorXML(RutaArchivo + NombreCPE, "", "DigestValue");
        }
        else
        {
          Retorno[0] = "1";
          Retorno[1] = codigoRta;
          Retorno[2] = msjRta;
          Retorno[3] = "";
          Retorno[4] = "";
        }
      }
      else
      {
        NodeList RtaFaultCode = body.getElementsByTagName("faultcode");
        if (RtaFaultCode.getLength() > 0)
        {
          Retorno[0] = "0";
          Retorno[1] = body.getElementsByTagName("faultcode").item(0).getTextContent().replace("soap-env:Client.", "");
          Retorno[2] = body.getElementsByTagName("faultstring").item(0).getTextContent();
          Retorno[3] = "";
          Retorno[4] = "";
        }
        else
        {
          Retorno[0] = "0";
          Retorno[1] = "0000";
          Retorno[2] = "Error desconocido";
          Retorno[3] = "";
          Retorno[4] = "";
        }
      }
      soapConnection.close();
    }
    catch (Exception e)
    {
      File ficheroRta;
      File ficheroDummy;
      Retorno[0] = "0";
      Retorno[1] = "0000";
      Retorno[2] = ("Error Conectarse a la SUNAT: " + e.getMessage());
      Retorno[3] = "";
      Retorno[4] = "";
    }
    finally
    {
      File ficheroRta = new File(RutaArchivo + NombreCDR + ".zip");
      ficheroRta.delete();
      
      File ficheroDummy = new File(RutaArchivo + "dummy");
      ficheroDummy.delete();
    }
    return Retorno[0] + "|" + Retorno[1] + "|" + Retorno[2] + "|" + Retorno[3] + "|" + Retorno[4];
  }
  
  public static void printSOAPResponse(SOAPMessage soapResponse)
    throws Exception
  {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    Source sourceContent = soapResponse.getSOAPPart().getContent();
    
    StringWriter writer = new StringWriter();
    StreamResult result = new StreamResult(writer);
    
    transformer.transform(sourceContent, result);
    
    String strResult = writer.toString();
    logger.debug("\nCDR Status XML response: " + strResult);
  }
  
  public static String encode(String RutaCPE)
    throws Exception
  {
    FileInputStream fis = new FileInputStream(RutaCPE);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int c = 0;
    while ((c = fis.read()) != -1) {
      baos.write(c);
    }
    fis.close();
    byte[] byteReturn = baos.toByteArray();
    
    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
    OutputStream b64os = MimeUtility.encode(baos2, "base64");
    b64os.write(byteReturn);
    b64os.close();
    return baos2.toString();
  }
  
  public static String decode(String Rptarchivo, String nombreArchivo)
    throws Exception
  {
    byte[] recibeArchivoBytes = null;
    recibeArchivoBytes = Rptarchivo.getBytes();
    
    ByteArrayInputStream bais = new ByteArrayInputStream(recibeArchivoBytes);
    InputStream b64is = MimeUtility.decode(bais, "base64");
    byte[] tmp = new byte[recibeArchivoBytes.length];
    int n = b64is.read(tmp);
    byte[] res = new byte[n];
    System.arraycopy(tmp, 0, res, 0, n);
    try
    { 
      String zippedFileName = XmlSunat.getValidatedPath(nombreArchivo + ".zip");
    	
      OutputStream out = new FileOutputStream(zippedFileName);
      out.write(res);
      out.close();
    }
    catch (Exception ex)
    {
      logger.error(ex.getMessage());
    }
    finally {}
    return "ok";
  }
  
  public static void extrac_unzip(String source, String destination)
  {
    try
    {
      ZipFile zipFile = new ZipFile(source);
      zipFile.extractAll(destination);
    }
    catch (ZipException e) {}
  }
  
  public static String valorXML(String rutaArchivo, String Nspace, String TagName)
    throws SAXException, IOException, ParserConfigurationException
  {
    String rta = "";
    try
    {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(rutaArchivo + ".xml"), "ISO-8859-1");
      doc.getDocumentElement().normalize();
      if (Nspace.length() > 0) {
        rta = doc.getDocumentElement().getElementsByTagNameNS("*", TagName).item(0).getTextContent();
      } else {
        rta = doc.getDocumentElement().getElementsByTagName(TagName).item(0).getTextContent();
      }
    }
    catch (Exception e) {}
    return rta;
  }
}
