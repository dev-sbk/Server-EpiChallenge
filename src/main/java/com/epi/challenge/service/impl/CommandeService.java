package com.epi.challenge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.epi.challenge.domain.Commande;
import com.epi.challenge.repository.ICommandeRepository;

import com.epi.challenge.service.ICommandeService;

@Service
@Transactional
public class CommandeService implements ICommandeService {
	@Autowired
	private ICommandeRepository repository;

	@Override
	public Commande save(Commande u) {
		repository.save(u);
		return u;
	}

	@Override
	public Commande update(Commande u) {
		repository.save(u);
		return u;
	}

	@Override
	public void delete(Commande u) {
		// TODO Auto-generated method stub
		repository.delete(u);
	}

	@Override
	public Commande getCommandeById(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<Commande> getAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Page<Commande> getAll(int page, int size) {
		// TODO Auto-generated method stub
		return repository.findAll(new PageRequest(page, size));
	}

	public ICommandeRepository getRepository() {
		return repository;
	}

	public void setRepository(ICommandeRepository repository) {
		this.repository = repository;
	}

}
