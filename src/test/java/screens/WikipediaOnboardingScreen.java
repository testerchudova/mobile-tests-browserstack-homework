package screens;

import org.openqa.selenium.By;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.id;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WikipediaOnboardingScreen extends BaseScreen {
    private final By legacyForwardButton = id("org.wikipedia.alpha:id/fragment_onboarding_forward_button");
    private final By legacyDoneButton = id("org.wikipedia.alpha:id/fragment_onboarding_done_button");
    private final By searchTab = id("org.wikipedia.alpha:id/nav_tab_search");
    private final By searchCard = id("org.wikipedia.alpha:id/search_card");
    private final By searchAction = accessibilityId("Search");

    public void shouldHaveKnowledgeScreen() {
        shouldHaveTitle("All the world's knowledge");
    }

    public void shouldHaveDataPrivacyScreen() {
        shouldHaveTitle("Data & Privacy");
    }

    public void shouldHaveLanguagesScreen() {
        shouldHaveTitle("Read in more than 300 languages");
    }

    public void shouldHaveCuriosityScreen() {
        shouldHaveTitle("Follow your curiosity");
    }

    public void next() {
        if (!elements(legacyForwardButton).isEmpty()) {
            click(legacyForwardButton);
            return;
        }

        tapByRelativePosition(0.93, 0.90);
    }

    public void complete() {
        if (!elements(legacyDoneButton).isEmpty()) {
            click(legacyDoneButton);
            return;
        }

        tapByRelativePosition(0.07, 0.90);
    }

    public void shouldBeCompleted() {
        findAny(searchTab, searchCard, searchAction);
    }

    private void shouldHaveTitle(String expectedTitle) {
        assertEquals(expectedTitle, find(byText(expectedTitle)).getText());
    }
}
