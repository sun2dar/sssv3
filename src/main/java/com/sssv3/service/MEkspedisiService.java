package com.sssv3.service;

import com.sssv3.domain.MEkspedisi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MEkspedisi.
 */
public interface MEkspedisiService {

    /**
     * Save a mEkspedisi.
     *
     * @param mEkspedisi the entity to save
     * @return the persisted entity
     */
    MEkspedisi save(MEkspedisi mEkspedisi);

    /**
     * Get all the mEkspedisis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MEkspedisi> findAll(Pageable pageable);


    /**
     * Get the "id" mEkspedisi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MEkspedisi> findOne(Long id);

    /**
     * Delete the "id" mEkspedisi.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
