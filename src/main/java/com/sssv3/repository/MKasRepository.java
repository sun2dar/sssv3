package com.sssv3.repository;

import com.sssv3.domain.MKas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MKas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MKasRepository extends JpaRepository<MKas, Long> {

}
