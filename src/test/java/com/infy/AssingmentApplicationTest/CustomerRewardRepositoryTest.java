package com.infy.AssingmentApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.infy.model.CustomerRecordDTO;
import com.infy.repository.CustomerRewardRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CustomerRewardRepositoryTest {
	
	@Autowired
	CustomerRewardRepository customerRewardRepository;
	
	@Test
    public void testSave() {
		LocalDate date = LocalDate.now();
		CustomerRecordDTO customerRecordDTO = new CustomerRecordDTO(10l,"Mithila02",2,date,30d);
		CustomerRecordDTO saved = customerRewardRepository.save(customerRecordDTO);
        assertNotNull(saved);
        assertEquals("Mithila02", saved.getCustomer());
    }
	
	@Test
    public void testGetCustomerId() {
		LocalDate date = LocalDate.now();
		CustomerRecordDTO customerRecordDTO = new CustomerRecordDTO(20l,"Mithila07",2,date,30d);
		CustomerRecordDTO saved = customerRewardRepository.save(customerRecordDTO);
		List<CustomerRecordDTO> fetchCustomerId = customerRewardRepository.findByCustomerId(customerRecordDTO.getCustomerId());
        assertNotNull(fetchCustomerId);
        assertEquals(2, saved.getCustomerId());
    }

}
