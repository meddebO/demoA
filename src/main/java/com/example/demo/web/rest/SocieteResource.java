package com.example.demo.web.rest;

import com.example.demo.domain.Societe;
import com.example.demo.repository.SocieteRepository;
import com.example.demo.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.example.demo.domain.Societe}.
 */
@RestController
@RequestMapping("/api")
public class SocieteResource {

    private final Logger log = LoggerFactory.getLogger(SocieteResource.class);

    private static final String ENTITY_NAME = "societe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocieteRepository societeRepository;

    public SocieteResource(SocieteRepository societeRepository) {
        this.societeRepository = societeRepository;
    }

    /**
     * {@code POST  /societes} : Create a new societe.
     *
     * @param societe the societe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societe, or with status {@code 400 (Bad Request)} if the societe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/societes")
    public ResponseEntity<Societe> createSociete(@RequestBody Societe societe) throws URISyntaxException {
        log.debug("REST request to save Societe : {}", societe);
        if (societe.getId() != null) {
            throw new BadRequestAlertException("A new societe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Societe result = societeRepository.save(societe);
        return ResponseEntity.created(new URI("/api/societes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /societes} : Updates an existing societe.
     *
     * @param societe the societe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societe,
     * or with status {@code 400 (Bad Request)} if the societe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/societes")
    public ResponseEntity<Societe> updateSociete(@RequestBody Societe societe) throws URISyntaxException {
        log.debug("REST request to update Societe : {}", societe);
        if (societe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Societe result = societeRepository.save(societe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, societe.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /societes} : get all the societes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societes in body.
     */
    @GetMapping("/societes")
    public List<Societe> getAllSocietes() {
        log.debug("REST request to get all Societes");
        return societeRepository.findAll();
    }

    /**
     * {@code GET  /societes/:id} : get the "id" societe.
     *
     * @param id the id of the societe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/societes/{id}")
    public ResponseEntity<Societe> getSociete(@PathVariable Long id) {
        log.debug("REST request to get Societe : {}", id);
        Optional<Societe> societe = societeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(societe);
    }

    /**
     * {@code DELETE  /societes/:id} : delete the "id" societe.
     *
     * @param id the id of the societe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/societes/{id}")
    public ResponseEntity<Void> deleteSociete(@PathVariable Long id) {
        log.debug("REST request to delete Societe : {}", id);
        societeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
