package dw.test.ui;

import dw.UITestBase;
import dw.enums.AggregateOption;
import dw.model.tables.CrimesColumns;
import dw.pageobject.LoginPage;
import dw.enums.SortOption;
import dw.pageobject.TabularViewPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static dw.framework.config.Constants.LOGIN;
import static dw.framework.config.Constants.PASSWORD;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

@Test
public class TabularTest extends UITestBase {

    private TabularViewPage tabularViewPage;
    private static final String LATITUDE_COLUMN = CrimesColumns.LATITUDE.getTitle();
    private static final String SERIAL_NUMBER_COLUMN = CrimesColumns.SERIAL_NUMBER.getTitle();
    private static final String ERROR_MESSAGE = "Aggregate max value for %s is different than displayed in tabular.";
    private String column;
    private AggregateOption aggregateOption = AggregateOption.MAX;

    @BeforeClass
    public void login() {
        startApp();
    }

    @Test
    public void checkMaxValueForIntegerColumn() {
        column = LATITUDE_COLUMN;
        String maxValueForColumnInAggregates = tabularViewPage.getAggregateValueForColumn(column, aggregateOption);
        assertThat(maxValueForColumnInAggregates)
                .describedAs(format(ERROR_MESSAGE, LATITUDE_COLUMN))
                .isEqualTo("59.38");
    }

    @Test(/*dependsOnMethods = "checkMaxValueForIntegerColumn"*/)
    public void compareMaxWithSortedColumn() {
        column = SERIAL_NUMBER_COLUMN;
        String maxValueForColumnInAggregates = tabularViewPage.getAggregateValueForColumn(column, aggregateOption);
        tabularViewPage.sortColumn(column, SortOption.ASC);
        String maxValueForSortedColumn = tabularViewPage.getAggregateValueForColumn(column, aggregateOption);
        assertThat(maxValueForSortedColumn)
                .describedAs(format(ERROR_MESSAGE, column))
                .isEqualTo(maxValueForColumnInAggregates);
    }

    @AfterMethod(alwaysRun = true)
    public void cleanAggregateOption(){
        tabularViewPage.openAggregatesForColumn(column)
                //.cleanAllAggregatesForColumn();// We chose this variant if we checked all aggregated checkboxes
                .cleanAggregateForColumn(aggregateOption);
    }

    //------------------------------------------------------------------------------------------------------------------
    private void startApp() {
        LoginPage loginPage = new LoginPage();
        loginPage.logOn(UI_URL, LOGIN, PASSWORD);
        if (loginPage.isActiveSession()) {
            loginPage.logOnInActiveSession(PASSWORD);
        }
        tabularViewPage = new TabularViewPage();
    }
}
