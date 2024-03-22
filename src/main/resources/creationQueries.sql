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





--inserting data into the status table



INSERT INTO status (status_code, status_name) VALUES

                                                  (1, 'Pending'),

                                                  (2, 'Processing'),

                                                  (3, 'Completed');





-- Insert dummy data into borrow_request_community_map

INSERT INTO borrow_request_community_map (borrow_request_id, community_id)

SELECT br.borrow_request_id, uc.community_id

FROM borrow_request br

         JOIN user_detail ud ON br.borrower_id = ud.user_id

         JOIN user_community_map uc ON ud.user_id = uc.user_id;





--Insert dummy data into borrow_request

Insert into borrow_request(borrow_request_id,borrower_id,return_period_days,monthly_interest_rate,borrow_status, requested_amount, collected_amount, default_fine,default_count,created_at,last_modified_at)

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





-- Inserting dummy data into the user_community_map table



INSERT INTO user_community_map(user_id, community_id) VALUES

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'garima@example.com'), (SELECT community_id from community where community_name='Sri Chaitanya School')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'advait@example.com'), (SELECT community_id from community where community_name='IIM Ahmedabad')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'eeshan@example.com'), (SELECT community_id from community where community_name='Delhi Public School')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'aaradhya@example.com'), (SELECT community_id from community where community_name='Delhi Public School')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'aaradhya@example.com'), (SELECT community_id from community where community_name='ISB Hyderabad')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'arya@example.com'), (SELECT community_id from community where community_name='IIM Ahmedabad')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'arya@example.com'), (SELECT community_id from community where community_name='Delhi Public School')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'ananya@example.com'), (SELECT community_id from community where community_name='Sri Chaitanya School')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'ananya@example.com'), (SELECT community_id from community where community_name='D.E.Shaw')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'devsingh@example.com'), (SELECT community_id from community where community_name='ISB Hyderabad')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'aditi@example.com'), (SELECT community_id from community where community_name='ISB Hyderabad')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'aditi@example.com'), (SELECT community_id from community where community_name='Sri Chaitanya School')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'akshayjoshi@example.com'), (SELECT community_id from community where community_name='Delhi Public School')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'akshayjoshi@example.com'), (SELECT community_id from community where community_name='ISB Hyderabad')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'charvi@example.com'), (SELECT community_id from community where community_name='Sri Chaitanya School')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'aaravsingh@example.com'), (SELECT community_id from community where community_name='Sri Chaitanya School')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'aaravsingh@example.com'), (SELECT community_id from community where community_name='D.E.Shaw')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'aarav@example.com'), (SELECT community_id from community where community_name='D.E.Shaw')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'aarav@example.com'), (SELECT community_id from community where community_name='IIM Ahmedabad')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'dhruv@example.com'), (SELECT community_id from community where community_name='D.E.Shaw')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'avani@example.com'), (SELECT community_id from community where community_name='ISB Hyderabad')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'avani@example.com'), (SELECT community_id from community where community_name='Sri Chaitanya School')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'bhavna@example.com'), (SELECT community_id from community where community_name='Delhi Public School')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'bhavna@example.com'), (SELECT community_id from community where community_name='ISB Hyderabad')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'darsh@example.com'), (SELECT community_id from community where community_name='IIM Ahmedabad')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'abhinavgupta@example.com'), (SELECT community_id from community where community_name='IIM Ahmedabad')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'abhinavgupta@example.com'), (SELECT community_id from community where community_name='Delhi Public School')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'ayaan@example.com'), (SELECT community_id from community where community_name='D.E.Shaw')),

                                                          ((SELECT user_id  FROM  user_detail WHERE email = 'ayaan@example.com'), (SELECT community_id from community where community_name='IIM Ahmedabad'));


-- Insert into transaction table


INSERT INTO  transaction (transaction_id ,receiver_id,sender_id,  amount, transaction_status)
VALUES
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aaradhya@example.com'),  (SELECT user_id from user_detail where email= 'eshaan@example.com' ) ,1000,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aaradhya@example.com'),  (SELECT user_id from user_detail where email= 'abhinavgupta@example.com' ),500 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aaradhya@example.com'),  (SELECT user_id from user_detail where email= 'arya@example.com' ),600 ,3),


    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='devsingh@example.com'),  (SELECT user_id from user_detail where email= 'aditi@example.com' ),1100 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='devsingh@example.com'),  (SELECT user_id from user_detail where email= 'bhavna@example.com' ),500 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='devsingh@example.com'),  (SELECT user_id from user_detail where email= 'akshayjoshi@example.com' ) ,1200,3),


    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ayaan@example.com'),  (SELECT user_id from user_detail where email= 'aarav@example.com' ) ,500,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ayaan@example.com'),  (SELECT user_id from user_detail where email= 'arya@example.com' ) ,1000,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ayaan@example.com'),  (SELECT user_id from user_detail where email= 'advait@example.com' ) ,1000,3),

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

    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aarav@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ),523 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='arya@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ) ,1068,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='advait@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ),1068 ,3),


    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aaravsingh@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ) ,743,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='dhruv@example.com'),  (SELECT user_id from user_detail where email= 'ayaan@example.com' ),1167 ,3),

    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='aaravsingh@example.com'),  (SELECT user_id from user_detail where email= 'ananya@example.com' ) ,1582,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='ayaan@example.com'),  (SELECT user_id from user_detail where email= 'ananya@example.com' ),1054 ,3),
    (uuid_generate_v4(), (SELECT user_id from user_detail where email ='dhruv@example.com'),  (SELECT user_id from user_detail where email= 'ananya@example.com' ) ,1582,3);


