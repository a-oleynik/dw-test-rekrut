package dw.framework.config;

import java.io.File;

import static dw.framework.config.ConfigurationManager.configuration;

public class Constants {
    public static final String BASE_URL = configuration().baseUrl();
    public static final String LOGIN = configuration().username();
    public static final String PASSWORD = configuration().password();
    public static final String TARGET_FOLDER = "target" + File.separator;
    public static final String SCREENSHOTS_FOLDER = TARGET_FOLDER + "screenshots" + File.separator;
}
