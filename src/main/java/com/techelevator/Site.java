package com.techelevator;

import java.math.BigDecimal;

public class Site {
private long siteId;
private long campgroundId;
private long siteNum;
private long maxOccupy;
private boolean available;
private long maxRVLength;
private boolean utilities;
private BigDecimal dailyFee;

public long getSiteId() {
	return siteId;
}
public void setSiteId(long siteId) {
	this.siteId = siteId;
}
public long getCampgroundId() {
	return campgroundId;
}
public void setCampgroundId(long campgroundId) {
	this.campgroundId = campgroundId;
}
public long getSiteNum() {
	return siteNum;
}
public void setSiteNum(long siteNum) {
	this.siteNum = siteNum;
}
public long getMaxOccupy() {
	return maxOccupy;
}
public void setMaxOccupy(long maxOccupy) {
	this.maxOccupy = maxOccupy;
}
public boolean isAvailable() {
	return available;
}
public void setAvailable(boolean available) {
	this.available = available;
}
public long getMaxRVLength() {
	return maxRVLength;
}
public void setMaxRVLength(long maxRVLength) {
	this.maxRVLength = maxRVLength;
}
public boolean isUtilities() {
	return utilities;
}
public void setUtilities(boolean utilities) {
	this.utilities = utilities;
}
public BigDecimal getDailyFee() {
	return dailyFee;
}
public void setDailyFee(BigDecimal dailyFee) {
	this.dailyFee = dailyFee.setScale(2);
}

}
