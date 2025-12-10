1. Added Maven plugin version for maven compiler. Added maven.compiler.target and maven.compiler.source variables.
Switched to Java 21. TODO to ask what Java is supported.
2. Added Surefire Maven plugin, maven.surefire.version variable.
3. Added testng.version variable and switched to TestNG 7.11.0.
4. Added selenium.version variable and switched to Selenium 4.38.0.
5. Added asserj.version and switched to AsserJ 3.27.6.
6. Added slf4j.version variable and added slf4j-simple dependency not to print warning by Selenium.
7. Added project.build.sourceEncoding to use UTF-8 encoding.
8. Added rest.assured.version variable and switched to Rest Assured 5.5.6.
9. Updated UITestBase to make it compile.
10. Update AbstractPageObject: update isElementDisplayed, removed public void implicitlyWait(int sec, TimeUnit timeUnit)
11. Add @Test annotation to checkMaxValueForIntegerColumn test
12. Fix locators on LoginPage
13. Removed throws InterruptedException.
14. Fix work with spinner.
15. Update waiters
16. Add active session modal handler
17. Fix locators for aggregate function elements
18. Removed CustomWait.staticWait from setAllAggregatesForColumn()
16. Fix checkMaxValueForIntegerColumn
17. Marked CustomWait methods as @Deprecated
18. Correct misspelled selectXpath
19. Fix general timeouts (increased to 20 seconds)
20. Finish the test compareMaxWithSortedColumn. Add two variants of method to scroll left until visible.
21. Clean up aggregates after tests. Added two alternative versions 
* a) we check all the aggregates during the test and uncheck all of them after the test.
* b) we check only the required aggregate option during the test and uncheck it after the test.



