package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static io.appium.java_client.AppiumBy.*;
import static io.qameta.allure.Allure.step;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Tag("android")
public class SearchTests extends TestBase {
    private static final Duration TIMEOUT = Duration.ofSeconds(30);

    @Test
    void successfulSearchTest() {
        step("Type search", () -> {
            skipOnboardingIfPresent();
            click(accessibilityId("Search Wikipedia"));
            type(id("org.wikipedia.alpha:id/search_src_text"), "Appium");
        });

        step("Verify content found", () ->
                assertFalse(searchResults().isEmpty()));
    }

    @Test
    void openArticleTest() {
        step("Search article", () -> {
            skipOnboardingIfPresent();
            click(accessibilityId("Search Wikipedia"));
            type(id("org.wikipedia.alpha:id/search_src_text"), "Appium");
        });

        step("Open first article", this::openFirstArticle);

        step("Verify article is opened", () ->
                waitUntilGone(id("org.wikipedia.alpha:id/page_list_item_title")));
    }

    private void skipOnboardingIfPresent() {
        List<WebElement> skipButtons = getWebDriver()
                .findElements(id("org.wikipedia.alpha:id/fragment_onboarding_skip_button"));

        if (!skipButtons.isEmpty()) {
            skipButtons.get(0).click();
        }
    }

    private void click(By locator) {
        find(locator).click();
    }

    private void type(By locator, String text) {
        find(locator).sendKeys(text);
    }

    private List<WebElement> searchResults() {
        return findAll(id("org.wikipedia.alpha:id/page_list_item_title"));
    }

    private void openFirstArticle() {
        searchResults();

        List<WebElement> articleContainers = getWebDriver()
                .findElements(id("org.wikipedia.alpha:id/page_list_item_container"));

        if (!articleContainers.isEmpty()) {
            articleContainers.get(0).click();
            return;
        }

        searchResults().get(0).click();
    }

    private WebElement find(By locator) {
        return webDriverWait().until(driver -> {
            List<WebElement> elements = driver.findElements(locator);
            return elements.isEmpty() ? null : elements.get(0);
        });
    }

    private List<WebElement> findAll(By locator) {
        return webDriverWait().until(driver -> {
            List<WebElement> elements = driver.findElements(locator);
            return elements.isEmpty() ? null : elements;
        });
    }

    private void waitUntilGone(By locator) {
        webDriverWait().until(driver -> driver.findElements(locator).isEmpty());
    }

    private WebDriverWait webDriverWait() {
        return new WebDriverWait(getWebDriver(), TIMEOUT);
    }
}
