package com.sssv3.service.impl;

import com.sssv3.service.TransaksiService;
import com.sssv3.domain.Transaksi;
import com.sssv3.repository.TransaksiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Transaksi.
 */
@Service
@Transactional
public class TransaksiServiceImpl implements TransaksiService {

    private final Logger log = LoggerFactory.getLogger(TransaksiServiceImpl.class);

    private final TransaksiRepository transaksiRepository;

    public TransaksiServiceImpl(TransaksiRepository transaksiRepository) {
        this.transaksiRepository = transaksiRepository;
    }

    /**
     * Save a transaksi.
     *
     * @param transaksi the entity to save
     * @return the persisted entity
     */
    @Override
    @Transactional
    public Transaksi save(Transaksi transaksi) {
        log.debug("Request to save Transaksi : {}", transaksi);
        //return transaksiRepository.save(transaksi);
        Transaksi tr = transaksiRepository.save(transaksi);

        if(tr.getTipe().name() == "PEMBELIAN"){
            switch (tr.getCategory().name()){
                case("LOG") :
                    break;
                case("VENEER") :
                    break;
                                /*case("PLYWOOD") :
                    break;*/
                case("MATERIAL") :
                    break;
            }
        }
        else if(tr.getTipe().name() == "PENJUALAN"){
            switch (tr.getCategory().name()) {
                case ("LOG"):
                    break;
                case ("VENEER"):
                    break;
                case ("PLYWOOD"):
                    break;
                case ("MATERIAL"):
                    break;
            }
        }
        else if(tr.getTipe().name() == "PRODUKSI"){
                        /*case("LOG") :
                break;
            case("VENEER") :
                break;
            case("PLYWOOD") :
                break;
            case("MATERIAL") :
                break;*/
        }
        else if(tr.getTipe().name() == "STOCKOPNAME"){
            switch (tr.getCategory().name()) {
                case ("LOG"):
                    break;
                case ("VENEER"):
                    break;
                case ("PLYWOOD"):
                    break;
                case ("MATERIAL"):
                    break;
            }
        }

        return tr;
        //     return transaksiRepository.save(transaksi);
    }

    @Override
    public Page<Transaksi> findByInvoiceNoOrSupplierName(String invoicenoOrSupplierNama, Pageable pageable) {
        log.debug("Request to get Transaksis search by invoiceno or suppliername");
        return transaksiRepository.findByInvoicenoOrSuppliername(invoicenoOrSupplierNama, pageable);
    }

    /**
     * Get all the transaksis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Transaksi> findAll(Pageable pageable) {
        log.debug("Request to get all Transaksis");
        return transaksiRepository.findAll(pageable);
    }


    /**
     * Get one transaksi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Transaksi> findOne(Long id) {
        log.debug("Request to get Transaksi : {}", id);
        return transaksiRepository.findById(id);
    }

    /**
     * Delete the transaksi by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transaksi : {}", id);
        transaksiRepository.deleteById(id);
    }
}
