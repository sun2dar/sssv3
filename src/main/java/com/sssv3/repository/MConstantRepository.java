package com.sssv3.repository;

import com.sssv3.domain.MConstant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MConstant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MConstantRepository extends JpaRepository<MConstant, Long> {

}
