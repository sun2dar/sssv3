package com.sssv3.service;

import com.sssv3.domain.MLogType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MLogType.
 */
public interface MLogTypeService {

    /**
     * Save a mLogType.
     *
     * @param mLogType the entity to save
     * @return the persisted entity
     */
    MLogType save(MLogType mLogType);

    /**
     * Get all the mLogTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MLogType> findAll(Pageable pageable);


    /**
     * Get the "id" mLogType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MLogType> findOne(Long id);

    /**
     * Delete the "id" mLogType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
