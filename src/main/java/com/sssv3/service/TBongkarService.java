package com.sssv3.service;

import com.sssv3.domain.TBongkar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TBongkar.
 */
public interface TBongkarService {

    /**
     * Save a tBongkar.
     *
     * @param tBongkar the entity to save
     * @return the persisted entity
     */
    TBongkar save(TBongkar tBongkar);

    /**
     * Get all the tBongkars.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TBongkar> findAll(Pageable pageable);


    /**
     * Get the "id" tBongkar.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TBongkar> findOne(Long id);

    /**
     * Delete the "id" tBongkar.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
