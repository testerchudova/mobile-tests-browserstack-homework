package screens;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.iOSNsPredicateString;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IosUiElementsScreen extends BaseScreen {

    public void shouldContainTextAction() {
        assertNotNull(findAny(
                accessibilityId("Text Button"),
                accessibilityId("Text"),
                iOSNsPredicateString("name == 'Text' OR label == 'Text'")
        ));
    }

    public void shouldContainAlertAction() {
        assertNotNull(findAny(
                accessibilityId("Alert Button"),
                accessibilityId("Alert"),
                iOSNsPredicateString("name == 'Alert' OR label == 'Alert'")
        ));
    }
}
