package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import screens.WikipediaArticleScreen;
import screens.WikipediaSearchScreen;

import static io.qameta.allure.Allure.step;

@Tag("android")
public class SearchTests extends TestBase {
    private final WikipediaSearchScreen searchScreen = new WikipediaSearchScreen();
    private final WikipediaArticleScreen articleScreen = new WikipediaArticleScreen();

    @Test
    void successfulSearchTest() {
        step("Type search", () ->
                searchScreen.searchFor("Appium"));

        step("Verify content found", () ->
                searchScreen.shouldHaveResults());
    }

    @Test
    void openArticleTest() {
        step("Search article", () ->
                searchScreen.searchFor("Appium"));

        step("Open first article", () ->
                searchScreen.openFirstArticle());

        step("Verify article is opened", () ->
                articleScreen.shouldBeOpened());
    }
}
