package com.sssv3.service;

import com.sssv3.domain.MLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MLog.
 */
public interface MLogService {

    /**
     * Save a mLog.
     *
     * @param mLog the entity to save
     * @return the persisted entity
     */
    MLog save(MLog mLog);

    /**
     * Get all the mLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MLog> findAll(Pageable pageable);

    Page<MLog> findByNama(String nama, Pageable pageable);

    /**
     * Get the "id" mLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MLog> findOne(Long id);

    /**
     * Delete the "id" mLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
