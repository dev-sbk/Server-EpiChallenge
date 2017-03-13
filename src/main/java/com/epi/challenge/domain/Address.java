package com.epi.challenge.domain;


 

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * 
 * @author Saber Ben Khalifa
 *
 */
@Embeddable
public class Address implements Serializable {
    private static final long serialVersionUID = 3307812871984275681L;
    
    private String street;
    private String country;
    private String city;
    private String zipCode;
    
 
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

    
}
