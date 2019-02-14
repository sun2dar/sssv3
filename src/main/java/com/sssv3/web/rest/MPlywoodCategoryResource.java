package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MPlywoodCategory;
import com.sssv3.service.MPlywoodCategoryService;
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
 * REST controller for managing MPlywoodCategory.
 */
@RestController
@RequestMapping("/api")
public class MPlywoodCategoryResource {

    private final Logger log = LoggerFactory.getLogger(MPlywoodCategoryResource.class);

    private static final String ENTITY_NAME = "mPlywoodCategory";

    private final MPlywoodCategoryService mPlywoodCategoryService;

    public MPlywoodCategoryResource(MPlywoodCategoryService mPlywoodCategoryService) {
        this.mPlywoodCategoryService = mPlywoodCategoryService;
    }

    /**
     * POST  /m-plywood-categories : Create a new mPlywoodCategory.
     *
     * @param mPlywoodCategory the mPlywoodCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mPlywoodCategory, or with status 400 (Bad Request) if the mPlywoodCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-plywood-categories")
    @Timed
    public ResponseEntity<MPlywoodCategory> createMPlywoodCategory(@Valid @RequestBody MPlywoodCategory mPlywoodCategory) throws URISyntaxException {
        log.debug("REST request to save MPlywoodCategory : {}", mPlywoodCategory);
        if (mPlywoodCategory.getId() != null) {
            throw new BadRequestAlertException("A new mPlywoodCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPlywoodCategory result = mPlywoodCategoryService.save(mPlywoodCategory);
        return ResponseEntity.created(new URI("/api/m-plywood-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-plywood-categories : Updates an existing mPlywoodCategory.
     *
     * @param mPlywoodCategory the mPlywoodCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mPlywoodCategory,
     * or with status 400 (Bad Request) if the mPlywoodCategory is not valid,
     * or with status 500 (Internal Server Error) if the mPlywoodCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-plywood-categories")
    @Timed
    public ResponseEntity<MPlywoodCategory> updateMPlywoodCategory(@Valid @RequestBody MPlywoodCategory mPlywoodCategory) throws URISyntaxException {
        log.debug("REST request to update MPlywoodCategory : {}", mPlywoodCategory);
        if (mPlywoodCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPlywoodCategory result = mPlywoodCategoryService.save(mPlywoodCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mPlywoodCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-plywood-categories : get all the mPlywoodCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mPlywoodCategories in body
     */
    @GetMapping("/m-plywood-categories")
    @Timed
    public ResponseEntity<List<MPlywoodCategory>> getAllMPlywoodCategories(Pageable pageable) {
        log.debug("REST request to get a page of MPlywoodCategories");
        Page<MPlywoodCategory> page = mPlywoodCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-plywood-categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-plywood-categories/paging")
    @Timed
    public ResponseEntity<Page<MPlywoodCategory>> getAllMPlywoodCategoriesWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of MPlywoodCategories");
        Page<MPlywoodCategory> page = mPlywoodCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-plywood-categories");
        return ResponseEntity.ok().headers(headers).body(page);
    }


    /**
     * GET  /m-plywood-categories/:id : get the "id" mPlywoodCategory.
     *
     * @param id the id of the mPlywoodCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mPlywoodCategory, or with status 404 (Not Found)
     */
    @GetMapping("/m-plywood-categories/{id}")
    @Timed
    public ResponseEntity<MPlywoodCategory> getMPlywoodCategory(@PathVariable Long id) {
        log.debug("REST request to get MPlywoodCategory : {}", id);
        Optional<MPlywoodCategory> mPlywoodCategory = mPlywoodCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPlywoodCategory);
    }

    /**
     * DELETE  /m-plywood-categories/:id : delete the "id" mPlywoodCategory.
     *
     * @param id the id of the mPlywoodCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-plywood-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteMPlywoodCategory(@PathVariable Long id) {
        log.debug("REST request to delete MPlywoodCategory : {}", id);
        mPlywoodCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
