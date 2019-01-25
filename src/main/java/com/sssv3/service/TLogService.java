package com.sssv3.service;

import com.sssv3.domain.TLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TLog.
 */
public interface TLogService {

    /**
     * Save a tLog.
     *
     * @param tLog the entity to save
     * @return the persisted entity
     */
    TLog save(TLog tLog);

    /**
     * Get all the tLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TLog> findAll(Pageable pageable);


    /**
     * Get the "id" tLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TLog> findOne(Long id);

    /**
     * Delete the "id" tLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
