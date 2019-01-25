package com.sssv3.service;

import com.sssv3.domain.MPlywoodGrade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MPlywoodGrade.
 */
public interface MPlywoodGradeService {

    /**
     * Save a mPlywoodGrade.
     *
     * @param mPlywoodGrade the entity to save
     * @return the persisted entity
     */
    MPlywoodGrade save(MPlywoodGrade mPlywoodGrade);

    /**
     * Get all the mPlywoodGrades.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MPlywoodGrade> findAll(Pageable pageable);


    /**
     * Get the "id" mPlywoodGrade.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MPlywoodGrade> findOne(Long id);

    /**
     * Delete the "id" mPlywoodGrade.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
