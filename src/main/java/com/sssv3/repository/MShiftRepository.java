package com.sssv3.repository;

import com.sssv3.domain.MShift;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MShift entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MShiftRepository extends JpaRepository<MShift, Long> {

}
