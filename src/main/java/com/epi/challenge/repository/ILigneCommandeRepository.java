package com.epi.challenge.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epi.challenge.domain.Commande;
import com.epi.challenge.domain.LigneCommande;

@Repository
public interface ILigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
   public List<LigneCommande> findByCommande(Commande commande);
  
}
