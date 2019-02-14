package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MPajak;
import com.sssv3.service.MPajakService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MPajak.
 */
@RestController
@RequestMapping("/api")
public class MPajakResource {

    private final Logger log = LoggerFactory.getLogger(MPajakResource.class);

    private static final String ENTITY_NAME = "mPajak";

    private final MPajakService mPajakService;

    public MPajakResource(MPajakService mPajakService) {
        this.mPajakService = mPajakService;
    }

    /**
     * POST  /m-pajaks : Create a new mPajak.
     *
     * @param mPajak the mPajak to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mPajak, or with status 400 (Bad Request) if the mPajak has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-pajaks")
    @Timed
    public ResponseEntity<MPajak> createMPajak(@Valid @RequestBody MPajak mPajak) throws URISyntaxException {
        log.debug("REST request to save MPajak : {}", mPajak);
        if (mPajak.getId() != null) {
            throw new BadRequestAlertException("A new mPajak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPajak result = mPajakService.save(mPajak);
        return ResponseEntity.created(new URI("/api/m-pajaks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-pajaks : Updates an existing mPajak.
     *
     * @param mPajak the mPajak to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mPajak,
     * or with status 400 (Bad Request) if the mPajak is not valid,
     * or with status 500 (Internal Server Error) if the mPajak couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-pajaks")
    @Timed
    public ResponseEntity<MPajak> updateMPajak(@Valid @RequestBody MPajak mPajak) throws URISyntaxException {
        log.debug("REST request to update MPajak : {}", mPajak);
        if (mPajak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPajak result = mPajakService.save(mPajak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mPajak.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-pajaks : get all the mPajaks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mPajaks in body
     */
    @GetMapping("/m-pajaks")
    @Timed
    public ResponseEntity<List<MPajak>> getAllMPajaks(Pageable pageable) {
        log.debug("REST request to get a page of MPajaks");
        Page<MPajak> page = mPajakService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-pajaks");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-pajaks/paging")
    @Timed
    public ResponseEntity<Page<MPajak>> getAllMPajaksWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of MPajaks");
        Page<MPajak> page = mPajakService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-pajaks");
        return ResponseEntity.ok().headers(headers).body(page);
    }

    /**
     * GET  /m-pajaks/:id : get the "id" mPajak.
     *
     * @param id the id of the mPajak to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mPajak, or with status 404 (Not Found)
     */
    @GetMapping("/m-pajaks/{id}")
    @Timed
    public ResponseEntity<MPajak> getMPajak(@PathVariable Long id) {
        log.debug("REST request to get MPajak : {}", id);
        Optional<MPajak> mPajak = mPajakService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPajak);
    }

    /**
     * DELETE  /m-pajaks/:id : delete the "id" mPajak.
     *
     * @param id the id of the mPajak to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-pajaks/{id}")
    @Timed
    public ResponseEntity<Void> deleteMPajak(@PathVariable Long id) {
        log.debug("REST request to delete MPajak : {}", id);
        mPajakService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
