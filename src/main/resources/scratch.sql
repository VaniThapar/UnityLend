CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create user_detail table

CREATE TABLE user_detail (
                             user_id CHAR(36),
                             password VARCHAR(255) NOT NULL,
                             name VARCHAR(255) NOT NULL,
                             email VARCHAR(255) NOT NULL UNIQUE,
                             contact_no VARCHAR(255) UNIQUE,
                             dob DATE,
                             income INTEGER NOT NULL,
                             community_details JSON,
                             PRIMARY KEY (user_id) -- Adding primary key constraint
);

CREATE OR REPLACE FUNCTION check_age()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.dob > CURRENT_DATE - INTERVAL '18 years' THEN
        RAISE EXCEPTION 'Age must be at least 18 years.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_age_trigger
    BEFORE INSERT ON user_detail
    FOR EACH ROW
EXECUTE FUNCTION check_age();



-- Create wallet table

CREATE TABLE wallet(
                       wallet_id CHAR(36) PRIMARY KEY,
                       user_id CHAR(36) NOT NULL,
                       balance DECIMAL(10,2) NOT NULL,
                       FOREIGN KEY (user_id) REFERENCES user_detail (user_id)
);


-- Create status table
CREATE TABLE status(
                       status_code INTEGER PRIMARY KEY,
                       status_name VARCHAR(20)
);

-- Create community table

CREATE TABLE community(
                          community_id CHAR(36) PRIMARY KEY,
                          community_name VARCHAR(255) NOT NULL,
                          community_tag VARCHAR(255) NOT NULL,
                          community_detail VARCHAR(255)
);



-- Create user_community_map table

CREATE TABLE user_community_map(
                                   user_id CHAR(36),
                                   community_id CHAR(36),
                                   PRIMARY KEY (user_id, community_id),
                                   FOREIGN KEY (user_id) REFERENCES user_detail (user_id),
                                   FOREIGN KEY (community_id) REFERENCES community(community_id)
);



-- Create borrow_request table

CREATE TABLE borrow_request(
                               borrow_request_id CHAR(36) PRIMARY KEY,
                               borrower_id CHAR(36) NOT NULL,
                               return_period_days INTEGER NOT NULL,
                               monthly_interest_rate DECIMAL(10,2) NOT NULL,
                               borrow_status INTEGER,
                               requested_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                               collected_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                               is_defaulted BOOLEAN DEFAULT false,
                               default_fine DECIMAL(10,2) DEFAULT 0.00,
                               default_count INTEGER DEFAULT 0,
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               last_modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (borrower_id) REFERENCES user_detail(user_id),
                               FOREIGN KEY (borrow_status ) REFERENCES status (status_code)
);

-- Create borrow_request_community map

CREATE TABLE borrow_request_community_map(
                                             borrow_request_id CHAR(36),
                                             community_id CHAR(36),
                                             PRIMARY KEY (borrow_request_id, community_id),
                                             FOREIGN KEY (borrow_request_id) REFERENCES borrow_request (borrow_request_id),
                                             FOREIGN KEY (community_id) REFERENCES community (community_id)
);



-- Create transaction table

sCREATE TABLE transaction(
                            transaction_id CHAR(36) PRIMARY KEY,
                            receiver_id CHAR(36),
                            sender_id CHAR(36),
                            amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                            transaction_status INTEGER,
                            transaction_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            last_updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (receiver_id) REFERENCES user_detail (user_id),
                            FOREIGN KEY (sender_id) REFERENCES user_detail (user_id),
                            FOREIGN KEY (transaction_status) REFERENCES status (status_code)
);





-- Create lend_transaction table

CREATE TABLE lend_transaction(
                                 lend_transaction_id CHAR(36) PRIMARY KEY,
                                 transaction_id CHAR(36),
                                 borrow_request_id CHAR(36),
                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 last_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (transaction_id) REFERENCES transaction (transaction_id),
                                 FOREIGN KEY (borrow_request_id) REFERENCES borrow_request (borrow_request_id)
);

-- Create  repayment_transaction table
create TABLE repayment_transaction
(
    repayment_transaction_id char(36) PRIMARY KEY,
    transaction_id char(36) NOT NULL,
    borrow_request_id char(36) NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (transaction_id) REFERENCES transaction(transaction_id),
    FOREIGN KEY (borrow_request_id) REFERENCES borrow_request(borrow_request_id)
);

--QUERIES TO ADD DATA TO THE DATABASE

-- Inserting dummy data into the user_detail table

