package dw.pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

public class TabularViewPage extends AbstractPageObject {

    public static String TABULAR_PAGE = "//div[@class='ag-table ag-datawalk']";
    public static String TABLE_HEADER_COLUMNS = TABULAR_PAGE + "//span";
    public static String PAGINATION_BAR = "//div[contains(@class,'ag-paging-panel')]";
    public static String PAGINATION_LAST = PAGINATION_BAR + "//button[@ref='btLast']";
    public static String FILTER_INSERT_INPUTS = "//div[@class='ag-filter']//div[contains(@class,'filter') and not(contains(@class,'hidden')) or not(@class)]/input[@class='ag-filter-filter']";
    public static String FILTER_CONFIRMATION_BUTTON = "//button[@id='applyButton']";
    public static String PANEL_REFRESH_BUTTON_SELECTOR = "//button[@data-ng-click='panel.refresh()']";
    public static String XPATH_OK_AGGREGATE_BUTTON = "//ul[@class='ag-dropdown dropdown-menu aggregates'][contains(@style,'display')]/button";
    public static String XPATH_TABULAR_VIEW_PAGE_SPINNER = "//div[@class='spinner-md']";
    public static String XPATH_TABLE_NAME = "//table//*[contains(@class, 'tab-table')]";
    public static String XPATH_ALL_COLUMNS_BUTTON = "//datawalk-chip/span";

    public TabularViewPage() {
        waitForElementDisplayed(By.xpath(XPATH_TABLE_NAME), 5);
        waitForElementDisplayed(By.xpath(XPATH_ALL_COLUMNS_BUTTON), 5);
    }

    public String getFirstRowColumnData(String colName) {
        return findElementsByXpath(TABULAR_PAGE + "//div[@class='ag-body-container']//div[@colid='" + getColumnNameId(colName) + "']//span")
                .get(0).getText();
    }

    public boolean isTabularViewOpen() {
        return isElementDisplayed("//table-component", 1);
    }

    private void openFilterModalAt(String column) {
        int x = 0;
        while (x < 5) {
            try {
                WebElement columnFilterDropdown = getColumnFilterDropdown(column);
                click(columnFilterDropdown);
                break;
            } catch (StaleElementReferenceException e) {
                x++;
                CustomWait.staticWait(0.5);
            }
        }
    }

    public List<String> getDisplayedColumns() {
        List<String> allCols = new ArrayList<>();
        List<WebElement> allHeader = findElementsByXpath(TABULAR_PAGE + "//div[@class='ag-header-viewport']//span[@ref='eText']");
        allHeader.forEach(col -> {

                    String colName = col.getText();
                    if (!colName.isEmpty()) {
                        allCols.add(colName);
                    }
                }
        );
        return allCols;
    }

    public TabularViewPage selectRow(int... rows) {
        Actions act = new Actions(driver);
        act.keyDown(Keys.LEFT_CONTROL).build().perform();
        for (int row : rows) {
            jsClick(TABULAR_PAGE + "//div[@class='ag-pinned-left-cols-container']/div[@row=" + (row - 1) + "]//span[@class='ag-selection-checkbox']/img[not(contains(@class,'hidden'))]");
        }
        act.keyUp(Keys.LEFT_CONTROL).build().perform();
        return this;
    }

    public TabularViewPage selectAllRowsOnCurrentPage() {
        click(TABULAR_PAGE + "//span[@ref='cbSelectAll']");
        CustomWait.staticWait(0.5);
        return this;
    }

    public TabularViewPage goToLastPage() {
        click(PAGINATION_LAST);
        waitForSpinnerFinish(30);//TODO: decrease time
        return this;
    }

    private void setAllAggregatesForColumn() {
        List<WebElement> allAggregatesElements = findElementsByXpath("//ul[@class='ag-dropdown dropdown-menu aggregates'][contains(@style,'display')]//li//input");
        allAggregatesElements.forEach(el -> {
            click(el);
        });
        click(XPATH_OK_AGGREGATE_BUTTON);
        // TODO: add spinner method
        waitForElementDisplayed(By.xpath(XPATH_TABULAR_VIEW_PAGE_SPINNER), 1);
        waitForElementNotDisplayed(By.xpath(XPATH_TABULAR_VIEW_PAGE_SPINNER), 10);
    }

