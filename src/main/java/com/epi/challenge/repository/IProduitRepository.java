package com.epi.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.epi.challenge.domain.Produit;
@Repository
public interface IProduitRepository extends JpaRepository<Produit, Long> {

}
