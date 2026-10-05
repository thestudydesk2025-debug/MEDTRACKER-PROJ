# Medicine Stock and Expiry Tracker

A complete, professional, full-stack Java web application designed for hospital pharmacies to manage medicine inventory, track expiry dates, generate automatic alerts, and assist staff in preventing stock-outs.

## Objective
A hospital pharmacy needs a database-based system to automatically monitor medicine stock and expiry dates and assist pharmacy staff in preventing stock-outs and expired medicines.

## Features
- **Authentication & Security:** Secure login, access-key based registration, password hashing (BCrypt).
- **Dashboard Analytics:** Animated statistics cards, Chart.js integration, dynamic summary.
- **Medicine Management:** Full CRUD operations for medicines.
- **Inventory Control:** Track Stock In, Stock Out, Damaged, and Expired removals. Complete transaction history.
- **Expiry Tracker:** Automatically identifies medicines expiring within 30 days or already expired.
- **Stock Alerts:** Automatically detects low stock (below minimum threshold) and out of stock medicines.
- **Category & Supplier Management:** Maintain records of suppliers and medicine categories.
- **Professional UI/UX:** Built with Bootstrap 5, FontAwesome, Google Fonts, and custom CSS for a modern, responsive, and animated interface.

## Technology Stack
- **Backend:** Java 17, Spring Boot 3, Spring MVC, Spring Data JPA, Spring Security
- **Database:** MySQL
- **Frontend:** HTML5, CSS3, JavaScript, Thymeleaf, Bootstrap 5
- **Charts:** Chart.js
- **Build Tool:** Maven

## System Requirements
- Java Development Kit (JDK) 17+
- Maven 3.6+
- MySQL Server 8.0+
- Modern Web Browser (Chrome, Edge, Brave)

## Installation & Database Setup
1. Create a MySQL database named `medicinetracker`:
   ```sql
   CREATE DATABASE medicinetracker;
   ```
2. The application is configured to connect to `jdbc:mysql://localhost:3306/medicinetracker` with username `root` and password `root`. Update `src/main/resources/application.properties` if your MySQL credentials differ.
3. The application uses `spring.jpa.hibernate.ddl-auto=update`, so tables will be created automatically.

## Running the Application
1. Open a terminal in the project root directory.
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
4. Alternatively, run the built JAR:
   ```bash
   java -jar target/medicinetracker-0.0.1-SNAPSHOT.jar
   ```

## Using the Application
Open your browser and navigate to:
[http://localhost:8080/](http://localhost:8080/)

### Demo Credentials (Auto-Initialized)
Upon the first startup, the system automatically creates demo data (categories, suppliers, medicines with varying stock/expiry states) and two users:

**Admin User:**
- Username: `admin`
- Password: `admin123`

**Staff User:**
- Username: `staff`
- Password: `staff123`

**Registration Access Key:**
- If you wish to register a new user from the login page, use the access key: `MEDTRACK2026`
