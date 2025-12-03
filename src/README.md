## UI TESTS
* Handle the **modal window** that appears just after login (select 'no case defined').
* Use **dynamic waits** instead of static ones in Tabular; handle **spinners**.
* Fix the test `checkMaxValueForIntegerColumn`.
* Finish the test `compareMaxWithSortedColumn` (compare the **MAX value** for 'Serial number' returned by the MAX function with the value displayed after sorting the column by DESC).
* Clean up after tests (uncheck all aggregates for the column).

## REST TESTS
* REST request for **aggregates** can be found on the tabular page (UI TabularTest uses this part of the page; a example screenshot is attached in resources).
* Finish the test: parse REST request response and compare it with the expected value.