package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import screens.WikipediaOnboardingScreen;

import static io.qameta.allure.Allure.step;

@Tag("android")
@EnabledIfSystemProperty(named = "deviceHost", matches = "emulation|real")
public class OnboardingTests extends TestBase {
    private final WikipediaOnboardingScreen onboardingScreen = new WikipediaOnboardingScreen();

    @Test
    void completeGettingStartedOnboardingTest() {
        step("Verify knowledge screen", () ->
                onboardingScreen.shouldHaveKnowledgeScreen());

        step("Open data privacy screen", () ->
                onboardingScreen.next());

        step("Verify data privacy screen", () ->
                onboardingScreen.shouldHaveDataPrivacyScreen());

        step("Open languages screen", () ->
                onboardingScreen.next());

        step("Verify languages screen", () ->
                onboardingScreen.shouldHaveLanguagesScreen());

        step("Open curiosity screen", () ->
                onboardingScreen.next());

        step("Verify curiosity screen", () ->
                onboardingScreen.shouldHaveCuriosityScreen());

        step("Complete onboarding", () ->
                onboardingScreen.complete());

        step("Verify onboarding is completed", () ->
                onboardingScreen.shouldBeCompleted());
    }
}
