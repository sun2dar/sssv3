package com.sssv3.repository;

import com.sssv3.domain.MSatuan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MSatuan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MSatuanRepository extends JpaRepository<MSatuan, Long> {

}
