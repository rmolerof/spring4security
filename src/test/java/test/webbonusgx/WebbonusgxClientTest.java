package test.webbonusgx;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import corp.Application;
import corp.domain.InvoiceDao;
import corp.domain.InvoicesRepository;
import corp.model.InvoiceVo;
import corp.services.ApplicationService;
import corp.services.GlobalProperties;
import webbonusgx.BonusPointsOperationResponse;
import webbonusgx.WSAcumuPx;
import webbonusgx.WSAcumuPxExecute;
import webbonusgx.WSAcumuPxExecuteResponse;
import webbonusgx.WSAcumuPxSoapPort;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class WebbonusgxClientTest {
	
	private static Logger logger = LoggerFactory.getLogger(WebbonusgxClientTest.class);
	
	@Autowired
	private GlobalProperties globalProperties;
	@Autowired
	private ApplicationService userService;
	@Autowired
	private InvoicesRepository invoicesRepository;
	
	@Ignore
	@Test
	public void webbonusgxSoapCallTest() {
		WSAcumuPx wsAcumuPx = new WSAcumuPx();
		WSAcumuPxSoapPort wsAcumuPxSoapPort = wsAcumuPx.getWSAcumuPxSoapPort();
		
		WSAcumuPxExecute parameters = new WSAcumuPxExecute();
		parameters.setComercio(globalProperties.getComercio());
		parameters.setTarjetac11("00005558259");
		parameters.setFechatransaccion("08082017");
		parameters.setHoratransaccion("0815");
		parameters.setPosId("00F001");
		parameters.setPosSecuencial("02");
		parameters.setLineasdetalle("00000050000000000000007100010000");
		parameters.setTotalsolestrn("0005000");
		parameters.setFlagoperacion("0");
		
		WSAcumuPxExecuteResponse wsAcumuPxExecuteResponse = wsAcumuPxSoapPort.execute(parameters);
		logger.info(wsAcumuPxExecuteResponse.toString());
		Assert.assertNotNull(wsAcumuPxExecuteResponse);
		Assert.assertNotNull("Property is null", wsAcumuPxExecuteResponse.getFlagretorno());
		Assert.assertNotNull("Property is null", wsAcumuPxExecuteResponse.getSaldopuntos());
	}
	
	@Test
	public void accumulateInvoiceForBonusPointsTest() {
		
		InvoiceDao invoiceDao = invoicesRepository.findFirstByInvoiceNumberAndSunatStatus("F001-00002047", ApplicationService.PENDING_STATUS);
		BonusPointsOperationResponse bonusPointsOperationResponse = userService.accumulateInvoiceForBonusPoints(new InvoiceVo(invoiceDao));
		
		Assert.assertNotNull(bonusPointsOperationResponse);
		Assert.assertNotNull("Property is null", bonusPointsOperationResponse.getAccumulatedPoints());
		Assert.assertNotNull("Property is null", bonusPointsOperationResponse.getResponseFlag());
	}
	
}