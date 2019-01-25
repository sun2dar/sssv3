package com.sssv3.service;

import com.sssv3.domain.TVeneer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TVeneer.
 */
public interface TVeneerService {

    /**
     * Save a tVeneer.
     *
     * @param tVeneer the entity to save
     * @return the persisted entity
     */
    TVeneer save(TVeneer tVeneer);

    /**
     * Get all the tVeneers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TVeneer> findAll(Pageable pageable);


    /**
     * Get the "id" tVeneer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TVeneer> findOne(Long id);

    /**
     * Delete the "id" tVeneer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
