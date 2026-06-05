package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import screens.IosUiElementsScreen;

import static io.qameta.allure.Allure.step;

@Tag("ios")
public class IosSearchTests extends TestBase {
    private final IosUiElementsScreen uiElementsScreen = new IosUiElementsScreen();

    @Test
    void uiElementsScreenShouldContainMainActionsTest() {
        step("Verify text action is available", () ->
                uiElementsScreen.shouldContainTextAction());

        step("Verify alert action is available", () ->
                uiElementsScreen.shouldContainAlertAction());
    }
}