INSERT INTO user_detail (user_id, password, name, email,contact_no, dob, income,community_details)
VALUES
    (uuid_generate_v4(), 'pass456', 'Garima Mishra', 'garima@example.com', '8763282638', '1994-04-03', 65000, '{"school": "Sri Chaitanya School"}'),
    (uuid_generate_v4(), 'pass456', 'Advait Tiwari', 'advait@example.com', '8763282639', '1995-06-12', 62000.00, '{"office": "D.E.Shaw", "university": "IIM Ahmedabad"}'),
    (uuid_generate_v4(), 'pass123', 'Eshaan Gupta', 'eeshan@example.com', '9876543210', '1978-06-28', 53000, '{"school": "Delhi Public School"}'),
    (uuid_generate_v4(), 'pass456', 'Aaradhya Sharma', 'aaradhya@example.com', '9876543211', '1985-05-15', 60000, '{"school": "Delhi Public School", "university": "ISB Hyderabad"}'),
    (uuid_generate_v4(), 'pass123', 'Arya Dubey', 'arya@example.com', '8765432109', '1983-02-20', 51000.00, '{"university": "IIM Ahmedabad", "school": "Delhi Public school"}'),
    (uuid_generate_v4(), 'passabc', 'Ananya Singh', 'ananya@example.com', '9876543212', '1975-04-17', 72000, '{"school": "Sri Chaitanya School", "office": "D.E.Shaw"}'),
    (uuid_generate_v4(), 'pass789', 'Dev Singh', 'devsingh@example.com', '8765432107', '1986-03-22', 47000, '{"university": "ISB Hyderabad"}'),
    (uuid_generate_v4(), 'pass123', 'Aditi Mishra', 'aditi@example.com', '9876543213', '1988-09-25', 55000, '{"university": "ISB Hyderabad", "school": "Sri Chaitanya School"}'),
    (uuid_generate_v4(), 'pass789', 'Akshay Joshi', 'akshayjoshi@example.com', '8765432108', '1991-11-08', 48000.00, '{"school": "Delhi Public School", "university": "ISB Hyderabad"}'),
    (uuid_generate_v4(), 'pass123', 'Charvi Sharma', 'charvi@example.com', '9876543214', '1989-12-04', 52000, '{"school": "Sri Chaitanya School"}'),
    (uuid_generate_v4(), 'pass789', 'Aarav Singh', 'aaravsingh@example.com', '8763282649', '1993-08-20', 45000, '{"school": "Sri Chaitanya School", "office": "D.E.Shaw"}'),
    (uuid_generate_v4(), 'pass123', 'Aarav Patel', 'aarav@example.com', '9876543220', '1990-01-01', 50000, '{"office": "D.E.Shaw", "university": "IIM Ahmedabad"}'),
    (uuid_generate_v4(), 'passabc', 'Dhruv Gupta', 'dhruv@example.com', '8765432129', '1992-01-15', 69000.00, '{"office": "D.E.Shaw"}'),
    (uuid_generate_v4(), 'pass456', 'Avani Patel', 'avani@example.com', '9876543215', '1987-07-30', 63000, '{"university": "ISB Hyderabad", "school": "Sri Chaitanya School"}'),
    (uuid_generate_v4(), 'passabc', 'Bhavna Shah', 'bhavna@example.com', '8763282658', '1982-10-12', 71000, '{"school": "Delhi Public School", "university": "ISB Hyderabad"}'),
    (uuid_generate_v4(), 'pass456', 'Darsh Jain', 'darsh@example.com', '9876543290', '1984-08-18', 64000, '{"university": "IIM Ahmedabad"}'),
    (uuid_generate_v4(), 'passabc', 'Abhinav Gupta', 'abhinavgupta@example.com', '8765432139', '1980-03-10', 70000.00, '{"university": "IIM Ahmedabad", "school": "Delhi Public School"}'),
    (uuid_generate_v4(), 'pass789', 'Ayaan Kumar', 'ayaan@example.com', '9876543230', '1990-05-05', 46000, '{"office": "D.E.Shaw", "university": "IIM Ahmedabad"}');

-- Generate and insert dummy data into the wallet table for each user
INSERT INTO wallet (wallet_id, user_id , balance)
SELECT
    uuid_generate_v4() AS wallet_id,
    u. user_id, ROUND(CAST(RANDOM() * 100000 AS numeric), 2) AS Balance FROM  user_detail u;


