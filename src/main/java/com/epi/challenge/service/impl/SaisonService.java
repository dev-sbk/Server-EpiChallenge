package com.epi.challenge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.epi.challenge.domain.Saison;
import com.epi.challenge.repository.ISaisonRepository;
import com.epi.challenge.service.ISaisonService;

@Service
@Transactional
public class SaisonService implements ISaisonService {
	@Autowired
	private ISaisonRepository repository;

	@Override
	public Saison save(Saison u) {
		// TODO Auto-generated method stub
		return repository.save(u);
	}

	@Override
	public Saison update(Saison u) {
		// TODO Auto-generated method stub
		return repository.save(u);
	}

	@Override
	public void delete(Saison u) {
		// TODO Auto-generated method stub
		repository.delete(u);
	}

	@Override
	public Saison getSaisonById(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<Saison> getAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Page<Saison> getAll(int page, int size) {
		// TODO Auto-generated method stub
		return repository.findAll(new PageRequest(page, size));
	}

}
