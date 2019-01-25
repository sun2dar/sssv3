package com.sssv3.repository;

import com.sssv3.domain.TUtang;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TUtang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TUtangRepository extends JpaRepository<TUtang, Long> {

}
