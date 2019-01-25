package com.sssv3.repository;

import com.sssv3.domain.MPajak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MPajak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPajakRepository extends JpaRepository<MPajak, Long> {

}
