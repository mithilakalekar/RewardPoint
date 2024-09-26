/**
 * service class for Monthly reward points calculation for each customer
 */
package com.infy.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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
	
	//Validate data and insert record into database
	public boolean insertRecord (CustomerRecordDTO record) {
		CustomerRecordDTO customerRecordDTO = new CustomerRecordDTO();
		LocalDate date = LocalDate.now();
		boolean validate = false;
		try {
			if(!record.equals(null))  {
				validate = validateRecord(record);
				if(validate) {
					logData.info("invalid customer data");
				}
				else {
					customerRecordDTO.setCustomerName(record.getCustomerName()!=null?record.getCustomerName():"");
					customerRecordDTO.setBillAmount(record.getBillAmount()>0d?record.getBillAmount():0);
					customerRecordDTO.setBillDate(record.getBillDate()!=null?record.getBillDate():date);
					customerRecordDTO.setCustomerId(record.getCustomerId()>0?record.getCustomerId():0);
					customerRewardRepository.save(customerRecordDTO);
					logData.info("customer data inserted");
				}
			}
		} catch (Exception e) {
			validate = true;
			e.printStackTrace();
		}
		return validate;
	}
	
	//show validation error based on flag
	public boolean validateRecord (CustomerRecordDTO record) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		boolean flag = false;

		if(!record.equals(null)) {
			if(record.getCustomerName() == null || record.getCustomerName().equalsIgnoreCase(null)) {
				flag = true;
			} else if(record.getCustomerId()<= 0 || String.valueOf(record.getCustomerId()).equalsIgnoreCase(null)) {    // need to check for CustomerId =  null/0
				flag = true;
			} else if(record.getBillAmount()<= 0d || String.valueOf(record.getBillAmount()).equalsIgnoreCase(null)) {
				flag = true;
			} else {
				if(record.getBillDate()!=null) {
					//validate Date format
					try {
						LocalDate parsedDate = LocalDate.parse(String.valueOf(record.getBillDate()),formatter);
						logData.info("Date: ",parsedDate);
					} catch(Exception e) {
						flag = true;
						logData.info("Invalid date inserted!");
					}
				}
				else
					flag = true;
			}
		}else {
			flag = true;
		}
		return flag;
	}
	
	//calculation for reward point based on Bill amount
	public double calculateRewardPoints(Double billAmount) {
		double rewardPoint = 0d;
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

	//Calculate monthly reward points for all entries in database
	public MonthlyRewardDTO getMonthlyTotalRewardPoint(List<CustomerRecordDTO> custRecords) {
		Map<Month, Double> monthlyRewardPoints = new HashMap<>();
		MonthlyRewardDTO monthlyRewardDTO = new MonthlyRewardDTO();
		double totalPoints = 0d;
		int i =0;
		for(CustomerRecordDTO transaction: custRecords) {
			//get months for record
			Month month = transaction.getBillDate().getMonth();
			//get total reward points
            totalPoints = calculateRewardPoints(transaction.getBillAmount());
            monthlyRewardPoints.merge(month, totalPoints, Double::sum);
            logData.info("Transaction for {}: {} points added.", month, totalPoints);
            
            monthlyRewardDTO.setMonthlyPoints(monthlyRewardPoints);
		}
        
		return monthlyRewardDTO;
	}
	
	//calculate monthly reward points for customer (customerId) given
	public Map<Month, Double> getCustomerTotalRewardPoint(List<CustomerRecordDTO> custRecords) {
		Map<Month, Double> totalRewardPoints = new HashMap<>();
		
		custRecords.forEach(transaction -> {
			//get months for record
			Month month = transaction.getBillDate().getMonth();
			//get total reward points
            double totalPoints = calculateRewardPoints(transaction.getBillAmount());
            totalRewardPoints.merge(month, totalPoints, Double::sum);
            logData.info("Transaction for {}: {} points added.", transaction.getCustomerName(), totalPoints);
		});
		
		return totalRewardPoints;
	}

	public Map<Month, List<CustomerRecordDTO>> getMonthlyTransactions() {
		return monthlyTransactions;
	}

}