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
@Data
public class Invoice extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 4211854570169058068L;
	
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

}
