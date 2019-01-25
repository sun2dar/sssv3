package com.sssv3.service;

import com.sssv3.domain.MSatuan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MSatuan.
 */
public interface MSatuanService {

    /**
     * Save a mSatuan.
     *
     * @param mSatuan the entity to save
     * @return the persisted entity
     */
    MSatuan save(MSatuan mSatuan);

    /**
     * Get all the mSatuans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MSatuan> findAll(Pageable pageable);


    /**
     * Get the "id" mSatuan.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MSatuan> findOne(Long id);

    /**
     * Delete the "id" mSatuan.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
