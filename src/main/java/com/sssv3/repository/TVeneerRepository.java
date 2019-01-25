package com.sssv3.repository;

import com.sssv3.domain.TVeneer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TVeneer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TVeneerRepository extends JpaRepository<TVeneer, Long> {

}
