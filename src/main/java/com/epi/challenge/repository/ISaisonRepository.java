package com.epi.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.epi.challenge.domain.Saison;
@Repository
public interface ISaisonRepository extends JpaRepository<Saison, Long> {

}
