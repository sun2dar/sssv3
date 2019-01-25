package com.sssv3.service;

import com.sssv3.domain.TPlywood;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TPlywood.
 */
public interface TPlywoodService {

    /**
     * Save a tPlywood.
     *
     * @param tPlywood the entity to save
     * @return the persisted entity
     */
    TPlywood save(TPlywood tPlywood);

    /**
     * Get all the tPlywoods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TPlywood> findAll(Pageable pageable);


    /**
     * Get the "id" tPlywood.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TPlywood> findOne(Long id);

    /**
     * Delete the "id" tPlywood.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
