package com.epi.challenge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epi.challenge.domain.Commande;
import com.epi.challenge.domain.LigneCommande;
import com.epi.challenge.repository.ILigneCommandeRepository;
import com.epi.challenge.service.ILigneCommandeService;
@Service
@Transactional
public class LigneCommandeService implements ILigneCommandeService {
	@Autowired
	private ILigneCommandeRepository repository;
	
	@Override
	public List<LigneCommande> findByCommande(Commande commande) {
		// TODO Auto-generated method stub
		return repository.findByCommande(commande);
	}

	public ILigneCommandeRepository getRepository() {
		return repository;
	}

	public void setRepository(ILigneCommandeRepository repository) {
		this.repository = repository;
	}

	

}
