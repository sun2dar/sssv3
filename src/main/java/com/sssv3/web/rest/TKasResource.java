package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.TKas;
import com.sssv3.service.TKasService;
import com.sssv3.web.rest.errors.BadRequestAlertException;
import com.sssv3.web.rest.util.HeaderUtil;
import com.sssv3.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TKas.
 */
@RestController
@RequestMapping("/api")
public class TKasResource {

    private final Logger log = LoggerFactory.getLogger(TKasResource.class);

    private static final String ENTITY_NAME = "tKas";

    private final TKasService tKasService;

    public TKasResource(TKasService tKasService) {
        this.tKasService = tKasService;
    }

    /**
     * POST  /t-kas : Create a new tKas.
     *
     * @param tKas the tKas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tKas, or with status 400 (Bad Request) if the tKas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-kas")
    @Timed
    public ResponseEntity<TKas> createTKas(@RequestBody TKas tKas) throws URISyntaxException {
        log.debug("REST request to save TKas : {}", tKas);
        if (tKas.getId() != null) {
            throw new BadRequestAlertException("A new tKas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TKas result = tKasService.save(tKas);
        return ResponseEntity.created(new URI("/api/t-kas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-kas : Updates an existing tKas.
     *
     * @param tKas the tKas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tKas,
     * or with status 400 (Bad Request) if the tKas is not valid,
     * or with status 500 (Internal Server Error) if the tKas couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-kas")
    @Timed
    public ResponseEntity<TKas> updateTKas(@RequestBody TKas tKas) throws URISyntaxException {
        log.debug("REST request to update TKas : {}", tKas);
        if (tKas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TKas result = tKasService.save(tKas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tKas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-kas : get all the tKas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tKas in body
     */
    @GetMapping("/t-kas")
    @Timed
    public ResponseEntity<List<TKas>> getAllTKas(Pageable pageable) {
        log.debug("REST request to get a page of TKas");
        Page<TKas> page = tKasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-kas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /t-kas/:id : get the "id" tKas.
     *
     * @param id the id of the tKas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tKas, or with status 404 (Not Found)
     */
    @GetMapping("/t-kas/{id}")
    @Timed
    public ResponseEntity<TKas> getTKas(@PathVariable Long id) {
        log.debug("REST request to get TKas : {}", id);
        Optional<TKas> tKas = tKasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tKas);
    }

    /**
     * DELETE  /t-kas/:id : delete the "id" tKas.
     *
     * @param id the id of the tKas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-kas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTKas(@PathVariable Long id) {
        log.debug("REST request to delete TKas : {}", id);
        tKasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