-- Insert into lend_transaction table

Insert into lend_transaction(lend_transaction_id,transaction_id,borrow_request_id,created_at,last_updated_at)values
                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email =‘aaradhya@example.com’) and sender_id=( SELECT user_id from user_detail where email =‘eshaan@example.com’) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘aaradhya@example.com’)),NOW(),NOW()) ,

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email =‘aaradhya@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'abhinavgupta@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘aaradhya@example.com’),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email =‘aaradhya@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'arya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘aaradhya@example.com’),NOW(),NOW()),




                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email =‘devsingh@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'aditi@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘devsingh@example.com’),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email =‘devsingh@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'bhavna@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘devsingh@example.com’),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email =‘devsingh@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'akshayjoshi@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘devsingh@example.com’),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘ayaan@example.com’)and sender_id=( SELECT user_id from user_detail where email='aarav@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ayaan@example.com’),NOW(),NOW()),
                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘ayaan@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'arya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ayaan@example.com’),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘ayaan@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'advait@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ayaan@example.com’),NOW(),NOW()),
                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘ayaan@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'aaravsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ayaan@example.com’),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘ayaan@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'dhruv@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ayaan@example.com’),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘ananya@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'aaravsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ananya@example.com’),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘ananya@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'dhruv@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ananya@example.com’),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘ananya@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ananya@example.com’),NOW(),NOW()),


                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='eshaan@example.com')and sender_id=( SELECT user_id from user_detail where email= ‘aaradhya@example.com’ ) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='eshaan@example.com'),NOW(),NOW()),


                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='abhinavgupta@example.com')and sender_id=( SELECT user_id from user_detail where email= ‘aaradhya@example.com’ ) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='abhinavgupta@example.com'),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='arya@example.com')and sender_id=( SELECT user_id from user_detail where email= ‘aaradhya@example.com’ ) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='arya@example.com'),NOW(),NOW()),


                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘aarav@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email=‘aarav@example.com’),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘arya@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'aayan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email=‘arya@example.com’),NOW(),NOW()),
                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘advait@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email=‘advait@example.com’),NOW(),NOW()),


                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘aditi@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'devsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email=‘aditi@example.com’),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘bhavna@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'devsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email=‘bhavna@example.com’),NOW(),NOW()),

                                                                                                                 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘akshayjoshi@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'devsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email=‘akshayjoshi@example.com’),NOW(),NOW());


-- Insert into repayment transaction table

Insert into repayment_transaction values(repayment_transaction_id, transaction_id, borrow_request_id, created_at, last_updated_at )
    Values
(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘eshaan@example.com’)and sender_id=( SELECT user_id from user_detail where email =‘aaradhya@example.com’) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘aaradhya@example.com’),NOW(),NOW()),

 (uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email =‘abhinavgupta@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'aaradhya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘aaradhya@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email =‘arya@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'aaradhya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘aaradhya@example.com’),NOW(),NOW()),


(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email =‘aditi@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'devsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘devsingh@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email =‘bhavna@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'devsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘devsingh@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email =‘akshayjoshi@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'devsingh@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘devsingh@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘aarav@example.com’)and sender_id=( SELECT user_id from user_detail where email='ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ayaan@example.com’),NOW(),NOW()),
(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘arya@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ayaan@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘advait@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ayaan@example.com’),NOW(),NOW()),
(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘aaravsingh@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ayaan@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘dhruv@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'ayaan@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ayaan@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘aaravsingh@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'ananya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ananya@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘dhruv@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'ananya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ananya@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘ayaan@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'ananya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email =‘ananya@example.com’),NOW(),NOW()),


(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='aaradhya@example.com')and sender_id=( SELECT user_id from user_detail where email= ‘eshaan@example.com’ ) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='eshaan@example.com'),NOW(),NOW()),


(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='aaradhya@example.com')and sender_id=( SELECT user_id from user_detail where email= ‘abhinavgupta@example.com’ ) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='abhinavgupta@example.com'),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email='aaradhya@example.com')and sender_id=( SELECT user_id from user_detail where email= ‘arya@example.com’ ) ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email='arya@example.com'),NOW(),NOW()),


(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘ayaan@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'aarav@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email=‘aarav@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘ayaan@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'arya@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email=‘arya@example.com’),NOW(),NOW()),
(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘ayaan@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'advait@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email=‘advait@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘devsingh@example.com’)and sender_id=( SELECT user_id from user_detail where email= aditi@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email=‘aditi@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘devsingh@example.com’)and sender_id=( SELECT user_id from user_detail where email= ‘bhavna@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email=‘bhavna@example.com’),NOW(),NOW()),

(uuid_generate_v4(),(select  transaction_id from transaction where receiver_id=(SELECT user_id from user_detail where email=‘devsingh@example.com’)and sender_id=( SELECT user_id from user_detail where email= 'akshayjoshi@example.com') ) , (select borrow_request_id from borrow_request where borrower_id=(SELECT user_id from user_detail where email=‘akshayjoshi@example.com’),NOW(),NOW());