package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.BrowserstackDriver;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class TestBase {
    @BeforeAll
    static void beforeAll() {
        Configuration.browser = BrowserstackDriver.class.getName();
        Configuration.browserSize = null;
        Configuration.screenshots = false;
        Configuration.savePageSource = false;
        Configuration.timeout = 30000;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(false)
                .savePageSource(false));
    }

    @BeforeEach
    void beforeEach() {
        open();
    }

    @AfterEach
    void addAttachments() {
        if (!WebDriverRunner.hasWebDriverStarted()) {
            return;
        }

        String sessionId = Selenide.sessionId().toString();
        Attach.attachAsText("BrowserStack session id", sessionId);

        try {
            addSafely("Screenshot attachment error", () ->
                    Attach.screenshotAs("Last screenshot"));
            addSafely("Page source attachment error", Attach::pageSource);
        } finally {
            closeWebDriver();
        }

        addSafely("Video attachment error", () ->
                Attach.addVideo(sessionId));
    }

    private void addSafely(String attachName, AttachmentAction action) {
        try {
            action.run();
        } catch (Throwable error) {
            Attach.attachAsText(attachName, error.toString());
        }
    }

    @FunctionalInterface
    private interface AttachmentAction {
        void run();
    }
}
