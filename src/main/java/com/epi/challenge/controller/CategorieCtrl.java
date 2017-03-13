package com.epi.challenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.epi.challenge.domain.Categorie;
import com.epi.challenge.service.ICategorieService;
/**
 * 
 * @author SABER KHALIFA
 *
 */
@RestController
@Service
@RequestMapping(value = "/categorie-services")
public class CategorieCtrl {
	@Autowired
	private ICategorieService categorieServ;

	@RequestMapping(value = "/getCategorie", method = RequestMethod.GET)
	public Categorie getProduit(Long id) {
		return categorieServ.getCategorieById(id);
	}

	@RequestMapping(value = "/addCategorie", method = RequestMethod.POST)
	public Categorie addProduit(Categorie categorie) {
		categorieServ.save(categorie);
		return categorie;
	}

	@RequestMapping(value = "/editCategorie", method = RequestMethod.PUT)
	public Categorie editProduit(Categorie categorie) {
		categorieServ.save(categorie);
		return categorie;
	}

	@RequestMapping(value = "/deleteCategorie", method = RequestMethod.DELETE)
	public void deleteProduit(Categorie categorie) {
		categorieServ.delete(categorie);
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public List<Categorie> getAll() {
		return categorieServ.getAll();
	}
	@RequestMapping(value = "/getAll{page}", method = RequestMethod.GET)
	public Page<Categorie> getAllByPage(int page) {
		return categorieServ.getAll(page, 10);
	}
	public ICategorieService getCategorieServ() {
		return categorieServ;
	}

	public void setCategorieServ(ICategorieService categorieServ) {
		this.categorieServ = categorieServ;
	}

}
