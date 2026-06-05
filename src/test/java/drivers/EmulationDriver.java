package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.ConfigReader;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

public class EmulationDriver implements WebDriverProvider {

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        try {
            return new RemoteWebDriver(new URL(ConfigReader.emulationUrl()), emulationCapabilities());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private Capabilities emulationCapabilities() {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setDeviceName(ConfigReader.emulationDevice());
        options.setPlatformVersion(ConfigReader.emulationOsVersion());
        options.setCapability("appPackage", ConfigReader.emulationAppPackage());
        options.setCapability("appActivity", ConfigReader.emulationAppActivity());
        options.setCapability("noReset", ConfigReader.emulationNoReset());

        if (!ConfigReader.emulationApp().isEmpty()) {
            options.setApp(ConfigReader.emulationApp());
        }

        return options;
    }
}
