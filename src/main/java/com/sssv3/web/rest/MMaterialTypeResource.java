package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MMaterialType;
import com.sssv3.service.MMaterialTypeService;
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
 * REST controller for managing MMaterialType.
 */
@RestController
@RequestMapping("/api")
public class MMaterialTypeResource {

    private final Logger log = LoggerFactory.getLogger(MMaterialTypeResource.class);

    private static final String ENTITY_NAME = "mMaterialType";

    private final MMaterialTypeService mMaterialTypeService;

    public MMaterialTypeResource(MMaterialTypeService mMaterialTypeService) {
        this.mMaterialTypeService = mMaterialTypeService;
    }

    /**
     * POST  /m-material-types : Create a new mMaterialType.
     *
     * @param mMaterialType the mMaterialType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mMaterialType, or with status 400 (Bad Request) if the mMaterialType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-material-types")
    @Timed
    public ResponseEntity<MMaterialType> createMMaterialType(@Valid @RequestBody MMaterialType mMaterialType) throws URISyntaxException {
        log.debug("REST request to save MMaterialType : {}", mMaterialType);
        if (mMaterialType.getId() != null) {
            throw new BadRequestAlertException("A new mMaterialType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MMaterialType result = mMaterialTypeService.save(mMaterialType);
        return ResponseEntity.created(new URI("/api/m-material-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-material-types : Updates an existing mMaterialType.
     *
     * @param mMaterialType the mMaterialType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mMaterialType,
     * or with status 400 (Bad Request) if the mMaterialType is not valid,
     * or with status 500 (Internal Server Error) if the mMaterialType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-material-types")
    @Timed
    public ResponseEntity<MMaterialType> updateMMaterialType(@Valid @RequestBody MMaterialType mMaterialType) throws URISyntaxException {
        log.debug("REST request to update MMaterialType : {}", mMaterialType);
        if (mMaterialType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MMaterialType result = mMaterialTypeService.save(mMaterialType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mMaterialType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-material-types : get all the mMaterialTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mMaterialTypes in body
     */
    @GetMapping("/m-material-types")
    @Timed
    public ResponseEntity<List<MMaterialType>> getAllMMaterialTypes(Pageable pageable) {
        log.debug("REST request to get a page of MMaterialTypes");
        Page<MMaterialType> page = mMaterialTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-material-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-material-types/:id : get the "id" mMaterialType.
     *
     * @param id the id of the mMaterialType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mMaterialType, or with status 404 (Not Found)
     */
    @GetMapping("/m-material-types/{id}")
    @Timed
    public ResponseEntity<MMaterialType> getMMaterialType(@PathVariable Long id) {
        log.debug("REST request to get MMaterialType : {}", id);
        Optional<MMaterialType> mMaterialType = mMaterialTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mMaterialType);
    }

    /**
     * DELETE  /m-material-types/:id : delete the "id" mMaterialType.
     *
     * @param id the id of the mMaterialType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-material-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteMMaterialType(@PathVariable Long id) {
        log.debug("REST request to delete MMaterialType : {}", id);
        mMaterialTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
