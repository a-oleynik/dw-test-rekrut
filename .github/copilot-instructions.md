# GitHub Copilot Instructions for DataWalk Test Automation Framework

## Project Overview
This is a hybrid test automation framework for DataWalk web application, combining UI (Selenium) and API (REST Assured) testing with TestNG as the test runner. The framework follows the Page Object Model (POM) design pattern and uses AssertJ for fluent assertions.

## Technology Stack
- **Java 21** - Primary programming language
- **TestNG 7.11.0** - Test framework and runner
- **Selenium 4.38.0** - UI automation
- **REST Assured 5.5.6** - API testing
- **AssertJ 3.27.6** - Fluent assertions
- **Maven** - Build and dependency management
- **SLF4J 2.0.17** - Logging framework

## Project Structure
```
src/
├── main/java/dw/
│   ├── TestBase.java                    # Base configuration for all tests
│   ├── UITestBase.java                  # WebDriver setup and teardown
│   ├── RestTestBase.java                # REST Assured configuration and authentication
│   ├── framework/config/
│   │   └── Constants.java               # Configuration constants (TODO: migrate to properties file)
│   └── pageobject/
│       ├── AbstractPageObject.java      # Base page object with common WebDriver operations
│       ├── LoginPage.java               # Login page object
│       ├── TabularViewPage.java         # Main tabular view page object
│       ├── TabularViewColumnFilter.java # Column filter component
│       ├── AggregateOption.java         # Enum for aggregate types (MIN, MAX, AVG, etc.)
│       └── CustomWait.java              # Custom wait utilities
└── test/java/dw/test/
    ├── ui/
    │   └── TabularTest.java             # UI test cases
    └── rest/
        └── TabularTest.java             # API test cases
```

## Coding Standards & Best Practices

### 1. Java & TestNG Conventions
- **Java Version**: Use Java 21 features when appropriate (sealed classes, pattern matching, records)
- **TestNG Annotations**: 
  - Use `@BeforeClass` for test setup (initialization, login)
  - Use `@AfterClass(alwaysRun = true)` for cleanup to ensure execution even on test failure
  - Use `@Test` annotation for all test methods
  - Use `dependsOnMethods` for test dependencies when needed
  - Group tests using `@Test(groups = {"smoke", "regression", "api", "ui"})`
- **Naming Conventions**:
  - Test methods: `camelCase` descriptive names (e.g., `checkMaxValueForIntegerColumn`)
  - Page objects: `PascalCase` ending with `Page` or `Component`
  - Constants: `UPPER_SNAKE_CASE`
  - Variables: `camelCase`

### 2. Page Object Model (POM) Guidelines
- **Page Objects should**:
  - Extend `AbstractPageObject` for UI components
  - Contain locators as `public static final String` constants
  - Provide methods that represent user actions (click, fill, select)
  - Return `this` or next page object for method chaining
  - NOT contain assertions - keep them in test classes
  - Handle waits internally to make tests more stable

- **Locator Strategy Priority**:
  1. `id` attributes (most stable)
  2. `data-*` attributes
  3. CSS selectors (for simple structures)
  4. XPath (for complex structures or text-based location)
  - Always use descriptive locator variable names

- **Example Pattern**:
```java
public class ExamplePage extends AbstractPageObject {
    // Locators
    public static final String SUBMIT_BUTTON = "//button[@id='submit']";
    public static final String ERROR_MESSAGE = "//div[@class='error']";
    
    // Actions
    public ExamplePage clickSubmit() {
        click(SUBMIT_BUTTON);
        waitForSpinnerFinish(10);
        return this;
    }
    
    public String getErrorMessage() {
        return findElementByXpath(ERROR_MESSAGE).getText();
    }
}
```

### 3. Wait Strategies
- **Avoid `Thread.sleep()` and `CustomWait.staticWait()`** - use explicit waits instead
- **Use explicit waits** from `AbstractPageObject`:
  - `waitForElementDisplayed(By locator, int seconds)`
  - `waitForElementNotDisplayed(By locator, int seconds)`
  - `waitForSpinnerFinish(int seconds)` - specific for DataWalk app spinners
- **Reduce implicit wait timeout** - Currently set to 10 seconds, should be reduced to 2-3 seconds
- **Strategy**: Explicit waits for critical elements, minimal implicit wait as fallback

### 4. REST Assured API Testing
- **Authentication**: Use `RestTestBase.authenticate()` in `@BeforeClass`
- **Request Building**: Use `of()` method which includes:
  - XSRF token headers and cookies
  - Base URI
  - Request logging
