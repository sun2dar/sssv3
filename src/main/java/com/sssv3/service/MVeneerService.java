package com.sssv3.service;

import com.sssv3.domain.MVeneer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MVeneer.
 */
public interface MVeneerService {

    /**
     * Save a mVeneer.
     *
     * @param mVeneer the entity to save
     * @return the persisted entity
     */
    MVeneer save(MVeneer mVeneer);

    /**
     * Get all the mVeneers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MVeneer> findAll(Pageable pageable);


    /**
     * Get the "id" mVeneer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MVeneer> findOne(Long id);

    /**
     * Delete the "id" mVeneer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
