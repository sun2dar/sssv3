package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MMaterial;
import com.sssv3.service.MMaterialService;
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
 * REST controller for managing MMaterial.
 */
@RestController
@RequestMapping("/api")
public class MMaterialResource {

    private final Logger log = LoggerFactory.getLogger(MMaterialResource.class);

    private static final String ENTITY_NAME = "mMaterial";

    private final MMaterialService mMaterialService;

    public MMaterialResource(MMaterialService mMaterialService) {
        this.mMaterialService = mMaterialService;
    }

    /**
     * POST  /m-materials : Create a new mMaterial.
     *
     * @param mMaterial the mMaterial to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mMaterial, or with status 400 (Bad Request) if the mMaterial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-materials")
    @Timed
    public ResponseEntity<MMaterial> createMMaterial(@Valid @RequestBody MMaterial mMaterial) throws URISyntaxException {
        log.debug("REST request to save MMaterial : {}", mMaterial);
        if (mMaterial.getId() != null) {
            throw new BadRequestAlertException("A new mMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MMaterial result = mMaterialService.save(mMaterial);
        return ResponseEntity.created(new URI("/api/m-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-materials : Updates an existing mMaterial.
     *
     * @param mMaterial the mMaterial to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mMaterial,
     * or with status 400 (Bad Request) if the mMaterial is not valid,
     * or with status 500 (Internal Server Error) if the mMaterial couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-materials")
    @Timed
    public ResponseEntity<MMaterial> updateMMaterial(@Valid @RequestBody MMaterial mMaterial) throws URISyntaxException {
        log.debug("REST request to update MMaterial : {}", mMaterial);
        if (mMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MMaterial result = mMaterialService.save(mMaterial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mMaterial.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-materials : get all the mMaterials.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mMaterials in body
     */
    @GetMapping("/m-materials")
    @Timed
    public ResponseEntity<List<MMaterial>> getAllMMaterials(Pageable pageable) {
        log.debug("REST request to get a page of MMaterials");
        Page<MMaterial> page = mMaterialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-materials");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-materials/:id : get the "id" mMaterial.
     *
     * @param id the id of the mMaterial to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mMaterial, or with status 404 (Not Found)
     */
    @GetMapping("/m-materials/{id}")
    @Timed
    public ResponseEntity<MMaterial> getMMaterial(@PathVariable Long id) {
        log.debug("REST request to get MMaterial : {}", id);
        Optional<MMaterial> mMaterial = mMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mMaterial);
    }

    /**
     * DELETE  /m-materials/:id : delete the "id" mMaterial.
     *
     * @param id the id of the mMaterial to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-materials/{id}")
    @Timed
    public ResponseEntity<Void> deleteMMaterial(@PathVariable Long id) {
        log.debug("REST request to delete MMaterial : {}", id);
        mMaterialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