- **Response Validation**:
  - Extract response first: `Response response = ...then().extract().response();`
  - Use JsonPath for parsing: `response.jsonPath().getString("path.to.value")`
  - Use AssertJ for assertions
- **Example Pattern**:
```java
Response response = of()
    .cookies(token)
    .contentType(ContentType.JSON)
    .body(requestBody)
    .when()
    .post("/api/v1/endpoint")
    .then()
    .statusCode(200)
    .extract().response();

String actualValue = response.jsonPath().getString("data.maxValue");
assertThat(actualValue).isEqualTo(expectedValue);
```

### 5. Assertions & Error Messages
- **Always use AssertJ** for fluent, readable assertions
- **Include descriptive messages** using `.describedAs()`:
```java
assertThat(actualValue)
    .describedAs("Max aggregate value for column '%s' should match sorted column value", columnName)
    .isEqualTo(expectedValue);
```
- **Common patterns**:
  - `.isEqualTo()` - exact match
  - `.isEqualToIgnoringCase()` - case-insensitive
  - `.contains()` - partial match
  - `.isNotNull()` / `.isNotEmpty()` - null/empty checks
  - `.matches()` - regex pattern matching

### 6. Test Data Management
- **Current State**: Hardcoded in test classes and `Constants.java`
- **TODO**: Migrate to externalized configuration:
  - Create `config.properties` file for environment-specific data
  - Use `PropertyReader` utility class
  - Support multiple environments (dev, test, staging)
  - Store sensitive data (credentials) in environment variables or secure vaults
- **Test Data Pattern**:
```java
// Avoid hardcoding
String columnName = "Latitude"; // Bad

// Use constants or properties
String columnName = TestData.LATITUDE_COLUMN; // Better
```

### 7. Error Handling & Logging
- **Logging**: Use SLF4J logger in complex methods
```java
private static final Logger logger = LoggerFactory.getLogger(ClassName.class);

logger.info("Starting test for column: {}", columnName);
logger.error("Failed to locate element: {}", xpath, exception);
```
- **Exception Handling**:
  - Catch specific exceptions (`NoSuchElementException`, `TimeoutException`, `StaleElementReferenceException`)
  - Re-throw with meaningful context
  - Use retry logic for known flaky operations (e.g., `StaleElementReferenceException`)

### 8. Test Cleanup & Maintenance
- **After Each Test**:
  - Uncheck all aggregates for columns
  - Clear filters
  - Reset sort order
  - Return to initial page state
- **Use `@AfterMethod` or `@AfterClass`** for cleanup:
```java
@AfterMethod
public void cleanupTest() {
    tabularViewPage.clearAllFilters()
                   .clearAllAggregates()
                   .refreshPage();
}
```

### 9. Parallel Execution
- **Current Configuration**: Maven Surefire with `parallel=classes` and `threadCount=3`
- **Thread Safety**: Ensure WebDriver instances are thread-safe
  - Use `ThreadLocal<WebDriver>` if running tests in parallel
  - Avoid static mutable state
- **Test Independence**: Each test should be independently executable

### 10. Code Quality & Comments
- **Comment When**:
  - Complex business logic needs explanation
  - Workarounds for known issues (with ticket reference)
  - TODO items for future improvements
  - Non-obvious XPath locators
- **TODO Format**: `// TODO: [JIRA-123] Description of what needs to be done`
- **Refactoring Opportunities**:
  - Remove commented-out code (use version control)
  - Extract magic numbers to constants
  - Extract repeated logic to utility methods
  - Reduce method complexity (aim for < 15 lines per method)

## Common Patterns & Anti-Patterns

### ✅ DO:
```java
// Use method chaining for fluent API
tabularViewPage
    .openFilterFor("Latitude")
    .setFilterValue("33")
    .applyFilter()
    .waitForSpinnerFinish(10);

// Use explicit waits
waitForElementDisplayed(By.xpath(SUBMIT_BUTTON), 10);

// Use descriptive assertions
assertThat(actualValue)
    .describedAs("Max value should match expected")
    .isEqualTo(expectedValue);

// Handle stale elements with retry
int retries = 3;
while (retries-- > 0) {
    try {
        element.click();
        break;
    } catch (StaleElementReferenceException e) {
        if (retries == 0) throw e;
        element = findElement(locator);
    }
}
```

### ❌ DON'T:
```java
// Don't use Thread.sleep()
Thread.sleep(5000); // BAD

// Don't hardcode waits
CustomWait.staticWait(5); // BAD - use explicit waits

// Don't put assertions in page objects
public void verifyTitle() {
    assertThat(getTitle()).isEqualTo("Expected"); // BAD - move to test
}

// Don't create massive methods
public void doEverything() {
    // 100 lines of code // BAD - split into smaller methods
}

// Don't swallow exceptions silently
try {
    element.click();
} catch (Exception e) {
    // Silent failure // BAD - log or re-throw
}
```

