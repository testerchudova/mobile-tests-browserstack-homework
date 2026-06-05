package screens;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.appium.java_client.AppiumBy.androidUIAutomator;

public class BaseScreen {
    private static final Duration TIMEOUT = Duration.ofSeconds(30);

    protected void click(By locator) {
        find(locator).click();
    }

    protected void type(By locator, String text) {
        find(locator).sendKeys(text);
    }

    protected WebElement find(By locator) {
        return webDriverWait().until(driver -> {
            List<WebElement> elements = driver.findElements(locator);
            return elements.isEmpty() ? null : elements.get(0);
        });
    }

    protected WebElement findAny(By... locators) {
        return webDriverWait().until(driver ->
                Arrays.stream(locators)
                        .map(driver::findElements)
                        .filter(elements -> !elements.isEmpty())
                        .map(elements -> elements.get(0))
                        .findFirst()
                        .orElse(null));
    }

    protected List<WebElement> findAll(By locator) {
        return webDriverWait().until(driver -> {
            List<WebElement> elements = driver.findElements(locator);
            return elements.isEmpty() ? null : elements;
        });
    }

    protected List<WebElement> elements(By locator) {
        return getWebDriver().findElements(locator);
    }

    protected By byText(String text) {
        return androidUIAutomator(String.format("new UiSelector().text(\"%s\")", escapeUiSelectorText(text)));
    }

    protected By byTextView(String text) {
        return androidUIAutomator(String.format(
                "new UiSelector().className(\"android.widget.TextView\").text(\"%s\")",
                escapeUiSelectorText(text)
        ));
    }

    protected void tapByRelativePosition(double xRatio, double yRatio) {
        Dimension size = getWebDriver().manage().window().getSize();
        int x = (int) Math.round(size.getWidth() * xRatio);
        int y = (int) Math.round(size.getHeight() * yRatio);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        ((RemoteWebDriver) getWebDriver()).perform(Collections.singletonList(tap));
    }

    protected void waitUntilGone(By locator) {
        webDriverWait().until(driver -> driver.findElements(locator).isEmpty());
    }

    private String escapeUiSelectorText(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private WebDriverWait webDriverWait() {
        return new WebDriverWait(getWebDriver(), TIMEOUT);
    }
}
