package dw.framework.config;

import static dw.framework.config.ConfigurationManager.configuration;

public class Constants {
    public static final String BASE_URL = configuration().baseUrl();
    public static final String LOGIN = configuration().username();
    public static final String PASSWORD = configuration().password();
}
