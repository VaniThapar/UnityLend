
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create "User" table

CREATE TABLE "User" (
                        UserId VARCHAR(36),
                        Password VARCHAR(255) NOT NULL,
                        Name VARCHAR(255) NOT NULL,
                        Email VARCHAR(255) NOT NULL UNIQUE,
                        DOB DATE,
                        Income INTEGER NOT NULL,
                        OfficeName VARCHAR(255),
                        CollegeUniversity VARCHAR(255),
                        Locality VARCHAR(255),
                        PRIMARY KEY (UserId) -- Adding primary key constraint
);

CREATE OR REPLACE FUNCTION check_age()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.DOB > CURRENT_DATE - INTERVAL '18 years' THEN
        RAISE EXCEPTION 'Age must be at least 18 years.';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_age_trigger
    BEFORE INSERT ON "User"
    FOR EACH ROW
    EXECUTE FUNCTION check_age();


-- Create Community table

CREATE TABLE Community (
                           CommunityId CHAR(36) PRIMARY KEY,
                           CommunityName VARCHAR(255) NOT NULL,
                           CommonTag VARCHAR(255) NOT NULL
);

-- Junction table to represent the many-to-many relationship between "User"s and communities

CREATE TABLE UserCommunity (
                               UserId CHAR(36),
                               CommunityId CHAR(36),
                               PRIMARY KEY (UserId, CommunityId),
                               FOREIGN KEY (UserId) REFERENCES "User"(UserId),
                               FOREIGN KEY (CommunityId) REFERENCES Community(CommunityId)
);

-- Create Borrow_Request table
CREATE TABLE Borrow_Request (
                                RequestID CHAR(36) PRIMARY KEY,
                                BorrowerID CHAR(36),
                                CommunityID CHAR(36),
                                ReturnPeriod INT NOT NULL,
                                Status VARCHAR(20) DEFAULT 'Pending' ,
                                Timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
                                CollectedAmount DECIMAL(10, 2) DEFAULT 0.00 ,
                                TargetAmount DECIMAL(10, 2) NOT NULL,
                                FOREIGN KEY (BorrowerID) REFERENCES "User"(UserId),
                                FOREIGN KEY (CommunityID) REFERENCES Community(CommunityId)
);

-- Create Lending_Transaction table
CREATE TABLE Lending_Transaction (
                                     LendTransactionId CHAR(36) PRIMARY KEY,
                                     LenderId CHAR(36),
                                     BorrowerId CHAR(36),
                                     RequestId CHAR(36),
                                     Amount DECIMAL(10, 2) NOT NULL,
                                     Timestamp TIMESTAMP NOT NULL,
                                     FOREIGN KEY (LenderId) REFERENCES "User"(UserId),
                                     FOREIGN KEY (BorrowerId) REFERENCES "User"(UserId),
                                     FOREIGN KEY (RequestId) REFERENCES Borrow_Request(RequestID)
);

-- Create Repayment_Transaction table
CREATE TABLE Repayment_Transaction (
                                       RepayTransactionId CHAR(36) PRIMARY KEY,
                                       PayerId CHAR(36),
                                       PayeeId CHAR(36),
                                       RequestId CHAR(36),
                                       Amount DECIMAL(10, 2) NOT NULL,
                                       Timestamp TIMESTAMP NOT NULL,
                                       FOREIGN KEY (PayerId) REFERENCES "User"(UserId),
                                       FOREIGN KEY (PayeeId) REFERENCES "User"(UserId),
                                       FOREIGN KEY (RequestId) REFERENCES Borrow_Request(RequestID)
);

-- Create Wallet table
CREATE TABLE Wallet (
                        WalletId CHAR(36) PRIMARY KEY,
                        UserId CHAR(36),
                        Balance DECIMAL(10, 2) NOT NULL,
                        FOREIGN KEY (UserId) REFERENCES "User"(UserId)
);

Queries to populate the dummy data into tables

-- Inserting dummy data into the "User" table

