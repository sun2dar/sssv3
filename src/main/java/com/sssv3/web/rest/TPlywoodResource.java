package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.TPlywood;
import com.sssv3.service.TPlywoodService;
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
 * REST controller for managing TPlywood.
 */
@RestController
@RequestMapping("/api")
public class TPlywoodResource {

    private final Logger log = LoggerFactory.getLogger(TPlywoodResource.class);

    private static final String ENTITY_NAME = "tPlywood";

    private final TPlywoodService tPlywoodService;

    public TPlywoodResource(TPlywoodService tPlywoodService) {
        this.tPlywoodService = tPlywoodService;
    }

    /**
     * POST  /t-plywoods : Create a new tPlywood.
     *
     * @param tPlywood the tPlywood to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tPlywood, or with status 400 (Bad Request) if the tPlywood has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-plywoods")
    @Timed
    public ResponseEntity<TPlywood> createTPlywood(@RequestBody TPlywood tPlywood) throws URISyntaxException {
        log.debug("REST request to save TPlywood : {}", tPlywood);
        if (tPlywood.getId() != null) {
            throw new BadRequestAlertException("A new tPlywood cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TPlywood result = tPlywoodService.save(tPlywood);
        return ResponseEntity.created(new URI("/api/t-plywoods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-plywoods : Updates an existing tPlywood.
     *
     * @param tPlywood the tPlywood to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tPlywood,
     * or with status 400 (Bad Request) if the tPlywood is not valid,
     * or with status 500 (Internal Server Error) if the tPlywood couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-plywoods")
    @Timed
    public ResponseEntity<TPlywood> updateTPlywood(@RequestBody TPlywood tPlywood) throws URISyntaxException {
        log.debug("REST request to update TPlywood : {}", tPlywood);
        if (tPlywood.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TPlywood result = tPlywoodService.save(tPlywood);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tPlywood.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-plywoods : get all the tPlywoods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tPlywoods in body
     */
    @GetMapping("/t-plywoods")
    @Timed
    public ResponseEntity<List<TPlywood>> getAllTPlywoods(Pageable pageable) {
        log.debug("REST request to get a page of TPlywoods");
        Page<TPlywood> page = tPlywoodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-plywoods");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /t-plywoods/:id : get the "id" tPlywood.
     *
     * @param id the id of the tPlywood to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tPlywood, or with status 404 (Not Found)
     */
    @GetMapping("/t-plywoods/{id}")
    @Timed
    public ResponseEntity<TPlywood> getTPlywood(@PathVariable Long id) {
        log.debug("REST request to get TPlywood : {}", id);
        Optional<TPlywood> tPlywood = tPlywoodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tPlywood);
    }

    /**
     * DELETE  /t-plywoods/:id : delete the "id" tPlywood.
     *
     * @param id the id of the tPlywood to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-plywoods/{id}")
    @Timed
    public ResponseEntity<Void> deleteTPlywood(@PathVariable Long id) {
        log.debug("REST request to delete TPlywood : {}", id);
        tPlywoodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
