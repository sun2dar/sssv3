package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.TBongkar;
import com.sssv3.service.TBongkarService;
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
 * REST controller for managing TBongkar.
 */
@RestController
@RequestMapping("/api")
public class TBongkarResource {

    private final Logger log = LoggerFactory.getLogger(TBongkarResource.class);

    private static final String ENTITY_NAME = "tBongkar";

    private final TBongkarService tBongkarService;

    public TBongkarResource(TBongkarService tBongkarService) {
        this.tBongkarService = tBongkarService;
    }

    /**
     * POST  /t-bongkars : Create a new tBongkar.
     *
     * @param tBongkar the tBongkar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tBongkar, or with status 400 (Bad Request) if the tBongkar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-bongkars")
    @Timed
    public ResponseEntity<TBongkar> createTBongkar(@RequestBody TBongkar tBongkar) throws URISyntaxException {
        log.debug("REST request to save TBongkar : {}", tBongkar);
        if (tBongkar.getId() != null) {
            throw new BadRequestAlertException("A new tBongkar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TBongkar result = tBongkarService.save(tBongkar);
        return ResponseEntity.created(new URI("/api/t-bongkars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-bongkars : Updates an existing tBongkar.
     *
     * @param tBongkar the tBongkar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tBongkar,
     * or with status 400 (Bad Request) if the tBongkar is not valid,
     * or with status 500 (Internal Server Error) if the tBongkar couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-bongkars")
    @Timed
    public ResponseEntity<TBongkar> updateTBongkar(@RequestBody TBongkar tBongkar) throws URISyntaxException {
        log.debug("REST request to update TBongkar : {}", tBongkar);
        if (tBongkar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TBongkar result = tBongkarService.save(tBongkar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tBongkar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-bongkars : get all the tBongkars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tBongkars in body
     */
    @GetMapping("/t-bongkars")
    @Timed
    public ResponseEntity<List<TBongkar>> getAllTBongkars(Pageable pageable) {
        log.debug("REST request to get a page of TBongkars");
        Page<TBongkar> page = tBongkarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-bongkars");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/t-bongkars/paging")
    @Timed
    public ResponseEntity<Page<TBongkar>> getAllTBongkarsWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of TBongkars");
        Page<TBongkar> page = tBongkarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-bongkars");
        return ResponseEntity.ok().headers(headers).body(page);
    }

    /**
     * GET  /t-bongkars/:id : get the "id" tBongkar.
     *
     * @param id the id of the tBongkar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tBongkar, or with status 404 (Not Found)
     */
    @GetMapping("/t-bongkars/{id}")
    @Timed
    public ResponseEntity<TBongkar> getTBongkar(@PathVariable Long id) {
        log.debug("REST request to get TBongkar : {}", id);
        Optional<TBongkar> tBongkar = tBongkarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tBongkar);
    }

    /**
     * DELETE  /t-bongkars/:id : delete the "id" tBongkar.
     *
     * @param id the id of the tBongkar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-bongkars/{id}")
    @Timed
    public ResponseEntity<Void> deleteTBongkar(@PathVariable Long id) {
        log.debug("REST request to delete TBongkar : {}", id);
        tBongkarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