## DataWalk-Specific Patterns

### Spinner Handling
DataWalk shows spinners during loading. Always wait for them:
```java
// Wait for spinner to appear and disappear
waitForElementDisplayed(By.xpath("//div[@class='spinner-md']"), 2);
waitForElementNotDisplayed(By.xpath("//div[@class='spinner-md']"), 10);

// Or use the helper method
waitForSpinnerFinish(10);
```

### Aggregate Operations
```java
// Get aggregate value for a column
String maxValue = tabularViewPage.getAggregateValueForColumn("Latitude", AggregateOption.MAX);

// Ensure cleanup after test
@AfterMethod
public void cleanup() {
    tabularViewPage.clearAggregatesForAllColumns();
}
```

### Tabular View Interactions
```java
// Select rows
tabularViewPage.selectRow(1, 2, 3);

// Sort column
tabularViewPage.sortColumn("Latitude", SortOrder.DESC);

// Apply filter
tabularViewPage
    .openFilterFor("ID")
    .setFilterValue(">100")
    .applyFilter();
```

## XLSX Export Testing (TODO)
When implementing XLSX export tests:
1. **Test Coverage Should Include**:
   - Export with filters applied
   - Export with sorting applied
   - Export with aggregates visible
   - Export all columns vs. selected columns
   - Export all data vs. current page
   - Large dataset export (performance)
   - Special characters and Unicode in exported data
   - Date/time format preservation
   - Number format and precision

2. **Implementation Approach**:
   - Download file to temporary directory
   - Use Apache POI for XLSX parsing
   - Verify file structure and content
   - Clean up downloaded files in `@AfterMethod`

3. **Example Structure**:
```java
public class XlsxExportTest extends UITestBase {
    
    private XlsxExportPage xlsxExportPage;
    private File downloadedFile;
    
    @Test
    public void verifyExportWithFilters() {
        // TODO: Apply filter to column
        // TODO: Trigger export
        // TODO: Wait for download completion
        // TODO: Parse XLSX file using Apache POI
        // TODO: Verify filtered data is correctly exported
        // TODO: Verify column headers match
    }
    
    @AfterMethod
    public void cleanupDownloads() {
        // TODO: Delete downloaded files
    }
}
```

## Configuration & Credentials
- **Current**: Hardcoded in `Constants.java`
- **TODO**: Move to `config.properties`:
```properties
# config.properties
base.url=https://dw-task2-oci.demo-datawalk.com
ui.url=${base.url}/?startPage=table&collectionId=8100
login.username=demo2
login.password=TDF$hSj6zFHN8XEtnp4@^Md^S3Cht4at1

# Timeouts
timeout.implicit=3
timeout.explicit=10
timeout.page.load=30
```

## Running Tests
```bash
# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=TabularTest

# Run tests by group
mvn test -Dgroups=smoke

# Run with parallel execution
mvn test -DparallelCount=3
```

## Debugging Tips
1. **Screenshot on Failure**: Implement `@AfterMethod` to capture screenshots
2. **Enhanced Logging**: Log before critical actions
3. **Browser DevTools**: Keep browser open on failure for debugging
4. **REST Assured Logging**: Already enabled with `.log().all()`
5. **Breakpoint Strategy**: Set breakpoints at assertions and page transitions

## Future Improvements & Roadmap
1. **Configuration Management**: Properties file with environment profiles
2. **Reporting**: Extent Reports or Allure integration
3. **CI/CD Integration**: Jenkins pipeline with Groovy scripts
4. **Test Data**: External CSV/JSON files or database
5. **Cross-Browser**: BrowserStack or Selenium Grid integration
6. **Docker**: Containerize tests for consistent execution
7. **Page Factory**: Consider migrating to `@FindBy` annotations
8. **Retry Logic**: TestNG retry analyzer for flaky tests
9. **Video Recording**: Capture test execution videos
10. **Performance Testing**: JMeter integration for load testing

## AI Tools Integration Suggestions
- **Code Review**: Use AI to suggest refactoring opportunities
- **Test Generation**: Generate test data and edge cases
- **Locator Optimization**: Suggest more stable locators
- **Documentation**: Auto-generate JavaDoc comments
- **Bug Analysis**: Analyze failure logs and suggest root causes

---

**Note**: This framework is under active development. Follow the TODO comments in the codebase for pending improvements. Always run `mvn clean test` before committing changes to ensure all tests pass.

