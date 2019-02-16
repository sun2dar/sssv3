package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MLogCategory;
import com.sssv3.service.MLogCategoryService;
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
 * REST controller for managing MLogCategory.
 */
@RestController
@RequestMapping("/api")
public class MLogCategoryResource {

    private final Logger log = LoggerFactory.getLogger(MLogCategoryResource.class);

    private static final String ENTITY_NAME = "mLogCategory";

    private final MLogCategoryService mLogCategoryService;

    public MLogCategoryResource(MLogCategoryService mLogCategoryService) {
        this.mLogCategoryService = mLogCategoryService;
    }

    /**
     * POST  /m-log-categories : Create a new mLogCategory.
     *
     * @param mLogCategory the mLogCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mLogCategory, or with status 400 (Bad Request) if the mLogCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-log-categories")
    @Timed
    public ResponseEntity<MLogCategory> createMLogCategory(@Valid @RequestBody MLogCategory mLogCategory) throws URISyntaxException {
        log.debug("REST request to save MLogCategory : {}", mLogCategory);
        if (mLogCategory.getId() != null) {
            throw new BadRequestAlertException("A new mLogCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MLogCategory result = mLogCategoryService.save(mLogCategory);
        return ResponseEntity.created(new URI("/api/m-log-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/m-log-categories/paging")
    @Timed
    public ResponseEntity<Page<MLogCategory>> getAllMLogCategoriesWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of MLogCategories");
        Page<MLogCategory> page = mLogCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-log-categories");
        return ResponseEntity.ok().headers(headers).body(page);
    }
    /**
     * PUT  /m-log-categories : Updates an existing mLogCategory.
     *
     * @param mLogCategory the mLogCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mLogCategory,
     * or with status 400 (Bad Request) if the mLogCategory is not valid,
     * or with status 500 (Internal Server Error) if the mLogCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-log-categories")
    @Timed
    public ResponseEntity<MLogCategory> updateMLogCategory(@Valid @RequestBody MLogCategory mLogCategory) throws URISyntaxException {
        log.debug("REST request to update MLogCategory : {}", mLogCategory);
        if (mLogCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MLogCategory result = mLogCategoryService.save(mLogCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mLogCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-log-categories : get all the mLogCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mLogCategories in body
     */
    @GetMapping("/m-log-categories")
    @Timed
    public ResponseEntity<List<MLogCategory>> getAllMLogCategories(Pageable pageable) {
        log.debug("REST request to get a page of MLogCategories");
        Page<MLogCategory> page = mLogCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-log-categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-log-categories/:id : get the "id" mLogCategory.
     *
     * @param id the id of the mLogCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mLogCategory, or with status 404 (Not Found)
     */
    @GetMapping("/m-log-categories/{id}")
    @Timed
    public ResponseEntity<MLogCategory> getMLogCategory(@PathVariable Long id) {
        log.debug("REST request to get MLogCategory : {}", id);
        Optional<MLogCategory> mLogCategory = mLogCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mLogCategory);
    }

    /**
     * DELETE  /m-log-categories/:id : delete the "id" mLogCategory.
     *
     * @param id the id of the mLogCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-log-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteMLogCategory(@PathVariable Long id) {
        log.debug("REST request to delete MLogCategory : {}", id);
        mLogCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
