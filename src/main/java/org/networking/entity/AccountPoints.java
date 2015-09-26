package org.networking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.networking.enums.PointType;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "ACCOUNT_POINTS")
public class AccountPoints extends BaseEntity{
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ACCOUNT_ID")
	private Account account;
	
	@Column(name="POINTS")
	private Long points;
	
	@Column(name="POINT_TYPE")
	@Enumerated(EnumType.STRING)
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
