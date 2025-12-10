package dw.test.ui;

import dw.UITestBase;
import dw.pageobject.AggregateOption;
import dw.pageobject.LoginPage;
import dw.pageobject.SortOption;
import dw.pageobject.TabularViewPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static dw.framework.config.Constants.LOGIN;
import static dw.framework.config.Constants.PASSWORD;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

@Test
public class TabularTest extends UITestBase {

    private TabularViewPage tabularViewPage;
    private static final String LATITUDE_COLUMN = "Latitude";
    private static final String SERIAL_NUMBER_COLUMN = "Serial number";
    private static final String ERROR_MESSAGE = "Aggregate max value for %s is different than displayed in tabular.";

    @BeforeClass
    public void login() {
        startApp();
    }

    @Test
    public void checkMaxValueForIntegerColumn() {
        String maxValueForColumnInAggregates = tabularViewPage.getAggregateValueForColumn(LATITUDE_COLUMN, AggregateOption.MAX);
        assertThat(maxValueForColumnInAggregates)
                .describedAs(format(ERROR_MESSAGE, LATITUDE_COLUMN))
                .isEqualTo("59.38");
    }

    @Test(/*dependsOnMethods = "checkMaxValueForIntegerColumn"*/)
    public void compareMaxWithSortedColumn() {
        String maxValueForColumnInAggregates = tabularViewPage.getAggregateValueForColumn(SERIAL_NUMBER_COLUMN, AggregateOption.MAX);
        tabularViewPage.sortColumn(SERIAL_NUMBER_COLUMN, SortOption.ASC);
        String maxValueForSortedColumn = tabularViewPage.getAggregateValueForColumn(SERIAL_NUMBER_COLUMN, AggregateOption.MAX);
        assertThat(maxValueForSortedColumn)
                .describedAs(format(ERROR_MESSAGE, SERIAL_NUMBER_COLUMN))
                .isEqualTo(maxValueForColumnInAggregates);
    }

    //------------------------------------------------------------------------------------------------------------------
    private void startApp() {
        LoginPage loginPage = new LoginPage();
        loginPage.logOn(UI_URL, LOGIN, PASSWORD);
        if (loginPage.isActiveSession()) {
            loginPage.logOnInActiveSession(LOGIN, PASSWORD);
        }
        tabularViewPage = new TabularViewPage();
    }
}
