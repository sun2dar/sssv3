package com.sssv3.service;

import com.sssv3.domain.MPlywood;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MPlywood.
 */
public interface MPlywoodService {

    /**
     * Save a mPlywood.
     *
     * @param mPlywood the entity to save
     * @return the persisted entity
     */
    MPlywood save(MPlywood mPlywood);

    /**
     * Get all the mPlywoods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MPlywood> findAll(Pageable pageable);


    /**
     * Get the "id" mPlywood.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MPlywood> findOne(Long id);

    /**
     * Delete the "id" mPlywood.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
