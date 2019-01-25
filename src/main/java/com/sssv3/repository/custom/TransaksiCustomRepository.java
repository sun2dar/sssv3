package com.sssv3.repository.custom;

import com.sssv3.domain.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaksiCustomRepository extends JpaRepository<Transaksi, Long> {

    /*@Query("SELECT c FROM Transaksi where supplier <> null and  ")
    List<Transaksi> findByTransaksiType();*/

}
