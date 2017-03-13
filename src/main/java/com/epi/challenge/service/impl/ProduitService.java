package com.epi.challenge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epi.challenge.domain.Produit;
import com.epi.challenge.repository.IProduitRepository;
import com.epi.challenge.service.IProduitService;

@Service
@Transactional
public class ProduitService implements IProduitService {
	@Autowired
	private IProduitRepository repository;

	@Override
	public Produit save(Produit u) {
		repository.save(u);
		return u;
	}

	@Override
	public Produit update(Produit u) {
		repository.save(u);
		return u;
	}

	@Override
	public void delete(Produit u) {
		// TODO Auto-generated method stub
		repository.delete(u);
	}

	@Override
	public Produit getProduitById(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<Produit> getAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Page<Produit> getAll(int page, int size) {
		// TODO Auto-generated method stub
		return repository.findAll(new PageRequest(page, size));
	}

	public IProduitRepository getRepository() {
		return repository;
	}

	public void setRepository(IProduitRepository repository) {
		this.repository = repository;
	}

}
