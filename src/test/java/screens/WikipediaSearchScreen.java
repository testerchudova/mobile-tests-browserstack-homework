package screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.id;

public class WikipediaSearchScreen extends BaseScreen {
    private final WikipediaOnboardingScreen onboardingScreen = new WikipediaOnboardingScreen();
    private final By searchTab = id("org.wikipedia.alpha:id/nav_tab_search");
    private final By searchCard = id("org.wikipedia.alpha:id/search_card");
    private final By searchButton = accessibilityId("Search Wikipedia");
    private final By searchInput = id("org.wikipedia.alpha:id/search_src_text");
    private final By legacyResultTitle = id("org.wikipedia.alpha:id/page_list_item_title");
    private final By legacyResultContainer = id("org.wikipedia.alpha:id/page_list_item_container");
    private final By legacyOnboardingSkipButton = id("org.wikipedia.alpha:id/fragment_onboarding_skip_button");
    private final By knowledgeTitle = byText("All the world's knowledge");
    private final By dataPrivacyTitle = byText("Data & Privacy");
    private final By languagesTitle = byText("Read in more than 300 languages");
    private final By curiosityTitle = byText("Follow your curiosity");

    private String lastSearchText;

    public void searchFor(String text) {
        lastSearchText = text;
        skipOnboardingIfPresent();
        openSearchInput();

        WebElement input = find(searchInput);
        input.clear();
        input.sendKeys(text);
    }

    public void shouldHaveResults() {
        findAny(legacyResultTitle, resultByText());
    }

    public void openFirstArticle() {
        findAny(legacyResultContainer, legacyResultTitle, resultByText()).click();
    }

    private void skipOnboardingIfPresent() {
        for (int i = 0; i < 5; i++) {
            findAny(
                    searchTab,
                    searchCard,
                    searchButton,
                    legacyOnboardingSkipButton,
                    knowledgeTitle,
                    dataPrivacyTitle,
                    languagesTitle,
                    curiosityTitle
            );

            if (isSearchAvailable()) {
                return;
            }

            if (!elements(legacyOnboardingSkipButton).isEmpty()) {
                click(legacyOnboardingSkipButton);
                continue;
            }

            if (!elements(curiosityTitle).isEmpty()) {
                onboardingScreen.complete();
                continue;
            }

            onboardingScreen.next();
        }
    }

    private void openSearchInput() {
        if (!elements(searchInput).isEmpty()) {
            return;
        }

        if (!elements(searchTab).isEmpty()) {
            click(searchTab);
            findAny(searchInput, searchCard, searchButton);
        }

        if (!elements(searchInput).isEmpty()) {
            return;
        }

        if (!elements(searchCard).isEmpty()) {
            click(searchCard);
        } else {
            click(searchButton);
        }

        find(searchInput);
    }

    private boolean isSearchAvailable() {
        return !elements(searchTab).isEmpty()
                || !elements(searchCard).isEmpty()
                || !elements(searchButton).isEmpty();
    }

    private By resultByText() {
        return byTextView(lastSearchText);
    }
}
