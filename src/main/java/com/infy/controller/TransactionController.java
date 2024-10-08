package com.infy.controller;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.model.CustomerRecordDTO;
import com.infy.model.MonthlyRewardDTO;
import com.infy.repository.CustomerRewardRepository;
import com.infy.service.MonthlyRewardService;

/**
 * Transaction Controller class for REST API
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private static final Logger logData = LoggerFactory.getLogger(TransactionController.class);
    
    @Autowired
    private MonthlyRewardService monthlyRewards;

    @Autowired
    private CustomerRewardRepository customerRewardRepository;

    // API to show all records from Database
    @GetMapping("/getAllRecords")
    public ResponseEntity<List<CustomerRecordDTO>> getAllRecords() {
        try {
            List<CustomerRecordDTO> customerRecordDto = new ArrayList<>();
            customerRewardRepository.findAll().forEach(customerRecordDto::add);
            
            if (customerRecordDto.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customerRecordDto, HttpStatus.OK);
        } catch (Exception e) {
            logData.error("Error fetching all records", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

 // API to show all records for respective customer
    @GetMapping("/getRecordsByCustomerId")
    public ResponseEntity<List<CustomerRecordDTO>> getRecordsByCustomerId(@RequestParam int customerId) {
        if (customerId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Changed to BAD_REQUEST for invalid customerId
        }

        try {
            List<CustomerRecordDTO> customerRecordDto = customerRewardRepository.findByCustomerId(customerId);
            List<CustomerRecordDTO> filterList = customerRecordDto.stream()
            		.filter(record->record.getCustomerId()==customerId)
            		.collect(Collectors.toList());
            if (filterList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(filterList, HttpStatus.OK);
        } catch (Exception e) {
            logData.error("Error fetching records for Customer ID: " + customerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // API to insert new record into database
    @PostMapping("/insertRecord")
    public ResponseEntity<String> insertRecord(@Valid @RequestBody CustomerRecordDTO customerRecordDTO) {
        logData.info("insertRecord API called with data: {}", customerRecordDTO);
        boolean validFlag = false;
        validFlag = monthlyRewards.insertRecord(customerRecordDTO);
        if(!validFlag)
        	return ResponseEntity.ok("Transaction procesed successfully!");
        else
        	return ResponseEntity.ok("Validation failed. Transaction Not inserted!");
    }

    //Monthly rewards for all customers
    @PostMapping("/calculateMonthlyRewards")
    public ResponseEntity<MonthlyRewardDTO> calculateMonthlyRewards() {
        try {
            List<CustomerRecordDTO> customerRecordDto = new ArrayList<>();
            customerRewardRepository.findAll().forEach(customerRecordDto::add);

            MonthlyRewardDTO rewards = monthlyRewards.getMonthlyTotalRewardPoint(customerRecordDto);
            return ResponseEntity.ok(rewards);
        } catch (Exception e) {
            logData.error("Error calculating monthly rewards", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Total reward point for customer (customerId) given
    @PostMapping("/totalRewardsByCustomer")
    public ResponseEntity<Map<Month, Double>> calculateRewardsByCustomer(@RequestParam int customerId) {
        if (customerId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }

        try {
            List<CustomerRecordDTO> customerRecordDto = customerRewardRepository.findByCustomerId(customerId);
            List<CustomerRecordDTO> filterList = customerRecordDto.stream()
            		.filter(record->record.getCustomerId()==customerId)
            		.collect(Collectors.toList());
            Map<Month, Double> monthlyRewardDTO = monthlyRewards.getCustomerTotalRewardPoint(filterList);
            return new ResponseEntity<>(monthlyRewardDTO, HttpStatus.OK);
        } catch (Exception e) {
            logData.error("Error calculating rewards for Customer ID: " + customerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}