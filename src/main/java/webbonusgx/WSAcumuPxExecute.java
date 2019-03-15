
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
 *         &lt;element name="Comercio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Tarjetac11" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Fechatransaccion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Horatransaccion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Pos_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Pos_secuencial" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Lineasdetalle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Totalsolestrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Flagoperacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "comercio",
    "tarjetac11",
    "fechatransaccion",
    "horatransaccion",
    "posId",
    "posSecuencial",
    "lineasdetalle",
    "totalsolestrn",
    "flagoperacion"
})
@XmlRootElement(name = "WSAcumuPx.Execute")
public class WSAcumuPxExecute {

    @XmlElement(name = "Comercio", required = true)
    protected String comercio;
    @XmlElement(name = "Tarjetac11", required = true)
    protected String tarjetac11;
    @XmlElement(name = "Fechatransaccion", required = true)
    protected String fechatransaccion;
    @XmlElement(name = "Horatransaccion", required = true)
    protected String horatransaccion;
    @XmlElement(name = "Pos_id", required = true)
    protected String posId;
    @XmlElement(name = "Pos_secuencial", required = true)
    protected String posSecuencial;
    @XmlElement(name = "Lineasdetalle", required = true)
    protected String lineasdetalle;
    @XmlElement(name = "Totalsolestrn", required = true)
    protected String totalsolestrn;
    @XmlElement(name = "Flagoperacion", required = true)
    protected String flagoperacion;

    /**
     * Gets the value of the comercio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComercio() {
        return comercio;
    }

    /**
     * Sets the value of the comercio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComercio(String value) {
        this.comercio = value;
    }

    /**
     * Gets the value of the tarjetac11 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarjetac11() {
        return tarjetac11;
    }

    /**
     * Sets the value of the tarjetac11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarjetac11(String value) {
        this.tarjetac11 = value;
    }

    /**
     * Gets the value of the fechatransaccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechatransaccion() {
        return fechatransaccion;
    }

    /**
     * Sets the value of the fechatransaccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechatransaccion(String value) {
        this.fechatransaccion = value;
    }

    /**
     * Gets the value of the horatransaccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoratransaccion() {
        return horatransaccion;
    }

    /**
     * Sets the value of the horatransaccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoratransaccion(String value) {
        this.horatransaccion = value;
    }

    /**
     * Gets the value of the posId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosId() {
        return posId;
    }

    /**
     * Sets the value of the posId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosId(String value) {
        this.posId = value;
    }

    /**
     * Gets the value of the posSecuencial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosSecuencial() {
        return posSecuencial;
    }

    /**
     * Sets the value of the posSecuencial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosSecuencial(String value) {
        this.posSecuencial = value;
    }

    /**
     * Gets the value of the lineasdetalle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineasdetalle() {
        return lineasdetalle;
    }

    /**
     * Sets the value of the lineasdetalle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineasdetalle(String value) {
        this.lineasdetalle = value;
    }

    /**
     * Gets the value of the totalsolestrn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalsolestrn() {
        return totalsolestrn;
    }

    /**
     * Sets the value of the totalsolestrn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalsolestrn(String value) {
        this.totalsolestrn = value;
    }

    /**
     * Gets the value of the flagoperacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagoperacion() {
        return flagoperacion;
    }

    /**
     * Sets the value of the flagoperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagoperacion(String value) {
        this.flagoperacion = value;
    }

}
