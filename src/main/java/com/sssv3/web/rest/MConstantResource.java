package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MConstant;
import com.sssv3.service.MConstantService;
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
 * REST controller for managing MConstant.
 */
@RestController
@RequestMapping("/api")
public class MConstantResource {

    private final Logger log = LoggerFactory.getLogger(MConstantResource.class);

    private static final String ENTITY_NAME = "mConstant";

    private final MConstantService mConstantService;

    public MConstantResource(MConstantService mConstantService) {
        this.mConstantService = mConstantService;
    }

    /**
     * POST  /m-constants : Create a new mConstant.
     *
     * @param mConstant the mConstant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mConstant, or with status 400 (Bad Request) if the mConstant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-constants")
    @Timed
    public ResponseEntity<MConstant> createMConstant(@Valid @RequestBody MConstant mConstant) throws URISyntaxException {
        log.debug("REST request to save MConstant : {}", mConstant);
        if (mConstant.getId() != null) {
            throw new BadRequestAlertException("A new mConstant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MConstant result = mConstantService.save(mConstant);
        return ResponseEntity.created(new URI("/api/m-constants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-constants : Updates an existing mConstant.
     *
     * @param mConstant the mConstant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mConstant,
     * or with status 400 (Bad Request) if the mConstant is not valid,
     * or with status 500 (Internal Server Error) if the mConstant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-constants")
    @Timed
    public ResponseEntity<MConstant> updateMConstant(@Valid @RequestBody MConstant mConstant) throws URISyntaxException {
        log.debug("REST request to update MConstant : {}", mConstant);
        if (mConstant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MConstant result = mConstantService.save(mConstant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mConstant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-constants : get all the mConstants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mConstants in body
     */
    @GetMapping("/m-constants")
    @Timed
    public ResponseEntity<List<MConstant>> getAllMConstants(Pageable pageable) {
        log.debug("REST request to get a page of MConstants");
        Page<MConstant> page = mConstantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-constants");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-constants/:id : get the "id" mConstant.
     *
     * @param id the id of the mConstant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mConstant, or with status 404 (Not Found)
     */
    @GetMapping("/m-constants/{id}")
    @Timed
    public ResponseEntity<MConstant> getMConstant(@PathVariable Long id) {
        log.debug("REST request to get MConstant : {}", id);
        Optional<MConstant> mConstant = mConstantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mConstant);
    }

    /**
     * DELETE  /m-constants/:id : delete the "id" mConstant.
     *
     * @param id the id of the mConstant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-constants/{id}")
    @Timed
    public ResponseEntity<Void> deleteMConstant(@PathVariable Long id) {
        log.debug("REST request to delete MConstant : {}", id);
        mConstantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
