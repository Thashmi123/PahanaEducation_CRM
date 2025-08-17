# 📚 Pahana Education Bookshop Management System

A comprehensive web-based bookshop management system built with Java EE, designed for Pahana Education in Colombo City. This system provides efficient management of customer accounts, inventory, and billing operations.

## 🚀 Features

### Core Functionalities

- **User Authentication & Authorization**

  - Secure login system with role-based access (Admin/Cashier)
  - Session management with automatic timeout
  - Audit logging for security

- **Customer Management**

  - Add new customer accounts
  - Edit customer information
  - Search and view customer records
  - Comprehensive customer database

- **Inventory Management**

  - Add, update, and delete items
  - Stock quantity tracking
  - Price management
  - Search and filter items

- **Billing System**

  - Create customer bills
  - Automatic total calculation
  - Bill history tracking
  - Invoice generation

- **User Interface**
  - Modern, responsive design
  - Mobile-friendly interface
  - Intuitive navigation
  - Professional styling

## 🛠️ Technology Stack

- **Backend**: Java EE (Servlets, JSP)
- **Database**: MySQL 8.0+
- **Frontend**: HTML5, CSS3, JavaScript
- **Build Tool**: Maven 3.6+
- **Server**: Apache Tomcat 9.0+
- **Java Version**: JDK 17+

## 📋 Prerequisites

Before running this application, ensure you have:

- **Java Development Kit (JDK) 17** or higher
- **MySQL 8.0** or higher
- **Apache Tomcat 9.0** or higher
- **Maven 3.6** or higher
- **Git** (for cloning the repository)

## 🗄️ Database Setup

### 1. Create Database

1. Open MySQL Workbench or MySQL command line
2. Run the `database_init.sql` script to create the database and tables
3. The script will create:
   - Database: `pahanaedu`
   - Tables: `users`, `customers`, `items`, `bills`, `bill_items`, `audit_logs`
   - Sample data for testing

### 2. Database Configuration

Update the database connection settings in `src/main/java/com/pahanaedu/util/DBConnection.java`:

```java
private static final String URL = "jdbc:mysql://127.0.0.1:3306/pahanaedu?useSSL=false&serverTimezone=UTC";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

### 3. Default Users

The system comes with two default users:

- **Admin**: username: `admin`, password: `admin123`
- **Cashier**: username: `cashier`, password: `cashier123`

## 🚀 Running the Application

### Method 1: Using Maven Tomcat Plugin

1. **Clone the repository**:

   ```bash
   git clone <repository-url>
   cd PahanaEducation
   ```

2. **Build the project**:

   ```bash
   mvn clean package
   ```

3. **Run with Tomcat**:

   ```bash
   mvn tomcat7:run
   ```

4. **Access the application**:
   - Open your browser and go to: `http://localhost:8080`
   - Login with the default credentials

### Method 2: Deploy to Tomcat Server

1. **Build the WAR file**:

   ```bash
   mvn clean package
   ```

2. **Deploy to Tomcat**:

   - Copy the generated `target/PahanaEducation.war` file
   - Paste it into Tomcat's `webapps` directory
   - Start Tomcat server

3. **Access the application**:
   - Navigate to: `http://localhost:8080/PahanaEducation`

## 📁 Project Structure

```
PahanaEducation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/pahanaedu/
│   │   │       ├── model/          # Data models
│   │   │       ├── dao/            # Data Access Objects
│   │   │       ├── servlet/        # Servlet controllers
│   │   │       └── util/           # Utility classes
│   │   ├── resources/              # Configuration files
│   │   └── webapp/                 # Web resources
│   │       ├── WEB-INF/           # Web configuration
│   │       ├── *.jsp              # JSP pages
│   │       └── web.xml            # Web deployment descriptor
│   └── test/                       # Test files
├── target/                         # Compiled output
├── pom.xml                        # Maven configuration
├── database_init.sql              # Database setup script
└── README.md                      # This file
```

## 🔧 Configuration

### Web Application Configuration

- **Session Timeout**: 30 minutes (configurable in `web.xml`)
- **Welcome File**: `login.jsp`
- **Error Handling**: Custom error pages and messages

### Database Configuration

- **Connection Pool**: Basic JDBC connection management
- **Transaction Management**: Manual transaction handling
- **SQL Injection Protection**: Prepared statements throughout

## 🧪 Testing

### Running Tests

```bash
mvn test
```

### Test Coverage

The project includes JUnit 5 tests for:

- Data Access Objects (DAOs)
- Model classes
- Utility functions

## 🔒 Security Features

- **Input Validation**: Server-side validation for all user inputs
- **SQL Injection Prevention**: Prepared statements for database queries
- **Session Management**: Secure session handling with timeout
- **Role-Based Access**: Different permissions for different user types
- **Audit Logging**: Track all system activities

## 📱 User Interface

### Design Principles

- **Responsive Design**: Works on all device sizes
- **Modern UI**: Clean, professional appearance
- **User Experience**: Intuitive navigation and workflows
- **Accessibility**: High contrast and readable fonts

### Browser Support

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+

## 🚨 Troubleshooting

### Common Issues

1. **Database Connection Error**:

   - Verify MySQL is running
   - Check database credentials in `DBConnection.java`
   - Ensure database `pahanaedu` exists

2. **Compilation Errors**:

   - Ensure JDK 17+ is installed
   - Check Maven version (3.6+)
   - Run `mvn clean` before building

3. **Runtime Errors**:

   - Check Tomcat logs for detailed error messages
   - Verify all dependencies are properly configured
   - Ensure database tables are created

4. **Page Not Loading**:
   - Check if Tomcat is running on correct port
   - Verify WAR file is properly deployed
   - Check browser console for JavaScript errors

### Getting Help

If you encounter issues:

1. Check the application logs in Tomcat
2. Review the database connection settings
3. Verify all prerequisites are met
4. Check the help section within the application
5. Contact system administrator

## 📈 Future Enhancements

Planned features for future versions:

- **Advanced Reporting**: Sales analytics and customer insights
- **Email Integration**: Automated customer communications
- **Payment Processing**: Online payment gateway integration
- **Mobile App**: Native mobile application
- **Multi-language Support**: Sinhala and Tamil language support
- **Advanced Search**: Full-text search capabilities
- **Data Export**: Excel and PDF export functionality

## 🤝 Contributing

To contribute to this project:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is developed for educational purposes as part of the Advanced Programming course (CIS6003).

## 📞 Support

For technical support or questions:

- **Email**: support@pahanaedu.com
- **Phone**: +94 11 234 5678
- **Address**: Pahana Education, Colombo City, Sri Lanka

---

**Note**: This system is designed for educational purposes and should be thoroughly tested before use in production environments.
