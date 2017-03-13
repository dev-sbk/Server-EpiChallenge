package com.epi.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epi.challenge.domain.Criter;

@Repository
public interface ICriterRepository extends JpaRepository<Criter, Long> {

}
