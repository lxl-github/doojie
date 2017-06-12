package com.doojie.domain.dto;

public class Content {
	private String address;
	private AddressDetail address_detail;
	private Point point;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public AddressDetail getAddress_detail() {
		return address_detail;
	}
	public void setAddress_detail(AddressDetail address_detail) {
		this.address_detail = address_detail;
	}
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((address_detail == null) ? 0 : address_detail.hashCode());
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Content other = (Content) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (address_detail == null) {
			if (other.address_detail != null)
				return false;
		} else if (!address_detail.equals(other.address_detail))
			return false;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Content [address=" + address + ", address_detail="
				+ address_detail + ", point=" + point + "]";
	}
}
