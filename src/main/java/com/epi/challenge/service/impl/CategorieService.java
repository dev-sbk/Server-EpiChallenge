package com.epi.challenge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.epi.challenge.domain.Categorie;
import com.epi.challenge.repository.ICategorieRepository;
import com.epi.challenge.service.ICategorieService;

@Service
@Transactional
public class CategorieService implements ICategorieService {
	@Autowired
	private ICategorieRepository repository;

	@Override
	public Categorie save(Categorie u) {
		repository.save(u);
		return u;
	}

	@Override
	public Categorie update(Categorie u) {
		repository.save(u);
		return u;
	}

	@Override
	public void delete(Categorie u) {
		// TODO Auto-generated method stub
		repository.delete(u);
	}

	@Override
	public Categorie getCategorieById(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<Categorie> getAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Page<Categorie> getAll(int page, int size) {
		// TODO Auto-generated method stub
		return repository.findAll(new PageRequest(page, size));
	}

	public ICategorieRepository getRepository() {
		return repository;
	}

	public void setRepository(ICategorieRepository repository) {
		this.repository = repository;
	}

}