-- Inserting dummy data into the community table
INSERT INTO community ( community_id, community_name,community_tag,community_detail)
VALUES
    (uuid_generate_v4(), 'D.E.Shaw','office','description'),
    (uuid_generate_v4(), 'IIM Ahmedabad','university','description'),
    (uuid_generate_v4(), 'Delhi Public School','school','description'),
    (uuid_generate_v4(), 'ISB Hyderabad','university','description'),
    (uuid_generate_v4(), 'Sri Chaitanya School','school','description');


-- Inserting dummy data into the user_community table



--inserting data into the status table

INSERT INTO status (status_code, status_name) VALUES
                                                  (1, 'Pending'),
                                                  (2, 'Processing'),
                                                  (3, 'Completed');


--inserting dummy data into the borrow request table





-- Inserting dummy data into the transaction table
INSERT INTO  transaction (transaction_id ,receiver_id,sender_id,  amount, transaction_status)
VALUES
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aaradhya@example.com'),  (SELECT user_id from user_detail where email= 'eshaan@example.com' ) ,1000,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aaradhya@example.com'),  (SELECT user_id from user_detail where email= 'abhinavgupta@example.com' ),500 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aaradhya@example.com'),  (SELECT user_id from user_detail where email= 'arya@example.com' ),600 ,3),


    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='devsingh@example.com'),  (SELECT user_id from user_detail where email= 'aditi@example.com' ),1100 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='devsingh@example.com'),  (SELECT user_id from user_detail where email= 'bhavna@example.com' ),500 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='devsingh@example.com'),  (SELECT user_id from user_detail where email= 'akshayjoshi@example.com' ) ,1200,3),



--IIM Ahmedabad community
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ayaan@example.com'),  (SELECT user_id from user_detail where email= 'aarav@example.com' ) ,500,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ayaan@example.com'),  (SELECT user_id from user_detail where email= 'arya@example.com' ) ,1000,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ayaan@example.com'),  (SELECT user_id from user_detail where email= 'advait@example.com' ) ,1000,3),
--D.E.Shaw community
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ayaan@example.com'),  (SELECT user_id from user_detail where email= 'aarav@example.com' ),600 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ayaan@example.com'),  (SELECT user_id from user_detail where email= 'advait@example.com' ) ,300,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ayaan@example.com'),  (SELECT user_id from user_detail where email= 'aaravsingh@example.com' ) ,700,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ayaan@example.com'),  (SELECT user_id from user_detail where email= 'dhruv@example.com' ) ,1100,3),

    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ananya@example.com'),  (SELECT user_id from user_detail where email= 'aaravsingh@example.com' ),1500 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ananya@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ),1000 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ananya@example.com'),  (SELECT user_id from user_detail where email= 'dhruv@example.com' ),500 ,3),

    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='eshaan@example.com'),  (SELECT user_id from user_detail where email= 'aaradhya@example.com'  ),1047 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='abhinavgupta@example.com'),  (SELECT user_id from user_detail where email= 'aaradhya@example.com' ) ,523,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='arya@example.com' ),  (SELECT user_id from user_detail where email='aaradhya@example.com'  ),628 ,3),


    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aditi@example.com'),  (SELECT user_id from user_detail where email= 'devsingh@example.com' ),1182 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='bhavna@example.com' ),  (SELECT user_id from user_detail where email= 'devsingh@example.com' ),537 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='akshayjoshi@example.com'),  (SELECT user_id from user_detail where email= 'devsingh@example.com' ),1290 ,3),


--IIM Ahmedabad community
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aarav@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ),523 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='arya@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ) ,1068,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='advait@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ),1068 ,3),
--D.E.Shaw community

    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aarav@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ) ,636,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='advait@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ),318 ,3),

    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aaravsingh@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ) ,743,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='dhruv@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ),1167 ,3),

    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aaravsingh@example.com'),  (SELECT user_id from user_detail where email= 'ananya@example.com' ) ,1582,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ayaan@example.com'),  (SELECT user_id from user_detail where email= 'ananya@example.com' ),1054 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='dhruv@example.com'),  (SELECT user_id from user_detail where email= 'ananya@example.com' ) ,1582,3);



-- Inserting dummy data into the LendTransaction table


