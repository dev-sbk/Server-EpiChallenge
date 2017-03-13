package com.epi.challenge.domain;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 
 * @author Saber Ben Khalifa
 *
 */
@Entity
@Table(name="ROLES")
public class Role extends GetId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3752764728039789483L;
	private String role;
	private String description;

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(String role, String description) {
		super();
		this.role = role;
		this.description = description;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [role=" + role + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		Role other = (Role) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

}
