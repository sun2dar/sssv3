package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MSupplier;
import com.sssv3.service.MSupplierService;
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
 * REST controller for managing MSupplier.
 */
@RestController
@RequestMapping("/api")
public class MSupplierResource {

    private final Logger log = LoggerFactory.getLogger(MSupplierResource.class);

    private static final String ENTITY_NAME = "mSupplier";

    private final MSupplierService mSupplierService;

    public MSupplierResource(MSupplierService mSupplierService) {
        this.mSupplierService = mSupplierService;
    }

    /**
     * POST  /m-suppliers : Create a new mSupplier.
     *
     * @param mSupplier the mSupplier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mSupplier, or with status 400 (Bad Request) if the mSupplier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-suppliers")
    @Timed
    public ResponseEntity<MSupplier> createMSupplier(@Valid @RequestBody MSupplier mSupplier) throws URISyntaxException {
        log.debug("REST request to save MSupplier : {}", mSupplier);
        if (mSupplier.getId() != null) {
            throw new BadRequestAlertException("A new mSupplier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MSupplier result = mSupplierService.save(mSupplier);
        return ResponseEntity.created(new URI("/api/m-suppliers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-suppliers : Updates an existing mSupplier.
     *
     * @param mSupplier the mSupplier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mSupplier,
     * or with status 400 (Bad Request) if the mSupplier is not valid,
     * or with status 500 (Internal Server Error) if the mSupplier couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-suppliers")
    @Timed
    public ResponseEntity<MSupplier> updateMSupplier(@Valid @RequestBody MSupplier mSupplier) throws URISyntaxException {
        log.debug("REST request to update MSupplier : {}", mSupplier);
        if (mSupplier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MSupplier result = mSupplierService.save(mSupplier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mSupplier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-suppliers : get all the mSuppliers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mSuppliers in body
     */
    @GetMapping("/m-suppliers")
    @Timed
    public ResponseEntity<List<MSupplier>> getAllMSuppliers(Pageable pageable) {
        log.debug("REST request to get a page of MSuppliers");
        Page<MSupplier> page = mSupplierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-suppliers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-suppliers/:id : get the "id" mSupplier.
     *
     * @param id the id of the mSupplier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mSupplier, or with status 404 (Not Found)
     */
    @GetMapping("/m-suppliers/{id}")
    @Timed
    public ResponseEntity<MSupplier> getMSupplier(@PathVariable Long id) {
        log.debug("REST request to get MSupplier : {}", id);
        Optional<MSupplier> mSupplier = mSupplierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mSupplier);
    }

    /**
     * DELETE  /m-suppliers/:id : delete the "id" mSupplier.
     *
     * @param id the id of the mSupplier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-suppliers/{id}")
    @Timed
    public ResponseEntity<Void> deleteMSupplier(@PathVariable Long id) {
        log.debug("REST request to delete MSupplier : {}", id);
        mSupplierService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
