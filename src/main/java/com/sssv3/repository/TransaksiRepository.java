package com.sssv3.repository;

import com.sssv3.domain.Transaksi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Transaksi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {

    @Query("select transaksi from Transaksi transaksi where transaksi.createdby.login = ?#{principal.username}")
    List<Transaksi> findByCreatedbyIsCurrentUser();

    @Query("select transaksi from Transaksi transaksi " +
                "where lower(transaksi.invoiceno) LIKE CONCAT('%', lower(:invoiceNoOrSupplierName) ,'%') " +
                "OR lower(transaksi.supplier.nama) = CONCAT('%', lower(:invoiceNoOrSupplierName) ,'%') ")
    Page<Transaksi> findByInvoicenoOrSuppliername (@Param("invoiceNoOrSupplierName") String invoiceNoOrSupplierName, Pageable pageable);

    Page<Transaksi> findByTipeAndAndCategory (String tipe, String category, Pageable pageable);
}
