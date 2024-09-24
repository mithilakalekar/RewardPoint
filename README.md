# RewardPoint
Assignment - Calculate Reward points for customer

API used to test the application
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
URL : http://localhost:8080/demo/transaction/getAllRecords?customerId=0
HEADER:  Content-Type = application/json
Expected API response: HTTP Status code-200 OK
[
{
"id": 3,
"customerId": 3,
"billDate": "2024-10-23",
"billAmount": 120.0,
"customer": "Mithila03"
}
]

API 3: POST
URL : http://localhost:8080/demo/transaction/calculateMonthlyRewards
HEADER:  Content-Type = application/json
Expected API response: HTTP Status code-200 OK
{
"OCTOBER": 90.0,
"SEPTEMBER": 90.0
}

API 4: POST
URL : http://localhost:8080/demo/transaction/totalRewardsByCustmer?customerId=3
HEADER:  Content-Type = application/json
Expected API response: HTTP Status code-200 OK
{
"OCTOBER": 90.0
}