INSERT INTO "User" (UserId, Password, Name, Email, DOB, Income)
VALUES
(uuid_generate_v4(), 'pass123', 'Aarav Patel', 'aarav@example.com', '1990-01-01', 50000.00),
(uuid_generate_v4(), 'pass456', 'Aaradhya Sharma', 'aaradhya@example.com', '1985-05-15', 60000.00),
(uuid_generate_v4(), 'pass789', 'Aarav Singh', 'aaravsingh@example.com', '1993-08-20', 45000.00),
(uuid_generate_v4(), 'passabc', 'Abhinav Gupta', 'abhinavgupta@example.com', '1980-03-10', 70000.00),
(uuid_generate_v4(), 'pass123', 'Aditi Mishra', 'aditi@example.com', '1988-09-25', 55000.00),
(uuid_generate_v4(), 'pass456', 'Advait Tiwari', 'advait@example.com', '1995-06-12', 62000.00),
(uuid_generate_v4(), 'pass789', 'Akshay Joshi', 'akshayjoshi@example.com', '1991-11-08', 48000.00),
(uuid_generate_v4(), 'passabc', 'Ananya Singh', 'ananya@example.com', '1975-04-17', 72000.00),
(uuid_generate_v4(), 'pass123', 'Arya Dubey', 'arya@example.com', '1983-02-20', 51000.00),
(uuid_generate_v4(), 'pass456', 'Avani Patel', 'avani@example.com', '1987-07-30', 63000.00),
(uuid_generate_v4(), 'pass789', 'Ayaan Kumar', 'ayaan@example.com', '1990-05-05', 46000.00),
(uuid_generate_v4(), 'passabc', 'Bhavna Shah', 'bhavna@example.com', '1982-10-12', 71000.00),
(uuid_generate_v4(), 'pass123', 'Charvi Sharma', 'charvi@example.com', '1989-12-04', 52000.00),
(uuid_generate_v4(), 'pass456', 'Darsh Jain', 'darsh@example.com', '1984-08-18', 64000.00),
(uuid_generate_v4(), 'pass789', 'Dev Singh', 'devsingh@example.com', '1986-03-22', 47000.00),
(uuid_generate_v4(), 'passabc', 'Dhruv Gupta', 'dhruv@example.com', '1992-01-15', 69000.00),
(uuid_generate_v4(), 'pass123', 'Eshaan Gupta', 'eshaan@example.com', '1978-06-28', 53000.00),
(uuid_generate_v4(), 'pass456', 'Garima Mishra', 'garima@example.com', '1994-04-03', 65000.00);



-- Inserting dummy data into the Community table for 5 rows

INSERT INTO Community (CommunityId, CommunityName, CommonTag)
VALUES
    (uuid_generate_v4(), 'Community A','D.E.Shaw'),
    (uuid_generate_v4(), 'Community B','Delhi Public School'),
    (uuid_generate_v4(), 'Community C','Sri Chaitanya School'),
    (uuid_generate_v4(), 'Community D','IIM Ahmedabad'),
    (uuid_generate_v4(), 'Community E','ISB Hyderabad');




-- Inserting dummy data into the UserCommunity table

