# TestNG-Gradle Example

This is a sample project demonstrating how to write and execute tests using the TestNG framework with Gradle.

## Prerequisites

Ensure that the following tools are installed on your machine:

1. [Java 8](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
2. [Gradle](https://gradle.org/install/)

## Setup Instructions

1. Clone this repository by running the following command:
   ```bash
   git clone https://github.com/qase-tms/qase-java.git
   cd qase-java/examples/testng/testng-gradle
   ```

2. Create a `qase.config.json` file in the root of the project. You can follow the instructions
   on [how to configure the file](https://github.com/qase-tms/qase-java/tree/main/qase-java-commons#readme).

3. Once the configuration is done, run the tests and upload the results to Qase by executing:
   ```bash
   gradle test
   ```

## Additional Resources

For more details on how to use this integration with Qase Test Management, visit
the [Qase Java documentation](https://github.com/qase-tms/qase-java).