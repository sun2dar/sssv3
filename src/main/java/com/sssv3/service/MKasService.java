package com.sssv3.service;

import com.sssv3.domain.MKas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MKas.
 */
public interface MKasService {

    /**
     * Save a mKas.
     *
     * @param mKas the entity to save
     * @return the persisted entity
     */
    MKas save(MKas mKas);

    /**
     * Get all the mKas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MKas> findAll(Pageable pageable);


    /**
     * Get the "id" mKas.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MKas> findOne(Long id);

    /**
     * Delete the "id" mKas.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
