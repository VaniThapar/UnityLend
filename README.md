# <div align="center">Welcome to UnityLend</div>

UnityLend is a groundbreaking platform designed to foster financial cooperation and empowerment within communities. Key features include registration, creation, and joining of community channels, transparent borrowing processes, and a hassle-free repayment system. UnityLend not only addresses immediate financial needs but also cultivates a sense of trust and collaboration among community members.

# Project Requirements
The software requirements for the application can be accessed through [Unity_Lend_Project_Requirements](https://github.com/VaniThapar/UnityLend/files/14396972/unity_lend.docx).

# DB Design
The Database design for the application can be accessed through [Unity_Lend_DB_Design](https://docs.google.com/document/d/1yztmCw-_h2KMAJjfsULA9HYDZTXnV1R2zH4husLGwB4/edit).

# Installation

Follow these steps to set up and run the project locally.

### 1. Clone the Repository
```bash
git clone https://github.com/VaniThapar/UnityLend.git
```
### 2. Install and Set Up PostgreSQL
1. You can download and install PostgreSQL from the [official website](https://www.postgresql.org/). For steps of installation follow this document [PostgreSQL_Installation_Steps.docx](https://github.com/VaniThapar/UnityLend/files/14628883/PostgreSQL_Installation_Steps.docx).
2. After installing PostgreSQL, open a terminal or command prompt and enter the following command to create a new database
```bash
createdb your_database
```
3. Create a new user for the application with the necessary privileges. Replace your_username and your_password with your desired username and password.
```bash
createuser --interactive --pwprompt
```
4. Grant necessary privileges to the user on the database.
```bash
psql
GRANT ALL PRIVILEGES ON DATABASE your_database TO your_username;
```
### 3. Configure Database Connection
Modify the application.properties file in your project to configure the database connection settings.
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```
### 4. Build the Application
```bash
mvn clean install
```
### 5. Run the Application
```bash
mvn spring-boot:run
```
### 6. Access the Application
Once the application is running, Tomcat server starts running at http://localhost:8085.

# Class Diagram
The class diagram can be accessed at [Unity_Lend_Class_Diagram](https://drive.google.com/file/d/1etFI_mWnXqV1lAzYVuepEDqdyavemlht/view).

# E R Diagram

The ER diagram can be accessed at [Unity-Lend-ER-Diagram](https://github.com/VaniThapar/UnityLend/files/14731925/Unity-Lend-EER-Diagram.pdf)

# Flow diagrams
Flow Diagarm for User Registration and Community Generation : [User Registration and Community Creation](https://github.com/VaniThapar/UnityLend/files/14731893/User.Registration.and.Community.Creation-Page-2.drawio.pdf)
 
Flow Diagram for raising a Borrow Request : [Flow_Diagram_For_Raising_Borrow_Request](https://github.com/VaniThapar/UnityLend/files/14731894/Flow_Diagram_For_Raising_Borrow_Request.drawio.pdf)

Flow Diagarm for generating EMI Schedule after Borrow Request is fulfilled : [Flow_Diagram_For_EMI_Generation](https://github.com/VaniThapar/UnityLend/files/14731899/Flow_Diagram_For_Lender.drawio.pdf)

Flow Diagram for Lender lending against a Borrow Request : [Flow_Diagram_For_Lender](https://github.com/VaniThapar/UnityLend/files/14731912/Flow_Diagram_For_Lender.drawio.pdf)

Flow Diagram for Borrower repaying the monthly EMI against a Borrow Request : [Flow_Diagram_for_Repayment](https://github.com/VaniThapar/UnityLend/files/14731900/Flow_Diagram_for_Repayment.drawio.pdf)




