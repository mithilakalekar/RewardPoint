CREATE TABLE record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customerName VARCHAR(255),
    customerId INT NOT NULL,
    billDate DATE NOT NULL,
    billAmount DOUBLE NOT NULL CHECK (billAmount >= 0)
);

INSERT INTO record (customerName, customerId, billDate, billAmount) VALUES ('Mithila01', 1, '2024-01-15', 150);
INSERT INTO record (customerName, customerId, billDate, billAmount) VALUES ('Mithila02', 2, '2024-02-20', 200);
INSERT INTO record (customerName, customerId, billDate, billAmount) VALUES ('Mithila03', 3, '2024-03-25', 300);
INSERT INTO record (customerName, customerId, billDate, billAmount) VALUES ('Mithila04', 4, '2024-04-30', 400);
INSERT INTO record (customerName, customerId, billDate, billAmount) VALUES ('Mithila05', 5, '2024-05-05', 500);