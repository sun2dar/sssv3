package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MSatuan;
import com.sssv3.service.MSatuanService;
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
 * REST controller for managing MSatuan.
 */
@RestController
@RequestMapping("/api")
public class MSatuanResource {

    private final Logger log = LoggerFactory.getLogger(MSatuanResource.class);

    private static final String ENTITY_NAME = "mSatuan";

    private final MSatuanService mSatuanService;

    public MSatuanResource(MSatuanService mSatuanService) {
        this.mSatuanService = mSatuanService;
    }

    /**
     * POST  /m-satuans : Create a new mSatuan.
     *
     * @param mSatuan the mSatuan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mSatuan, or with status 400 (Bad Request) if the mSatuan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-satuans")
    @Timed
    public ResponseEntity<MSatuan> createMSatuan(@Valid @RequestBody MSatuan mSatuan) throws URISyntaxException {
        log.debug("REST request to save MSatuan : {}", mSatuan);
        if (mSatuan.getId() != null) {
            throw new BadRequestAlertException("A new mSatuan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MSatuan result = mSatuanService.save(mSatuan);
        return ResponseEntity.created(new URI("/api/m-satuans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-satuans : Updates an existing mSatuan.
     *
     * @param mSatuan the mSatuan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mSatuan,
     * or with status 400 (Bad Request) if the mSatuan is not valid,
     * or with status 500 (Internal Server Error) if the mSatuan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-satuans")
    @Timed
    public ResponseEntity<MSatuan> updateMSatuan(@Valid @RequestBody MSatuan mSatuan) throws URISyntaxException {
        log.debug("REST request to update MSatuan : {}", mSatuan);
        if (mSatuan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MSatuan result = mSatuanService.save(mSatuan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mSatuan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-satuans : get all the mSatuans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mSatuans in body
     */
    @GetMapping("/m-satuans")
    @Timed
    public ResponseEntity<List<MSatuan>> getAllMSatuans(Pageable pageable) {
        log.debug("REST request to get a page of MSatuans");
        Page<MSatuan> page = mSatuanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-satuans");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-satuans/:id : get the "id" mSatuan.
     *
     * @param id the id of the mSatuan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mSatuan, or with status 404 (Not Found)
     */
    @GetMapping("/m-satuans/{id}")
    @Timed
    public ResponseEntity<MSatuan> getMSatuan(@PathVariable Long id) {
        log.debug("REST request to get MSatuan : {}", id);
        Optional<MSatuan> mSatuan = mSatuanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mSatuan);
    }

    /**
     * DELETE  /m-satuans/:id : delete the "id" mSatuan.
     *
     * @param id the id of the mSatuan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-satuans/{id}")
    @Timed
    public ResponseEntity<Void> deleteMSatuan(@PathVariable Long id) {
        log.debug("REST request to delete MSatuan : {}", id);
        mSatuanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
