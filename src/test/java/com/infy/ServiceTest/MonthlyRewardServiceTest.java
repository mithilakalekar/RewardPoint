package com.infy.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.model.CustomerRecordDTO;
import com.infy.model.MonthlyRewardDTO;
import com.infy.repository.CustomerRewardRepository;
import com.infy.service.MonthlyRewardService;

@SpringBootTest
public class MonthlyRewardServiceTest {
	
	@InjectMocks
    private MonthlyRewardService monthlyRewardService;

    @Mock
    private CustomerRewardRepository customerRewardRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Test function insertRecord()
    @Test
    public void testInsertRecord_ValidData() {
        CustomerRecordDTO record = new CustomerRecordDTO();
        record.setId(100L);
        record.setCustomer("Mithila07");
        record.setBillAmount(150);
        record.setBillDate(LocalDate.of(2024, Month.JANUARY, 15));
        record.setCustomerId(1);

        monthlyRewardService.insertRecord(record);
        customerRewardRepository.save(record);
        verify(customerRewardRepository, times(2)).save(any(CustomerRecordDTO.class));
    }

    //Test function insertRecord()
    @Test
    public void testInsertRecord_InvalidData() {
        CustomerRecordDTO record = new CustomerRecordDTO();
        record.setCustomer(null); // Invalid customer name
        record.setBillAmount(150);
        record.setBillDate(LocalDate.of(2024, Month.JANUARY, 15));
        record.setCustomerId(1);

        // Call the method under test
        monthlyRewardService.insertRecord(record);

        verify(customerRewardRepository, never()).save(any(CustomerRecordDTO.class));
    }

    //Test function calculateRewardPoints()
    @Test
    public void testCalculateRewardPoints_Above100() {
        double rewardPoints = monthlyRewardService.calculateRewardPoints(150.0);
        assertEquals(150, rewardPoints); // 50 dollars over $100 gets 2 points each
    }

    //Test function calculateRewardPoints()
    @Test
    public void testCalculateRewardPoints_Between50And100() {
        double rewardPoints = monthlyRewardService.calculateRewardPoints(75d);
        assertEquals(25, rewardPoints); // $75 - $50 = $25 gets 1 point each
    }

    //Test function calculateRewardPoints()
    @Test
    public void testCalculateRewardPoints_Below50() {
        double rewardPoints = monthlyRewardService.calculateRewardPoints(30d);
        assertEquals(0, rewardPoints); // No points for less than $50
    }

    //Test function getMonthlyTotalRewardPoint()
    @Test
    public void testGetMonthlyTotalRewardPoint() {
        List<CustomerRecordDTO> records = new ArrayList<>();
        
        CustomerRecordDTO record1 = new CustomerRecordDTO();
        record1.setBillAmount(150);   //reward points= 150
        record1.setBillDate(LocalDate.of(2024, Month.JANUARY, 15));
        
        CustomerRecordDTO record2 = new CustomerRecordDTO();
        record2.setBillAmount(75);    //reward points= 25
        record2.setBillDate(LocalDate.of(2024, Month.JANUARY, 20));

        records.add(record1);
        records.add(record2);

        MonthlyRewardDTO result = monthlyRewardService.getMonthlyTotalRewardPoint(records);

        assertEquals("{JANUARY=175.0}", result.getMonthlyPoints().toString()); 
    }

    //Test function validateRecord()
    @Test
    public void testValidateRecord_ValidData() {
        CustomerRecordDTO validRecord = new CustomerRecordDTO();
        validRecord.setCustomer("Mithila09");
        validRecord.setCustomerId(1);
        validRecord.setBillAmount(150.0);

        boolean isValid = monthlyRewardService.validateRecord(validRecord);
        
        assertFalse(isValid); // Should return false for valid data
    }

    //Test function validateRecord()
    @Test
    public void testValidateRecord_InvalidData() {
        CustomerRecordDTO invalidRecord = new CustomerRecordDTO();
        
        boolean isValid = monthlyRewardService.validateRecord(invalidRecord);
        
        assertTrue(isValid); // Should return true for invalid data (null customer and zero values)
    }

}
