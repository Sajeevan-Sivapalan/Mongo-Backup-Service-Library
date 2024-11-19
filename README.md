# MongoDB Backup Service Library

This is a Spring Boot-based library that provides tools for backing up and restoring MongoDB databases. It includes features such as compression, scheduling, and selective collection backups.

---

## Features

1. **Backup MongoDB Databases**:
   - Backs up all collections or specific collections from a MongoDB database.
   - Exports data in JSON format for easy restoration.

2. **Restore Functionality**:
   - Restores MongoDB collections from JSON backup files.

3. **Compression**:
   - Compresses backup files into a `.zip` format to save space.

4. **Scheduled Backups**:
   - Supports automated backups with configurable schedules:
     - **Daily** at **12:00 AM** (midnight).
     - **Weekly** on **Sundays** at **12:00 AM**.
     - **Monthly** on the **1st day of the month** at **12:00 AM**.
   - The backup schedule can be customized in the `application.properties` file.

5. **Selective Backups**:
   - Ability to back up specific collections instead of the entire database.

6. **Error Handling and Logging**:
   - Includes robust error handling and logging using SLF4J for better debugging and traceability.

---

## Requirements

- **Java 17** or higher.
- **Maven** for dependency management.
- A running **MongoDB** instance.

---

## Installation

# ExceptionLogger Integration Guide

## Steps for Integration

### 1. Download the JAR File

1. Download the `mongobackup-0.0.1-SNAPSHOT.jar` file.
2. Place it in the `libs` folder under your project's root directory. If the `libs` folder doesn't exist, create one.

### 2. Add the JAR File to `pom.xml`

To include the local JAR file, update your `pom.xml` file as follows:

1. Open `pom.xml`.
2. Add the following dependency inside the `<dependencies>` section:

```xml
<dependencies>    
    <dependency>
        <groupId>com.MongoBackupService</groupId>
        <artifactId>MongoBackup</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>

```

### 3. Configure the `application.properties` file with your MongoDB connection details:
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017
   spring.data.mongodb.database=your-database-name

   # Backup directory
   backup.directory=path/to/backup

   # Backup schedule
   backup.schedule=daily   # Options: daily, weekly, monthly
