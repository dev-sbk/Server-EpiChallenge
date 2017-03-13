package com.epi.challenge.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.epi.challenge.converter.LocalDatePersistenceConverter;

@Entity
@Table(name = "CRITERS")
public class Criter extends GetId implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="idSaison")
	private Saison saison;
	@ManyToOne
	@JoinColumn(name="idCategorie")
	private Categorie categorie;
	private String sante;
	private String promotion;
	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate age;

	public Criter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Criter(String sante, String promotion, LocalDate age) {
		super();
		this.sante = sante;
		this.promotion = promotion;
		this.age = age;
	}

	public Saison getSaison() {
		return saison;
	}

	public void setSaison(Saison saison) {
		this.saison = saison;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public String getSante() {
		return sante;
	}

	public void setSante(String sante) {
		this.sante = sante;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public LocalDate getAge() {
		return age;
	}

	public void setAge(LocalDate age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Criter [saison=" + saison + ", categorie=" + categorie + ", sante=" + sante + ", promotion=" + promotion
				+ ", age=" + age + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((categorie == null) ? 0 : categorie.hashCode());
		result = prime * result + ((promotion == null) ? 0 : promotion.hashCode());
		result = prime * result + ((saison == null) ? 0 : saison.hashCode());
		result = prime * result + ((sante == null) ? 0 : sante.hashCode());
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
		Criter other = (Criter) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (categorie == null) {
			if (other.categorie != null)
				return false;
		} else if (!categorie.equals(other.categorie))
			return false;
		if (promotion == null) {
			if (other.promotion != null)
				return false;
		} else if (!promotion.equals(other.promotion))
			return false;
		if (saison == null) {
			if (other.saison != null)
				return false;
		} else if (!saison.equals(other.saison))
			return false;
		if (sante == null) {
			if (other.sante != null)
				return false;
		} else if (!sante.equals(other.sante))
			return false;
		return true;
	}

	
}
