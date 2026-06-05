package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/emulation.properties",
        "classpath:config/emulation.local.properties",
        "system:properties"
})
public interface EmulationConfig extends Config {

    @Key("emulation.url")
    @DefaultValue("http://127.0.0.1:4723/wd/hub")
    String url();

    @Key("emulation.device")
    @DefaultValue("Pixel 4")
    String device();

    @Key("emulation.os.version")
    @DefaultValue("11.0")
    String osVersion();

    @Key("emulation.app")
    @DefaultValue("")
    String app();

    @Key("emulation.app.package")
    @DefaultValue("org.wikipedia.alpha")
    String appPackage();

    @Key("emulation.app.activity")
    @DefaultValue("org.wikipedia.main.MainActivity")
    String appActivity();

    @Key("emulation.noReset")
    @DefaultValue("false")
    boolean noReset();
}
