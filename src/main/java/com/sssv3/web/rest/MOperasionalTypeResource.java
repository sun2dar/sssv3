package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MOperasionalType;
import com.sssv3.service.MOperasionalTypeService;
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
 * REST controller for managing MOperasionalType.
 */
@RestController
@RequestMapping("/api")
public class MOperasionalTypeResource {

    private final Logger log = LoggerFactory.getLogger(MOperasionalTypeResource.class);

    private static final String ENTITY_NAME = "mOperasionalType";

    private final MOperasionalTypeService mOperasionalTypeService;

    public MOperasionalTypeResource(MOperasionalTypeService mOperasionalTypeService) {
        this.mOperasionalTypeService = mOperasionalTypeService;
    }

    /**
     * POST  /m-operasional-types : Create a new mOperasionalType.
     *
     * @param mOperasionalType the mOperasionalType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mOperasionalType, or with status 400 (Bad Request) if the mOperasionalType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-operasional-types")
    @Timed
    public ResponseEntity<MOperasionalType> createMOperasionalType(@Valid @RequestBody MOperasionalType mOperasionalType) throws URISyntaxException {
        log.debug("REST request to save MOperasionalType : {}", mOperasionalType);
        if (mOperasionalType.getId() != null) {
            throw new BadRequestAlertException("A new mOperasionalType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MOperasionalType result = mOperasionalTypeService.save(mOperasionalType);
        return ResponseEntity.created(new URI("/api/m-operasional-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-operasional-types : Updates an existing mOperasionalType.
     *
     * @param mOperasionalType the mOperasionalType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mOperasionalType,
     * or with status 400 (Bad Request) if the mOperasionalType is not valid,
     * or with status 500 (Internal Server Error) if the mOperasionalType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-operasional-types")
    @Timed
    public ResponseEntity<MOperasionalType> updateMOperasionalType(@Valid @RequestBody MOperasionalType mOperasionalType) throws URISyntaxException {
        log.debug("REST request to update MOperasionalType : {}", mOperasionalType);
        if (mOperasionalType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MOperasionalType result = mOperasionalTypeService.save(mOperasionalType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mOperasionalType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-operasional-types : get all the mOperasionalTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mOperasionalTypes in body
     */
    @GetMapping("/m-operasional-types")
    @Timed
    public ResponseEntity<List<MOperasionalType>> getAllMOperasionalTypes(Pageable pageable) {
        log.debug("REST request to get a page of MOperasionalTypes");
        Page<MOperasionalType> page = mOperasionalTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-operasional-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-operasional-types/paging")
    @Timed
    public ResponseEntity<Page<MOperasionalType>> getAllMOperasionalTypesWithPaging(Pageable pageable) {
        log.debug("REST request to get a page of MOperasionalTypes");
        Page<MOperasionalType> page = mOperasionalTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-operasional-types");
        return ResponseEntity.ok().headers(headers).body(page);
    }

    /**
     * GET  /m-operasional-types/:id : get the "id" mOperasionalType.
     *
     * @param id the id of the mOperasionalType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mOperasionalType, or with status 404 (Not Found)
     */
    @GetMapping("/m-operasional-types/{id}")
    @Timed
    public ResponseEntity<MOperasionalType> getMOperasionalType(@PathVariable Long id) {
        log.debug("REST request to get MOperasionalType : {}", id);
        Optional<MOperasionalType> mOperasionalType = mOperasionalTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mOperasionalType);
    }

    /**
     * DELETE  /m-operasional-types/:id : delete the "id" mOperasionalType.
     *
     * @param id the id of the mOperasionalType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-operasional-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteMOperasionalType(@PathVariable Long id) {
        log.debug("REST request to delete MOperasionalType : {}", id);
        mOperasionalTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
