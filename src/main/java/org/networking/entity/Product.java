package org.networking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "PRODUCT")
public class Product extends BaseEntity{
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="PRICE")
	private Double price;
	
	@Column(name="POINTS")
	private Double points;
	
	@Column(name="DESCRIPTION", length=1000)
	private String description;

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public Double getPoints() {
		return points;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
