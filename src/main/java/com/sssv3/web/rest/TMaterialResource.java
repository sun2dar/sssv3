package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.TMaterial;
import com.sssv3.service.TMaterialService;
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
 * REST controller for managing TMaterial.
 */
@RestController
@RequestMapping("/api")
public class TMaterialResource {

    private final Logger log = LoggerFactory.getLogger(TMaterialResource.class);

    private static final String ENTITY_NAME = "tMaterial";

    private final TMaterialService tMaterialService;

    public TMaterialResource(TMaterialService tMaterialService) {
        this.tMaterialService = tMaterialService;
    }

    /**
     * POST  /t-materials : Create a new tMaterial.
     *
     * @param tMaterial the tMaterial to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tMaterial, or with status 400 (Bad Request) if the tMaterial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-materials")
    @Timed
    public ResponseEntity<TMaterial> createTMaterial(@RequestBody TMaterial tMaterial) throws URISyntaxException {
        log.debug("REST request to save TMaterial : {}", tMaterial);
        if (tMaterial.getId() != null) {
            throw new BadRequestAlertException("A new tMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TMaterial result = tMaterialService.save(tMaterial);
        return ResponseEntity.created(new URI("/api/t-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-materials : Updates an existing tMaterial.
     *
     * @param tMaterial the tMaterial to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tMaterial,
     * or with status 400 (Bad Request) if the tMaterial is not valid,
     * or with status 500 (Internal Server Error) if the tMaterial couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-materials")
    @Timed
    public ResponseEntity<TMaterial> updateTMaterial(@RequestBody TMaterial tMaterial) throws URISyntaxException {
        log.debug("REST request to update TMaterial : {}", tMaterial);
        if (tMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TMaterial result = tMaterialService.save(tMaterial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tMaterial.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-materials : get all the tMaterials.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tMaterials in body
     */
    @GetMapping("/t-materials")
    @Timed
    public ResponseEntity<List<TMaterial>> getAllTMaterials(Pageable pageable) {
        log.debug("REST request to get a page of TMaterials");
        Page<TMaterial> page = tMaterialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-materials");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /t-materials/:id : get the "id" tMaterial.
     *
     * @param id the id of the tMaterial to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tMaterial, or with status 404 (Not Found)
     */
    @GetMapping("/t-materials/{id}")
    @Timed
    public ResponseEntity<TMaterial> getTMaterial(@PathVariable Long id) {
        log.debug("REST request to get TMaterial : {}", id);
        Optional<TMaterial> tMaterial = tMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tMaterial);
    }

    /**
     * DELETE  /t-materials/:id : delete the "id" tMaterial.
     *
     * @param id the id of the tMaterial to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-materials/{id}")
    @Timed
    public ResponseEntity<Void> deleteTMaterial(@PathVariable Long id) {
        log.debug("REST request to delete TMaterial : {}", id);
        tMaterialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
