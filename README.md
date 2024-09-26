WebAPI Developer - Rewards Point Program

#Overview:
This Spring Boot project implements a rewards program for a retail company, allowing customers to earn points based on their purchases. Customers earn points according to the following criteria:
2 points for every dollar spent over $100 in each transaction and 1 point for every dollar spent between $50 and $100 in each transaction.
For example, a $120 purchase would yield:
2 points for the $20 over $100 = 40 points
1 point for the first $50 = 50 points
Total = 90 points
The application calculates the reward points earned by each customer per month and totals them over a three-month period.

#Features:
(1)RESTful API to calculate and retrieve reward points.
(2).Data set simulating customer transactions over three months.
(3)Calculation of monthly and total reward points for each customer.

#Technologies Used:
(1)Java 8
(2)Spring Boot 2.6.2
(3)Maven
(4)JUnit for testing
(5)H2 Database for data storage

#Getting Started:
Prerequisites
Make sure you have the following installed:
Java Development Kit (JDK) 1.8 or higher
Maven
An IDE (e.g., IntelliJ IDEA, Eclipse)

#Installation:
1.Clone the repository: git clone https://github.com/mithilakalekar/RewardPoint
2.Navigate to the project directory: cd RewardPoint
3.Build the project using Maven: mvn clean install
4.Run the application: mvn spring-boot:run

#API Endpoints
Assignment - Calculate Reward points for customer
API 1: POST
URL : http://localhost:8080/demo/transaction/insertRecord
HEADER:  Content-Type = application/json
BODY:
{
  "customer":"Mithila03",
  "customerId":3,
  "billDate":"2024-10-23",
  "billAmount":120
}
Expected API response: HTTP Status code-200 OK
Transaction procesed successfully!

API 2: GET
URL : http://localhost:8080/demo/transaction/getAllRecords
Expected API response: HTTP Status code-200 OK
[
    {
        "id": 59,
        "customerId": 3,
        "billDate": "2024-10-23",
        "billAmount": 120.0,
        "customer": "Mithila03"
    }
]

API 3: GET
URL : http://localhost:8080/demo/transaction/getRecordsByCustomerId?customerId=3
HEADER:  Content-Type = application/json
Expected API response: HTTP Status code-200 OK
[
    {
        "id": 59,
        "customerId": 3,
        "billDate": "2024-10-23",
        "billAmount": 120.0,
        "customer": "Mithila03"
    }
]

API 4: POST
URL : http://localhost:8080/demo/transaction/calculateMonthlyRewards
HEADER:  Content-Type = application/json
Expected API response: HTTP Status code-200 OK
{
    "customerId": 0,
    "monthlyPoints": {
        "NOVEMBER": 90.0,
        "OCTOBER": 90.0
    },
    "totalPoints": 0.0
}

API 5: POST
URL : http://localhost:8080/demo/transaction/totalRewardsByCustmer?customerId=3
HEADER:  Content-Type = application/json
Expected API response: HTTP Status code-200 OK
{
    "OCTOBER": 90.0
}

#Sample Data Set:
The application includes a sample dataset of transactions that can be used to test the API. The dataset is created using a simple Java class that generates random transactions for demonstration purposes.

#Testing:
Unit tests are included in the project to ensure the functionality of the reward calculation logic. 
You can run tests using Maven: mvn test

#Contributing:
Contributions are welcome! If you have suggestions or improvements, please fork the repository and submit a pull request.

#License:
This project is licensed under the MIT License - see the LICENSE file for details.

#Contact:
For any questions or inquiries, please reach out via GitHub Issues. Thank you for checking out this rewards program API! We hope it serves as a useful tool for managing customer rewards efficiently.
