# CSV Report Generation App

This is a Java-based application designed to generate CSV reports. 
The application uses various libraries to read, process, and output data in CSV format. It is packaged as a JAR file and can be easily run with all dependencies bundled into a single executable file.

## Features

- **CSV Data Processing**: Utilize the Jackson CSV data format library to read and write CSV files.
- **Logging**: Uses SLF4J for logging and Logback for configuration.
- **Testing**: JUnit 5 for unit testing.
- **Executable JAR**: Built with Maven Assembly Plugin, creating a JAR file with all dependencies included.

## Requirements

- Java 17 or later.
- Maven 3.x for building the project.

## Installation

### Clone the repository

### Build the project
Run the following Maven command to build the project and create the executable JAR file:
```bash
mvn clean package
```

This will generate a csv-report-gen-app-1.0-SNAPSHOT-jar-with-dependencies.jar file in the target directory.

Run the application
Once the build is complete, you can run the application as follows:

```bash
java -jar target/csv-report-gen-app-1.0-SNAPSHOT-jar-with-dependencies.jar
```
