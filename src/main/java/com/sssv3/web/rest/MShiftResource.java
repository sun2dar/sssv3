package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MShift;
import com.sssv3.service.MShiftService;
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
 * REST controller for managing MShift.
 */
@RestController
@RequestMapping("/api")
public class MShiftResource {

    private final Logger log = LoggerFactory.getLogger(MShiftResource.class);

    private static final String ENTITY_NAME = "mShift";

    private final MShiftService mShiftService;

    public MShiftResource(MShiftService mShiftService) {
        this.mShiftService = mShiftService;
    }

    /**
     * POST  /m-shifts : Create a new mShift.
     *
     * @param mShift the mShift to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mShift, or with status 400 (Bad Request) if the mShift has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-shifts")
    @Timed
    public ResponseEntity<MShift> createMShift(@Valid @RequestBody MShift mShift) throws URISyntaxException {
        log.debug("REST request to save MShift : {}", mShift);
        if (mShift.getId() != null) {
            throw new BadRequestAlertException("A new mShift cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MShift result = mShiftService.save(mShift);
        return ResponseEntity.created(new URI("/api/m-shifts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-shifts : Updates an existing mShift.
     *
     * @param mShift the mShift to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mShift,
     * or with status 400 (Bad Request) if the mShift is not valid,
     * or with status 500 (Internal Server Error) if the mShift couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-shifts")
    @Timed
    public ResponseEntity<MShift> updateMShift(@Valid @RequestBody MShift mShift) throws URISyntaxException {
        log.debug("REST request to update MShift : {}", mShift);
        if (mShift.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MShift result = mShiftService.save(mShift);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mShift.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-shifts : get all the mShifts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mShifts in body
     */
    @GetMapping("/m-shifts")
    @Timed
    public ResponseEntity<List<MShift>> getAllMShifts(Pageable pageable) {
        log.debug("REST request to get a page of MShifts");
        Page<MShift> page = mShiftService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-shifts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-shifts/:id : get the "id" mShift.
     *
     * @param id the id of the mShift to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mShift, or with status 404 (Not Found)
     */
    @GetMapping("/m-shifts/{id}")
    @Timed
    public ResponseEntity<MShift> getMShift(@PathVariable Long id) {
        log.debug("REST request to get MShift : {}", id);
        Optional<MShift> mShift = mShiftService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mShift);
    }

    /**
     * DELETE  /m-shifts/:id : delete the "id" mShift.
     *
     * @param id the id of the mShift to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-shifts/{id}")
    @Timed
    public ResponseEntity<Void> deleteMShift(@PathVariable Long id) {
        log.debug("REST request to delete MShift : {}", id);
        mShiftService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