INSERT INTO UserCommunity (UserId, CommunityId)
VALUES
    ((SELECT UserId FROM "User" WHERE Name = 'Aarav Patel'), (SELECT CommunityId from Community where CommunityName='Community A')),
    ((SELECT UserId FROM "User" WHERE Name = 'Aaradhya Sharma'),  (SELECT CommunityId from Community where CommunityName='Community B')),
    ((SELECT UserId FROM "User" WHERE Name = 'Aarav Singh'),  (SELECT CommunityId from Community where CommunityName='Community C')),
    ((SELECT UserId FROM "User" WHERE Name = 'Abhinav Gupta'),  (SELECT CommunityId from Community where CommunityName='Community D')),
    ((SELECT UserId FROM "User" WHERE Name = 'Aditi Mishra'),  (SELECT CommunityId from Community where CommunityName='Community E')),
    ((SELECT UserId FROM "User" WHERE Name = 'Advait Tiwari'),  (SELECT CommunityId from Community where CommunityName='Community A')),
    ((SELECT UserId FROM "User" WHERE Name = 'Akshay Joshi'),  (SELECT CommunityId from Community where CommunityName='Community B')),
    ((SELECT UserId FROM "User" WHERE Name = 'Ananya Singh'),  (SELECT CommunityId from Community where CommunityName='Community C')),
    ((SELECT UserId FROM "User" WHERE Name = 'Arya Dubey'),  (SELECT CommunityId from Community where CommunityName='Community D')),
    ((SELECT UserId FROM "User" WHERE Name = 'Avani Patel'), (SELECT CommunityId from Community where CommunityName='Community E')),
    ((SELECT UserId FROM "User" WHERE Name = 'Ayaan Kumar'),  (SELECT CommunityId from Community where CommunityName='Community A')),
    ((SELECT UserId FROM "User" WHERE Name = 'Bhavna Shah'),  (SELECT CommunityId from Community where CommunityName='Community B')),
    ((SELECT UserId FROM "User" WHERE Name = 'Charvi Sharma'),  (SELECT CommunityId from Community where CommunityName='Community C')),
    ((SELECT UserId FROM "User" WHERE Name = 'Darsh Jain'),  (SELECT CommunityId from Community where CommunityName='Community D')),
    ((SELECT UserId FROM "User" WHERE Name = 'Dev Singh'),  (SELECT CommunityId from Community where CommunityName='Community E')),
    ((SELECT UserId FROM "User" WHERE Name = 'Dhruv Gupta'),  (SELECT CommunityId from Community where CommunityName='Community A')),
    ((SELECT UserId FROM "User" WHERE Name = 'Eshaan Gupta'),  (SELECT CommunityId from Community where CommunityName='Community B')),
    ((SELECT UserId FROM "User" WHERE Name = 'Garima Mishra'),  (SELECT CommunityId from Community where CommunityName='Community C')),
    ((SELECT UserId FROM "User" WHERE Name = 'Aarav Patel'),  (SELECT CommunityId from Community where CommunityName='Community D')),
    ((SELECT UserId FROM "User" WHERE Name = 'Aaradhya Sharma'),  (SELECT CommunityId from Community where CommunityName='Community E')),
    ((SELECT UserId FROM "User" WHERE Name = 'Aarav Singh'),  (SELECT CommunityId from Community where CommunityName='Community A')),
    ((SELECT UserId FROM "User" WHERE Name = 'Abhinav Gupta'),  (SELECT CommunityId from Community where CommunityName='Community B')),
    ((SELECT UserId FROM "User" WHERE Name = 'Aditi Mishra'),  (SELECT CommunityId from Community where CommunityName='Community C')),
    ((SELECT UserId FROM "User" WHERE Name = 'Advait Tiwari'), (SELECT CommunityId from Community where CommunityName='Community D')),
    ((SELECT UserId FROM "User" WHERE Name = 'Akshay Joshi'),  (SELECT CommunityId from Community where CommunityName='Community E')),
    ((SELECT UserId FROM "User" WHERE Name = 'Ananya Singh'), (SELECT CommunityId from Community where CommunityName='Community A')),
    ((SELECT UserId FROM "User" WHERE Name = 'Arya Dubey'), (SELECT CommunityId from Community where CommunityName='Community B')),
    ((SELECT UserId FROM "User" WHERE Name = 'Avani Patel'),  (SELECT CommunityId from Community where CommunityName='Community C')),
    ((SELECT UserId FROM "User" WHERE Name = 'Ayaan Kumar'), (SELECT CommunityId from Community where CommunityName='Community D')),
    ((SELECT UserId FROM "User" WHERE Name = 'Bhavna Shah'),  (SELECT CommunityId from Community where CommunityName='Community E'));

-- Inserting dummy data into the Borrow_Request table

INSERT INTO Borrow_Request (RequestID, BorrowerID, CommunityID, ReturnPeriod, Status, Timestamp, CollectedAmount, TargetAmount)
VALUES
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Aarav Patel'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community A'), 60, 'Pending', NOW(), 0.00, 2500.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Aaradhya Sharma'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community B'),  35, 'Fulfilled', NOW(), 2200.00, 2200.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Ananya Singh'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community C'),  45, 'Pending', NOW(), 0.00, 2700.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Dev Singh'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community E'),  55, 'Fulfilled', NOW(), 2800.00, 2800.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Aarav Singh'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community C'),  40, 'Pending', NOW(), 0.00, 2000.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Garima Mishra'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community C'),  25, 'Pending', NOW(), 1500.00, 2300.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Ayaan Kumar'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community D'),  50, 'Fulfilled', NOW(), 2500.00, 2500.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Avani Patel'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community C'), 30, 'Pending', NOW(), 0.00, 2200.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Charvi Sharma'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community C'), 40, 'Pending', NOW(), 0.00, 2700.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Darsh Jain'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community D'), 55, 'Fulfilled', NOW(), 2800.00, 2800.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Dhruv Gupta'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community A'),  35, 'Pending', NOW(), 0.00, 2000.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Ayaan Kumar'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community A'), 45, 'Fulfilled', NOW(), 2700.00, 2700.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Avani Patel'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community E'), 30, 'Pending', NOW(), 0.00, 2200.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Arya Dubey'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community D'),  50, 'Pending', NOW(), 0.00, 2800.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Ananya Singh'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community A'),  40, 'Fulfilled', NOW(), 3000.00, 3000.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Akshay Joshi'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community B'), 35, 'Pending', NOW(), 0.00, 2000.00),
    (uuid_generate_v4(), (SELECT UserId FROM "User" WHERE Name = 'Advait Tiwari'), (SELECT CommunityId FROM Community WHERE CommunityName = 'Community A'), 55, 'Pending', NOW(), 0.00, 2500.00);


