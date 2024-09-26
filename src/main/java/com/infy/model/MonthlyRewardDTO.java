/**
 * Monthly Reward object
 */
package com.infy.model;

import java.time.Month;
import java.util.Map;

/**
 * Show Month-wise rewards
 */
public class MonthlyRewardDTO {
	private int customerId;
    private Map<Month, Double> monthlyPoints;
    private double totalPoints;
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Map<Month, Double> getMonthlyPoints() {
		return monthlyPoints;
	}
	public void setMonthlyPoints(Map<Month, Double> monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}
	public double getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(double totalPoints2) {
		this.totalPoints = totalPoints2;
	}

}
