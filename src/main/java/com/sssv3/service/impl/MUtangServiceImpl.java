package com.sssv3.service.impl;

import com.sssv3.service.MUtangService;
import com.sssv3.domain.MUtang;
import com.sssv3.repository.MUtangRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MUtang.
 */
@Service
@Transactional
public class MUtangServiceImpl implements MUtangService {

    private final Logger log = LoggerFactory.getLogger(MUtangServiceImpl.class);

    private final MUtangRepository mUtangRepository;

    public MUtangServiceImpl(MUtangRepository mUtangRepository) {
        this.mUtangRepository = mUtangRepository;
    }

    /**
     * Save a mUtang.
     *
     * @param mUtang the entity to save
     * @return the persisted entity
     */
    @Override
    public MUtang save(MUtang mUtang) {
        log.debug("Request to save MUtang : {}", mUtang);
        return mUtangRepository.save(mUtang);
    }

    /**
     * Get all the mUtangs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MUtang> findAll(Pageable pageable) {
        log.debug("Request to get all MUtangs");
        return mUtangRepository.findAll(pageable);
    }


    /**
     * Get one mUtang by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MUtang> findOne(Long id) {
        log.debug("Request to get MUtang : {}", id);
        return mUtangRepository.findById(id);
    }

    /**
     * Delete the mUtang by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MUtang : {}", id);
        mUtangRepository.deleteById(id);
    }
}
