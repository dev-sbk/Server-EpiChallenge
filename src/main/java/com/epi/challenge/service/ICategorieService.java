package com.epi.challenge.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.epi.challenge.domain.Categorie;


public interface ICategorieService {
	public Categorie save(Categorie u);
	public Categorie update(Categorie u);
	public void delete(Categorie u);
	public Categorie getCategorieById(Long id);
	public List<Categorie> getAll();
	public Page<Categorie> getAll(int page, int size);
}
