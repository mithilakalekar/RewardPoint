/**
 * service class for Monthly reward points calculation for each customer
 */
package com.infy.service;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.model.CustomerRecordDTO;
import com.infy.model.MonthlyRewardDTO;
import com.infy.repository.CustomerRewardRepository;

/**
 * Monthly reward points calculation
 */
@Service
public class MonthlyRewardService {
	
	private static final Logger logData = LoggerFactory.getLogger(MonthlyRewardService.class);
	
	@Autowired
	CustomerRewardRepository customerRewardRepository;

	private String customer;

	private Map<Month, List<CustomerRecordDTO>> monthlyTransactions = new HashMap<>();

	public MonthlyRewardService() {
		super();
	}
	public MonthlyRewardService(String customer) {
		super();
		this.customer = customer;
	}

	public String getCustomer(String customer) {
		return customer;
	}
	
	public Optional<CustomerRecordDTO> getId(Long id) {
        return customerRewardRepository.findById(id);
    }
	
	public void insertRecord (CustomerRecordDTO record) {
		CustomerRecordDTO customerRecordDTO = new CustomerRecordDTO();
		try {
			if(!record.equals(null))  {
				boolean validate = validateRecord(record);
				if(validate) {
					logData.info("invalid customer data");
				}
				else {
					customerRecordDTO.setCustomer(record.getCustomer());
					customerRecordDTO.setBillAmount(record.getBillAmount());
					customerRecordDTO.setBillDate(record.getBillDate());
					customerRecordDTO.setCustomerId(record.getCustomerId());
					customerRewardRepository.save(customerRecordDTO);
					logData.info("customer data inserted");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean validateRecord (CustomerRecordDTO record) {
		boolean flag = false;
		if(!record.equals(null)) {
			if(record.getCustomer()==null || record.getCustomer().isEmpty()) {
				flag = true;
				if(record.getCustomerId()<= 0) {
					flag = true;
					if(record.getBillAmount()<= 0d) {
						flag = true;
					}
				}
			}
		} else {
			flag = true;
		}
		return flag;
	}
	
		double rewardPoint = 0d;
		public double calculateRewardPoints(Double billAmount) {
		//2 points for every dollar spent over $100 in each transaction
		if (billAmount > 100) {
			double twoPoints = (billAmount-100)*2;
			rewardPoint += twoPoints;
			
			//1 point for remaining $100
			rewardPoint += (100-50)*1;
			return rewardPoint;
		} 
		
		//1 point for every dollar spent between $50 and $100 in each transaction
		if (billAmount >=50 && billAmount <= 100) {
			double onePoints =  (billAmount-50)*1;
			rewardPoint += onePoints;
		} 
		
		return rewardPoint;
	}

	public MonthlyRewardDTO getMonthlyTotalRewardPoint(List<CustomerRecordDTO> custRecords) {
		Map<Month, Double> monthlyRewardPoints = new HashMap<>();
		MonthlyRewardDTO monthlyRewardDTO = new MonthlyRewardDTO();
		double totalPoints = 0d;
		for(CustomerRecordDTO transaction: custRecords) {
			//get months for record
			Month month = transaction.getBillDate().getMonth();
			//get total reward points
            totalPoints = calculateRewardPoints(transaction.getBillAmount());
            
            monthlyRewardPoints.merge(month, totalPoints, Double::sum);
            logData.info("Transaction for {}: {} points added.", month, totalPoints);
		}
		
		monthlyRewardDTO.setCustomerId(custRecords.get(0).getCustomerId());
        monthlyRewardDTO.setMonthlyPoints(monthlyRewardPoints);
        monthlyRewardDTO.setTotalPoints(totalPoints);
        
		return monthlyRewardDTO;
	}
	
	public Map<Month, Double> getCustomerTotalRewardPoint(List<CustomerRecordDTO> custRecords) {
		Map<Month, Double> totalRewardPoints = new HashMap<>();
		
		custRecords.forEach(transaction -> {
			//get months for record
			Month month = transaction.getBillDate().getMonth();
			//get total reward points
            double totalPoints = calculateRewardPoints(transaction.getBillAmount());
            totalRewardPoints.merge(month, totalPoints, Double::sum);
            logData.info("Transaction for {}: {} points added.", transaction.getCustomer(), totalPoints);
		});
		
		return totalRewardPoints;
	}

	public Map<Month, List<CustomerRecordDTO>> getMonthlyTransactions() {
		return monthlyTransactions;
	}

}