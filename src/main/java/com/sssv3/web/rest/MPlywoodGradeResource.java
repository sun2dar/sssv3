package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MPlywoodGrade;
import com.sssv3.service.MPlywoodGradeService;
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
 * REST controller for managing MPlywoodGrade.
 */
@RestController
@RequestMapping("/api")
public class MPlywoodGradeResource {

    private final Logger log = LoggerFactory.getLogger(MPlywoodGradeResource.class);

    private static final String ENTITY_NAME = "mPlywoodGrade";

    private final MPlywoodGradeService mPlywoodGradeService;

    public MPlywoodGradeResource(MPlywoodGradeService mPlywoodGradeService) {
        this.mPlywoodGradeService = mPlywoodGradeService;
    }

    /**
     * POST  /m-plywood-grades : Create a new mPlywoodGrade.
     *
     * @param mPlywoodGrade the mPlywoodGrade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mPlywoodGrade, or with status 400 (Bad Request) if the mPlywoodGrade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-plywood-grades")
    @Timed
    public ResponseEntity<MPlywoodGrade> createMPlywoodGrade(@Valid @RequestBody MPlywoodGrade mPlywoodGrade) throws URISyntaxException {
        log.debug("REST request to save MPlywoodGrade : {}", mPlywoodGrade);
        if (mPlywoodGrade.getId() != null) {
            throw new BadRequestAlertException("A new mPlywoodGrade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPlywoodGrade result = mPlywoodGradeService.save(mPlywoodGrade);
        return ResponseEntity.created(new URI("/api/m-plywood-grades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-plywood-grades : Updates an existing mPlywoodGrade.
     *
     * @param mPlywoodGrade the mPlywoodGrade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mPlywoodGrade,
     * or with status 400 (Bad Request) if the mPlywoodGrade is not valid,
     * or with status 500 (Internal Server Error) if the mPlywoodGrade couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-plywood-grades")
    @Timed
    public ResponseEntity<MPlywoodGrade> updateMPlywoodGrade(@Valid @RequestBody MPlywoodGrade mPlywoodGrade) throws URISyntaxException {
        log.debug("REST request to update MPlywoodGrade : {}", mPlywoodGrade);
        if (mPlywoodGrade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPlywoodGrade result = mPlywoodGradeService.save(mPlywoodGrade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mPlywoodGrade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-plywood-grades : get all the mPlywoodGrades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mPlywoodGrades in body
     */
    @GetMapping("/m-plywood-grades")
    @Timed
    public ResponseEntity<List<MPlywoodGrade>> getAllMPlywoodGrades(Pageable pageable) {
        log.debug("REST request to get a page of MPlywoodGrades");
        Page<MPlywoodGrade> page = mPlywoodGradeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-plywood-grades");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-plywood-grades/paging")
    @Timed
    public ResponseEntity<Page<MPlywoodGrade>> getAllMPlywoodGradesWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of MPlywoodGrades");
        Page<MPlywoodGrade> page = mPlywoodGradeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-plywood-grades");
        return ResponseEntity.ok().headers(headers).body(page);
    }


    /**
     * GET  /m-plywood-grades/:id : get the "id" mPlywoodGrade.
     *
     * @param id the id of the mPlywoodGrade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mPlywoodGrade, or with status 404 (Not Found)
     */
    @GetMapping("/m-plywood-grades/{id}")
    @Timed
    public ResponseEntity<MPlywoodGrade> getMPlywoodGrade(@PathVariable Long id) {
        log.debug("REST request to get MPlywoodGrade : {}", id);
        Optional<MPlywoodGrade> mPlywoodGrade = mPlywoodGradeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPlywoodGrade);
    }

    /**
     * DELETE  /m-plywood-grades/:id : delete the "id" mPlywoodGrade.
     *
     * @param id the id of the mPlywoodGrade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-plywood-grades/{id}")
    @Timed
    public ResponseEntity<Void> deleteMPlywoodGrade(@PathVariable Long id) {
        log.debug("REST request to delete MPlywoodGrade : {}", id);
        mPlywoodGradeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
