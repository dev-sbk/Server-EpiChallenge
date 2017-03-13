package com.epi.challenge.domain;

import java.io.Serializable;
/**
 * 
 * @author Saber Ben Khaklifa
 *
 */
import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.epi.challenge.converter.LocalDatePersistenceConverter;

@Entity
@Table(name = "COMMANDES")
public class Commande extends GetId implements Serializable {
	private static final long serialVersionUID = 1L;
	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate dateCmd;
	@ManyToOne
	@JoinColumn(name = "idClient")
	private Client client;
	@OneToMany(mappedBy = "commande", fetch = FetchType.LAZY)
	private Collection<LigneCommande> ligneCommandes;
	public Commande() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Commande(LocalDate dateCmd) {
		super();
		this.dateCmd = dateCmd;
	}

	public LocalDate getDateCmd() {
		return dateCmd;
	}

	public void setDateCmd(LocalDate dateCmd) {
		this.dateCmd = dateCmd;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Collection<LigneCommande> getLigneCommandes() {
		return ligneCommandes;
	}

	public void setLigneCommandes(Collection<LigneCommande> ligneCommandes) {
		this.ligneCommandes = ligneCommandes;
	}

	@Override
	public String toString() {
		return "Commande [dateCmd=" + dateCmd + ", client=" + client + ", ligneCommandes=" + ligneCommandes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((dateCmd == null) ? 0 : dateCmd.hashCode());
		result = prime * result + ((ligneCommandes == null) ? 0 : ligneCommandes.hashCode());
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
		Commande other = (Commande) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (dateCmd == null) {
			if (other.dateCmd != null)
				return false;
		} else if (!dateCmd.equals(other.dateCmd))
			return false;
		if (ligneCommandes == null) {
			if (other.ligneCommandes != null)
				return false;
		} else if (!ligneCommandes.equals(other.ligneCommandes))
			return false;
		return true;
	}

}
