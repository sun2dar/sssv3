package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MKas;
import com.sssv3.service.MKasService;
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
 * REST controller for managing MKas.
 */
@RestController
@RequestMapping("/api")
public class MKasResource {

    private final Logger log = LoggerFactory.getLogger(MKasResource.class);

    private static final String ENTITY_NAME = "mKas";

    private final MKasService mKasService;

    public MKasResource(MKasService mKasService) {
        this.mKasService = mKasService;
    }

    /**
     * POST  /m-kas : Create a new mKas.
     *
     * @param mKas the mKas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mKas, or with status 400 (Bad Request) if the mKas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-kas")
    @Timed
    public ResponseEntity<MKas> createMKas(@RequestBody MKas mKas) throws URISyntaxException {
        log.debug("REST request to save MKas : {}", mKas);
        if (mKas.getId() != null) {
            throw new BadRequestAlertException("A new mKas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MKas result = mKasService.save(mKas);
        return ResponseEntity.created(new URI("/api/m-kas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-kas : Updates an existing mKas.
     *
     * @param mKas the mKas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mKas,
     * or with status 400 (Bad Request) if the mKas is not valid,
     * or with status 500 (Internal Server Error) if the mKas couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-kas")
    @Timed
    public ResponseEntity<MKas> updateMKas(@RequestBody MKas mKas) throws URISyntaxException {
        log.debug("REST request to update MKas : {}", mKas);
        if (mKas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MKas result = mKasService.save(mKas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mKas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-kas : get all the mKas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mKas in body
     */
    @GetMapping("/m-kas")
    @Timed
    public ResponseEntity<List<MKas>> getAllMKas(Pageable pageable) {
        log.debug("REST request to get a page of MKas");
        Page<MKas> page = mKasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-kas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-kas/:id : get the "id" mKas.
     *
     * @param id the id of the mKas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mKas, or with status 404 (Not Found)
     */
    @GetMapping("/m-kas/{id}")
    @Timed
    public ResponseEntity<MKas> getMKas(@PathVariable Long id) {
        log.debug("REST request to get MKas : {}", id);
        Optional<MKas> mKas = mKasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mKas);
    }

    /**
     * DELETE  /m-kas/:id : delete the "id" mKas.
     *
     * @param id the id of the mKas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-kas/{id}")
    @Timed
    public ResponseEntity<Void> deleteMKas(@PathVariable Long id) {
        log.debug("REST request to delete MKas : {}", id);
        mKasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