-- Inserting dummy data into the Lending_Transaction table

INSERT INTO Lending_Transaction (LenderId, BorrowerId, LendTransactionId, RequestId,
Amount, Timestamp)
VALUES
((SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Eshaan Gupta' AND c.CommunityName = 'Community B'
 ), (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Aaradhya Sharma' AND c.CommunityName = 'Community B'
), uuid_generate_v4(), (SELECT RequestId
FROM Borrow_Request WHERE BorrowerId = (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Aaradhya Sharma' AND c.CommunityName = 'Community B'
)), 1000.00, NOW()),

((SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Abhinav Gupta' AND c.CommunityName = 'Community B'
 ), (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Aaradhya Sharma' AND c.CommunityName = 'Community B'
), uuid_generate_v4(), (SELECT RequestId
FROM Borrow_Request WHERE BorrowerId = (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Aaradhya Sharma' AND c.CommunityName = 'Community B'
)), 500.00, NOW()),

((SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Arya Dubey' AND c.CommunityName = 'Community B'
 ), (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Aaradhya Sharma' AND c.CommunityName = 'Community B'
), uuid_generate_v4(), (SELECT RequestId
FROM Borrow_Request WHERE BorrowerId = (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Aaradhya Sharma' AND c.CommunityName = 'Community B'
)), 600.00, NOW()),


(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Aarav Singh' AND c.CommunityName = 'Community C'
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Garima Mishra' AND c.CommunityName = 'Community C'
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Garima Mishra' AND c.CommunityName = 'Community C'
        )
    ),
    300.00,
    NOW()
),

(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Charvi Sharma' AND c.CommunityName = 'Community C'
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Garima Mishra' AND c.CommunityName = 'Community C'
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Garima Mishra' AND c.CommunityName = 'Community C'
        )
    ),
    1100.00,
    NOW()
),


(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Charvi Sharma' AND c.CommunityName = 'Community C'
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Garima Mishra' AND c.CommunityName = 'Community C'
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Garima Mishra' AND c.CommunityName = 'Community C'
        )
    ),
    900.00,
    NOW()
),



(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Aditi Mishra' AND c.CommunityName = 'Community E'
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
        )
    ),
    1100.00,
    NOW()
),

(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Bhavna Shah' AND c.CommunityName = 'Community E'
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
        )
    ),
    500.00,
    NOW()
),


(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Akshay Joshi' AND c.CommunityName = 'Community E'
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
        )
    ),
    1200.00,
    NOW()
),

(
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Akshay Joshi' AND c.CommunityName = 'Community E'
        LIMIT 1
    ),
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT MAX(RequestId)
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT MAX(u.UserId)
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
            LIMIT 1
        )
        LIMIT 1
    ),
    1200.00,
    NOW()
),



(
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Aarav Patel' AND c.CommunityName = 'Community D'
        LIMIT 1
    ),
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community D'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT MAX(RequestId)
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT MAX(u.UserId)
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community D'
            LIMIT 1
        )
        LIMIT 1
    ),
    500.00,
    NOW()
),


(
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Arya Dubey' AND c.CommunityName = 'Community D'
        LIMIT 1
    ),
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community D'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT MAX(RequestId)
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT MAX(u.UserId)
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community D'
            LIMIT 1
        )
        LIMIT 1
    ),
    1000.00,
    NOW()
),
(
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Advait Tiwari' AND c.CommunityName = 'Community D'
        LIMIT 1
    ),
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community D'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT MAX(RequestId)
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT MAX(u.UserId)
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community D'
            LIMIT 1
        )
        LIMIT 1
    ),
    1000.00,
    NOW()
),

