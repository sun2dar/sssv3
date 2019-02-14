package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.TOperasional;
import com.sssv3.service.TOperasionalService;
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
 * REST controller for managing TOperasional.
 */
@RestController
@RequestMapping("/api")
public class TOperasionalResource {

    private final Logger log = LoggerFactory.getLogger(TOperasionalResource.class);

    private static final String ENTITY_NAME = "tOperasional";

    private final TOperasionalService tOperasionalService;

    public TOperasionalResource(TOperasionalService tOperasionalService) {
        this.tOperasionalService = tOperasionalService;
    }

    /**
     * POST  /t-operasionals : Create a new tOperasional.
     *
     * @param tOperasional the tOperasional to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tOperasional, or with status 400 (Bad Request) if the tOperasional has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-operasionals")
    @Timed
    public ResponseEntity<TOperasional> createTOperasional(@RequestBody TOperasional tOperasional) throws URISyntaxException {
        log.debug("REST request to save TOperasional : {}", tOperasional);
        if (tOperasional.getId() != null) {
            throw new BadRequestAlertException("A new tOperasional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TOperasional result = tOperasionalService.save(tOperasional);
        return ResponseEntity.created(new URI("/api/t-operasionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-operasionals : Updates an existing tOperasional.
     *
     * @param tOperasional the tOperasional to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tOperasional,
     * or with status 400 (Bad Request) if the tOperasional is not valid,
     * or with status 500 (Internal Server Error) if the tOperasional couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-operasionals")
    @Timed
    public ResponseEntity<TOperasional> updateTOperasional(@RequestBody TOperasional tOperasional) throws URISyntaxException {
        log.debug("REST request to update TOperasional : {}", tOperasional);
        if (tOperasional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TOperasional result = tOperasionalService.save(tOperasional);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tOperasional.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-operasionals : get all the tOperasionals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tOperasionals in body
     */
    @GetMapping("/t-operasionals")
    @Timed
    public ResponseEntity<List<TOperasional>> getAllTOperasionals(Pageable pageable) {
        log.debug("REST request to get a page of TOperasionals");
        Page<TOperasional> page = tOperasionalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-operasionals");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/t-operasionals/paging")
    @Timed
    public ResponseEntity<Page<TOperasional>> getAllTOperasionalsWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of TOperasionals");
        Page<TOperasional> page = tOperasionalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-operasionals");
        return ResponseEntity.ok().headers(headers).body(page);
    }


    /**
     * GET  /t-operasionals/:id : get the "id" tOperasional.
     *
     * @param id the id of the tOperasional to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tOperasional, or with status 404 (Not Found)
     */
    @GetMapping("/t-operasionals/{id}")
    @Timed
    public ResponseEntity<TOperasional> getTOperasional(@PathVariable Long id) {
        log.debug("REST request to get TOperasional : {}", id);
        Optional<TOperasional> tOperasional = tOperasionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tOperasional);
    }

    /**
     * DELETE  /t-operasionals/:id : delete the "id" tOperasional.
     *
     * @param id the id of the tOperasional to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-operasionals/{id}")
    @Timed
    public ResponseEntity<Void> deleteTOperasional(@PathVariable Long id) {
        log.debug("REST request to delete TOperasional : {}", id);
        tOperasionalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
