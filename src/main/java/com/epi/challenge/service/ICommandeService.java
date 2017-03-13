package com.epi.challenge.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.epi.challenge.domain.Commande;


public interface ICommandeService {
	public Commande save(Commande u);
	public Commande update(Commande u);
	public void delete(Commande u);
	public Commande getCommandeById(Long id);
	public List<Commande> getAll();
	public Page<Commande> getAll(int page, int size);
}
