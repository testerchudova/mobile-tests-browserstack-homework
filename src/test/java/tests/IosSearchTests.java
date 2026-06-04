package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.iOSNsPredicateString;
import static io.qameta.allure.Allure.step;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("ios")
public class IosSearchTests extends TestBase {
    private static final Duration TIMEOUT = Duration.ofSeconds(30);

    @Test
    void uiElementsScreenShouldContainMainActionsTest() {
        step("Verify text action is available", () ->
                assertNotNull(findAny(
                        accessibilityId("Text Button"),
                        accessibilityId("Text"),
                        iOSNsPredicateString("name == 'Text' OR label == 'Text'")
                )));

        step("Verify alert action is available", () ->
                assertNotNull(findAny(
                        accessibilityId("Alert Button"),
                        accessibilityId("Alert"),
                        iOSNsPredicateString("name == 'Alert' OR label == 'Alert'")
                )));
    }

    private WebElement findAny(By... locators) {
        return webDriverWait().until(driver -> {
            return Arrays.stream(locators)
                    .map(driver::findElements)
                    .filter(elements -> !elements.isEmpty())
                    .map(elements -> elements.get(0))
                    .findFirst()
                    .orElse(null);
        });
    }

    private WebDriverWait webDriverWait() {
        return new WebDriverWait(getWebDriver(), TIMEOUT);
    }
}
