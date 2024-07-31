# Paws on Deck API

This GraphQL API offers a robust interface for managing pets and pet owners. It supports various CRUD operations (
create, read, update, delete) and leverages GraphQL for a flexible query structure. The main functionality of this API
is to check Pets and see if they are eligible to attend the Paws On Deck Boat Rental service.

## Getting Started

### Prerequisites

* Java JDK 11 or newer
* Gradle
* PostgreSQL
* An IDE such as IntelliJ IDEA or Eclipse that supports Gradle
* Postman

### Setup Instructions

1. Clone the Repository

```
git clone https://github.com/HaydenKey/pet-screening-pre-interview
```

2. Database Setup

* Create a PostgreSQL database named `PawsOnDeckDB`.
* Update the src/main/resources/application.properties with your database credentials:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/pawsondeckdb
spring.datasource.username=<yourusername>
spring.datasource.password=<yourpassword>
```

3. Build and Run the Application

Navigate to the pawsondeck folder and use Gradle to build and run the application:

```bash
cd ./pawsondeck
./gradlew bootRun
```

The application will start running at http://localhost:8080.

4. Access the API Using Postman

Postman is an effective tool for testing APIs, including GraphQL. Use it to easily send queries and mutations to your
API.

#### Setting Up Postman

1. **Install Postman**: Download from [Postman's official website](https://www.postman.com/downloads/) and install it.

2. **Create a New Request**:
    - Open Postman and click on "New" > "Request".
    - Name your request, select "POST" as the method, and enter the URL for your GraphQL endpoint, for
      example, `http://localhost:8080/graphql`.

3. **Configure the Request**:
    - In the "Headers" section, add a header with `Key` as `Content-Type` and `Value` as `application/json`.
    - Go to the "Body" tab, select "GraphQL", and input your query or mutation. Example query:

      ```graphql
      query {
          pets {
              id
              name
              breed
          }
      }
      ```

4. **Send and Analyze**:
    - Click "Send" to execute the request.
    - View the response in the output section below to analyze the data or debug issues.

## Running Tests

This project uses JUnit and Spring Boot Test for unit and integration testing. Running the tests is straightforward and
can be done directly from your IDE or via the command line.

### Using the IDE

You can run tests from your IDE by right-clicking on the test class or the test method, and selecting "Run Test". Most
modern IDEs like IntelliJ IDEA and Eclipse support this feature.

### Using the Command Line

If you prefer to use the command line, you can run the tests using Gradle:

```bash
./gradlew test
```

This command runs all tests in the project and provides a summary of the results in the console. Detailed test reports
can be found in `build/reports/tests/test/index.html` after you run the tests.

### Viewing Test Reports

After running the tests, you can view detailed test reports in HTML format:

- Open the file `build/reports/tests/test/index.html` in any web browser to view the test report.

## Troubleshooting and Known Issues

### Known Issue: GraphQL API Errors

Currently, all GraphQL calls are experiencing issues where they return the following error:

```json
{
  "errors": [
    {
      "message": "The field at path '/<query-path>' was declared as a non null type, but the code involved in retrieving data has wrongly returned a null value. The GraphQL specification requires that the parent field be set to null, or if that is non nullable, it must bubble up null to its parent and so on. The non-nullable type is '[Type!]' within parent type 'Query'",
      "path": [
        "<query-path>"
      ],
      "extensions": {
        "classification": "NullValueInNonNullableField"
      }
    }
  ],
  "data": null
}
```

This issue affects all paths and types in the queries and mutations. The error occurs due to a misalignment between the
expected non-nullable return types and the actual null values returned by the backend when the expected data is missing
or an error occurs during data fetching.

Steps Being Taken:

* Debugging: The development team (me) is actively investigating the root cause, focusing on database connectivity, data
  presence, error handling in resolvers, and correct implementation of GraphQL schemas.
* Testing: Comprehensive testing is being conducted to ensure that all endpoints correctly handle null and non-null
  scenarios as per the GraphQL specifications.
* Updates: Updates will be provided as the issue progresses, including any necessary patches or steps for users to
  mitigate the issue temporarily.