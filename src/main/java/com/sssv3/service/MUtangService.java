package com.sssv3.service;

import com.sssv3.domain.MUtang;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MUtang.
 */
public interface MUtangService {

    /**
     * Save a mUtang.
     *
     * @param mUtang the entity to save
     * @return the persisted entity
     */
    MUtang save(MUtang mUtang);

    /**
     * Get all the mUtangs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MUtang> findAll(Pageable pageable);


    /**
     * Get the "id" mUtang.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MUtang> findOne(Long id);

    /**
     * Delete the "id" mUtang.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
