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
1. You can download and install PostgreSQL from the [official website](https://www.postgresql.org/). For steps of installation follow this document [Installation Steps]([MySQLWindows.docx](https://github.com/VaniThapar/UnityLend/files/14361101/MySQLWindows.docx)).
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
The class diagram can be accessed at [Unity_Lend_Class_Diagram](https://lucid.app/lucidchart/39966a62-7f72-496b-9ed5-c1dac1ad927d/edit?viewport_loc=-399%2C-176%2C3186%2C1446%2CHWEp-vi-RSFO&invitationId=inv_48100e8f-d365-4ba3-a794-3cf28767bf10).

# E R Diagram:
The ER diagram can be accessed at [Unity_Lend_ER_Diagram](https://drive.google.com/file/d/1gyd7sokLba8Ai6vOULL_auioWAXFHsps/view?usp=drive_link).

# Flow diagram for the lender
The Flow diagram demonstrating the operations of a lender can be accessed at [Flow_Diagra_For_Lender](https://app.diagrams.net/#G1dLFlWWprYTwwtwjXvi5WEJS94RS7x-uo#%7B%22pageId%22%3A%22W7Y_DPB_WjcdcxQGYy3G%22%7D).

# Flow diagram for raising a borrow request
The Flow diagram demonstrating the steps of raising a borrow request can be accessed at [Flow_Diagram_For_Raising_Request](https://drive.google.com/file/d/1pA5A4blyuBDjGBg7mWg6v0XT-DEOwUn8/view?usp=sharing).
