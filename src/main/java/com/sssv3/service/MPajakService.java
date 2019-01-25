package com.sssv3.service;

import com.sssv3.domain.MPajak;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MPajak.
 */
public interface MPajakService {

    /**
     * Save a mPajak.
     *
     * @param mPajak the entity to save
     * @return the persisted entity
     */
    MPajak save(MPajak mPajak);

    /**
     * Get all the mPajaks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MPajak> findAll(Pageable pageable);


    /**
     * Get the "id" mPajak.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MPajak> findOne(Long id);

    /**
     * Delete the "id" mPajak.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
