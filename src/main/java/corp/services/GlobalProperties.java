package corp.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("globalProperties")
public class GlobalProperties {
	
	private String companyName;
	private String shortCompanyName;
	private String companyAddress;
	private String companyState;
	private String companyProvince;
	private String companyDistrict;
	private String companyCountryCode;
	private String companyUbigeo;
	private String companyAddress2;
	private String companyPhoneNumber;
	private String companyUrl;
	private String emailUsername;
	private String emailPassword;
	private String emailHost;
	private String emailFrom;
	private String myRuc;
	private String sunatInvoicingServiceURL;
	private String sunatConsultInvoiceURL;
	private String sunatSignatureFileName;
	private String sunatSignaturePassword;
	private String sunatSolUsername;
	private String sunatSolPassword;
	private String timeZoneID;
	private String comercio;
	private String bonusURL;
	
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
	public String getSunatConsultInvoiceURL() {
		return sunatConsultInvoiceURL;
	}
	public void setSunatConsultInvoiceURL(String sunatConsultInvoiceURL) {
		this.sunatConsultInvoiceURL = sunatConsultInvoiceURL;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getShortCompanyName() {
		return shortCompanyName;
	}
	public void setShortCompanyName(String shortCompanyName) {
		this.shortCompanyName = shortCompanyName;
	}
	public String getCompanyUbigeo() {
		return companyUbigeo;
	}
	public void setCompanyUbigeo(String companyUbigeo) {
		this.companyUbigeo = companyUbigeo;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyState() {
		return companyState;
	}
	public void setCompanyState(String companyState) {
		this.companyState = companyState;
	}
	public String getCompanyProvince() {
		return companyProvince;
	}
	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}
	public String getCompanyDistrict() {
		return companyDistrict;
	}
	public void setCompanyDistrict(String companyDistrict) {
		this.companyDistrict = companyDistrict;
	}
	public String getCompanyCountryCode() {
		return companyCountryCode;
	}
	public void setCompanyCountryCode(String companyCountryCode) {
		this.companyCountryCode = companyCountryCode;
	}
	public String getCompanyAddress2() {
		return companyAddress2;
	}
	public void setCompanyAddress2(String companyAddress2) {
		this.companyAddress2 = companyAddress2;
	}
	public String getCompanyPhoneNumber() {
		return companyPhoneNumber;
	}
	public void setCompanyPhoneNumber(String companyPhoneNumber) {
		this.companyPhoneNumber = companyPhoneNumber;
	}
	public String getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	
}
