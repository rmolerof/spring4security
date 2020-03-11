package corp.sunat.electronica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

public class FirmaCPESunat
{
  public static void main(String[] args)
    throws FileNotFoundException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, ParserConfigurationException, SAXException, MarshalException, KeyStoreException, IOException, CertificateException, Exception
  {
    int flg_firma = 0;
    
    String rutaXML = "H:\\CPE\\BETA\\20100078792-20-R002-2251";
    String rutaFirma = "H:\\CPE\\FIRMABETA\\CD AVON 2017.pfx";
    String passFirma = "Avon2017";
    
    int Result = Signature(flg_firma, rutaXML, rutaFirma, passFirma);
    System.out.println(Result);
  }
  
  public static int Signature(int flg_firma, String rutaXML, String rutaFirma, String passFirma)
    throws FileNotFoundException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, ParserConfigurationException, SAXException, MarshalException, KeyStoreException, IOException, CertificateException, Exception
  {
    int result = 0;
    try
    {
      XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
      Reference ref = fac.newReference("", fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), Collections.singletonList(fac.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec)null)), null, null);
      
      SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (C14NMethodParameterSpec)null), fac
      
        .newSignatureMethod("http://www.w3.org/2000/09/xmldsig#rsa-sha1", null), 
        Collections.singletonList(ref));
      
      KeyStore p12 = KeyStore.getInstance("pkcs12");
      p12.load(new FileInputStream(rutaFirma), passFirma.toCharArray());
      
      Enumeration e = p12.aliases();
      String alias = (String)e.nextElement();
      System.out.println("Alias certifikata:" + alias);
      
      KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry)p12.getEntry(alias, new KeyStore.PasswordProtection(passFirma.toCharArray()));
      X509Certificate cert = (X509Certificate)keyEntry.getCertificate();
      
      System.out.println("cert name:" + cert.getSubjectX500Principal().getName());
      System.out.println("cert serial number: " + cert.getSerialNumber());
      
      KeyInfoFactory kif = fac.getKeyInfoFactory();
      List x509Content = new ArrayList();
      x509Content.add(cert.getSubjectX500Principal().getName());
      x509Content.add(cert);
      X509Data xd = kif.newX509Data(x509Content);
      KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));
      
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(rutaXML + ".xml"), "ISO-8859-1");
      
      Element name = (Element)doc.getElementsByTagName("Signature").item(0);
      if (name != null) {
        name.getParentNode().removeChild(name);
      }
      doc.getDocumentElement().normalize();
      
      DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement().getElementsByTagNameNS("*", "ExtensionContent").item(flg_firma));
      XMLSignature signature = fac.newXMLSignature(si, ki, null, "SignatureSP", null);
      signature.sign(dsc);
      
      OutputStream os = new FileOutputStream(rutaXML + ".xml");
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer trans = tf.newTransformer();
      trans.transform(new DOMSource(doc), new StreamResult(os));
      
      add_unzip(rutaXML + ".xml", rutaXML + ".zip");
      
      result = 1;
    }
    catch (Exception e)
    {
      result = 0;
      System.err.println("" + e.getMessage());
      e.printStackTrace();
    }
    return result;
  }
  
  public static void add_unzip(String archivoOriginal, String archivoZip)
  {
    try
    {
      ZipFile archivo = new ZipFile(archivoZip);
      ArrayList lista = new ArrayList();
      lista.add(new File(archivoOriginal));
      ZipParameters parametros = new ZipParameters();
      parametros.setCompressionMethod(8);
      parametros.setCompressionLevel(5);
      archivo.addFiles(lista, parametros);
    }
    catch (ZipException ex)
    {
      ex.printStackTrace();
    }
  }
}
