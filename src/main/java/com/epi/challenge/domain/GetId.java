package com.epi.challenge.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.epi.challenge.domain.utilities.EntityUtil;
/**
 * 
 * @author Saber Ben Khalifa
 *
 */
@MappedSuperclass
public class GetId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3639142288714709764L;



	
	

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Long.valueOf(id).hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}

		Long objId = ((GetId) obj).getId();
		if (id == objId) {
			if (id != 0) {
				// if ids!=0, both objects are already in the database.
				return true;
			} else {
				// both objects are new instances not yet stored in the database
				return EntityUtil.equals(this, obj);
			}
		}

		if (id == 0 ^ objId == 0) {
			// only one object is already stored in the database
			return EntityUtil.equals(this, obj);
		}

		// both objects are already store in the database (id!=0 and objId!=0)
		return false;
	}
}
