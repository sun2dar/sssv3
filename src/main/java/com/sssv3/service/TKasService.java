package com.sssv3.service;

import com.sssv3.domain.TKas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TKas.
 */
public interface TKasService {

    /**
     * Save a tKas.
     *
     * @param tKas the entity to save
     * @return the persisted entity
     */
    TKas save(TKas tKas);

    /**
     * Get all the tKas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TKas> findAll(Pageable pageable);


    /**
     * Get the "id" tKas.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TKas> findOne(Long id);

    /**
     * Delete the "id" tKas.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