Insert into lend_transaction(lend_transaction_id,transaction_id,borrow_request_id,created_at,last_updated_at) VALUES

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email ='aaradhya@example.com'), sender_id=( SELECT user_id from user_detail where email ='eshaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='aaradhya@example.com'),NOW(),NOW()) ,

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email ='aaradhya@example.com'), sender_id=( SELECT user_id from user_detail where email= 'abhinavgupta@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='aaradhya@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email ='aaradhya@example.com'), sender_id=( SELECT user_id from user_detail where email= 'arya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='aaradhya@example.com'),NOW(),NOW()),




                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email ='devsingh@example.com'), sender_id=( SELECT user_id from user_detail where email= 'aditi@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='devsingh@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email ='devsingh@example.com'), sender_id=( SELECT user_id from user_detail where email= 'bhavna@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='devsingh@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email ='devsingh@example.com'), sender_id=( SELECT user_id from user_detail where email= 'akshayjoshi@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='devsingh@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='ayaan@example.com'), sender_id=( SELECT user_id from user_detail where email='aarav@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ayaan@example.com'),NOW(),NOW()),
                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='ayaan@example.com'), sender_id=( SELECT user_id from user_detail where email= 'arya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ayaan@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='ayaan@example.com'), sender_id=( SELECT user_id from user_detail where email= 'advait@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ayaan@example.com'),NOW(),NOW()),
                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='ayaan@example.com'), sender_id=( SELECT user_id from user_detail where email= 'aaravsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ayaan@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='ayaan@example.com'), sender_id=( SELECT user_id from user_detail where email= 'dhruv@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ayaan@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='ananya@example.com'), sender_id=( SELECT user_id from user_detail where email= 'aaravsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ananya@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='ananya@example.com'), sender_id=( SELECT user_id from user_detail where email= 'dhruv@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ananya@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='ananya@example.com'), sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ananya@example.com'),NOW(),NOW()),


                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='eshaan@example.com'), sender_id=( SELECT user_id from user_detail where email= 'aaradhya@example.com' ) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='eshaan@example.com'),NOW(),NOW()),


                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='abhinavgupta@example.com'), sender_id=( SELECT user_id from user_detail where email= 'aaradhya@example.com' ) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='abhinavgupta@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='arya@example.com'), sender_id=( SELECT user_id from user_detail where email= 'aaradhya@example.com' ) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='arya@example.com'),NOW(),NOW()),


                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='aarav@example.com'), sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='aarav@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='arya@example.com'), sender_id=( SELECT user_id from user_detail where email= 'aayan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='arya@example.com'),NOW(),NOW()),
                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='advait@example.com'), sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='advait@example.com'),NOW(),NOW()),


                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='aditi@example.com'), sender_id=( SELECT user_id from user_detail where email= 'devsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='aditi@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='bhavna@example.com'), sender_id=( SELECT user_id from user_detail where email= 'devsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='bhavna@example.com'),NOW(),NOW()),

                                                                                                                  (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='akshayjoshi@example.com'), sender_id=( SELECT user_id from user_detail where email= 'devsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='akshayjoshi@example.com'),NOW(),NOW());






-- Insert into repayment transaction table'


Insert into repayment_transaction values(repayment_transaction_id, transaction_id, borrow_request_id, created_at, last_updated_at )
Values
    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='eshaan@example.com'), sender_id=( SELECT user_id from user_detail where email ='aaradhya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='aaradhya@example.com'),NOW(),NOW()),
    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email ='abhinavgupta@example.com'), sender_id=( SELECT user_id from user_detail where email= 'aaradhya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='aaradhya@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email ='arya@example.com'), sender_id=( SELECT user_id from user_detail where email= 'aaradhya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='aaradhya@example.com'),NOW(),NOW()),


    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email ='aditi@example.com'), sender_id=( SELECT user_id from user_detail where email= 'devsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='devsingh@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email ='bhavna@example.com'), sender_id=( SELECT user_id from user_detail where email= 'devsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='devsingh@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email ='akshayjoshi@example.com'), sender_id=( SELECT user_id from user_detail where email= 'devsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='devsingh@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='aarav@example.com'), sender_id=( SELECT user_id from user_detail where email='ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ayaan@example.com'),NOW(),NOW()),
    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='arya@example.com'), sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ayaan@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='advait@example.com'), sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ayaan@example.com'),NOW(),NOW()),
    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='aaravsingh@example.com'), sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ayaan@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='dhruv@example.com'), sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ayaan@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='aaravsingh@example.com'), sender_id=( SELECT user_id from user_detail where email= 'ananya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ananya@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='dhruv@example.com'), sender_id=( SELECT user_id from user_detail where email= 'ananya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ananya@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='ayaan@example.com'), sender_id=( SELECT user_id from user_detail where email= 'ananya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email ='ananya@example.com'),NOW(),NOW()),


    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='aaradhya@example.com'), sender_id=( SELECT user_id from user_detail where email= 'eshaan@example.com' ) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='eshaan@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='aaradhya@example.com'), sender_id=( SELECT user_id from user_detail where email= 'abhinavgupta@example.com' ) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='abhinavgupta@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='aaradhya@example.com'), sender_id=( SELECT user_id from user_detail where email= 'arya@example.com' ) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='arya@example.com'),NOW(),NOW()),


    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='ayaan@example.com'), sender_id=( SELECT user_id from user_detail where email= 'aarav@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='aarav@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='ayaan@example.com'), sender_id=( SELECT user_id from user_detail where email= 'arya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='arya@example.com'),NOW(),NOW()),
    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='ayaan@example.com'), sender_id=( SELECT user_id from user_detail where email= 'advait@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='advait@example.com'),NOW(),NOW()),

    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='devsingh@example.com'), sender_id=( SELECT user_id from user_detail where email= 'aditi@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='aditi@example.com'),NOW(),NOW()),
    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='devsingh@example.com'), sender_id=( SELECT user_id from user_detail where email= 'bhavna@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='bhavna@example.com'),NOW(),NOW()),
    (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='devsingh@example.com'), sender_id=( SELECT user_id from user_detail where email= 'akshayjoshi@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='akshayjoshi@example.com'),NOW(),NOW());





-- Insert dummy data into borrow_request_community_map

-- Create borrow_request_community_map
INSERT INTO borrow_request_community_map (borrow_request_id, community_id)
SELECT br.borrow_request_id, uc.community_id
FROM borrow_request br
         JOIN user_detail ud ON br.borrower_id = ud.user_id
         JOIN user_community_map uc ON ud.user_id = uc.user_id;


-- borrow_request


Insert into borrow_request (borrow_request_id,borrower_id,return_period_days,monthly_interest_rate,borrow_status, requested_amount, collected_amount, default_fine,default_count,created_at,last_modified_at)
values

    (uuid_generate_v4(),(select user_id from user_detail where name='Aarav Patel'),60,50,1,2500.00,0.00,0.00,0,NOW(),NOW()),

    (uuid_generate_v4(),(select user_id from user_detail where name='Aaradhya Sharma'),35,50,3,2200.00,2200.00,0.00,0,NOW(),NOW()),

    (uuid_generate_v4(),(select user_id from user_detail where name='Ananya Singh'),45,50,1,2700.00,0.00,0.00,0,NOW(),NOW()),
    (uuid_generate_v4(),(select user_id from user_detail where name='Dev Singh'),55,50,3,2800.00,2800.00,0.00,0,NOW(),NOW()),


    (uuid_generate_v4(),(select user_id from user_detail where name='Aarav Singh'),40,50,1,2000.00,0.00,0.00,0,NOW(),NOW()),


    (uuid_generate_v4(),(select user_id from user_detail where name='Garima Mishra'),25,50,1,2300.00,1500.00,0.00,0,NOW(),NOW()),

    (uuid_generate_v4(),(select user_id from user_detail where name='Ayaan Kumar'),50,50,3,2500.00,2500.00,0.00,0,NOW(),NOW()),

    (uuid_generate_v4(),(select user_id from user_detail where name='Avani Patel'),30,50,1,2200.00,0.00,0.00,0,NOW(),NOW()),

    (uuid_generate_v4(),(select user_id from user_detail where name='Charvi Sharma'),40,50,1,2700.00,0.00,0.00,0,NOW(),NOW()),


    (uuid_generate_v4(),(select user_id from user_detail where name='Darsh Jain'),55,50,1,2800.00,2800.00,0.00,0,NOW(),NOW()),

    (uuid_generate_v4(),(select user_id from user_detail where name='Dhruv Gupta'),35,50,1,2000.00,0.00,0.00,0,NOW(),NOW()),

    (uuid_generate_v4(),(select user_id from user_detail where name='Ayaan Kumar'),45,50,1,2700.00,2700.00,0.00,0,NOW(),NOW()),

    (uuid_generate_v4(),(select user_id from user_detail where name='Avani Patel'),30,50,1,2200.00,0.00,0.00,0,NOW(),NOW()),

    (uuid_generate_v4(),(select user_id from user_detail where name='Arya Dubey'),50,50,1,2800.00,0.00,0.00,0,NOW(),NOW()),

    (uuid_generate_v4(),(select user_id from user_detail where name='Ananya Singh'),40,50,3,3000.00,3000.00,0.00,0,NOW(),NOW()),

    (uuid_generate_v4(),(select user_id from user_detail where name='Akshay Joshi'),35,50,1,0.00,2000.00,0.00,0,NOW(),NOW()),

    (uuid_generate_v4(),(select user_id from user_detail where name='Advait Tiwari'),55,50,1,2500.00,0.00,0.00,0,NOW(),NOW());

