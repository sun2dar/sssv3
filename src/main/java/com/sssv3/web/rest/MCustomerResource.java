package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MCustomer;
import com.sssv3.service.MCustomerService;
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
 * REST controller for managing MCustomer.
 */
@RestController
@RequestMapping("/api")
public class MCustomerResource {

    private final Logger log = LoggerFactory.getLogger(MCustomerResource.class);

    private static final String ENTITY_NAME = "mCustomer";

    private final MCustomerService mCustomerService;

    public MCustomerResource(MCustomerService mCustomerService) {
        this.mCustomerService = mCustomerService;
    }

    /**
     * POST  /m-customers : Create a new mCustomer.
     *
     * @param mCustomer the mCustomer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mCustomer, or with status 400 (Bad Request) if the mCustomer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-customers")
    @Timed
    public ResponseEntity<MCustomer> createMCustomer(@Valid @RequestBody MCustomer mCustomer) throws URISyntaxException {
        log.debug("REST request to save MCustomer : {}", mCustomer);
        if (mCustomer.getId() != null) {
            throw new BadRequestAlertException("A new mCustomer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MCustomer result = mCustomerService.save(mCustomer);
        return ResponseEntity.created(new URI("/api/m-customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-customers : Updates an existing mCustomer.
     *
     * @param mCustomer the mCustomer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mCustomer,
     * or with status 400 (Bad Request) if the mCustomer is not valid,
     * or with status 500 (Internal Server Error) if the mCustomer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-customers")
    @Timed
    public ResponseEntity<MCustomer> updateMCustomer(@Valid @RequestBody MCustomer mCustomer) throws URISyntaxException {
        log.debug("REST request to update MCustomer : {}", mCustomer);
        if (mCustomer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MCustomer result = mCustomerService.save(mCustomer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mCustomer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-customers : get all the mCustomers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mCustomers in body
     */
    @GetMapping("/m-customers")
    @Timed
    public ResponseEntity<List<MCustomer>> getAllMCustomers(Pageable pageable) {
        log.debug("REST request to get a page of MCustomers");
        Page<MCustomer> page = mCustomerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-customers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-customers/:id : get the "id" mCustomer.
     *
     * @param id the id of the mCustomer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mCustomer, or with status 404 (Not Found)
     */
    @GetMapping("/m-customers/{id}")
    @Timed
    public ResponseEntity<MCustomer> getMCustomer(@PathVariable Long id) {
        log.debug("REST request to get MCustomer : {}", id);
        Optional<MCustomer> mCustomer = mCustomerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mCustomer);
    }

    /**
     * DELETE  /m-customers/:id : delete the "id" mCustomer.
     *
     * @param id the id of the mCustomer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-customers/{id}")
    @Timed
    public ResponseEntity<Void> deleteMCustomer(@PathVariable Long id) {
        log.debug("REST request to delete MCustomer : {}", id);
        mCustomerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
