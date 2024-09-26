/**
 * Customer record object
 */
package com.infy.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * Customer Data object
 */
@Entity
@Table(name = "record")
public class CustomerRecordDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "customerName")
    private String customerName; 
    
    @Column(name = "customerId")
    @NotNull(message = "CustomerId cannot be Null")
    private int customerId;
    
    @Column(name = "billDate")
    @NotNull(message = "billDate cannot be Null")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate billDate;  // month for bill transaction
    
    @Column(name = "billAmount")
    @NotNull(message = "billAmount cannot be Null")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private double billAmount; // bill amount

    public CustomerRecordDTO() {
        super();
    }
    
    public CustomerRecordDTO(int customerId, double billAmount) {
        super();
        this.customerId = customerId;
        this.billAmount = billAmount;
    }
    
    public CustomerRecordDTO(Long id, String customerName, int customerId, LocalDate billDate, double billAmount) {
        super();
        this.id = id;
        this.customerName = customerName;
        this.customerId = customerId;
        this.billDate = billDate;
        this.billAmount = billAmount;
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customerName;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customerName = customer;
	}

	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the billDate
	 */
	public LocalDate getBillDate() {
		return billDate;
	}

	/**
	 * @param billDate the billDate to set
	 */
	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}

	/**
	 * @return the billAmount
	 */
	public double getBillAmount() {
		return billAmount;
	}

	/**
	 * @param billAmount the billAmount to set
	 */
	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	@Override
	public String toString() {
		return "CustomerRecordDTO [id=" + id + ", customerName=" + customerName + ", customerId=" + customerId + ", billDate="
				+ billDate + ", billAmount=" + billAmount + "]";
	}

}