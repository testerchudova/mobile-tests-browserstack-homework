package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/real.properties",
        "classpath:config/real.local.properties",
        "system:properties"
})
public interface RealDeviceConfig extends Config {

    @Key("real.url")
    @DefaultValue("http://127.0.0.1:4723/wd/hub")
    String url();

    @Key("real.device")
    @DefaultValue("Android Device")
    String device();

    @Key("real.os.version")
    @DefaultValue("")
    String osVersion();

    @Key("real.app")
    @DefaultValue("")
    String app();

    @Key("real.app.package")
    @DefaultValue("org.wikipedia.alpha")
    String appPackage();

    @Key("real.app.activity")
    @DefaultValue("org.wikipedia.main.MainActivity")
    String appActivity();

    @Key("real.udid")
    @DefaultValue("")
    String udid();

    @Key("real.noReset")
    @DefaultValue("false")
    boolean noReset();
}
