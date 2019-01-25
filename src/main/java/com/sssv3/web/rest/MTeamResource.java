package com.sssv3.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sssv3.domain.MTeam;
import com.sssv3.service.MTeamService;
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
 * REST controller for managing MTeam.
 */
@RestController
@RequestMapping("/api")
public class MTeamResource {

    private final Logger log = LoggerFactory.getLogger(MTeamResource.class);

    private static final String ENTITY_NAME = "mTeam";

    private final MTeamService mTeamService;

    public MTeamResource(MTeamService mTeamService) {
        this.mTeamService = mTeamService;
    }

    /**
     * POST  /m-teams : Create a new mTeam.
     *
     * @param mTeam the mTeam to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mTeam, or with status 400 (Bad Request) if the mTeam has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/m-teams")
    @Timed
    public ResponseEntity<MTeam> createMTeam(@Valid @RequestBody MTeam mTeam) throws URISyntaxException {
        log.debug("REST request to save MTeam : {}", mTeam);
        if (mTeam.getId() != null) {
            throw new BadRequestAlertException("A new mTeam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MTeam result = mTeamService.save(mTeam);
        return ResponseEntity.created(new URI("/api/m-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /m-teams : Updates an existing mTeam.
     *
     * @param mTeam the mTeam to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mTeam,
     * or with status 400 (Bad Request) if the mTeam is not valid,
     * or with status 500 (Internal Server Error) if the mTeam couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/m-teams")
    @Timed
    public ResponseEntity<MTeam> updateMTeam(@Valid @RequestBody MTeam mTeam) throws URISyntaxException {
        log.debug("REST request to update MTeam : {}", mTeam);
        if (mTeam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MTeam result = mTeamService.save(mTeam);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mTeam.getId().toString()))
            .body(result);
    }

    /**
     * GET  /m-teams : get all the mTeams.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mTeams in body
     */
    @GetMapping("/m-teams")
    @Timed
    public ResponseEntity<List<MTeam>> getAllMTeams(Pageable pageable) {
        log.debug("REST request to get a page of MTeams");
        Page<MTeam> page = mTeamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/m-teams");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /m-teams/:id : get the "id" mTeam.
     *
     * @param id the id of the mTeam to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mTeam, or with status 404 (Not Found)
     */
    @GetMapping("/m-teams/{id}")
    @Timed
    public ResponseEntity<MTeam> getMTeam(@PathVariable Long id) {
        log.debug("REST request to get MTeam : {}", id);
        Optional<MTeam> mTeam = mTeamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mTeam);
    }

    /**
     * DELETE  /m-teams/:id : delete the "id" mTeam.
     *
     * @param id the id of the mTeam to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/m-teams/{id}")
    @Timed
    public ResponseEntity<Void> deleteMTeam(@PathVariable Long id) {
        log.debug("REST request to delete MTeam : {}", id);
        mTeamService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
