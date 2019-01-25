package com.sssv3.repository;

import com.sssv3.domain.MUtang;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MUtang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MUtangRepository extends JpaRepository<MUtang, Long> {

}
