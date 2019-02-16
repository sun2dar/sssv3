package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.TVeneer;
import com.sssv3.service.TVeneerService;
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
 * REST controller for managing TVeneer.
 */
@RestController
@RequestMapping("/api")
public class TVeneerResource {

    private final Logger log = LoggerFactory.getLogger(TVeneerResource.class);

    private static final String ENTITY_NAME = "tVeneer";

    private final TVeneerService tVeneerService;

    public TVeneerResource(TVeneerService tVeneerService) {
        this.tVeneerService = tVeneerService;
    }

    /**
     * POST  /t-veneers : Create a new tVeneer.
     *
     * @param tVeneer the tVeneer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tVeneer, or with status 400 (Bad Request) if the tVeneer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-veneers")
    @Timed
    public ResponseEntity<TVeneer> createTVeneer(@RequestBody TVeneer tVeneer) throws URISyntaxException {
        log.debug("REST request to save TVeneer : {}", tVeneer);
        if (tVeneer.getId() != null) {
            throw new BadRequestAlertException("A new tVeneer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TVeneer result = tVeneerService.save(tVeneer);
        return ResponseEntity.created(new URI("/api/t-veneers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-veneers : Updates an existing tVeneer.
     *
     * @param tVeneer the tVeneer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tVeneer,
     * or with status 400 (Bad Request) if the tVeneer is not valid,
     * or with status 500 (Internal Server Error) if the tVeneer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-veneers")
    @Timed
    public ResponseEntity<TVeneer> updateTVeneer(@RequestBody TVeneer tVeneer) throws URISyntaxException {
        log.debug("REST request to update TVeneer : {}", tVeneer);
        if (tVeneer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TVeneer result = tVeneerService.save(tVeneer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tVeneer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-veneers : get all the tVeneers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tVeneers in body
     */
    @GetMapping("/t-veneers")
    @Timed
    public ResponseEntity<List<TVeneer>> getAllTVeneers(Pageable pageable) {
        log.debug("REST request to get a page of TVeneers");
        Page<TVeneer> page = tVeneerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-veneers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/tveneers/paging")
    @Timed
    public ResponseEntity<Page<TVeneer>> getAllTVeneersWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of TVeneers");
        Page<TVeneer> page = tVeneerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tveneers");
        return ResponseEntity.ok().headers(headers).body(page);
    }
    /**
     * GET  /t-veneers/:id : get the "id" tVeneer.
     *
     * @param id the id of the tVeneer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tVeneer, or with status 404 (Not Found)
     */
    @GetMapping("/t-veneers/{id}")
    @Timed
    public ResponseEntity<TVeneer> getTVeneer(@PathVariable Long id) {
        log.debug("REST request to get TVeneer : {}", id);
        Optional<TVeneer> tVeneer = tVeneerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tVeneer);
    }

    /**
     * DELETE  /t-veneers/:id : delete the "id" tVeneer.
     *
     * @param id the id of the tVeneer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-veneers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTVeneer(@PathVariable Long id) {
        log.debug("REST request to delete TVeneer : {}", id);
        tVeneerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
