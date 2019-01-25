package com.sssv3.service.impl;

import com.sssv3.service.MEkspedisiService;
import com.sssv3.domain.MEkspedisi;
import com.sssv3.repository.MEkspedisiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MEkspedisi.
 */
@Service
@Transactional
public class MEkspedisiServiceImpl implements MEkspedisiService {

    private final Logger log = LoggerFactory.getLogger(MEkspedisiServiceImpl.class);

    private final MEkspedisiRepository mEkspedisiRepository;

    public MEkspedisiServiceImpl(MEkspedisiRepository mEkspedisiRepository) {
        this.mEkspedisiRepository = mEkspedisiRepository;
    }

    /**
     * Save a mEkspedisi.
     *
     * @param mEkspedisi the entity to save
     * @return the persisted entity
     */
    @Override
    public MEkspedisi save(MEkspedisi mEkspedisi) {
        log.debug("Request to save MEkspedisi : {}", mEkspedisi);
        return mEkspedisiRepository.save(mEkspedisi);
    }

    /**
     * Get all the mEkspedisis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MEkspedisi> findAll(Pageable pageable) {
        log.debug("Request to get all MEkspedisis");
        return mEkspedisiRepository.findAll(pageable);
    }


    /**
     * Get one mEkspedisi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MEkspedisi> findOne(Long id) {
        log.debug("Request to get MEkspedisi : {}", id);
        return mEkspedisiRepository.findById(id);
    }

    /**
     * Delete the mEkspedisi by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MEkspedisi : {}", id);
        mEkspedisiRepository.deleteById(id);
    }
}
