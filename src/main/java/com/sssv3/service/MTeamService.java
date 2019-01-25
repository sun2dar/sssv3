package com.sssv3.service;

import com.sssv3.domain.MTeam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MTeam.
 */
public interface MTeamService {

    /**
     * Save a mTeam.
     *
     * @param mTeam the entity to save
     * @return the persisted entity
     */
    MTeam save(MTeam mTeam);

    /**
     * Get all the mTeams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MTeam> findAll(Pageable pageable);


    /**
     * Get the "id" mTeam.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MTeam> findOne(Long id);

    /**
     * Delete the "id" mTeam.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
