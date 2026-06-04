package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/browserstack.properties",
        "classpath:config/browserstack.local.properties",
        "system:properties"
})
public interface BrowserstackConfig extends Config {

    @Key("platform")
    @DefaultValue("android")
    String platform();

    @Key("browserstack.user")
    @DefaultValue("")
    String browserstackUser();

    @Key("browserstack.key")
    @DefaultValue("")
    String browserstackKey();

    @Key("browserstack.url")
    @DefaultValue("https://hub.browserstack.com/wd/hub")
    String url();

    @Key("browserstack.api.url")
    @DefaultValue("https://api.browserstack.com/app-automate/sessions")
    String apiUrl();

    @Key("browserstack.local.url")
    @DefaultValue("")
    String localUrl();

    @Key("browserstack.project")
    @DefaultValue("Mobile automation homework")
    String project();

    @Key("browserstack.build")
    @DefaultValue("browserstack-build-1")
    String build();

    @Key("browserstack.name")
    @DefaultValue("Wikipedia mobile test")
    String name();

    @Key("browserstack.debug")
    @DefaultValue("true")
    boolean debug();

    @Key("browserstack.networkLogs")
    @DefaultValue("true")
    boolean networkLogs();

    @Key("browserstack.app.android")
    @DefaultValue("")
    String androidApp();

    @Key("android.device")
    @DefaultValue("Google Pixel 7")
    String androidDevice();

    @Key("android.os.version")
    @DefaultValue("13.0")
    String androidOsVersion();

    @Key("browserstack.app.ios")
    @DefaultValue("bs://sample.app")
    String iosApp();

    @Key("ios.device")
    @DefaultValue("iPhone 14 Pro Max")
    String iosDevice();

    @Key("ios.os.version")
    @DefaultValue("16")
    String iosOsVersion();
}
