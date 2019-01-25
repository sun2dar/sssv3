package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.Transaksi;
import com.sssv3.service.TransaksiService;
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
 * REST controller for managing Transaksi.
 */
@RestController
@RequestMapping("/api")
public class TransaksiResource {

    private final Logger log = LoggerFactory.getLogger(TransaksiResource.class);

    private static final String ENTITY_NAME = "transaksi";

    private final TransaksiService transaksiService;

    public TransaksiResource(TransaksiService transaksiService) {
        this.transaksiService = transaksiService;
    }

    /**
     * POST  /transaksis : Create a new transaksi.
     *
     * @param transaksi the transaksi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transaksi, or with status 400 (Bad Request) if the transaksi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transaksis")
    @Timed
    public ResponseEntity<Transaksi> createTransaksi(@Valid @RequestBody Transaksi transaksi) throws URISyntaxException {
        log.debug("REST request to save Transaksi : {}", transaksi);
        if (transaksi.getId() != null) {
            throw new BadRequestAlertException("A new transaksi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transaksi result = transaksiService.save(transaksi);
        return ResponseEntity.created(new URI("/api/transaksis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transaksis : Updates an existing transaksi.
     *
     * @param transaksi the transaksi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transaksi,
     * or with status 400 (Bad Request) if the transaksi is not valid,
     * or with status 500 (Internal Server Error) if the transaksi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transaksis")
    @Timed
    public ResponseEntity<Transaksi> updateTransaksi(@Valid @RequestBody Transaksi transaksi) throws URISyntaxException {
        log.debug("REST request to update Transaksi : {}", transaksi);
        if (transaksi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transaksi result = transaksiService.save(transaksi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transaksi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transaksis : get all the transaksis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of transaksis in body
     */
    @GetMapping("/transaksis")
    @Timed
    public ResponseEntity<List<Transaksi>> getAllTransaksis(Pageable pageable) {
        log.debug("REST request to get a page of Transaksis");
        Page<Transaksi> page = transaksiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/transaksis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /transaksis/:id : get the "id" transaksi.
     *
     * @param id the id of the transaksi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transaksi, or with status 404 (Not Found)
     */
    @GetMapping("/transaksis/{id}")
    @Timed
    public ResponseEntity<Transaksi> getTransaksi(@PathVariable Long id) {
        log.debug("REST request to get Transaksi : {}", id);
        Optional<Transaksi> transaksi = transaksiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transaksi);
    }

    /**
     * DELETE  /transaksis/:id : delete the "id" transaksi.
     *
     * @param id the id of the transaksi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transaksis/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransaksi(@PathVariable Long id) {
        log.debug("REST request to delete Transaksi : {}", id);
        transaksiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
