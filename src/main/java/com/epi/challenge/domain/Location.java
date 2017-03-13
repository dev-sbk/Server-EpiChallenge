package com.epi.challenge.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
@Embeddable
public class Location  implements Serializable {
	private static final long serialVersionUID = 1L;
	private double Latitude;
	private double Longitude;

	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Location(double latitude, double longitude) {
		super();
		Latitude = latitude;
		Longitude = longitude;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	@Override
	public String toString() {
		return "Location [Latitude=" + Latitude + ", Longitude=" + Longitude + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(Latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(Longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (Double.doubleToLongBits(Latitude) != Double.doubleToLongBits(other.Latitude))
			return false;
		if (Double.doubleToLongBits(Longitude) != Double.doubleToLongBits(other.Longitude))
			return false;
		return true;
	}

}
