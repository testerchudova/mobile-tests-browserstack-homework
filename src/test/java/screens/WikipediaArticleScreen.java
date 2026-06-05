package screens;

import org.openqa.selenium.By;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.id;

public class WikipediaArticleScreen extends BaseScreen {
    private final By pageWebView = id("org.wikipedia.alpha:id/page_web_view");
    private final By pageSaveButton = id("org.wikipedia.alpha:id/page_save");
    private final By gamesPopupCloseButton = id("org.wikipedia.alpha:id/closeButton");
    private final By popupCloseButton = accessibilityId("Close");

    public void shouldBeOpened() {
        for (int i = 0; i < 3; i++) {
            findAny(pageWebView, pageSaveButton, gamesPopupCloseButton, popupCloseButton);
            closePopupIfPresent();

            if (!elements(pageWebView).isEmpty() || !elements(pageSaveButton).isEmpty()) {
                return;
            }
        }

        findAny(pageWebView, pageSaveButton);
    }

    private void closePopupIfPresent() {
        if (!elements(gamesPopupCloseButton).isEmpty()) {
            click(gamesPopupCloseButton);
            return;
        }

        if (!elements(popupCloseButton).isEmpty()) {
            click(popupCloseButton);
        }
    }
}
