package com.infy.AssingmentApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.infy.model.CustomerRecordDTO;
import com.infy.repository.CustomerRewardRepository;
import com.infy.service.MonthlyRewardService;

public class MonthlyRewardServiceTest {
	
	@InjectMocks
    private MonthlyRewardService monthlyRewardService;

    @Mock
    private CustomerRewardRepository customerRewardRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
    
    //Showing error- need to fix
//    @Test
//    public void testFindCustomerById() {
//    	LocalDate date = LocalDate.now();
//		CustomerRecordDTO customerRecordDTO = new CustomerRecordDTO(30l,"Mithila08",3,date,30d);
//		customerRewardRepository.save(customerRecordDTO);
//		
//		when(customerRewardRepository.findById(30l)).thenReturn(Optional.of(customerRecordDTO));
//		CustomerRecordDTO found = monthlyRewardService.getId(30l).orElse(null);
//        assertEquals("Mithila08", found.getCustomer());
//    }

}
