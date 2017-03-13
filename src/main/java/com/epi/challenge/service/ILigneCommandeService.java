package com.epi.challenge.service;

import java.util.List;


import com.epi.challenge.domain.Commande;
import com.epi.challenge.domain.LigneCommande;

public interface ILigneCommandeService {
	public List<LigneCommande> findByCommande(Commande commande);
}
