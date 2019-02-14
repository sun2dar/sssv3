package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MUtang;
import com.sssv3.service.MUtangService;
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
 * REST controller for managing MUtang.
 */
@RestController
@RequestMapping("/api")
public class MUtangResource {

    private final Logger log = LoggerFactory.getLogger(MUtangResource.class);

    private static final String ENTITY_NAME = "mUtang";

    private final MUtangService mUtangService;

    public MUtangResource(MUtangService mUtangService) {
        this.mUtangService = mUtangService;
    }

    /**
     * POST  /m-utangs : Create a new mUtang.
     *
     * @param mUtang the mUtang to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mUtang, or with status 400 (Bad Request) if the mUtang has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-utangs")
    @Timed
    public ResponseEntity<MUtang> createMUtang(@RequestBody MUtang mUtang) throws URISyntaxException {
        log.debug("REST request to save MUtang : {}", mUtang);
        if (mUtang.getId() != null) {
            throw new BadRequestAlertException("A new mUtang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MUtang result = mUtangService.save(mUtang);
        return ResponseEntity.created(new URI("/api/m-utangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-utangs : Updates an existing mUtang.
     *
     * @param mUtang the mUtang to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mUtang,
     * or with status 400 (Bad Request) if the mUtang is not valid,
     * or with status 500 (Internal Server Error) if the mUtang couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-utangs")
    @Timed
    public ResponseEntity<MUtang> updateMUtang(@RequestBody MUtang mUtang) throws URISyntaxException {
        log.debug("REST request to update MUtang : {}", mUtang);
        if (mUtang.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MUtang result = mUtangService.save(mUtang);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mUtang.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-utangs : get all the mUtangs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mUtangs in body
     */
    @GetMapping("/m-utangs")
    @Timed
    public ResponseEntity<List<MUtang>> getAllMUtangs(Pageable pageable) {
        log.debug("REST request to get a page of MUtangs");
        Page<MUtang> page = mUtangService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-utangs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-utangs/paging")
    @Timed
    public ResponseEntity<Page<MUtang>> getAllMUtangsWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of MUtangs");
        Page<MUtang> page = mUtangService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-utangs");
        return ResponseEntity.ok().headers(headers).body(page);
    }
    /**
     * GET  /m-utangs/:id : get the "id" mUtang.
     *
     * @param id the id of the mUtang to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mUtang, or with status 404 (Not Found)
     */
    @GetMapping("/m-utangs/{id}")
    @Timed
    public ResponseEntity<MUtang> getMUtang(@PathVariable Long id) {
        log.debug("REST request to get MUtang : {}", id);
        Optional<MUtang> mUtang = mUtangService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mUtang);
    }

    /**
     * DELETE  /m-utangs/:id : delete the "id" mUtang.
     *
     * @param id the id of the mUtang to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-utangs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMUtang(@PathVariable Long id) {
        log.debug("REST request to delete MUtang : {}", id);
        mUtangService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
