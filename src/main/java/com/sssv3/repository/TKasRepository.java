package com.sssv3.repository;

import com.sssv3.domain.TKas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TKas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TKasRepository extends JpaRepository<TKas, Long> {

}
