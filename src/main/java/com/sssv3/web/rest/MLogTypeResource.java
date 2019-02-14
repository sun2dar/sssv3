package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MLogType;
import com.sssv3.service.MLogTypeService;
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
 * REST controller for managing MLogType.
 */
@RestController
@RequestMapping("/api")
public class MLogTypeResource {

    private final Logger log = LoggerFactory.getLogger(MLogTypeResource.class);

    private static final String ENTITY_NAME = "mLogType";

    private final MLogTypeService mLogTypeService;

    public MLogTypeResource(MLogTypeService mLogTypeService) {
        this.mLogTypeService = mLogTypeService;
    }

    /**
     * POST  /m-log-types : Create a new mLogType.
     *
     * @param mLogType the mLogType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mLogType, or with status 400 (Bad Request) if the mLogType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-log-types")
    @Timed
    public ResponseEntity<MLogType> createMLogType(@Valid @RequestBody MLogType mLogType) throws URISyntaxException {
        log.debug("REST request to save MLogType : {}", mLogType);
        if (mLogType.getId() != null) {
            throw new BadRequestAlertException("A new mLogType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MLogType result = mLogTypeService.save(mLogType);
        return ResponseEntity.created(new URI("/api/m-log-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-log-types : Updates an existing mLogType.
     *
     * @param mLogType the mLogType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mLogType,
     * or with status 400 (Bad Request) if the mLogType is not valid,
     * or with status 500 (Internal Server Error) if the mLogType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-log-types")
    @Timed
    public ResponseEntity<MLogType> updateMLogType(@Valid @RequestBody MLogType mLogType) throws URISyntaxException {
        log.debug("REST request to update MLogType : {}", mLogType);
        if (mLogType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MLogType result = mLogTypeService.save(mLogType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mLogType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-log-types : get all the mLogTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mLogTypes in body
     */
    @GetMapping("/m-log-types")
    @Timed
    public ResponseEntity<List<MLogType>> getAllMLogTypes(Pageable pageable) {
        log.debug("REST request to get a page of MLogTypes");
        Page<MLogType> page = mLogTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-log-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-log-types/paging")
    @Timed
    public ResponseEntity<Page<MLogType>> getAllMLogTypesWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of MLogTypes");
        Page<MLogType> page = mLogTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-log-types");
        return ResponseEntity.ok().headers(headers).body(page);
    }

    /**
     * GET  /m-log-types/:id : get the "id" mLogType.
     *
     * @param id the id of the mLogType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mLogType, or with status 404 (Not Found)
     */
    @GetMapping("/m-log-types/{id}")
    @Timed
    public ResponseEntity<MLogType> getMLogType(@PathVariable Long id) {
        log.debug("REST request to get MLogType : {}", id);
        Optional<MLogType> mLogType = mLogTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mLogType);
    }

    /**
     * DELETE  /m-log-types/:id : delete the "id" mLogType.
     *
     * @param id the id of the mLogType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-log-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteMLogType(@PathVariable Long id) {
        log.debug("REST request to delete MLogType : {}", id);
        mLogTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
