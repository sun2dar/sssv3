package com.sssv3.repository;

import com.sssv3.domain.TPlywood;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TPlywood entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TPlywoodRepository extends JpaRepository<TPlywood, Long> {

}
