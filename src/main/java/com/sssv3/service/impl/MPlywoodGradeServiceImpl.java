package com.sssv3.service.impl;

import com.sssv3.service.MPlywoodGradeService;
import com.sssv3.domain.MPlywoodGrade;
import com.sssv3.repository.MPlywoodGradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MPlywoodGrade.
 */
@Service
@Transactional
public class MPlywoodGradeServiceImpl implements MPlywoodGradeService {

    private final Logger log = LoggerFactory.getLogger(MPlywoodGradeServiceImpl.class);

    private final MPlywoodGradeRepository mPlywoodGradeRepository;

    public MPlywoodGradeServiceImpl(MPlywoodGradeRepository mPlywoodGradeRepository) {
        this.mPlywoodGradeRepository = mPlywoodGradeRepository;
    }

    /**
     * Save a mPlywoodGrade.
     *
     * @param mPlywoodGrade the entity to save
     * @return the persisted entity
     */
    @Override
    public MPlywoodGrade save(MPlywoodGrade mPlywoodGrade) {
        log.debug("Request to save MPlywoodGrade : {}", mPlywoodGrade);
        return mPlywoodGradeRepository.save(mPlywoodGrade);
    }

    /**
     * Get all the mPlywoodGrades.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MPlywoodGrade> findAll(Pageable pageable) {
        log.debug("Request to get all MPlywoodGrades");
        return mPlywoodGradeRepository.findAll(pageable);
    }


    /**
     * Get one mPlywoodGrade by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MPlywoodGrade> findOne(Long id) {
        log.debug("Request to get MPlywoodGrade : {}", id);
        return mPlywoodGradeRepository.findById(id);
    }

    /**
     * Delete the mPlywoodGrade by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MPlywoodGrade : {}", id);
        mPlywoodGradeRepository.deleteById(id);
    }
}
