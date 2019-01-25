package com.sssv3.repository;

import com.sssv3.domain.MCustomer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MCustomer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MCustomerRepository extends JpaRepository<MCustomer, Long> {

}
