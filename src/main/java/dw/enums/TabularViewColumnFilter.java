package dw.enums;

public enum TabularViewColumnFilter {
    EQUALS("equals"),
    NOT_EQUAL("notEqual"),
    LESS_THEN("lessThan"),
    LESS_THAN_OR_EQUAL("lessThanOrEqual"),
    GREATER_THEN("greaterThan"),
    GREATER_THAN_OR_EQUAL("greaterThanOrEqual"),
    IN_RANGE("inRange"),
    STARTS_WITH("startsWith"),
    ENDS_WITH("endsWith"),
    CONTAINS("contains"),
    NOT_CONTAINS("notContains"),
    CONTAINS_WORDS("containsWords");

    private final String value;

    TabularViewColumnFilter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TabularViewColumnFilter getIgnoringWhiteAndCaseCharFrom(String text) {
        text = text.replaceAll("\\s+","")
                .replace("Notequals","Notequal");  //because <option> html tag value has typo
        for (TabularViewColumnFilter label : TabularViewColumnFilter.values()) {
            if (label.value.equalsIgnoreCase(text)) {
                return label;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
