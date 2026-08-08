# Smart Library Manager (Spring Boot Web App)

A modern, web-based Library Management System. This project uses **Spring Boot**, **Spring JDBC**, and **Thymeleaf** to provide a sleek, glassmorphism-styled web interface for managing books, authors, publishers, and library members.

---

## Features

- **Modern Glassmorphism UI:** A sleek, responsive web interface built with pure CSS and Thymeleaf templating. Dark-mode aesthetics with vibrant gradients.
- **Role-Based Access:** Separate dashboards and functionalities for Librarians and Members.
- **Database Driven:** Powered by MySQL using `JdbcTemplate` for safe and efficient SQL queries.
- **Librarian Capabilities:**
  - Add / Update / Delete Books
  - Manage Authors and Publishers
  - View Member Borrow History and active records
  - Reset member penalties
- **Member Capabilities:**
  - Self-Registration system
  - Issue and Return Books
  - Search Catalog by Book, Author, or Publisher
  - Auto-penalty calculation for overdue books

---

## Technologies Used

- **Backend:** Java 22, Spring Boot, Spring MVC
- **Data Access:** Spring JDBC (`JdbcTemplate`), MySQL, `mysql-connector-j`
- **Frontend:** HTML5, CSS3, Thymeleaf Templates
- **Build Tool:** Maven

---

## Project Setup

### 1. Database Setup
1. Ensure you have a local instance of **MySQL** running on port `3306`.
2. Locate the `database_setup.sql` script inside the `src/main/resources/` directory.
3. Import the SQL script into your MySQL database to automatically generate the database schema, tables, dummy data, and stored procedures.
   ```bash
   mysql -u root -p < src/main/resources/database_setup.sql
   ```

### 2. Configure Credentials
Update your database configuration in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library
spring.datasource.username=root
spring.datasource.password=YourMySQLPassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 3. Compile and Run
Use the included Maven wrapper to build and run the application natively:
```powershell
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```
Once the server starts, open your web browser and navigate to:
**http://localhost:8080**

**Default Librarian Credentials:**
- User ID: `1`
- Password: `admin123`

---

## Project Structure

```text
Library-Management-System/
│
├── src/main/java/com/smartlibrary/
│   ├── controller/      # Spring MVC Web Controllers (Auth, Librarian, Member)
│   ├── model/           # Data Domain Models (Book, Member, Author, etc.)
│   ├── repository/      # Spring JDBC Repositories
│   └── LibraryApplication.java
│
├── src/main/resources/
│   ├── static/css/      # Glassmorphism Style CSS
│   ├── templates/       # Thymeleaf HTML Views
│   ├── application.properties # Spring configuration
│   └── database_setup.sql     # Database init script
│
├── pom.xml              # Maven dependencies configuration
└── README.md
```

---

## Author
Jatin Kumar Verma
