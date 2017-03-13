package com.epi.challenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.epi.challenge.domain.Produit;
import com.epi.challenge.service.IProduitService;


/**
 * 
 * @author SABER KHALIFA
 *
 */
@RestController
@Service
@RequestMapping(value = "/produit-services")
public class ProduitCtrl {
	@Autowired
	private IProduitService produitServ;

	@RequestMapping(value = "/getProduit", method = RequestMethod.GET)
	public Produit getProduit(Long id) {
		return produitServ.getProduitById(id);
	}

	@RequestMapping(value = "/addProduit", method = RequestMethod.POST)
	public Produit addProduit(Produit produit) {
		produitServ.save(produit);
		return produit;
	}

	@RequestMapping(value = "/editProduit", method = RequestMethod.PUT)
	public Produit editProduit(Produit produit) {
		produitServ.save(produit);
		return produit;
	}

	@RequestMapping(value = "/deleteProduit", method = RequestMethod.DELETE)
	public void deleteProduit(Produit produit) {
		produitServ.delete(produit);
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public List<Produit> getAll() {

		return produitServ.getAll();
	}
	@RequestMapping(value = "/getAll{page}", method = RequestMethod.GET)
	public Page<Produit> getAllByPage(int page) {
		return produitServ.getAll(page, 10);
	}
	public IProduitService getProduitServ() {
		return produitServ;
	}

	public void setProduitServ(IProduitService produitServ) {
		this.produitServ = produitServ;
	}

}
