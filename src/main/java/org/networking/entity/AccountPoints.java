package org.networking.entity;

import org.networking.enums.PointType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "ACCOUNT_POINTS")
public class AccountPoints extends BaseEntity{

	public AccountPoints(Account account, PointType pointType, Long points){
		this.account = account;
		this.pointType = pointType;
		this.points = points;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ACCOUNT_ID")
	private Account account;
	
	@Column(name="POINTS")
	private Long points;

	@Column(name="POINT_TYPE")
	private PointType pointType;

	public Long getPoints() {
		return points;
	}

	public PointType getPointType() {
		return pointType;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public void setPointType(PointType pointType) {
		this.pointType = pointType;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}
