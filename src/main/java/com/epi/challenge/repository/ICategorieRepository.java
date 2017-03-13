package com.epi.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epi.challenge.domain.Categorie;

@Repository
public interface ICategorieRepository extends JpaRepository<Categorie, Long> {

}
