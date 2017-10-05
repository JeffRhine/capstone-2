package com.techelevator;

import java.time.LocalDate;

public class Park {
	private String name;
	private String location;
	private LocalDate date;
	private long area;
	private long visitors;
	private String description;
	private long parkId;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getEstDate() {
		return date;
	}
	public void setEstDate(LocalDate date) {
		this.date = date;
	}
	public long getArea() {
		return area;
	}
	public void setArea(long area) {
		this.area = area;
	}
	public long getVisitors() {
		return visitors;
	}
	public void setVisitors(long visitors) {
		this.visitors = visitors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getId() {
		return parkId;
	}
	public void setId(long parkId) {
		this.parkId = parkId;
	}
	@Override
	public String toString() {
		return name +" National Park" ;
	}
}
