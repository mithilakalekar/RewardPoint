package com.infy.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;
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
	
	//Test Save repository function
	@Test
    public void testSave() {
		CustomerRecordDTO customerRecordDTO = new CustomerRecordDTO(10l,"Mithila02",2,LocalDate.of(2024, Month.JANUARY, 15),30d);
		CustomerRecordDTO saved = customerRewardRepository.save(customerRecordDTO);
        assertNotNull(saved);
        assertEquals("Mithila02", saved.getCustomer());
    }
	
	//Test findByCustomerId repository function
	@Test
    public void testGetCustomerId() {
		CustomerRecordDTO customerRecordDTO = new CustomerRecordDTO(20l,"Mithila07",2,LocalDate.of(2024, Month.JANUARY, 15),30d);
		CustomerRecordDTO saved = customerRewardRepository.save(customerRecordDTO);
		List<CustomerRecordDTO> fetchCustomerId = customerRewardRepository.findByCustomerId(customerRecordDTO.getCustomerId());
        assertNotNull(fetchCustomerId);
        assertEquals(2, saved.getCustomerId());
    }

}
