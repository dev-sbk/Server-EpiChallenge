package com.epi.challenge.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Saber Ben Khalifa
 *
 */
@Entity
@Table(name = "PRODUITS")
public class Produit extends GetId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private double price;
	private int qte;
	private String image;
	@ManyToOne
	@JoinColumn(name = "idCategorie")
	private Categorie gategorie;
	@ManyToOne
	@JoinColumn(name = "idUnite")
	private Unite unite;

	public Produit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Produit(String name, String description, double price, int qte, String image) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.qte = qte;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Categorie getGategorie() {
		return gategorie;
	}

	public void setGategorie(Categorie gategorie) {
		this.gategorie = gategorie;
	}

	public Unite getUnite() {
		return unite;
	}

	public void setUnite(Unite unite) {
		this.unite = unite;
	}

	@Override
	public String toString() {
		return "Produit [name=" + name + ", description=" + description + ", price=" + price + ", qte=" + qte
				+ ", image=" + image + ", gategorie=" + gategorie + ", unite=" + unite + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((gategorie == null) ? 0 : gategorie.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + qte;
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
		Produit other = (Produit) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (gategorie == null) {
			if (other.gategorie != null)
				return false;
		} else if (!gategorie.equals(other.gategorie))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (qte != other.qte)
			return false;
		if (unite == null) {
			if (other.unite != null)
				return false;
		} else if (!unite.equals(other.unite))
			return false;
		return true;
	}

}
