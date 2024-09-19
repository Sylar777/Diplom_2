# Diploma Project (part №2): API Testing for Stellar Burgers 

## Overview
This project focuses on testing the API endpoints of the Stellar Burgers service. The API documentation provides a full overview of all the available endpoints, but the tests are focused only on the required endpoints as specified in the assignment.

## Key Technologies
- **JUnit 4**: For unit testing.
- **RestAssured**: To test the API endpoints.
- **Allure**: For generating detailed test reports.

## Objectives
1. Test the following API functionalities:
   - **User Creation**:
     - Create a unique user.
     - Try to create a user who is already registered.
     - Attempt to create a user without one of the required fields.
   - **User Login**:
     - Log in as an existing user.
     - Attempt to log in with incorrect credentials.
   - **User Data Modification**:
     - Modify user data with authorization.
     - Attempt to modify user data without authorization.
     - Ensure that any field can be modified, and unauthorized users get the appropriate error.
   - **Order Creation**:
     - Create an order with authorization.
     - Attempt to create an order without authorization.
     - Create an order with valid ingredients.
     - Attempt to create an order without ingredients.
     - Try to create an order with an invalid ingredient hash.
   - **Order Retrieval**:
     - Retrieve orders as an authorized user.
     - Attempt to retrieve orders as an unauthorized user.
2. Generate a detailed test report using **Allure**.

## Implementation

### Tested API Endpoints

#### 1. User Creation
- **Test Cases**:
  - Creating a unique user.
  - Trying to create a user that already exists.
  - Creating a user without filling in a required field.

#### 2. User Login
- **Test Cases**:
  - Logging in with valid credentials.
  - Attempting to log in with an invalid username or password.

#### 3. User Data Modification
- **Test Cases**:
  - Modifying user data with authorization.
  - Attempting to modify data without authorization.
  - Verifying that any field can be updated, and that unauthorized requests return an error.

#### 4. Order Creation
- **Test Cases**:
  - Creating an order with authorization.
  - Attempting to create an order without authorization.
  - Creating an order with valid ingredients.
  - Attempting to create an order without ingredients.
  - Trying to create an order with an invalid ingredient hash.

#### 5. Order Retrieval
- **Test Cases**:
  - Retrieving orders for an authorized user.
  - Attempting to retrieve orders as an unauthorized user.

## Running the Project

### Prerequisites
- **Java 8 or higher**
- **Maven** for dependencies
- **JUnit 4**, **RestAssured**, and **Allure**

### Steps
1. Clone the repository:
   ```bash
   git clone <repository_url>
   ```
2. Navigate to the project directory:
   ```bash
   cd <project_name>
   ```
3. Run API tests:
   ```bash
   mvn test
   ```
4. Generate the Allure report:
   ```bash
   mvn allure:serve
   ```

## Reports
The Allure report will be automatically generated and opened in your default browser after running mvn allure:serve.

## Conclusion
This project demonstrates API testing for the Stellar Burgers service using JUnit 4, RestAssured, and Allure. It covers a variety of test scenarios, ensuring the reliability and correctness of key API functionalities.
