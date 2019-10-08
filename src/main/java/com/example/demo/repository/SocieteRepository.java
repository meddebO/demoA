package com.example.demo.repository;

import com.example.demo.domain.Societe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Societe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocieteRepository extends JpaRepository<Societe, Long> {

}
