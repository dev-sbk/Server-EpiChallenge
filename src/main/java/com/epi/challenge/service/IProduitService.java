package com.epi.challenge.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.epi.challenge.domain.Produit;


public interface IProduitService {
	public Produit save(Produit u);
	public Produit update(Produit u);
	public void delete(Produit u);
	public Produit getProduitById(Long id);
	public List<Produit> getAll();
	public Page<Produit> getAll(int page, int size);
}
