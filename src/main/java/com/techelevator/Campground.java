package com.techelevator;

import java.math.BigDecimal;

public class Campground {
private long campgroundId;
private long parkId;
private String name;
private String open;
private String closed;
private BigDecimal fee;


public long getCampgroundId() {
	return campgroundId;
}
public void setCampgroundId(long campgroundId) {
	this.campgroundId = campgroundId;
}
public long getParkId() {
	return parkId;
}
public void setParkId(long parkId) {
	this.parkId = parkId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getOpen() {
	return open;
}
public void setOpen(String open) {
	this.open = open;
}
public String getClosed() {
	return closed;
}
public void setClosed(String closed) {
	this.closed = closed;
}
public BigDecimal getFee() {
	return fee;
}
public void setFee(BigDecimal fee) {
	this.fee = fee;
}
@Override
public String toString() {
	return name ;
}

}
