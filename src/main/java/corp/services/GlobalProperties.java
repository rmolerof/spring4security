package corp.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("globalProperties")
public class GlobalProperties {
	
	public String emailUsername;
	public String emailPassword;
	public String emailHost;
	public String emailFrom;
	public String myRuc;
	public String sunatInvoicingServiceURL;
	public String sunatSignatureFileName;
	public String sunatSignaturePassword;
	public String sunatSolUsername;
	public String sunatSolPassword;
	public String timeZoneID;
	public String comercio;
	public String bonusURL;
	
	public String getEmailUsername() {
		return emailUsername;
	}
	public void setEmailUsername(String emailUsername) {
		this.emailUsername = emailUsername;
	}
	public String getEmailPassword() {
		return emailPassword;
	}
	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}
	public String getEmailHost() {
		return emailHost;
	}
	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}
	public String getEmailFrom() {
		return emailFrom;
	}
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}
	public String getMyRuc() {
		return myRuc;
	}
	public void setMyRuc(String myRuc) {
		this.myRuc = myRuc;
	}
	public String getSunatInvoicingServiceURL() {
		return sunatInvoicingServiceURL;
	}
	public void setSunatInvoicingServiceURL(String sunatInvoicingServiceURL) {
		this.sunatInvoicingServiceURL = sunatInvoicingServiceURL;
	}
	public String getSunatSignatureFileName() {
		return sunatSignatureFileName;
	}
	public void setSunatSignatureFileName(String sunatSignatureFileName) {
		this.sunatSignatureFileName = sunatSignatureFileName;
	}
	public String getSunatSignaturePassword() {
		return sunatSignaturePassword;
	}
	public void setSunatSignaturePassword(String sunatSignaturePassword) {
		this.sunatSignaturePassword = sunatSignaturePassword;
	}
	public String getSunatSolUsername() {
		return sunatSolUsername;
	}
	public void setSunatSolUsername(String sunatSolUsername) {
		this.sunatSolUsername = sunatSolUsername;
	}
	public String getSunatSolPassword() {
		return sunatSolPassword;
	}
	public void setSunatSolPassword(String sunatSolPassword) {
		this.sunatSolPassword = sunatSolPassword;
	}
	public String getTimeZoneID() {
		return timeZoneID;
	}
	public void setTimeZoneID(String timeZoneID) {
		this.timeZoneID = timeZoneID;
	}
	public String getComercio() {
		return comercio;
	}
	public void setComercio(String comercio) {
		this.comercio = comercio;
	}
	public String getBonusURL() {
		return bonusURL;
	}
	public void setBonusURL(String bonusURL) {
		this.bonusURL = bonusURL;
	}
	
}
