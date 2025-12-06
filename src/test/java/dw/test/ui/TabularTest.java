package dw.test.ui;

import dw.UITestBase;
import dw.pageobject.AggregateOption;
import dw.pageobject.LoginPage;
import dw.pageobject.TabularViewPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static dw.framework.config.Constants.LOGIN;
import static dw.framework.config.Constants.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

@Test
public class TabularTest extends UITestBase {

    private TabularViewPage tabularViewPage;
    private static final String COLUMN_TO_TEST = "Latitude";

    @BeforeClass
    public void login() {
        startApp();
    }

    @Test
    public void checkMaxValueForIntegerColumn() {
        String maxValueForColumnInAggregates = tabularViewPage.getAggregateValueForColumn(COLUMN_TO_TEST, AggregateOption.MAX);
        assertThat(maxValueForColumnInAggregates)
                .describedAs("Aggregate max value for: ABC is different than displayed in tabular.")
                .isEqualTo("59.38");
    }

    @Test(/*dependsOnMethods = "checkMaxValueForIntegerColumn"*/)
    public void compareMaxWithSortedColumn() {
        String maxValueForSortedColumn = "sort by desc";
        String maxValueForColumnInAggregates = tabularViewPage.getAggregateValueForColumn(COLUMN_TO_TEST, AggregateOption.MAX);
        assertThat(maxValueForSortedColumn)
                .describedAs("Aggregate max value for: ABC is different than displayed in tabular.")
                .isEqualTo(maxValueForColumnInAggregates);
    }

    //------------------------------------------------------------------------------------------------------------------
    private void startApp() {
        LoginPage loginPage = new LoginPage();
        loginPage.logOn(UI_URL, LOGIN, PASSWORD);
        if (loginPage.isActiveSession()){
            loginPage.logOnInActiveSession(LOGIN, PASSWORD);
        }
        tabularViewPage = new TabularViewPage();
    }
}
