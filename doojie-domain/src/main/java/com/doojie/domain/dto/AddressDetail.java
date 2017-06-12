package com.doojie.domain.dto;

public class AddressDetail {
	private String city;
	private Integer city_code;
	private String district;
	private String province;
	private String street;
	private String street_number;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getCity_code() {
		return city_code;
	}
	public void setCity_code(Integer city_code) {
		this.city_code = city_code;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet_number() {
		return street_number;
	}
	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((city_code == null) ? 0 : city_code.hashCode());
		result = prime * result
				+ ((district == null) ? 0 : district.hashCode());
		result = prime * result
				+ ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result
				+ ((street_number == null) ? 0 : street_number.hashCode());
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
		AddressDetail other = (AddressDetail) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (city_code == null) {
			if (other.city_code != null)
				return false;
		} else if (!city_code.equals(other.city_code))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (street_number == null) {
			if (other.street_number != null)
				return false;
		} else if (!street_number.equals(other.street_number))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AddressDetail [city=" + city + ", city_code=" + city_code
				+ ", district=" + district + ", province=" + province
				+ ", street=" + street + ", street_number=" + street_number
				+ "]";
	}
}
