# Exception Logging Library

This is a simple exception logging library for Spring Boot applications. It captures and logs exceptions thrown within your application and provides an API to retrieve these logs in JSON format. The logged data includes detailed information about the error, such as the error type, message, stack trace, source class, and method.

## Features
- Logs exception details such as type, message, stack trace, and source.
- Stores logs in a MongoDB database.
- Exposes an API to retrieve logs in JSON format.
- Easily integrates with existing Spring Boot applications.

## Requirements
- Java 17 or later
- Spring Boot 2.x or later
- MongoDB (for storing error logs)
- Spring Data MongoDB

## Installation

# ExceptionLogger Integration Guide

## Steps for Integration

### 1. Download the JAR File

1. Download the `ExceptionHandler-0.0.1-SNAPSHOT.jar` file.
2. Place it in the `libs` folder under your project's root directory. If the `libs` folder doesn't exist, create one.

### 2. Add the JAR File to `pom.xml`

To include the local JAR file, update your `pom.xml` file as follows:

1. Open `pom.xml`.
2. Add the following dependency inside the `<dependencies>` section:

```xml
<dependencies>    
    <dependency>
        <groupId>com.ExceptionLogger</groupId>
        <artifactId>ExceptionHandler</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
