package dw.framework.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config.properties"})
public interface Configuration extends Config {
    @Key("username")
    String username();

    @Key("password")
    String password();

    @Key("base.url")
    String baseUrl();
}
