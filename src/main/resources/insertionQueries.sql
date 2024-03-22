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



CREATE TABLE transaction(

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