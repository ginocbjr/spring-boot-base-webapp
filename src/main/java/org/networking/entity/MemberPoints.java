package org.networking.entity;

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
@Table(name = "MEMBER_POINTS")
public class MemberPoints extends BaseEntity{

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MEMBER_ID")
	private Member member;
	
	@Column(name="POINTS")
	private Long points;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="POINT_TYPE")
	private PointType pointType;

	public Member getMember() {
		return member;
	}

	public Long getPoints() {
		return points;
	}

	public PointType getPointType() {
		return pointType;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public void setPointType(PointType pointType) {
		this.pointType = pointType;
	}
	
}
