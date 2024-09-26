/**
 * Repository class
 */
package com.infy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.model.CustomerRecordDTO;


/**
 * find by customer id Repository
 * find by id Repository
 */
@Repository
public interface CustomerRewardRepository extends JpaRepository<CustomerRecordDTO, Long> {
	
	List<CustomerRecordDTO> findByCustomerId(int customerId);
	Optional<CustomerRecordDTO> findById(Long id);

}