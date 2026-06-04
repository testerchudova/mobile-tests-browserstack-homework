package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.ConfigReader;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

public class BrowserstackDriver implements WebDriverProvider {

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        try {
            return new RemoteWebDriver(new URL(ConfigReader.url()), mobileCapabilities());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private Capabilities mobileCapabilities() {
        return ConfigReader.isIos() ? iosCapabilities() : androidCapabilities();
    }

    private Capabilities androidCapabilities() {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName(ConfigReader.platformName());
        options.setPlatformVersion(ConfigReader.osVersion());
        options.setDeviceName(ConfigReader.device());
        options.setApp(ConfigReader.app());
        options.setCapability("bstack:options", browserstackOptions());

        return options;
    }

    private Capabilities iosCapabilities() {
        XCUITestOptions options = new XCUITestOptions();

        options.setPlatformName(ConfigReader.platformName());
        options.setPlatformVersion(ConfigReader.osVersion());
        options.setDeviceName(ConfigReader.device());
        options.setApp(ConfigReader.app());
        options.setCapability("bstack:options", browserstackOptions());

        return options;
    }

    private MutableCapabilities browserstackOptions() {
        MutableCapabilities options = new MutableCapabilities();

        options.setCapability("userName", ConfigReader.user());
        options.setCapability("accessKey", ConfigReader.key());
        options.setCapability("projectName", ConfigReader.project());
        options.setCapability("buildName", ConfigReader.build());
        options.setCapability("sessionName", ConfigReader.name());
        options.setCapability("debug", ConfigReader.debug());
        options.setCapability("networkLogs", ConfigReader.networkLogs());

        return options;
    }
}
