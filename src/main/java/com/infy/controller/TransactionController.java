/**
 * 
 */
package com.infy.controller;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	MonthlyRewardService monthlyRewards;
	
	@Autowired
	CustomerRewardRepository customerRewardRepository;
	
	@GetMapping("/getAllRecords")
	public ResponseEntity<List<CustomerRecordDTO>> getAllRecords(@RequestParam(required = false) int customerId) {
		try {
			List<CustomerRecordDTO> customerRecordDto = new ArrayList<CustomerRecordDTO>();

			if (customerId==0) //show all records
				customerRewardRepository.findAll().forEach(customerRecordDto::add);
			else
				customerRewardRepository.findByCustomerId(customerId).forEach(customerRecordDto::add); // show record by CustomerId

			if (customerRecordDto.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(customerRecordDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/insertRecord")
    public ResponseEntity<String> insertRecord(@Valid @RequestBody CustomerRecordDTO customerRecordDTO) {
		logData.info("insertRecord API called");
		monthlyRewards.insertRecord(customerRecordDTO);
		return ResponseEntity.ok("Transaction procesed successfully!");
		
	}
	
	@PostMapping("/calculateMonthlyRewards")
    public ResponseEntity<Map<Month, Double>> calculateMonthlyRewards() {
		List<CustomerRecordDTO> customerRecordDto = new ArrayList<CustomerRecordDTO>();
		customerRewardRepository.findAll().forEach(customerRecordDto::add);
		
		Map<Month, Double> rewards = monthlyRewards.getMonthlyTotalRewardPoint(customerRecordDto);
        return ResponseEntity.ok(rewards);
    }
	
	@PostMapping("/totalRewardsByCustmer")
    public ResponseEntity<Map<Month, Double>> calculateRewardsByCustmer(@RequestParam(required = false) int customerId) {
		List<CustomerRecordDTO> customerRecordDto = new ArrayList<CustomerRecordDTO>();
		customerRewardRepository.findByCustomerId(customerId).forEach(customerRecordDto::add);
		
		Map<Month, Double> rewards = monthlyRewards.getMonthlyTotalRewardPoint(customerRecordDto);
        return ResponseEntity.ok(rewards);
    }

}