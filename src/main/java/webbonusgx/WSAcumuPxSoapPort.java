
package webbonusgx;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WSAcumuPxSoapPort", targetNamespace = "WebBonusGX")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WSAcumuPxSoapPort {


    /**
     * 
     * @param parameters
     * @return
     *     returns webbonusgx.WSAcumuPxExecuteResponse
     */
    @WebMethod(operationName = "Execute", action = "WebBonusGXaction/AWSACUMUPX.Execute")
    @WebResult(name = "WSAcumuPx.ExecuteResponse", targetNamespace = "WebBonusGX", partName = "parameters")
    public WSAcumuPxExecuteResponse execute(
        @WebParam(name = "WSAcumuPx.Execute", targetNamespace = "WebBonusGX", partName = "parameters")
        WSAcumuPxExecute parameters);

}
