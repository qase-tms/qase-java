# Cucumber4-Maven Example

This is a sample project demonstrating how to write and execute tests using the Cucumber 4 framework with Maven.

## Prerequisites

Ensure that the following tools are installed on your machine:

1. [Java 8](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
2. [Maven](https://maven.apache.org/download.cgi)

## Setup Instructions

1. Clone this repository by running the following command:
   ```bash
   git clone https://github.com/qase-tms/qase-java.git
   cd qase-java/examples/cucumber4/cucumber4-maven
   ```

2. Create a `qase.config.json` file in the root of the project. You can follow the instructions
   on [how to configure the file](https://github.com/qase-tms/qase-java/tree/main/qase-java-commons#readme).

3. Once the configuration is done, run the tests and upload the results to Qase by executing:
   ```bash
   mvn clean test
   ```

## Additional Resources

For more details on how to use this integration with Qase Test Management, visit
the [Qase Java documentation](https://github.com/qase-tms/qase-java).
