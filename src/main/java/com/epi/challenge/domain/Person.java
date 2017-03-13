package com.epi.challenge.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import com.epi.challenge.converter.LocalDatePersistenceConverter;

/**
 * 
 * @author Saber Ben Khalifa
 *
 */
@MappedSuperclass
public class Person extends GetId implements Serializable {
	private static final long serialVersionUID = 8815979015869571647L;

	@Column(name = "firstName")
	private String firstName;
	@Column(name = "lastName")
	private String lastName;
	@Convert(converter = LocalDatePersistenceConverter.class)
	private Date brithDay;
	@Embedded
	private Contact contact;
	@Lob
	private byte[] picture;
	@Embedded
	private Address adress;
	@Embedded
	private Location location;
	@Column(name = "sexe")
	private String sexe;
	@Column(name = "actived")
	private boolean actived;

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(String firstName, String lastName, Date brithDay, byte[] picture, String sexe, boolean actived) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.brithDay = brithDay;
		this.picture = picture;
		this.sexe = sexe;
		this.actived = actived;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBrithDay() {
		return brithDay;
	}

	public void setBrithDay(Date brithDay) {
		this.brithDay = brithDay;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Address getAdress() {
		return adress;
	}

	public void setAdress(Address adress) {
		this.adress = adress;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", brithDay=" + brithDay + ", contact="
				+ contact + ", picture=" + Arrays.toString(picture) + ", adress=" + adress + ", location=" + location
				+ ", sexe=" + sexe + ", actived=" + actived + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (actived ? 1231 : 1237);
		result = prime * result + ((adress == null) ? 0 : adress.hashCode());
		result = prime * result + ((brithDay == null) ? 0 : brithDay.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + Arrays.hashCode(picture);
		result = prime * result + ((sexe == null) ? 0 : sexe.hashCode());
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
		Person other = (Person) obj;
		if (actived != other.actived)
			return false;
		if (adress == null) {
			if (other.adress != null)
				return false;
		} else if (!adress.equals(other.adress))
			return false;
		if (brithDay == null) {
			if (other.brithDay != null)
				return false;
		} else if (!brithDay.equals(other.brithDay))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (!Arrays.equals(picture, other.picture))
			return false;
		if (sexe == null) {
			if (other.sexe != null)
				return false;
		} else if (!sexe.equals(other.sexe))
			return false;
		return true;
	}

}