    public String getColumnNames() {
        waitForElementNotDisplayed(By.xpath(SPINNER), 10);

        // check if data are displayed
        List<WebElement> allColumnsData = findElementsByXpath("//div[@class='ag-header-cell-label']");

        StringBuilder data = new StringBuilder();

        // get names
        allColumnsData.forEach(el -> {
            WebElement attributeName = findElementByXpath(el, "./span[@class='ag-header-cell-text']");
            data.append(attributeName.getText().trim())
                    .append("\n");
        });

        return data.toString();
    }

    private String getColumnNameId(String columnName) {
        return findElementByXpath(TABLE_HEADER_COLUMNS + "[contains(text(),'" + columnName + "')]/ancestor::div[@colid]")
                .getAttribute("colid");
    }

    private WebElement getColumnFilterDropdown(String columnName) {
        return findElementByXpath(TABLE_HEADER_COLUMNS + "[contains(text(),'" + columnName + "')]/parent::div/preceding-sibling::span");
    }

    public TabularViewPage refreshTabularPage() {
        click(PANEL_REFRESH_BUTTON_SELECTOR);
        waitForSpinnerFinish(30); //TODO:decrease time
        return this;
    }

    public TabularViewPage showMenuOnRow(int... rows) {
        // select object
        Actions act = new Actions(driver);
        act.keyDown(Keys.LEFT_CONTROL).build().perform();
        for (int row : rows) {
            jsClick(TABULAR_PAGE + "//div[@class='ag-pinned-left-cols-container']/div[@row=" + (row - 1) + "]//span[@class='ag-selection-checkbox']/img[not(contains(@class,'hidden'))]");
        }
        act.keyUp(Keys.LEFT_CONTROL).build().perform();

        click("//button[@id='menu-open-header']");

        return this;
    }

    public TabularViewPage openMenuForAllSelectedRows() {
        click("//button[@id='menu-open-header']");
        return this;
    }

    private WebElement getColumnElement(String colName) {
        return findElementByXpath(TABLE_HEADER_COLUMNS + "[contains(text(),'" + colName + "')]");
    }

    private void applyFilter() {
        click(getFilterConfirmationButton());
        waitForSpinnerFinish(3);
    }

    private WebElement getFilterConfirmationButton() {
        return findElementByXpath(FILTER_CONFIRMATION_BUTTON);
    }

    public void jsClick(String xpathExpression) {
        xpathExpression = xpathExpression.replaceAll("'", "\"");

        JavascriptExecutor jsDriver = (JavascriptExecutor) this.driver;
        jsDriver.executeScript("document.evaluate('" + xpathExpression + "',"
                + "document,null,XPathResult.ANY_TYPE,null).iterateNext().click()");
    }

    public String getAggregateValueForColumn(String colName, AggregateOption option) {
        String value = "";
        String aggregateCell = "//div[@colid='" + getColumnNameId(colName) + "']" +
                "//div[@class='aggregates']/h6[contains(text(),'" + option.name() + "')]";
        if (isElementDisplayed(aggregateCell, 1)) {
            value = findElementByXpath(findElementByXpath(aggregateCell, 1), "./small", 1).getText();
        } else {// if not open aggregates menu
            openAggregatesForColumn(colName);
            // select all aggregates
            setAllAggregatesForColumn();
            // read value
            value = findElementByXpath(findElementByXpath(aggregateCell, 1), "./small", 1).getText();
        }
        return value;
    }

    private TabularViewPage openAggregatesForColumn(String columnName) {
        jsClick("//div[@class='ag-floating-bottom-viewport']//div[@colid='" + getColumnNameId(columnName) + "']//button/i");
        CustomWait.staticWait(1);
        return this;
    }
}
