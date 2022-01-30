package com.aldidb.backenddb.message;

public class RequestCreateInvoice {
	
	private String businessKey;
	
	private String sellerName;
	
	private String buyerName;
	
	private String amount;
	
	private String currencyUuid;
	
	private String productUuid;
	
	private String userUuid;

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrencyUuid() {
		return currencyUuid;
	}

	public void setCurrencyUuid(String currencyUuid) {
		this.currencyUuid = currencyUuid;
	}

	public String getProductUuid() {
		return productUuid;
	}

	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
}
