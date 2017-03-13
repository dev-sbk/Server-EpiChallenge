package com.epi.challenge.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "UNITES")
public class Unite extends GetId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String unite;

	public Unite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Unite(String unite) {
		super();
		this.unite = unite;
	}

	public String getUnite() {
		return unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}

	@Override
	public String toString() {
		return "Unite [unite=" + unite + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((unite == null) ? 0 : unite.hashCode());
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
		Unite other = (Unite) obj;
		if (unite == null) {
			if (other.unite != null)
				return false;
		} else if (!unite.equals(other.unite))
			return false;
		return true;
	}

}
