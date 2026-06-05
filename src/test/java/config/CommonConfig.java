package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/common.properties",
        "classpath:config/common.local.properties",
        "system:properties"
})
public interface CommonConfig extends Config {

    @Key("platform")
    @DefaultValue("android")
    String platform();

    @Key("deviceHost")
    @DefaultValue("browserstack")
    String deviceHost();
}
