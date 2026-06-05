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

public class RealDeviceDriver implements WebDriverProvider {

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        try {
            return new RemoteWebDriver(new URL(ConfigReader.realDeviceUrl()), realDeviceCapabilities());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private Capabilities realDeviceCapabilities() {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setDeviceName(ConfigReader.realDeviceName());
        options.setCapability("appPackage", ConfigReader.realDeviceAppPackage());
        options.setCapability("appActivity", ConfigReader.realDeviceAppActivity());
        options.setCapability("noReset", ConfigReader.realDeviceNoReset());

        if (!ConfigReader.realDeviceOsVersion().isEmpty()) {
            options.setPlatformVersion(ConfigReader.realDeviceOsVersion());
        }

        if (!ConfigReader.realDeviceUdid().isEmpty()) {
            options.setCapability("udid", ConfigReader.realDeviceUdid());
        }

        if (!ConfigReader.realDeviceApp().isEmpty()) {
            options.setApp(ConfigReader.realDeviceApp());
        }

        return options;
    }
}
