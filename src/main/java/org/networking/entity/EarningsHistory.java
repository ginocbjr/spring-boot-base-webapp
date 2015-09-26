package org.networking.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Gino on 9/26/2015.
 */
@Entity
@Table(name = "EARNINGS_HISTORY")
public class EarningsHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "MEMBER_ID", insertable = false, updatable = false)
    private Long memberId;

    @Column(name = "DATE_CLAIMED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateClaimed;

    @Column(name = "TOTAL_POINTS")
    private Long totalPoints;

    @Column(name = "TOTAL_EARNINGS")
    private Double totalEarnings;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getDateClaimed() {
        return dateClaimed;
    }

    public void setDateClaimed(Date dateClaimed) {
        this.dateClaimed = dateClaimed;
    }

    public Long getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Long totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(Double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }
}
