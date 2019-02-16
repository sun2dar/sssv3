package com.sssv3.service;

import com.sssv3.domain.Transaksi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Transaksi.
 */
public interface TransaksiService {

    /**
     * Save a transaksi.
     *
     * @param transaksi the entity to save
     * @return the persisted entity
     */
    Transaksi save(Transaksi transaksi);

    /**
     * Get all the transaksis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Transaksi> findAll(Pageable pageable);

    Page<Transaksi> findByInvoiceNoOrSupplierName(String invoicenoOrSupplierNama, Pageable pageable);

    Page<Transaksi> findByTipeAndCategory(String tipe, String category, Pageable pageable);
    /**
     * Get the "id" transaksi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Transaksi> findOne(Long id);

    /**
     * Delete the "id" transaksi.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
