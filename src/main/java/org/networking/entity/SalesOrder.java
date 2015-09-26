package org.networking.entity;

import org.networking.enums.PointType;

import java.util.List;

import javax.persistence.*;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "SALES_ORDER")
public class SalesOrder extends BaseEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SELLER_ID")
	private Member seller;

	@Column(name = "SELLER_ID", insertable = false, updatable = false)
	private Long sellerId;

	@Column(name = "TOTAL_PRICE")
	private Double totalPrice;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<SalesItem> items;

	@Transient
	private Double totalMemberPoints;

	@Transient
	private Double totalGroupPoints;

	public Member getSeller() {
		return seller;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public List<SalesItem> getItems() {
		return items;
	}

	public void setSeller(Member seller) {
		this.seller = seller;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setItems(List<SalesItem> items) {
		this.items = items;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Double getTotalMemberPoints() {

		return totalMemberPoints;
	}

	public void setTotalMemberPoints(Double totalMemberPoints) {
		this.totalMemberPoints = totalMemberPoints;
	}

	public Double getTotalGroupPoints() {
		return totalGroupPoints;
	}

	public void setTotalGroupPoints(Double totalGroupPoints) {
		this.totalGroupPoints = totalGroupPoints;
	}


}

