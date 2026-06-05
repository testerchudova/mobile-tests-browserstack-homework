package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.ConfigReader;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;

public class MobileDriver implements WebDriverProvider {

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        switch (ConfigReader.deviceHost()) {
            case BROWSERSTACK:
                return new BrowserstackDriver().createDriver(capabilities);
            case EMULATION:
                return new EmulationDriver().createDriver(capabilities);
            case REAL:
                return new RealDeviceDriver().createDriver(capabilities);
            default:
                throw new IllegalArgumentException("Unsupported deviceHost: " + ConfigReader.deviceHost());
        }
    }
}
