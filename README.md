# GRAD-PROJECT-MAJD

## Database Configuration

This project is configured to work with multiple database systems, allowing for easy switching between them. By default, it's set up to use PostgreSQL, but it can be easily configured to use Oracle, MySQL, or Microsoft SQL Server.

### Default Configuration (PostgreSQL)

The project is pre-configured to use PostgreSQL with the following settings:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gradproject
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Switching to a Different Database

To switch to a different database system, follow these steps:

1. Open `src/main/resources/application.properties`
2. Comment out the current database configuration (PostgreSQL by default)
3. Uncomment the configuration for your desired database system
4. Comment out the current Hibernate dialect
5. Uncomment the Hibernate dialect for your desired database system

#### Oracle Configuration Example
```properties
# Comment out PostgreSQL configuration
#spring.datasource.url=jdbc:postgresql://localhost:5432/gradproject
#spring.datasource.username=postgres
#spring.datasource.password=postgres
#spring.datasource.driver-class-name=org.postgresql.Driver

# Uncomment Oracle configuration
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=system
spring.datasource.password=oracle
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# Comment out PostgreSQL dialect
#spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Uncomment Oracle dialect
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
```

### Database Schema Management

The project uses Liquibase for database schema management, which ensures that the database schema is consistent across different database systems. The schema migrations are defined in XML files located in `src/main/resources/db.changelog/changes/`.

### Creating the Database

Before running the application, make sure to create the database:

#### PostgreSQL
```sql
CREATE DATABASE gradproject;
```

#### Oracle
```sql
-- Connect as system user
CREATE USER system IDENTIFIED BY oracle;
GRANT CONNECT, RESOURCE, DBA TO system;
```

#### MySQL
```sql
CREATE DATABASE gradproject;
```

#### MS SQL Server
```sql
CREATE DATABASE gradproject;
```

### Additional Configuration

You may need to adjust the database connection settings (URL, username, password) based on your specific environment. The provided settings assume default installations with standard ports and credentials.

## Production Database Configuration

A production database configuration has been set up in `src/main/resources/application-prod.properties`. This configuration is designed to connect to the production database server.

### Connecting to the Production Database

To connect to the production database, you need to activate the production profile when running the application. There are several ways to do this:

#### Method 1: Command Line Argument

```bash
java -jar your-application.jar --spring.profiles.active=prod
```

#### Method 2: Environment Variable

```bash
export SPRING_PROFILES_ACTIVE=prod
java -jar your-application.jar
```

#### Method 3: In IntelliJ IDEA

1. Edit Run Configuration
2. Add VM option: `-Dspring.profiles.active=prod`

### Production Database Settings

The production database is configured with the following settings (these are placeholders and should be replaced with actual production values):

```properties
spring.datasource.url=jdbc:postgresql://prod-db-server:5432/gradproject_prod
spring.datasource.username=prod_user
spring.datasource.password=prod_password
```

**Important:** For security reasons, it's recommended to use environment variables for sensitive information like database credentials in production:

```properties
spring.datasource.url=${PROD_DB_URL:jdbc:postgresql://prod-db-server:5432/gradproject_prod}
spring.datasource.username=${PROD_DB_USERNAME:prod_user}
spring.datasource.password=${PROD_DB_PASSWORD:prod_password}
```
