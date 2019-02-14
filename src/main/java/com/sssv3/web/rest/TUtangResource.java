package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.TUtang;
import com.sssv3.service.TUtangService;
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
 * REST controller for managing TUtang.
 */
@RestController
@RequestMapping("/api")
public class TUtangResource {

    private final Logger log = LoggerFactory.getLogger(TUtangResource.class);

    private static final String ENTITY_NAME = "tUtang";

    private final TUtangService tUtangService;

    public TUtangResource(TUtangService tUtangService) {
        this.tUtangService = tUtangService;
    }

    /**
     * POST  /t-utangs : Create a new tUtang.
     *
     * @param tUtang the tUtang to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tUtang, or with status 400 (Bad Request) if the tUtang has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-utangs")
    @Timed
    public ResponseEntity<TUtang> createTUtang(@RequestBody TUtang tUtang) throws URISyntaxException {
        log.debug("REST request to save TUtang : {}", tUtang);
        if (tUtang.getId() != null) {
            throw new BadRequestAlertException("A new tUtang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TUtang result = tUtangService.save(tUtang);
        return ResponseEntity.created(new URI("/api/t-utangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-utangs : Updates an existing tUtang.
     *
     * @param tUtang the tUtang to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tUtang,
     * or with status 400 (Bad Request) if the tUtang is not valid,
     * or with status 500 (Internal Server Error) if the tUtang couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-utangs")
    @Timed
    public ResponseEntity<TUtang> updateTUtang(@RequestBody TUtang tUtang) throws URISyntaxException {
        log.debug("REST request to update TUtang : {}", tUtang);
        if (tUtang.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TUtang result = tUtangService.save(tUtang);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tUtang.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-utangs : get all the tUtangs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tUtangs in body
     */
    @GetMapping("/t-utangs")
    @Timed
    public ResponseEntity<List<TUtang>> getAllTUtangs(Pageable pageable) {
        log.debug("REST request to get a page of TUtangs");
        Page<TUtang> page = tUtangService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-utangs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/t-utangs/paging")
    @Timed
    public ResponseEntity<Page<TUtang>> getAllTUtangsWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of TUtangs");
        Page<TUtang> page = tUtangService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-utangs");
        return ResponseEntity.ok().headers(headers).body(page);
    }

    /**
     * GET  /t-utangs/:id : get the "id" tUtang.
     *
     * @param id the id of the tUtang to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tUtang, or with status 404 (Not Found)
     */
    @GetMapping("/t-utangs/{id}")
    @Timed
    public ResponseEntity<TUtang> getTUtang(@PathVariable Long id) {
        log.debug("REST request to get TUtang : {}", id);
        Optional<TUtang> tUtang = tUtangService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tUtang);
    }

    /**
     * DELETE  /t-utangs/:id : delete the "id" tUtang.
     *
     * @param id the id of the tUtang to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-utangs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTUtang(@PathVariable Long id) {
        log.debug("REST request to delete TUtang : {}", id);
        tUtangService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
