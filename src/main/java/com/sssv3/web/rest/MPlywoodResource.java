package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MPlywood;
import com.sssv3.service.MPlywoodService;
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
 * REST controller for managing MPlywood.
 */
@RestController
@RequestMapping("/api")
public class MPlywoodResource {

    private final Logger log = LoggerFactory.getLogger(MPlywoodResource.class);

    private static final String ENTITY_NAME = "mPlywood";

    private final MPlywoodService mPlywoodService;

    public MPlywoodResource(MPlywoodService mPlywoodService) {
        this.mPlywoodService = mPlywoodService;
    }

    /**
     * POST  /m-plywoods : Create a new mPlywood.
     *
     * @param mPlywood the mPlywood to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mPlywood, or with status 400 (Bad Request) if the mPlywood has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-plywoods")
    @Timed
    public ResponseEntity<MPlywood> createMPlywood(@RequestBody MPlywood mPlywood) throws URISyntaxException {
        log.debug("REST request to save MPlywood : {}", mPlywood);
        if (mPlywood.getId() != null) {
            throw new BadRequestAlertException("A new mPlywood cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPlywood result = mPlywoodService.save(mPlywood);
        return ResponseEntity.created(new URI("/api/m-plywoods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-plywoods : Updates an existing mPlywood.
     *
     * @param mPlywood the mPlywood to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mPlywood,
     * or with status 400 (Bad Request) if the mPlywood is not valid,
     * or with status 500 (Internal Server Error) if the mPlywood couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-plywoods")
    @Timed
    public ResponseEntity<MPlywood> updateMPlywood(@RequestBody MPlywood mPlywood) throws URISyntaxException {
        log.debug("REST request to update MPlywood : {}", mPlywood);
        if (mPlywood.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPlywood result = mPlywoodService.save(mPlywood);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mPlywood.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-plywoods : get all the mPlywoods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mPlywoods in body
     */
    @GetMapping("/m-plywoods")
    @Timed
    public ResponseEntity<List<MPlywood>> getAllMPlywoods(Pageable pageable) {
        log.debug("REST request to get a page of MPlywoods");
        Page<MPlywood> page = mPlywoodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-plywoods");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-plywoods/:id : get the "id" mPlywood.
     *
     * @param id the id of the mPlywood to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mPlywood, or with status 404 (Not Found)
     */
    @GetMapping("/m-plywoods/{id}")
    @Timed
    public ResponseEntity<MPlywood> getMPlywood(@PathVariable Long id) {
        log.debug("REST request to get MPlywood : {}", id);
        Optional<MPlywood> mPlywood = mPlywoodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPlywood);
    }

    /**
     * DELETE  /m-plywoods/:id : delete the "id" mPlywood.
     *
     * @param id the id of the mPlywood to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-plywoods/{id}")
    @Timed
    public ResponseEntity<Void> deleteMPlywood(@PathVariable Long id) {
        log.debug("REST request to delete MPlywood : {}", id);
        mPlywoodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
