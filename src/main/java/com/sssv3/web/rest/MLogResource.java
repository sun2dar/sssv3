package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MLog;
import com.sssv3.service.MLogService;
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
 * REST controller for managing MLog.
 */
@RestController
@RequestMapping("/api")
public class MLogResource {

    private final Logger log = LoggerFactory.getLogger(MLogResource.class);

    private static final String ENTITY_NAME = "mLog";

    private final MLogService mLogService;

    public MLogResource(MLogService mLogService) {
        this.mLogService = mLogService;
    }

    /**
     * POST  /m-logs : Create a new mLog.
     *
     * @param mLog the mLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mLog, or with status 400 (Bad Request) if the mLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-logs")
    @Timed
    public ResponseEntity<MLog> createMLog(@Valid @RequestBody MLog mLog) throws URISyntaxException {
        log.debug("REST request to save MLog : {}", mLog);
        if (mLog.getId() != null) {
            throw new BadRequestAlertException("A new mLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MLog result = mLogService.save(mLog);
        return ResponseEntity.created(new URI("/api/m-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-logs : Updates an existing mLog.
     *
     * @param mLog the mLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mLog,
     * or with status 400 (Bad Request) if the mLog is not valid,
     * or with status 500 (Internal Server Error) if the mLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-logs")
    @Timed
    public ResponseEntity<MLog> updateMLog(@Valid @RequestBody MLog mLog) throws URISyntaxException {
        log.debug("REST request to update MLog : {}", mLog);
        if (mLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MLog result = mLogService.save(mLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-logs : get all the mLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mLogs in body
     */
    @GetMapping("/m-logs")
    @Timed
    public ResponseEntity<List<MLog>> getAllMLogs(Pageable pageable) {
        log.debug("REST request to get a page of MLogs");
        Page<MLog> page = mLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-logs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-logs/paging")
    @Timed
    public ResponseEntity<Page<MLog>> getAllMLogsWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of MLogs");
        Page<MLog> page = mLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-logs");
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/m-logs/search")
    @Timed
    public ResponseEntity<Page<MLog>> getAllMLogs(@RequestParam(value = "nama") String nama, Pageable pageable) {
        log.debug("REST request to get a page of Search in MLogs");
        Page<MLog> page = mLogService.findByNama(nama, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-logs");
        return ResponseEntity.ok().headers(headers).body(page);
    }

    /**
     * GET  /m-logs/:id : get the "id" mLog.
     *
     * @param id the id of the mLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mLog, or with status 404 (Not Found)
     */
    @GetMapping("/m-logs/{id}")
    @Timed
    public ResponseEntity<MLog> getMLog(@PathVariable Long id) {
        log.debug("REST request to get MLog : {}", id);
        Optional<MLog> mLog = mLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mLog);
    }

    /**
     * DELETE  /m-logs/:id : delete the "id" mLog.
     *
     * @param id the id of the mLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMLog(@PathVariable Long id) {
        log.debug("REST request to delete MLog : {}", id);
        mLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