(
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Aarav Patel' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT MAX(RequestId)
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT MAX(u.UserId)
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    600.00,
    NOW()
),

(
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Advait Tiwari' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT MAX(RequestId)
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT MAX(u.UserId)
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    300.00,
    NOW()
),



(
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Aarav Singh' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT MAX(RequestId)
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT MAX(u.UserId)
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    700.00,
    NOW()
),

(
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Dhruv Gupta' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT MAX(u.UserId)
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT MAX(RequestId)
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT MAX(u.UserId)
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    1100.00,
    NOW()
),

(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Aarav Singh' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ananya Singh' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ananya Singh' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    1500.00,
    NOW()
),

(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ananya Singh' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ananya Singh' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    1000.00,
    NOW()
),

(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Dhruv Gupta' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ananya Singh' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ananya Singh' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    500.00,
    NOW()
);


-- Inserting dummy data into the Repayment_Transaction table

INSERT INTO Repayment_Transaction (PayerId, PayeeId, RepayTransactionId, RequestId,
                                   Amount, Timestamp)
VALUES
    ((SELECT u.UserId
      FROM "User" u
               JOIN UserCommunity uc ON u.UserId = uc.UserId
               JOIN Community c ON uc.CommunityId = c.CommunityId
      WHERE u.Name = 'Aaradhya Sharma' AND c.CommunityName = 'Community B'
     ),   (SELECT u.UserId
           FROM "User" u
                    JOIN UserCommunity uc ON u.UserId = uc.UserId
                    JOIN Community c ON uc.CommunityId = c.CommunityId
           WHERE u.Name = 'Eshaan Gupta' AND c.CommunityName = 'Community B'
     )  , uuid_generate_v4(),(SELECT RequestId
                  FROM Borrow_Request WHERE BorrowerId = (SELECT u.UserId
                                                          FROM "User" u
                                                                   JOIN UserCommunity uc ON u.UserId = uc.UserId
                                                                   JOIN Community c ON uc.CommunityId = c.CommunityId
                                                          WHERE u.Name = 'Aaradhya Sharma' AND c.CommunityName = 'Community B'
    )), 1047.00 ,NOW()),



    ((SELECT u.UserId
      FROM "User" u
               JOIN UserCommunity uc ON u.UserId = uc.UserId
               JOIN Community c ON uc.CommunityId = c.CommunityId
      WHERE u.Name = 'Aaradhya Sharma' AND c.CommunityName = 'Community B'
     ),   (SELECT u.UserId
           FROM "User" u
                    JOIN UserCommunity uc ON u.UserId = uc.UserId
                    JOIN Community c ON uc.CommunityId = c.CommunityId
           WHERE u.Name = 'Abhinav Gupta' AND c.CommunityName = 'Community B'
     )  , uuid_generate_v4(),(SELECT RequestId
                  FROM Borrow_Request WHERE BorrowerId = (SELECT u.UserId
                                                          FROM "User" u
                                                                   JOIN UserCommunity uc ON u.UserId = uc.UserId
                                                                   JOIN Community c ON uc.CommunityId = c.CommunityId
                                                          WHERE u.Name = 'Aaradhya Sharma' AND c.CommunityName = 'Community B'
    )), 523.00 ,NOW()),

    ((SELECT u.UserId
      FROM "User" u
               JOIN UserCommunity uc ON u.UserId = uc.UserId
               JOIN Community c ON uc.CommunityId = c.CommunityId
      WHERE u.Name = 'Aaradhya Sharma' AND c.CommunityName = 'Community B'
     ),   (SELECT u.UserId
           FROM "User" u
                    JOIN UserCommunity uc ON u.UserId = uc.UserId
                    JOIN Community c ON uc.CommunityId = c.CommunityId
           WHERE u.Name = 'Arya Dubey'  AND c.CommunityName = 'Community B'
     )  , uuid_generate_v4(),(SELECT RequestId
                  FROM Borrow_Request WHERE BorrowerId = (SELECT u.UserId
                                                          FROM "User" u
                                                                   JOIN UserCommunity uc ON u.UserId = uc.UserId
                                                                   JOIN Community c ON uc.CommunityId = c.CommunityId
                                                          WHERE u.Name = 'Aaradhya Sharma' AND c.CommunityName = 'Community B'
    )), 628.00 ,NOW()),

    (
        (
            SELECT u.UserId
            FROM "User" u
                     JOIN UserCommunity uc ON u.UserId = uc.UserId
                     JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community D'
            LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Aarav Patel' AND c.CommunityName = 'Community D'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community D'
        LIMIT 1
        )
        LIMIT 1
    ),
    523.00,
    NOW()
    ),

