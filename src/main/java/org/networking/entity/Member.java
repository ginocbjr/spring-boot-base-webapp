package org.networking.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "MEMBER")
public class Member extends User{

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Account> accounts;
	
	@Column(name="DATE_JOINED")
	private Date dateJoined;

	@OneToOne(cascade = CascadeType.ALL)
	private Member referrer;
	
	@Column(name="AGE")
	private Integer age;
	
	@Column(name="BIRTH_DAY")
	private Date birthday;
	
	@Column(name="TIN")
	private String tinNumber;
	
	@Column(name="CONTACT_NO")
	private String contactNo;
	
	@Column(name="OCCUPATION")
	private String occupation;
	
	@Column(name="CIVIL_SATUS")
	private String civilStatus;
	
	@Column(name="ADDRESS")
	private String address;

	public List<Account> getAccounts() {
		return accounts;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public Member getReferrer() {
		return referrer;
	}

	public Integer getAge() {
		return age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getTinNumber() {
		return tinNumber;
	}

	public String getContactNo() {
		return contactNo;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getCivilStatus() {
		return civilStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	public void setReferrer(Member referrer) {
		this.referrer = referrer;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public void setCivilStatus(String civilStatus) {
		this.civilStatus = civilStatus;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

}
