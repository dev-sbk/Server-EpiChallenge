package com.epi.challenge.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epi.challenge.domain.Unite;
import com.epi.challenge.repository.IUniteRepository;
import com.epi.challenge.service.IUniteService;

@Service
@Transactional
public class UniteService implements IUniteService {
	@Autowired
	private IUniteRepository repository;

	@Override
	public Unite save(Unite u) {
		// TODO Auto-generated method stub
		return repository.save(u);
	}

	@Override
	public Unite update(Unite u) {
		// TODO Auto-generated method stub
		return repository.save(u);
	}

	@Override
	public void delete(Unite u) {
		// TODO Auto-generated method stub
		repository.delete(u);
	}

	@Override
	public Unite getUniteById(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<Unite> getAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Page<Unite> getAll(int page, int size) {
		// TODO Auto-generated method stub
		return repository.findAll(new PageRequest(page, size));
	}

}
