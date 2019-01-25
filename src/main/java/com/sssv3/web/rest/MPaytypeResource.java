package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MPaytype;
import com.sssv3.service.MPaytypeService;
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
 * REST controller for managing MPaytype.
 */
@RestController
@RequestMapping("/api")
public class MPaytypeResource {

    private final Logger log = LoggerFactory.getLogger(MPaytypeResource.class);

    private static final String ENTITY_NAME = "mPaytype";

    private final MPaytypeService mPaytypeService;

    public MPaytypeResource(MPaytypeService mPaytypeService) {
        this.mPaytypeService = mPaytypeService;
    }

    /**
     * POST  /m-paytypes : Create a new mPaytype.
     *
     * @param mPaytype the mPaytype to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mPaytype, or with status 400 (Bad Request) if the mPaytype has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-paytypes")
    @Timed
    public ResponseEntity<MPaytype> createMPaytype(@Valid @RequestBody MPaytype mPaytype) throws URISyntaxException {
        log.debug("REST request to save MPaytype : {}", mPaytype);
        if (mPaytype.getId() != null) {
            throw new BadRequestAlertException("A new mPaytype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPaytype result = mPaytypeService.save(mPaytype);
        return ResponseEntity.created(new URI("/api/m-paytypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-paytypes : Updates an existing mPaytype.
     *
     * @param mPaytype the mPaytype to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mPaytype,
     * or with status 400 (Bad Request) if the mPaytype is not valid,
     * or with status 500 (Internal Server Error) if the mPaytype couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-paytypes")
    @Timed
    public ResponseEntity<MPaytype> updateMPaytype(@Valid @RequestBody MPaytype mPaytype) throws URISyntaxException {
        log.debug("REST request to update MPaytype : {}", mPaytype);
        if (mPaytype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPaytype result = mPaytypeService.save(mPaytype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mPaytype.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-paytypes : get all the mPaytypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mPaytypes in body
     */
    @GetMapping("/m-paytypes")
    @Timed
    public ResponseEntity<List<MPaytype>> getAllMPaytypes(Pageable pageable) {
        log.debug("REST request to get a page of MPaytypes");
        Page<MPaytype> page = mPaytypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-paytypes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-paytypes/:id : get the "id" mPaytype.
     *
     * @param id the id of the mPaytype to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mPaytype, or with status 404 (Not Found)
     */
    @GetMapping("/m-paytypes/{id}")
    @Timed
    public ResponseEntity<MPaytype> getMPaytype(@PathVariable Long id) {
        log.debug("REST request to get MPaytype : {}", id);
        Optional<MPaytype> mPaytype = mPaytypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPaytype);
    }

    /**
     * DELETE  /m-paytypes/:id : delete the "id" mPaytype.
     *
     * @param id the id of the mPaytype to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-paytypes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMPaytype(@PathVariable Long id) {
        log.debug("REST request to delete MPaytype : {}", id);
        mPaytypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
