package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.TLog;
import com.sssv3.service.TLogService;
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
 * REST controller for managing TLog.
 */
@RestController
@RequestMapping("/api")
public class TLogResource {

    private final Logger log = LoggerFactory.getLogger(TLogResource.class);

    private static final String ENTITY_NAME = "tLog";

    private final TLogService tLogService;

    public TLogResource(TLogService tLogService) {
        this.tLogService = tLogService;
    }

    /**
     * POST  /t-logs : Create a new tLog.
     *
     * @param tLog the tLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tLog, or with status 400 (Bad Request) if the tLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-logs")
    @Timed
    public ResponseEntity<TLog> createTLog(@RequestBody TLog tLog) throws URISyntaxException {
        log.debug("REST request to save TLog : {}", tLog);
        if (tLog.getId() != null) {
            throw new BadRequestAlertException("A new tLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TLog result = tLogService.save(tLog);
        return ResponseEntity.created(new URI("/api/t-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-logs : Updates an existing tLog.
     *
     * @param tLog the tLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tLog,
     * or with status 400 (Bad Request) if the tLog is not valid,
     * or with status 500 (Internal Server Error) if the tLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-logs")
    @Timed
    public ResponseEntity<TLog> updateTLog(@RequestBody TLog tLog) throws URISyntaxException {
        log.debug("REST request to update TLog : {}", tLog);
        if (tLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TLog result = tLogService.save(tLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-logs : get all the tLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tLogs in body
     */
    @GetMapping("/t-logs")
    @Timed
    public ResponseEntity<List<TLog>> getAllTLogs(Pageable pageable) {
        log.debug("REST request to get a page of TLogs");
        Page<TLog> page = tLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-logs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/t-logs/paging")
    @Timed
    public ResponseEntity<Page<TLog>> getAllTLogsWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of TLogs");
        Page<TLog> page = tLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-logs");
        return ResponseEntity.ok().headers(headers).body(page);
    }

    /**
     * GET  /t-logs/:id : get the "id" tLog.
     *
     * @param id the id of the tLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tLog, or with status 404 (Not Found)
     */
    @GetMapping("/t-logs/{id}")
    @Timed
    public ResponseEntity<TLog> getTLog(@PathVariable Long id) {
        log.debug("REST request to get TLog : {}", id);
        Optional<TLog> tLog = tLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tLog);
    }

    /**
     * DELETE  /t-logs/:id : delete the "id" tLog.
     *
     * @param id the id of the tLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTLog(@PathVariable Long id) {
        log.debug("REST request to delete TLog : {}", id);
        tLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
