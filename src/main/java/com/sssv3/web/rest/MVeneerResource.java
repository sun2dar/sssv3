package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MVeneer;
import com.sssv3.service.MVeneerService;
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
 * REST controller for managing MVeneer.
 */
@RestController
@RequestMapping("/api")
public class MVeneerResource {

    private final Logger log = LoggerFactory.getLogger(MVeneerResource.class);

    private static final String ENTITY_NAME = "mVeneer";

    private final MVeneerService mVeneerService;

    public MVeneerResource(MVeneerService mVeneerService) {
        this.mVeneerService = mVeneerService;
    }

    /**
     * POST  /m-veneers : Create a new mVeneer.
     *
     * @param mVeneer the mVeneer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mVeneer, or with status 400 (Bad Request) if the mVeneer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-veneers")
    @Timed
    public ResponseEntity<MVeneer> createMVeneer(@RequestBody MVeneer mVeneer) throws URISyntaxException {
        log.debug("REST request to save MVeneer : {}", mVeneer);
        if (mVeneer.getId() != null) {
            throw new BadRequestAlertException("A new mVeneer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVeneer result = mVeneerService.save(mVeneer);
        return ResponseEntity.created(new URI("/api/m-veneers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-veneers : Updates an existing mVeneer.
     *
     * @param mVeneer the mVeneer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mVeneer,
     * or with status 400 (Bad Request) if the mVeneer is not valid,
     * or with status 500 (Internal Server Error) if the mVeneer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-veneers")
    @Timed
    public ResponseEntity<MVeneer> updateMVeneer(@RequestBody MVeneer mVeneer) throws URISyntaxException {
        log.debug("REST request to update MVeneer : {}", mVeneer);
        if (mVeneer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVeneer result = mVeneerService.save(mVeneer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mVeneer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-veneers : get all the mVeneers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mVeneers in body
     */
    @GetMapping("/m-veneers")
    @Timed
    public ResponseEntity<List<MVeneer>> getAllMVeneers(Pageable pageable) {
        log.debug("REST request to get a page of MVeneers");
        Page<MVeneer> page = mVeneerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-veneers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-veneers/:id : get the "id" mVeneer.
     *
     * @param id the id of the mVeneer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mVeneer, or with status 404 (Not Found)
     */
    @GetMapping("/m-veneers/{id}")
    @Timed
    public ResponseEntity<MVeneer> getMVeneer(@PathVariable Long id) {
        log.debug("REST request to get MVeneer : {}", id);
        Optional<MVeneer> mVeneer = mVeneerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVeneer);
    }

    /**
     * DELETE  /m-veneers/:id : delete the "id" mVeneer.
     *
     * @param id the id of the mVeneer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-veneers/{id}")
    @Timed
    public ResponseEntity<Void> deleteMVeneer(@PathVariable Long id) {
        log.debug("REST request to delete MVeneer : {}", id);
        mVeneerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
