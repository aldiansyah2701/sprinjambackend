package com.aldidb.backenddb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity(name= "invoice")
@Table(name="invoice")
public class Invoice extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 4211854570169058068L;
	
	public enum STATUS{
		PENDING, APPROVED, REJECTED
	}
	
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",
            strategy = "org.hibernate.id.UUIDGenerator")
    private String uuid;
	
	@Column(name = "business_key")
	private String businessKey;
	
	@Column(name="seller_code")
	private String sellerCode;
	
	@Column(name="buyer_code")
	private String buyerCode;
	
	@Column
	private String status;
	
	@Column(nullable = false)
	private Double amount;
	
	@JoinColumn(name = "currency_uuid")
	@ManyToOne
	private Currency currency;
	
	@JoinColumn(name = "product_uuid")
	@ManyToOne
	private Product product;
	
	@JoinColumn(name= "user_uuid")
	@ManyToOne
	private User user;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
