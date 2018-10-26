package hello.businessModel;

public class ExpenseOrCredit {
	
	private String item;
	private Double amt;
	
	public ExpenseOrCredit() {
		
	}
	
	public ExpenseOrCredit(String item, Double amt) {
		this.item = item;
		this.amt = amt;
	}
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Double getAmt() {
		return amt;
	}
	public void setAmt(Double amt) {
		this.amt = amt;
	}

}
