package com.sssv3.service.impl;

import com.sssv3.service.MSatuanService;
import com.sssv3.domain.MSatuan;
import com.sssv3.repository.MSatuanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MSatuan.
 */
@Service
@Transactional
public class MSatuanServiceImpl implements MSatuanService {

    private final Logger log = LoggerFactory.getLogger(MSatuanServiceImpl.class);

    private final MSatuanRepository mSatuanRepository;

    public MSatuanServiceImpl(MSatuanRepository mSatuanRepository) {
        this.mSatuanRepository = mSatuanRepository;
    }

    /**
     * Save a mSatuan.
     *
     * @param mSatuan the entity to save
     * @return the persisted entity
     */
    @Override
    public MSatuan save(MSatuan mSatuan) {
        log.debug("Request to save MSatuan : {}", mSatuan);
        return mSatuanRepository.save(mSatuan);
    }

    /**
     * Get all the mSatuans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MSatuan> findAll(Pageable pageable) {
        log.debug("Request to get all MSatuans");
        return mSatuanRepository.findAll(pageable);
    }


    /**
     * Get one mSatuan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MSatuan> findOne(Long id) {
        log.debug("Request to get MSatuan : {}", id);
        return mSatuanRepository.findById(id);
    }

    /**
     * Delete the mSatuan by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MSatuan : {}", id);
        mSatuanRepository.deleteById(id);
    }
}
