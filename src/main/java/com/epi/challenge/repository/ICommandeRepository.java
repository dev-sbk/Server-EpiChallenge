package com.epi.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epi.challenge.domain.Commande;

@Repository
public interface ICommandeRepository extends JpaRepository<Commande, Long> {

}