(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community D'
        LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Arya Dubey' AND c.CommunityName = 'Community D'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community D'
            LIMIT 1
        )
        LIMIT 1
    ),
    1068.00,
    NOW()
),

(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community D'
        LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Advait Tiwari' AND c.CommunityName = 'Community D'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community D'
            LIMIT 1
        )
        LIMIT 1
    ),
    1068.00,
    NOW()
),

((SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
 ),   (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Bhavna Shah' AND c.CommunityName = 'Community E'
 )  , uuid_generate_v4(),(SELECT RequestId
FROM Borrow_Request WHERE BorrowerId = (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
)), 537.00 ,NOW()),

((SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
 ),   (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Aditi Mishra' AND c.CommunityName = 'Community E'
 )  , uuid_generate_v4(),(SELECT RequestId
FROM Borrow_Request WHERE BorrowerId = (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
)), 1182.00 ,NOW()),

((SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
 ),   (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Akshay Joshi' AND c.CommunityName = 'Community E'
 )  , uuid_generate_v4(),(SELECT RequestId
FROM Borrow_Request WHERE BorrowerId = (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Dev Singh' AND c.CommunityName = 'Community E'
)), 1290.00 ,NOW()),
((SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Darsh Jain' AND c.CommunityName = 'Community B'
 ),   (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Abhinav Gupta' AND c.CommunityName = 'Community B'
 )  , uuid_generate_v4(),(SELECT RequestId
FROM Borrow_Request WHERE BorrowerId = (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Darsh Jain' AND c.CommunityName = 'Community B'
)), 1505.00 ,NOW()),

((SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Darsh Jain' AND c.CommunityName = 'Community B'
 ),   (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Arya Dubey' AND c.CommunityName = 'Community B'
 )  , uuid_generate_v4(),(SELECT RequestId
FROM Borrow_Request WHERE BorrowerId = (SELECT u.UserId
FROM "User" u
JOIN UserCommunity uc ON u.UserId = uc.UserId
JOIN Community c ON uc.CommunityId = c.CommunityId
WHERE u.Name = 'Darsh Jain' AND c.CommunityName = 'Community B'
)), 1505.00 ,NOW()),

(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Aarav Patel' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    636.00,
    NOW()
),



(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Advait Tiwari' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    318.00,
    NOW()
),

(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Dhruv Gupta' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    743.00,
    NOW()
),


(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Aarav Singh' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    1167.00,
    NOW()
),


(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ananya Singh' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Aarav Singh' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ananya Singh' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    1582.00,
    NOW()
),


(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ananya Singh' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ayaan Kumar' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ananya Singh' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    1054.00,
    NOW()
),


(
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Ananya Singh' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    (
        SELECT u.UserId
        FROM "User" u
        JOIN UserCommunity uc ON u.UserId = uc.UserId
        JOIN Community c ON uc.CommunityId = c.CommunityId
        WHERE u.Name = 'Dhruv Gupta' AND c.CommunityName = 'Community A'
        LIMIT 1
    ),
    uuid_generate_v4(),
    (
        SELECT RequestId
        FROM Borrow_Request
        WHERE BorrowerId = (
            SELECT u.UserId
            FROM "User" u
            JOIN UserCommunity uc ON u.UserId = uc.UserId
            JOIN Community c ON uc.CommunityId = c.CommunityId
            WHERE u.Name = 'Ananya Singh' AND c.CommunityName = 'Community A'
            LIMIT 1
        )
        LIMIT 1
    ),
    1582.00,
    NOW()
);



-- Generate and insert dummy data into the Wallet table for each "User"
INSERT INTO Wallet (WalletId, UserId, Balance)
SELECT
    uuid_generate_v4() AS WalletId,
    u.UserId,
    ROUND(CAST(RANDOM() * 100000 AS numeric), 2) AS Balance
FROM
    "User" u;

alter table "TempUser" rename to "tempuser";

ALTER TABLE tempuser
    ADD COLUMN isActive BOOLEAN DEFAULT true;

ALTER TABLE community
    ADD COLUMN communitytype VARCHAR(255); -- Specify the desired length for VARCHAR



