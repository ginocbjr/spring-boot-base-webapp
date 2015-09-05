package org.networking.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "SALES_ITEM")
public class SalesItem extends BaseEntity{

	@OneToOne(cascade = CascadeType.ALL)
	private Product product;
	
	@Column(name="QTY")
	private Long quantity;
	
	@Column(name="TOTAL_PRICE")
	private Double totalPrice;

	public Product getProduct() {
		return product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
