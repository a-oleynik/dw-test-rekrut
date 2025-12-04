package dw.test.ui;

import dw.UITestBase;
import dw.pageobject.AggregateOption;
import dw.pageobject.LoginPage;
import dw.pageobject.TabularViewPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Test
public class TabularTest extends UITestBase {

    private TabularViewPage tabularViewPage;

    @BeforeClass
    public void login() throws InterruptedException {
        startApp();
    }

    @Test
    public void checkMaxValueForIntegerColumn() {
        String maxValueForColumnInAggregates = tabularViewPage.getAggregateValueForColumn("Victim Age", AggregateOption.MAX);
        assertThat(maxValueForColumnInAggregates)
                .describedAs("Aggregate max value for: ABC is different than displayed in tabular.")
                .isEqualTo("33");
    }

    @Test(dependsOnMethods = "checkMaxValueForIntegerColumn")
    public void compareMaxWithSortedColumn() {
        String maxValueForSortedColumn = "sort by desc";
        String maxValueForColumnInAggregates = "something temporary"; //TODO: to implement this
        assertThat(maxValueForSortedColumn)
                .describedAs("Aggregate max value for: ABC is different than displayed in tabular.")
                .isEqualTo(maxValueForColumnInAggregates);
    }

    //------------------------------------------------------------------------------------------------------------------
    private void startApp() throws InterruptedException {
        LoginPage loginPage = new LoginPage();
        loginPage.logOn(UI_URL, "demo1", "%4UQ4aX@nMLq&Vsv#Vp@%v3%4yc9@WXj");
        tabularViewPage = new TabularViewPage();
    }
}
