# DataWalk Test Automation Recruitment Task

## General Notes
   - Refactor if needed/Clean up code
   - Comment all places that you think need to be rewritten
   - Keep code in the same packages structure
   - You can add new dependencies or modify the project structure if it improves the code organization
   - Document your decisions and trade-offs in comments
   - Provide descriptive solutions in markdown - in README or new files
   - AI tools usage is allowed - approach and prompts to be discussed on technical meeting
   - Make it your model project.

## Evaluation Criteria
Your solution will be evaluated based on:
   - Code quality and organization
   - Proper error handling
   - Documentation and comments
   - Analytical skills and creativity

## Test Environment
   - Task is designed to work with the test instance of DataWalk's web application
   - You should see login page under this address https://dw-task-oci.demo-datawalk.com
   - Credentials provided via e-mail - put them to `config.properties` file
   - In case of any issues, i.e. environment not running, please contact task sender (e-mail or SMS)

## Test Automation Tasks [Technical Exercise]
1. Review, fix and refactor the existing code where necessary
   - Focus on code quality, maintainability, and performance
   - Add comprehensive comments explaining complex logic
   - Implement proper error handling and logging
   - You can start with running `src\test\java\com\datawalk\test\ui\TabularTest.java`

2. UI Test Implementation
   - Fix the `checkMaxValueForIntegerColumn` test
   - Complete the `compareMaxWithSortedColumn` test
   - Add wait mechanisms
   - Implement/suggest proper test data management
   - Ensure all aggregates are unchecked for columns after tests
   - Implement/suggest test cleanup

3. REST API Test Implementation
   - Complete the `checkMaxValueForIntegerColumn` test

## New Test Cases for XLSX Export [Analytical Exercise]
   - Familiarize with the XLSX export functionality (available in Table module)
   - Based on your experience propose tests that should be covered
   - Design skeleton of test classes, methods and utils - no need to implement logic here but project must compile
   - Provide descriptions in comments, TODOs  etc.

## Portfolio [Groovy and AI tools]
   - Share your experience with Jenkins Groovy scripting in any form: screenshot, anonymized script attached or describe your achievements
   - Share your experience with AI tools integration in the testing process: scripts/pipelines optimizing your and others work

## New Ideas [Conceptual Exercise]
1. Automatic acceptance tests
DataWalk is deployed as a distributed system in on-premise model without remote access to environments. 
Field Engineering team wants to prepare UI tests that will be executed on customer's test environment after system upgrades and implemented solution modifications. 
They have access to host machines and the DataWalk app when visiting customer.
   - Design (describe) possible approach, including process and technical solution in details
   - What challenges you see and how would you handle them, technical/business

2. Automatic Test Report Analyzer
   - Design (describe) a solution integrating AI agent to the test pipeline for automatic test report analysis
   - Goal is to speed up report analysis
   - What context is needed
   - How to mark tests that fails also on main branch when running test pipeline for feature branch
   - How to ignore fails for tests with bug reported in Jira - waiting for bugfix
   - Define criteria for determining if test results are acceptable or require deeper analysis