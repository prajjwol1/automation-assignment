This repository contains my full implementation of the automation
assignments.

Since the real ChatGPT UI cannot be automated due to strong bot
detection and dynamic loading, I created a MockGPT static HTML page
that mimics ChatGPT's response behavior. This allowed me to perform
stable and predictable Selenium automation. I attempted live ChatGPT
automation but it was not possible due to bot protection.

# Assignment 1 -- UI Automation and HTML Report

What I automated: 
1. Open Chrome browser
2. Navigate to MockGPT
3. Enter the prompt:
  `Please provide a Python function that accepts parameters from the command line and returns the addition result.`
4. MockGPT returns a predefined Python function 5. Selenium copies the Python function
5. Saves it as a `.py` file
6. Reads test data from CSV
7. Opens the CodeChef IDE
8. Executes the Python function with different test inputs 10. Collects output, status, time, memory usage, and pass or fail
9. Generates an HTML automation report

# Why MockGPT:
Real ChatGPT uses heavy bot detection and automation
guards. It repeatedly showed a loading overlay, so automation was not
reliable. A static MockGPT solved this by returning a consistent output.

# Assignment 2 -- TestNG Integration and Data Provider

# What TestNG added: Parameterization through `@DataProvider` this helped us to achieve: 
- Cleaner test structure
-  Reusable setup and teardown
-  Better reporting Ability to scale more test cases Support for parallel execution Automatic TestNG HTML report generation

# Benefits I observed: 
Scaling test cases is very easy and integration becomes smooth
Test reports generate without extra effort and aslo we have clear control over execution order

# Assignment 3 -- JDBC, POJO, and CSV

Steps implemented: 
- Connected to SQLite database Created the `salary` table
- Inserted 100 random employee records Created the Salary POJO class
- Performed the following operations:
- Finding the highest salary
- Exporting employees with salary greater than 10000
- Exporting employees who joined within the last 30 days
- Displaying employees sorted alphabetically by name

# Assignment 5 -- My Thoughts on QA in the AI Age

I believe QA will not disappear but it will continue to evolve. AI can
automate repetitive tasks but cannot replace skills like risk analysis,
creative test design, critical thinking, or understanding user behavior.
Future QA roles will be more aligned with being problem solvers and
evaluators of AI-driven systems. We will continue to matter, but
continuous learning is important.

# Screenshots: Actual screenshots are available inside the `screenshots`
folder: MockGPT response page  CodeChef
output TestNG HTML report SQLite database results

# Dependencies: 
Java 17 Chrome and Chromedriver Any editor such as
IntelliJ, Eclipse, or VS Code

# Running the Project: You can run the project directly from your IDE after cloning the repo.

# Assignment 1:
`Assignments/src/test/java/testcases/ChatGPTToCodeChefTest_NoTestNG.java`

# Assignment 2:
`Assignments/src/test/java/testcases/ChatGPTToCodeChefTest_WithTestNG.java`

# Assignment 3: 
Assignments/src/main/java/JdbcPojo/Main.java`

# Automation reports for assignment 1 and 2 can be found at:
`Assignments/reports/report.html`

# CSV output for assignment 3 can be found at:
`Assignments/salary_gt_10k.csv`

# Test Cases Included for Assignment 1 and 2:
- Check the response for valid input
- Check the response for invalid input

