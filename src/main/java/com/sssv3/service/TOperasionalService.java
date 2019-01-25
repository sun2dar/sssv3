package com.sssv3.service;

import com.sssv3.domain.TOperasional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TOperasional.
 */
public interface TOperasionalService {

    /**
     * Save a tOperasional.
     *
     * @param tOperasional the entity to save
     * @return the persisted entity
     */
    TOperasional save(TOperasional tOperasional);

    /**
     * Get all the tOperasionals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TOperasional> findAll(Pageable pageable);


    /**
     * Get the "id" tOperasional.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TOperasional> findOne(Long id);

    /**
     * Delete the "id" tOperasional.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
