package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MMessage;
import com.sssv3.service.MMessageService;
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
 * REST controller for managing MMessage.
 */
@RestController
@RequestMapping("/api")
public class MMessageResource {

    private final Logger log = LoggerFactory.getLogger(MMessageResource.class);

    private static final String ENTITY_NAME = "mMessage";

    private final MMessageService mMessageService;

    public MMessageResource(MMessageService mMessageService) {
        this.mMessageService = mMessageService;
    }

    /**
     * POST  /m-messages : Create a new mMessage.
     *
     * @param mMessage the mMessage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mMessage, or with status 400 (Bad Request) if the mMessage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-messages")
    @Timed
    public ResponseEntity<MMessage> createMMessage(@RequestBody MMessage mMessage) throws URISyntaxException {
        log.debug("REST request to save MMessage : {}", mMessage);
        if (mMessage.getId() != null) {
            throw new BadRequestAlertException("A new mMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MMessage result = mMessageService.save(mMessage);
        return ResponseEntity.created(new URI("/api/m-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-messages : Updates an existing mMessage.
     *
     * @param mMessage the mMessage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mMessage,
     * or with status 400 (Bad Request) if the mMessage is not valid,
     * or with status 500 (Internal Server Error) if the mMessage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-messages")
    @Timed
    public ResponseEntity<MMessage> updateMMessage(@RequestBody MMessage mMessage) throws URISyntaxException {
        log.debug("REST request to update MMessage : {}", mMessage);
        if (mMessage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MMessage result = mMessageService.save(mMessage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mMessage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-messages : get all the mMessages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mMessages in body
     */
    @GetMapping("/m-messages")
    @Timed
    public ResponseEntity<List<MMessage>> getAllMMessages(Pageable pageable) {
        log.debug("REST request to get a page of MMessages");
        Page<MMessage> page = mMessageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-messages");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-messages/:id : get the "id" mMessage.
     *
     * @param id the id of the mMessage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mMessage, or with status 404 (Not Found)
     */
    @GetMapping("/m-messages/{id}")
    @Timed
    public ResponseEntity<MMessage> getMMessage(@PathVariable Long id) {
        log.debug("REST request to get MMessage : {}", id);
        Optional<MMessage> mMessage = mMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mMessage);
    }

    /**
     * DELETE  /m-messages/:id : delete the "id" mMessage.
     *
     * @param id the id of the mMessage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-messages/{id}")
    @Timed
    public ResponseEntity<Void> deleteMMessage(@PathVariable Long id) {
        log.debug("REST request to delete MMessage : {}", id);
        mMessageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
