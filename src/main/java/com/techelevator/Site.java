package com.techelevator;

public class Site {
private long siteId;
private long campgroundId;
private long siteNum;
private long maxOccupy;
private boolean available;
private long maxRVLength;
private boolean utitlities;
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
public boolean isUtitlities() {
	return utitlities;
}
public void setUtitlities(boolean utitlities) {
	this.utitlities = utitlities;
}

}
