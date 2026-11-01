# 🚀 REST API Automation Framework

An enterprise-ready, modular, and simple-to-explain **REST API Automation Framework** built using **Java**, **REST Assured**, **TestNG**, and **Maven**.

Designed with clean architecture and industry best practices for live technical interview demonstrations and real-world API test automation.

---

## 📋 Table of Contents
- [Key Features](#-key-features)
- [Tech Stack](#-tech-stack)
- [Project Architecture](#-project-architecture)
- [Folder Structure](#-folder-structure)
- [Core Components Explained](#-core-components-explained)
- [Test Scenarios Implemented](#-test-scenarios-implemented)
- [How to Run Tests](#-how-to-run-tests)
- [Test Execution Reports](#-test-execution-reports)
- [Interview Preparation](#-interview-preparation)

---

## ✨ Key Features
- **Automated REST API CRUD Operations**: Full lifecycle coverage for `POST` (Create), `GET` (Read single & list with query parameters), `PUT` (Full Update), `PATCH` (Partial Update), and `DELETE` (Remove).
- **HTTP Status Code & Response Validation**: Strict validations for `200 OK`, `201 Created`, and `404 Not Found`.
- **POJO Serialization & Deserialization**: Uses **Jackson** to convert Java objects into JSON request bodies and deserialize API responses back into strongly-typed Java models.
- **Positive & Negative Test Suites**: Validates happy paths as well as error handling for non-existent user IDs, non-existent post IDs, and undefined endpoints.
- **Reusable API Utilities**:
  - `ConfigReader`: Dynamic environment configuration via `config.properties`.
  - `SpecBuilder`: Centralized `RequestSpecification` and `ResponseSpecification` avoiding duplicate boilerplate.
  - `ExtentReportManager`: Automatic, thread-safe HTML reports powered by ExtentReports 5 and TestNG `ITestListener`.
- **Response Time & Header Validations**: SLA enforcement (response time < 5s) and `Content-Type` verification.

---

## 🛠 Tech Stack
| Technology | Version | Purpose |
| :--- | :--- | :--- |
| **Java** | 17 / 21 | Programming Language |
| **REST Assured** | 5.4.0 | RESTful API Testing & BDD DSL |
| **TestNG** | 7.10.2 | Test Execution, Assertions & Listeners |
| **Jackson Databind** | 2.17.2 | JSON to Java POJO Mapping (Serialization/Deserialization) |
| **ExtentReports** | 5.1.2 | Rich Interactive HTML Reporting |
| **Hamcrest** | 2.2 | Fluent Matchers & Body Assertions |
| **Maven** | 3.9+ | Build and Dependency Management |

---

## 🏗 Project Architecture

```
                       ┌────────────────────────┐
                       │   config.properties    │
                       └───────────┬────────────┘
                                   │
                           ┌───────▼───────┐
                           │ ConfigReader  │
                           └───────┬───────┘
                                   │
┌──────────────────┐       ┌───────▼───────┐       ┌──────────────────┐
│   Endpoints.java │◄─────►│  SpecBuilder  │◄─────►│    POJO Models   │
└──────────────────┘       └───────┬───────┘       └──────────────────┘
                                   │
                           ┌───────▼───────┐
                           │   BaseTest    │
                           └───────┬───────┘
                                   │
              ┌────────────────────┴────────────────────┐
              │                                         │
       ┌──────▼───────┐                          ┌──────▼───────┐
       │ UserCrudTests│                          │NegativeTests │
       └──────┬───────┘                          └──────┬───────┘
              │                                         │
              └────────────────────┬────────────────────┘
                                   │
                       ┌───────────▼────────────┐
                       │  ExtentReportManager   │
                       │     (ITestListener)    │
                       └───────────┬────────────┘
                                   │
                       ┌───────────▼────────────┐
                       │  reports/*.html Report │
                       └────────────────────────┘
```

---

## 📂 Folder Structure

```
REST-API-Automation-Framework/
│
├── pom.xml                                  # Maven dependencies and build plugins
├── testng.xml                               # TestNG suite runner & listener registration
├── README.md                                # Project overview & guide
├── INTERVIEW_CHEATSHEET.md                  # Quick interview walkthrough & Q&A
│
├── src/main/java/com/api/
│   ├── constants/
│   │   └── Endpoints.java                   # Centralized API endpoints & URI paths
│   │
│   ├── models/                              # Java POJOs for Serialization & Deserialization
│   │   ├── UserRequest.java                 # Payload for creating/updating users
│   │   ├── UserResponse.java                # Model for user responses with unknown property protection
│   │   ├── PostRequest.java                 # Payload for creating posts
│   │   └── PostResponse.java                # Model for post responses
│   │
│   └── utils/                               # Reusable framework utilities
│       ├── ConfigReader.java                # Loads configuration from config.properties
│       ├── SpecBuilder.java                 # Reusable RequestSpec and ResponseSpec builders
│       └── ExtentReportManager.java         # TestNG ITestListener with ThreadLocal HTML reporting
│
├── src/test/java/com/api/
│   ├── base/
│   │   └── BaseTest.java                    # Suite setup, BaseURI, and global RequestSpec
│   │
│   └── tests/
│       ├── UserCrudTests.java               # 6 Positive CRUD test cases (POST, GET, PUT, PATCH, DELETE)
│       └── NegativeTests.java               # 4 Negative test cases (404 Not Found on invalid IDs & routes)
│
├── src/test/resources/
│   └── config.properties                    # Base URL and environment configurations
│
└── reports/                                 # Generated HTML test execution reports
    └── API_Automation_Report_*.html
```

---

## 🔍 Core Components Explained

### 1. `Endpoints.java`
Centralized repository of API routes. Instead of hardcoding URLs like `"/users"` inside test methods, constants are referenced. If an endpoint changes, it is updated in one place.

### 2. `ConfigReader.java`
Reads environment properties (`base.url`, `timeout.seconds`) using standard Java `Properties` loaded from `config.properties`. Allows quick switching between DEV, QA, and STAGING environments.

### 3. `SpecBuilder.java`
Implements the Builder pattern to create reusable `RequestSpecification` and `ResponseSpecification`:
- **RequestSpec**: Sets Base URI, JSON Content-Type, Accept header, and log filters.
- **ResponseSpec**: Verifies common status codes and guarantees response times remain under SLA limits (< 5 seconds).

### 4. `UserRequest.java` & `UserResponse.java` (POJO Models)
- Uses **Jackson Databind** for serialization (`UserRequest` object ➔ JSON string) and deserialization (JSON response ➔ `UserResponse` object).
- `@JsonInclude(JsonInclude.Include.NON_NULL)`: Omits null fields from outgoing payloads.
- `@JsonIgnoreProperties(ignoreUnknown = true)`: Prevents crashes when the backend API introduces new nested or metadata fields.

### 5. `BaseTest.java`
Parent test class annotated with TestNG's `@BeforeSuite`. It configures `RestAssured.baseURI` and initializes `requestSpec` before any tests execute.

### 6. `ExtentReportManager.java`
Implements TestNG `ITestListener` to generate an HTML dashboard report:
- Uses **`ThreadLocal<ExtentTest>`** to guarantee thread safety when running tests in parallel.
- Logs test step status, execution duration, system metadata, and failure stack traces.

---

## 🧪 Test Scenarios Implemented

### 1. Positive CRUD Suite (`UserCrudTests.java`)
| Method | HTTP Verb | Endpoint | Status | What is Validated |
| :--- | :--- | :--- | :--- | :--- |
| `testCreateUser` | `POST` | `/users` | **201 Created** | Body matches request, generated `id` is present, SLA < 5s |
| `testGetUserById` | `GET` | `/users/1` | **200 OK** | `id == 1`, `email == "Sincere@april.biz"`, `name == "Leanne Graham"` |
| `testGetPostsWithQueryParams` | `GET` | `/posts?userId=1` | **200 OK** | Query param filtering, all posts belong to `userId == 1` |
| `testUpdateUserWithPut` | `PUT` | `/users/1` | **200 OK** | Full user update, checks updated name, username, email |
| `testPartialUpdateUserWithPatch`| `PATCH` | `/users/1` | **200 OK** | Partial update of email only |
| `testDeleteUser` | `DELETE` | `/users/1` | **200 OK** | Successful deletion confirmed by 200 status code |

### 2. Negative Test Suite (`NegativeTests.java`)
| Method | HTTP Verb | Endpoint | Status | What is Validated |
| :--- | :--- | :--- | :--- | :--- |
| `testGetUser_NotFound_404` | `GET` | `/users/999` | **404 Not Found** | Status code 404 for invalid/missing user ID |
| `testGetPost_NotFound_404` | `GET` | `/posts/99999` | **404 Not Found** | Status code 404 for non-existent post ID |
| `testInvalidEndpoint_404` | `GET` | `/non_existent_resource` | **404 Not Found** | Correct routing 404 error on unknown route |
| `testPostToInvalidEndpoint_404` | `POST` | `/non_existent_resource` | **404 Not Found** | Correct rejection of POST to non-existent endpoint |

---

## ⚡ How to Run Tests

### Run via Maven (Command Line)
```bash
# Execute entire TestNG test suite
mvn clean test
```

### Run via TestNG XML in IDE
1. Open project in **IntelliJ IDEA** or **Eclipse**.
2. Right-click on `testng.xml`.
3. Select **Run 'testng.xml'**.

### Run via Specific Test Class
```bash
mvn test -Dtest=UserCrudTests
```

---

## 📊 Test Execution Reports

After running the tests, an interactive HTML report is generated automatically in the `reports/` folder:

```
reports/API_Automation_Report_YYYYMMDD_HHMMSS.html
```

To view the report:
- Open the `.html` file in any web browser (Chrome, Edge, Firefox).
- The dashboard displays test counts, execution duration, categories, and detailed step-by-step logs.

---

## 💡 Interview Preparation
Review [INTERVIEW_CHEATSHEET.md](INTERVIEW_CHEATSHEET.md) for:
- 2-minute elevator pitch to explain this framework.
- Top 15 REST Assured & TestNG interview questions with code examples.
- Architecture design decisions and answers.
