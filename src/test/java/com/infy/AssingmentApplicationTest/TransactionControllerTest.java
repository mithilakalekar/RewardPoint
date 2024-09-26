package com.infy.AssingmentApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.infy.controller.TransactionController;
import com.infy.model.CustomerRecordDTO;
import com.infy.model.MonthlyRewardDTO;
import com.infy.repository.CustomerRewardRepository;
import com.infy.service.MonthlyRewardService;

@SpringBootTest
public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private MonthlyRewardService monthlyRewards;

    @Mock
    private CustomerRewardRepository customerRewardRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testGetAllRecords_NoRecords() throws Exception {
        when(customerRewardRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/transaction/getAllRecords")) 
                .andExpect(status().isNoContent()); 
    }

    @Test
    public void testGetAllRecords_WithRecords() throws Exception {
        List<CustomerRecordDTO> records = Arrays.asList(new CustomerRecordDTO(30L, "Mithila08", 3, LocalDate.of(2024, Month.JANUARY, 15), 190d));
        when(customerRewardRepository.findAll()).thenReturn(records);

        mockMvc.perform(get("/transaction/getAllRecords")) 
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1))) 
                .andExpect(jsonPath("$[0].customer").value("Mithila08")); 
    }

    @Test
    public void testInsertRecord() throws Exception {
        CustomerRecordDTO record = new CustomerRecordDTO(0L, "Mithila03", 3, LocalDate.of(2024, Month.OCTOBER, 23), 120d);
        
        mockMvc.perform(post("/transaction/insertRecord") 
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\":0,\"customerName\":\"Mithila03\", \"customerId\":3, \"billDate\":\"2024-10-23\", \"billAmount\":120 }"))
                .andExpect(status().isOk())
                .andExpect(content().string("Transaction procesed successfully!"));

        verify(monthlyRewards, times(1)).insertRecord(any(CustomerRecordDTO.class));
    }

    @Test
    public void testCalculateMonthlyRewards() throws Exception {
        List<CustomerRecordDTO> records = Arrays.asList(new CustomerRecordDTO(30L,"Mithila08",3,LocalDate.of(2024, Month.JANUARY, 15),130d));
        
        when(customerRewardRepository.findAll()).thenReturn(records);
        MonthlyRewardDTO rewards = new MonthlyRewardDTO();
        when(monthlyRewards.getMonthlyTotalRewardPoint(records)).thenReturn(rewards);

        mockMvc.perform(post("/transaction/calculateMonthlyRewards")) 
                .andExpect(status().isOk());

        verify(monthlyRewards, times(1)).getMonthlyTotalRewardPoint(records);
    }

    @Test
    public void testCalculateRewardsByCustomer() throws Exception {
        List<CustomerRecordDTO> records = Arrays.asList(new CustomerRecordDTO(30L,"Mithila08",1,LocalDate.of(2024, Month.JANUARY, 15),150d));
        
        int customerId = 1; 
        when(customerRewardRepository.findByCustomerId(customerId)).thenReturn(records);
        
        MonthlyRewardDTO rewards = new MonthlyRewardDTO();
        when(monthlyRewards.getMonthlyTotalRewardPoint(records)).thenReturn(rewards);

        mockMvc.perform(post("/transaction/totalRewardsByCustomer?customerId=" + customerId)) 
                .andExpect(status().isOk());

        verify(monthlyRewards, times(1)).getCustomerTotalRewardPoint(records);
    }
}