# DB Design:
https://docs.google.com/document/d/1yztmCw-_h2KMAJjfsULA9HYDZTXnV1R2zH4husLGwB4/edit

# A STEP BY STEP GUIDE OF MYSQL INSTALLATION:
[MySQLWindows.docx](https://github.com/VaniThapar/UnityLend/files/14361101/MySQLWindows.docx)

# To run the project follow the below steps:
Clone the project : git clone https://github.com/VaniThapar/UnityLend.git
Compile the project : mvn clean install
Run the project: mvn spring:boot run

# Class Diagram:
Lucidchart Link:
https://lucid.app/lucidchart/39966a62-7f72-496b-9ed5-c1dac1ad927d/edit?viewport_loc=-399%2C-176%2C3186%2C1446%2CHWEp-vi-RSFO&invitationId=inv_48100e8f-d365-4ba3-a794-3cf28767bf10

# E R Diagram:
Google Drive Link: 
https://drive.google.com/file/d/1gyd7sokLba8Ai6vOULL_auioWAXFHsps/view?usp=drive_link

![UnityLend drawio](https://github.com/VaniThapar/UnityLend/assets/91086564/d81ee720-ae83-4ba6-810b-1676812c2d48)

# Project Requirements 
Project Name: UnityLend (Community Lending and borrowing Platform)

 

Project Summary:

UnityLend is a groundbreaking platform designed to foster financial cooperation and empowerment within communities. Envisioned as a dynamic community lending ecosystem, the platform enables users to seamlessly borrow and lend money among themselves. Key features include registration, creation, and joining of community channels, transparent borrowing processes, and a hassle-free repayment system. UnityLend not only addresses immediate financial needs but also cultivates a sense of trust and collaboration among community members. UnityLend stands as a cornerstone for community-driven financial support, redefining the way communities come together to support each other's financial goals.

 

Project Requirements:

Must have requirements: 

User registration with community tags (i.e. - Place of residence, School, College, Workplace etc.) along with basic details
Password based user authentication. 
Based on user tags the application takes care of formation of communities.
Define the borrowing and lending limit for user based on the income.
Feature to create/ raise a borrowing request.
Feature to list the borrowing requests in the user community and option to lend.
User wallet management feature with pre-defined virtual money with option to lend and borrow from wallet money.
Show the repayment schedule to the borrower with the date and amount to be repaid.
Show the repayment schedule to the lender with date and amount he will be getting back.
 

Good to have requirements:

User rating management through community member feedback and loan repayment records.
Overall community feedback/rating based on the loan repayment schedule miss.
Fine on overdue payment schedule.
Minimal platform fee for every successful borrowing request.
Future enhancement:

Integrate with the actual money wallet or bank accounts. 
Enhance user verification and income source.
Loan recovery and penalizing mechanisms.
Project output: 

The culmination of the UnityLend project yields a fully operational and user-centric platform designed to revolutionize community-based lending. The output encompasses an interface to empower users to seamlessly navigate through features such as user registration, community creation, and participation in borrowing and lending activities. Transparent financial tracking mechanisms ensure accountability, with detailed records showcasing lending, borrowing, and associated interest. With further enhancements, UnityLend has the potential to deliver a secure and efficient financial ecosystem. The platform's scalability, security measures, and established support mechanisms underline its commitment to providing a future-ready solution that not only meets immediate financial needs within communities but also fosters trust and financial empowerment.

 

Tech stack:


    Database: SQL

    Backend: Spring boot, Java

    UI: JavaScript




Project plan:


week 1:

                Basic infrastructure setup:
                                1. Database setup
                                2. Spring boot setup
                                3. UI setup
                                4. Testing the setup with one dummy url
week 2:

                1. Database: Database design 

                2. Backend: User profile management 

                3. UI: User profile management page 

week 3:

                1. Database: Minor enhancement in DB design

                2. Backend: 

                                1. Handling the community formation based on the User tags, and mapping the community with users 

                                2. Wallet service for every user: management of debit and credit

                                3. Management of user borrow feature and implement various required services for borrowing

                3. UI:

                                1. Create community UI page and show the community based on the users along with user count

                                2. Wallet page for every user: 

                                                1. shows current balance

                                                2. total Borrowed balance

                                                3. Total lent balance

                                                3. Inflow for next 1 month



~Puru




