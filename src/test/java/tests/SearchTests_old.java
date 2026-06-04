package tests;

import config.ConfigReader;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

@Disabled("Legacy raw WebDriver example. Use SearchTests with TestBase and Selenide.")
public class SearchTests_old {

    @Test
    void successfulSearchTest() throws MalformedURLException, InterruptedException {

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", ConfigReader.platformName());
        caps.setCapability("appium:app", ConfigReader.app());
        caps.setCapability("appium:deviceName", ConfigReader.device());
        caps.setCapability("appium:platformVersion", ConfigReader.osVersion());

        DesiredCapabilities browserstackOptions = new DesiredCapabilities();
        browserstackOptions.setCapability("userName", ConfigReader.user());
        browserstackOptions.setCapability("accessKey", ConfigReader.key());
        browserstackOptions.setCapability("projectName", ConfigReader.project());
        browserstackOptions.setCapability("buildName", ConfigReader.build());
        browserstackOptions.setCapability("sessionName", ConfigReader.name());
        caps.setCapability("bstack:options", browserstackOptions);


        RemoteWebDriver driver = new RemoteWebDriver(
                new URL(ConfigReader.url()), caps);

        WebElement searchElement = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.elementToBeClickable(
                        AppiumBy.accessibilityId("Search Wikipedia")));
        searchElement.click();
        WebElement insertTextElement = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.elementToBeClickable(
                        AppiumBy.id("org.wikipedia.alpha:id/search_src_text")));
        insertTextElement.sendKeys("Appium");
        Thread.sleep(5000);
        List<WebElement> allProductsName = driver.findElements(AppiumBy.className(
                "android.widget.TextView"));
        assert (allProductsName.size() > 0);


        driver.quit();

    }
}
