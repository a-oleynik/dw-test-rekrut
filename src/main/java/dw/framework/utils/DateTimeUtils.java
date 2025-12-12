package dw.framework.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final String DATETIME_FORMAT_FOR_ALLURE_CONSOLE_LOG = "ddMMyy hh:mm:ss.SSS";
    public static final String DATETIME_FORMAT_FOR_SCREENSHOT_NAME = "yyyyMMdd.hh.mm.ss.SSS";

    public static String getDateTimeForAllureConsoleLog() {
        return getCurrentDateTime(getDateTimeFormatter(DATETIME_FORMAT_FOR_ALLURE_CONSOLE_LOG));
    }

    public static String getDateTimeForScreenshotName() {
        return getCurrentDateTime(getDateTimeFormatter(DATETIME_FORMAT_FOR_SCREENSHOT_NAME));
    }

    public static String getCurrentDateTime(DateTimeFormatter formatter) {
        return getCurrentDateTime(ZoneId.of("Europe/Warsaw"), formatter);
    }

    public static String getCurrentDateTime(ZoneId zoneId, DateTimeFormatter formatter) {
        return LocalDateTime.now(zoneId).format(formatter);
    }

    public static DateTimeFormatter getDateTimeFormatter(String format) {
        return DateTimeFormatter.ofPattern(format);
    }
}
