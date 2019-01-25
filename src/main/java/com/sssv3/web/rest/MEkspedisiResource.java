package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MEkspedisi;
import com.sssv3.service.MEkspedisiService;
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
 * REST controller for managing MEkspedisi.
 */
@RestController
@RequestMapping("/api")
public class MEkspedisiResource {

    private final Logger log = LoggerFactory.getLogger(MEkspedisiResource.class);

    private static final String ENTITY_NAME = "mEkspedisi";

    private final MEkspedisiService mEkspedisiService;

    public MEkspedisiResource(MEkspedisiService mEkspedisiService) {
        this.mEkspedisiService = mEkspedisiService;
    }

    /**
     * POST  /m-ekspedisis : Create a new mEkspedisi.
     *
     * @param mEkspedisi the mEkspedisi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mEkspedisi, or with status 400 (Bad Request) if the mEkspedisi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-ekspedisis")
    @Timed
    public ResponseEntity<MEkspedisi> createMEkspedisi(@Valid @RequestBody MEkspedisi mEkspedisi) throws URISyntaxException {
        log.debug("REST request to save MEkspedisi : {}", mEkspedisi);
        if (mEkspedisi.getId() != null) {
            throw new BadRequestAlertException("A new mEkspedisi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MEkspedisi result = mEkspedisiService.save(mEkspedisi);
        return ResponseEntity.created(new URI("/api/m-ekspedisis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-ekspedisis : Updates an existing mEkspedisi.
     *
     * @param mEkspedisi the mEkspedisi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mEkspedisi,
     * or with status 400 (Bad Request) if the mEkspedisi is not valid,
     * or with status 500 (Internal Server Error) if the mEkspedisi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-ekspedisis")
    @Timed
    public ResponseEntity<MEkspedisi> updateMEkspedisi(@Valid @RequestBody MEkspedisi mEkspedisi) throws URISyntaxException {
        log.debug("REST request to update MEkspedisi : {}", mEkspedisi);
        if (mEkspedisi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MEkspedisi result = mEkspedisiService.save(mEkspedisi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mEkspedisi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-ekspedisis : get all the mEkspedisis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mEkspedisis in body
     */
    @GetMapping("/m-ekspedisis")
    @Timed
    public ResponseEntity<List<MEkspedisi>> getAllMEkspedisis(Pageable pageable) {
        log.debug("REST request to get a page of MEkspedisis");
        Page<MEkspedisi> page = mEkspedisiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-ekspedisis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-ekspedisis/:id : get the "id" mEkspedisi.
     *
     * @param id the id of the mEkspedisi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mEkspedisi, or with status 404 (Not Found)
     */
    @GetMapping("/m-ekspedisis/{id}")
    @Timed
    public ResponseEntity<MEkspedisi> getMEkspedisi(@PathVariable Long id) {
        log.debug("REST request to get MEkspedisi : {}", id);
        Optional<MEkspedisi> mEkspedisi = mEkspedisiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mEkspedisi);
    }

    /**
     * DELETE  /m-ekspedisis/:id : delete the "id" mEkspedisi.
     *
     * @param id the id of the mEkspedisi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-ekspedisis/{id}")
    @Timed
    public ResponseEntity<Void> deleteMEkspedisi(@PathVariable Long id) {
        log.debug("REST request to delete MEkspedisi : {}", id);
        mEkspedisiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
