package com.example.demo.repository;

import com.example.demo.domain.Employe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Employe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {

}
