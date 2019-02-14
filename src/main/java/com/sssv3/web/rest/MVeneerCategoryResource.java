package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MVeneerCategory;
import com.sssv3.service.MVeneerCategoryService;
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
 * REST controller for managing MVeneerCategory.
 */
@RestController
@RequestMapping("/api")
public class MVeneerCategoryResource {

    private final Logger log = LoggerFactory.getLogger(MVeneerCategoryResource.class);

    private static final String ENTITY_NAME = "mVeneerCategory";

    private final MVeneerCategoryService mVeneerCategoryService;

    public MVeneerCategoryResource(MVeneerCategoryService mVeneerCategoryService) {
        this.mVeneerCategoryService = mVeneerCategoryService;
    }

    /**
     * POST  /m-veneer-categories : Create a new mVeneerCategory.
     *
     * @param mVeneerCategory the mVeneerCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mVeneerCategory, or with status 400 (Bad Request) if the mVeneerCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-veneer-categories")
    @Timed
    public ResponseEntity<MVeneerCategory> createMVeneerCategory(@Valid @RequestBody MVeneerCategory mVeneerCategory) throws URISyntaxException {
        log.debug("REST request to save MVeneerCategory : {}", mVeneerCategory);
        if (mVeneerCategory.getId() != null) {
            throw new BadRequestAlertException("A new mVeneerCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVeneerCategory result = mVeneerCategoryService.save(mVeneerCategory);
        return ResponseEntity.created(new URI("/api/m-veneer-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-veneer-categories : Updates an existing mVeneerCategory.
     *
     * @param mVeneerCategory the mVeneerCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mVeneerCategory,
     * or with status 400 (Bad Request) if the mVeneerCategory is not valid,
     * or with status 500 (Internal Server Error) if the mVeneerCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-veneer-categories")
    @Timed
    public ResponseEntity<MVeneerCategory> updateMVeneerCategory(@Valid @RequestBody MVeneerCategory mVeneerCategory) throws URISyntaxException {
        log.debug("REST request to update MVeneerCategory : {}", mVeneerCategory);
        if (mVeneerCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVeneerCategory result = mVeneerCategoryService.save(mVeneerCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mVeneerCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-veneer-categories : get all the mVeneerCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mVeneerCategories in body
     */
    @GetMapping("/m-veneer-categories")
    @Timed
    public ResponseEntity<List<MVeneerCategory>> getAllMVeneerCategories(Pageable pageable) {
        log.debug("REST request to get a page of MVeneerCategories");
        Page<MVeneerCategory> page = mVeneerCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-veneer-categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-veneer-categories/paging")
    @Timed
    public ResponseEntity<Page<MVeneerCategory>> getAllMVeneerCategoriesWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of MVeneerCategories");
        Page<MVeneerCategory> page = mVeneerCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-veneer-categories");
        return ResponseEntity.ok().headers(headers).body(page);
    }

    /**
     * GET  /m-veneer-categories/:id : get the "id" mVeneerCategory.
     *
     * @param id the id of the mVeneerCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mVeneerCategory, or with status 404 (Not Found)
     */
    @GetMapping("/m-veneer-categories/{id}")
    @Timed
    public ResponseEntity<MVeneerCategory> getMVeneerCategory(@PathVariable Long id) {
        log.debug("REST request to get MVeneerCategory : {}", id);
        Optional<MVeneerCategory> mVeneerCategory = mVeneerCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVeneerCategory);
    }

    /**
     * DELETE  /m-veneer-categories/:id : delete the "id" mVeneerCategory.
     *
     * @param id the id of the mVeneerCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-veneer-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteMVeneerCategory(@PathVariable Long id) {
        log.debug("REST request to delete MVeneerCategory : {}", id);
        mVeneerCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
