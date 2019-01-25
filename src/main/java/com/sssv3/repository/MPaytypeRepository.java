package com.sssv3.repository;

import com.sssv3.domain.MPaytype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MPaytype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPaytypeRepository extends JpaRepository<MPaytype, Long> {

}
