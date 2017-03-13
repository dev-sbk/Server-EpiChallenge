package com.epi.challenge.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.epi.challenge.converter.LocalDatePersistenceConverter;
@Entity
@Table(name="SAISONS")
public class Saison extends GetId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	@Convert(converter=LocalDatePersistenceConverter.class)
	private LocalDate dateDebut;
	@Convert(converter=LocalDatePersistenceConverter.class)
	private LocalDate dateFin;
	public Saison() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Saison(String name, LocalDate dateDebut, LocalDate dateFin) {
		super();
		this.name = name;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	@Override
	public String toString() {
		return "Saison [name=" + name + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dateDebut == null) ? 0 : dateDebut.hashCode());
		result = prime * result + ((dateFin == null) ? 0 : dateFin.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Saison other = (Saison) obj;
		if (dateDebut == null) {
			if (other.dateDebut != null)
				return false;
		} else if (!dateDebut.equals(other.dateDebut))
			return false;
		if (dateFin == null) {
			if (other.dateFin != null)
				return false;
		} else if (!dateFin.equals(other.dateFin))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
