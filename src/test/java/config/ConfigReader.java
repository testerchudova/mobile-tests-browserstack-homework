package config;

import org.aeonbits.owner.ConfigFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final BrowserstackConfig CONFIG =
            ConfigFactory.create(BrowserstackConfig.class, properties());

    private ConfigReader() {
    }

    public static String user() {
        return required("browserstack.user", "BROWSERSTACK_USER", CONFIG.browserstackUser());
    }

    public static String key() {
        return required("browserstack.key", "BROWSERSTACK_KEY", CONFIG.browserstackKey());
    }

    public static String url() {
        return CONFIG.url();
    }

    public static String apiUrl() {
        return CONFIG.apiUrl();
    }

    public static String localUrl() {
        return CONFIG.localUrl();
    }

    public static String project() {
        return CONFIG.project();
    }

    public static String build() {
        return CONFIG.build();
    }

    public static String name() {
        return CONFIG.name();
    }

    public static boolean debug() {
        return CONFIG.debug();
    }

    public static boolean networkLogs() {
        return CONFIG.networkLogs();
    }

    public static String platformName() {
        String value = CONFIG.platform().trim().toLowerCase();

        if ("ios".equals(value)) {
            return "iOS";
        }

        if ("android".equals(value)) {
            return "Android";
        }

        throw new IllegalArgumentException("Unsupported platform: " + CONFIG.platform());
    }

    public static String app() {
        return isIos()
                ? required("browserstack.app.ios", "BROWSERSTACK_APP_IOS", CONFIG.iosApp())
                : required("browserstack.app.android", "BROWSERSTACK_APP_ANDROID", CONFIG.androidApp());
    }

    public static String device() {
        return isIos() ? CONFIG.iosDevice() : CONFIG.androidDevice();
    }

    public static String osVersion() {
        return isIos() ? CONFIG.iosOsVersion() : CONFIG.androidOsVersion();
    }

    public static boolean isIos() {
        return "ios".equalsIgnoreCase(CONFIG.platform());
    }

    private static String required(String propertyName, String envName, String configuredValue) {
        String systemPropertyValue = System.getProperty(propertyName);

        if (!isBlank(systemPropertyValue)) {
            return systemPropertyValue;
        }

        String environmentValue = System.getenv(envName);

        if (!isBlank(environmentValue)) {
            return environmentValue;
        }

        if (!isBlank(configuredValue)) {
            return configuredValue;
        }

        throw new IllegalStateException(
                String.format("Set %s in properties, -D%s or %s environment variable",
                        propertyName, propertyName, envName));
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static Properties properties() {
        Properties properties = new Properties();

        load(properties, "config/browserstack.properties");
        load(properties, "config/browserstack.local.properties");
        System.getProperties().forEach((key, value) ->
                properties.setProperty(String.valueOf(key), String.valueOf(value)));

        return properties;
    }

    private static void load(Properties properties, String resourcePath) {
        try (InputStream stream = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (stream != null) {
                properties.load(stream);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + resourcePath, e);
        }
    }
}
