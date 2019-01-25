package com.sssv3.repository;

import com.sssv3.domain.Transaksi;
import org.springframework.data.jpa.repository.*;
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

}
