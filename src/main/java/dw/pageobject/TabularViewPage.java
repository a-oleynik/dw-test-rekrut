package dw.pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class TabularViewPage extends AbstractPageObject {

    private static final String TABULAR_PAGE = "//div[@class='ag-table ag-datawalk']";
    private static final String TABLE_HEADER_COLUMNS = TABULAR_PAGE + "//span";
    private static final String PAGINATION_BAR = "//div[contains(@class,'ag-paging-panel')]";
    private static final String PAGINATION_LAST = PAGINATION_BAR + "//button[@ref='btLast']";
    private static final String FILTER_INSERT_INPUTS = "//div[@class='ag-filter']//div[contains(@class,'filter') and not(contains(@class,'hidden')) or not(@class)]/input[@class='ag-filter-filter']";
    private static final String FILTER_CONFIRMATION_BUTTON = "//button[@id='applyButton']";
    private static final String PANEL_REFRESH_BUTTON_SELECTOR = "//button[@data-ng-click='panel.refresh()']";
    private static final String XPATH_OK_AGGREGATE_BUTTON = "//ul[@class='ag-dropdown dropdown-menu aggregates'][contains(@style,'display')]/button";
    private static final String XPATH_TABULAR_VIEW_PAGE_SPINNER = "//div[@class='spinner-md']";
    private static final String XPATH_TABLE_NAME = "//table//*[contains(@class, 'tab-table')]";
    private static final String XPATH_ALL_COLUMNS_BUTTON = "//datawalk-chip/span";
    private static final String XPATH_SCROLL_BAR = "//div[@class='ag-body-viewport']";
    private static final String XPATH_COLUMN_HEADER = "//div[@colid='%s']";
    private static final String XPATH_AGGREGATE_COLUMN_VALUE = XPATH_COLUMN_HEADER + "//div[@class='aggregates']/h6[contains(text(),'%s')]";
    private static final String XPATH_COLUMN_SORTING = XPATH_COLUMN_HEADER +
            "//span[contains(@ref,'eSort') and not(contains(@class, 'hidden')) and not(contains(@ref,'eSortOrder'))]";
    private static final String XPATH_AGGREGATES_MENU_DROPDOWN
            = "//ul[@class='ag-dropdown dropdown-menu aggregates'][contains(@style,'display')]";
    private static final String XPATH_ALL_AGGREGATES_ELEMENTS = XPATH_AGGREGATES_MENU_DROPDOWN + "//li//input";
    private static final String XPATH_SEPARATE_AGGREGATES_ELEMENTS = XPATH_AGGREGATES_MENU_DROPDOWN + "//label[contains(.,'%s')]/input";

    public TabularViewPage() {
        // Check if the table name of the first table and the All columns button are visible
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
        waitForSpinnerFinish(30);
        return this;
    }

    private void setAllAggregatesForColumn() {
        List<WebElement> allAggregatesElements = findElementsByXpath(XPATH_ALL_AGGREGATES_ELEMENTS);
        allAggregatesElements.forEach(el -> {
            if (!el.isSelected()){
                click(el);
            }
        });
        click(XPATH_OK_AGGREGATE_BUTTON);
        waitForElementNotDisplayed(By.xpath(XPATH_AGGREGATES_MENU_DROPDOWN), 3);
    }

    private void setAggregateForColumn(AggregateOption option) {
        WebElement aggregateCheckbox = findElementByXpath(format(XPATH_SEPARATE_AGGREGATES_ELEMENTS, option.name()));
        if (!aggregateCheckbox.isSelected()) {
            click(aggregateCheckbox);
        }
        click(XPATH_OK_AGGREGATE_BUTTON);
        waitForElementNotDisplayed(By.xpath(XPATH_AGGREGATES_MENU_DROPDOWN), 3);
    }

    public void cleanAggregateForColumn(AggregateOption option) {
        WebElement aggregateCheckbox = findElementByXpath(format(XPATH_SEPARATE_AGGREGATES_ELEMENTS, option.name()));
        if (aggregateCheckbox.isSelected()) {
            click(aggregateCheckbox);
        }
        click(XPATH_OK_AGGREGATE_BUTTON);
        waitForElementNotDisplayed(By.xpath(XPATH_AGGREGATES_MENU_DROPDOWN), 3);
    }

    public void cleanAllAggregatesForColumn() {
        List<WebElement> allAggregatesElements = findElementsByXpath(XPATH_ALL_AGGREGATES_ELEMENTS);
        allAggregatesElements.forEach(el -> {
            if (el.isSelected()) {
                click(el);
            }
        });
        click(XPATH_OK_AGGREGATE_BUTTON);
        waitForElementNotDisplayed(By.xpath(XPATH_AGGREGATES_MENU_DROPDOWN), 3);
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
        By by = By.xpath(TABLE_HEADER_COLUMNS + "[contains(text(),'" + columnName + "')]/ancestor::div[@colid]");
        return scrollLeftUntilElementPresent(by, XPATH_SCROLL_BAR).getAttribute("colid");
        // Alternative implementation
        //return scrollLeftUntilElementPresentV2(by, XPATH_SCROLL_BAR, 5).getAttribute("colid");
    }

    private WebElement getColumnFilterDropdown(String columnName) {
        return findElementByXpath(TABLE_HEADER_COLUMNS + "[contains(text(),'" + columnName + "')]/parent::div/preceding-sibling::span");
    }

    public TabularViewPage refreshTabularPage() {
        click(PANEL_REFRESH_BUTTON_SELECTOR);
        waitForSpinnerFinish(30);
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
        String aggregateCell = format(XPATH_AGGREGATE_COLUMN_VALUE, getColumnNameId(colName), option.name());
        if (isElementDisplayed(aggregateCell, 1)) {
            value = findElementByXpath(findElementByXpath(aggregateCell, 1), "./small", 1).getText();
        } else {// if not open aggregates menu
            openAggregatesForColumn(colName);
            // select all aggregates
            setAggregateForColumn(option);
            //TODO: we need to set only the required option but the previous variant should be checked with the available
            // scenarios with the current team. Return the previous variant if required
            //setAllAggregatesForColumn();
            // read value
            value = findElementByXpath(findElementByXpath(aggregateCell, 1), "./small", 1).getText();
        }
        return value;
    }

    public TabularViewPage sortColumn(String columnName, SortOption option) {
        if (getColumnSorting(columnName).equals(option)) {
            return this;
        }
        for (int i = 0; i < 2; i++) {
            click(format(XPATH_COLUMN_HEADER, getColumnNameId(columnName)));
            if (getColumnSorting(columnName).equals(option)) {
                return this;
            }
        }
        throw new RuntimeException("Cannot switch to " + option);
    }

    public SortOption getColumnSorting(String columnName) {
        String refValue = findElementByXpath(format(XPATH_COLUMN_SORTING, getColumnNameId(columnName))).getAttribute("ref");
        return SortOption.fromRef(refValue).orElse(SortOption.NONE);
    }

    public TabularViewPage openAggregatesForColumn(String columnName) {
        jsClick("//div[@class='ag-floating-bottom-viewport']//div[@colid='" + getColumnNameId(columnName) + "']//button/i");
        return this;
    }
}
