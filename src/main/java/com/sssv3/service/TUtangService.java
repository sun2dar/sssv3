package com.sssv3.service;

import com.sssv3.domain.TUtang;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TUtang.
 */
public interface TUtangService {

    /**
     * Save a tUtang.
     *
     * @param tUtang the entity to save
     * @return the persisted entity
     */
    TUtang save(TUtang tUtang);

    /**
     * Get all the tUtangs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TUtang> findAll(Pageable pageable);


    /**
     * Get the "id" tUtang.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TUtang> findOne(Long id);

    /**
     * Delete the "id" tUtang.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
