# Test framework for Restful booker API
This is a test framework that is checking that 3 endpoints (getBookingIds, partialUpdateBooking, deleteBooking) 
in Restful booker API are working as expected.
It is using Java with Maven and Junit. Framework for writing BDD (scenario based) tests is Cucmber.

## Requirements
In order to run tests, it is necessary that the user has Java and Maven installed.
For reporting, local installation of Allure is needed.

## Building a project
After cloning the project repository, run 

```mvn clean package```

to build the project and install dependencies.

## Running tests
To run the full suite of tests, execute:

```mvn clean test```

To narrow tests down and run only a specific set of tests, run the command in the following pattern:

```
 mvn test -Dcucumber.filter.tags=${TAG}"
```

where you can replace the ${TAG} with the value of the tag for the specific (set of) tests.

For example, to run smoke tests, you can run:

```
 mvn test -Dcucumber.filter.tags="@smoke"  
 ```

To run entire suite for a single endpoint, choose one of the following tags: 
`@getBookingIds`, `@partialUpdateBooking` or `@deleteBooking`.

Possible values of tags for a specific feature can be found in `resources/features/booking/*.feature` files.

# Reporting
After tests have been executed, Allure reports will be generated and placed in `allure-result` folder.
To view them, run:

```allure serve```
