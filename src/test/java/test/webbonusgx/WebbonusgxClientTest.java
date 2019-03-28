package test.webbonusgx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import corp.Application;
import corp.services.GlobalProperties;
import webbonusgx.WSAcumuPx;
import webbonusgx.WSAcumuPxExecute;
import webbonusgx.WSAcumuPxExecuteResponse;
import webbonusgx.WSAcumuPxSoapPort;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class WebbonusgxClientTest {
	
	@Autowired
	private GlobalProperties globalProperties;
	
	@Test
	public void webbonusgxSoapCallTest() {
		WSAcumuPx wsAcumuPx = new WSAcumuPx();
		WSAcumuPxSoapPort wsAcumuPxSoapPort = wsAcumuPx.getWSAcumuPxSoapPort();
		
		WSAcumuPxExecute parameters = new WSAcumuPxExecute();
		parameters.setComercio("013308");
		parameters.setTarjetac11("00005558259");
		parameters.setFechatransaccion("08082017");
		parameters.setHoratransaccion("0815");
		parameters.setPosId("PC1");
		parameters.setPosSecuencial("02");
		parameters.setLineasdetalle("00050000000000000007100010000");
		parameters.setTotalsolestrn("0005000");
		parameters.setFlagoperacion("0");
		
		WSAcumuPxExecuteResponse wsAcumuPxExecuteResponse = wsAcumuPxSoapPort.execute(parameters);
		System.out.println(wsAcumuPxExecuteResponse.toString());
	}	
	
}