
package webbonusgx;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Saldopuntos" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Flagretorno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "saldopuntos",
    "flagretorno"
})
@XmlRootElement(name = "WSAcumuPx.ExecuteResponse")
public class WSAcumuPxExecuteResponse {

    @XmlElement(name = "Saldopuntos", required = true)
    protected String saldopuntos;
    @XmlElement(name = "Flagretorno", required = true)
    protected String flagretorno;

    /**
     * Gets the value of the saldopuntos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSaldopuntos() {
        return saldopuntos;
    }

    /**
     * Sets the value of the saldopuntos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaldopuntos(String value) {
        this.saldopuntos = value;
    }

    /**
     * Gets the value of the flagretorno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagretorno() {
        return flagretorno;
    }

    /**
     * Sets the value of the flagretorno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagretorno(String value) {
        this.flagretorno = value;
    }

}
