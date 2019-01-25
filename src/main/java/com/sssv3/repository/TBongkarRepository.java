package com.sssv3.repository;

import com.sssv3.domain.TBongkar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TBongkar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBongkarRepository extends JpaRepository<TBongkar, Long> {

}
