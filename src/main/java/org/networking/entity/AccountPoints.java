package org.networking.entity;

import org.networking.enums.PointType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "ACCOUNT_POINTS")
public class AccountPoints extends BaseEntity{

	public AccountPoints(){}

	public AccountPoints(Account account, PointType pointType, Long points){
		this.account = account;
		this.pointType = pointType;
		this.points = points;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ACCOUNT_ID")
	private Account account;

	@Column(name="ACCOUNT_ID", insertable = false, updatable = false)
	private Long accountId;
	
	@Column(name="POINTS")
	private Long points;

	@Column(name="POINT_TYPE")
	@Enumerated(EnumType.STRING)
	private PointType pointType;

	@Column(name = "IS_CLAIMED")
	private Boolean isClaimed;

	@Column(name = "DATE_CLAIMED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateClaimed;

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

	public Boolean getIsClaimed() {
		return isClaimed;
	}

	public void setIsClaimed(Boolean isClaimed) {
		this.isClaimed = isClaimed;
	}

	public Date getDateClaimed() {
		return dateClaimed;
	}

	public void setDateClaimed(Date dateClaimed) {
		this.dateClaimed = dateClaimed;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
}
