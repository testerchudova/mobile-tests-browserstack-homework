package config;

import org.aeonbits.owner.ConfigFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties PROPERTIES = properties();
    private static final CommonConfig COMMON_CONFIG =
            ConfigFactory.create(CommonConfig.class, PROPERTIES);
    private static final BrowserstackConfig BROWSERSTACK_CONFIG =
            ConfigFactory.create(BrowserstackConfig.class, PROPERTIES);
    private static final EmulationConfig EMULATION_CONFIG =
            ConfigFactory.create(EmulationConfig.class, PROPERTIES);
    private static final RealDeviceConfig REAL_DEVICE_CONFIG =
            ConfigFactory.create(RealDeviceConfig.class, PROPERTIES);

    private ConfigReader() {
    }

    public static String user() {
        return required("browserstack.user", "BROWSERSTACK_USER", BROWSERSTACK_CONFIG.browserstackUser());
    }

    public static String key() {
        return required("browserstack.key", "BROWSERSTACK_KEY", BROWSERSTACK_CONFIG.browserstackKey());
    }

    public static String url() {
        return BROWSERSTACK_CONFIG.url();
    }

    public static String apiUrl() {
        return BROWSERSTACK_CONFIG.apiUrl();
    }

    public static String localUrl() {
        return BROWSERSTACK_CONFIG.localUrl();
    }

    public static String project() {
        return BROWSERSTACK_CONFIG.project();
    }

    public static String build() {
        return BROWSERSTACK_CONFIG.build();
    }

    public static String name() {
        return BROWSERSTACK_CONFIG.name();
    }

    public static boolean debug() {
        return BROWSERSTACK_CONFIG.debug();
    }

    public static boolean networkLogs() {
        return BROWSERSTACK_CONFIG.networkLogs();
    }

    public static String platformName() {
        String value = COMMON_CONFIG.platform().trim().toLowerCase();

        if ("ios".equals(value)) {
            return "iOS";
        }

        if ("android".equals(value)) {
            return "Android";
        }

        throw new IllegalArgumentException("Unsupported platform: " + COMMON_CONFIG.platform());
    }

    public static DeviceHost deviceHost() {
        String value = COMMON_CONFIG.deviceHost().trim().toUpperCase();

        try {
            return DeviceHost.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported deviceHost: " + COMMON_CONFIG.deviceHost(), e);
        }
    }

    public static boolean isBrowserstack() {
        return deviceHost() == DeviceHost.BROWSERSTACK;
    }

    public static String app() {
        return isIos()
                ? required("browserstack.app.ios", "BROWSERSTACK_APP_IOS", BROWSERSTACK_CONFIG.iosApp())
                : required("browserstack.app.android", "BROWSERSTACK_APP_ANDROID", BROWSERSTACK_CONFIG.androidApp());
    }

    public static String device() {
        return isIos() ? BROWSERSTACK_CONFIG.iosDevice() : BROWSERSTACK_CONFIG.androidDevice();
    }

    public static String osVersion() {
        return isIos() ? BROWSERSTACK_CONFIG.iosOsVersion() : BROWSERSTACK_CONFIG.androidOsVersion();
    }

    public static boolean isIos() {
        return "ios".equalsIgnoreCase(COMMON_CONFIG.platform());
    }

    public static String emulationUrl() {
        return EMULATION_CONFIG.url();
    }

    public static String emulationDevice() {
        return EMULATION_CONFIG.device();
    }

    public static String emulationOsVersion() {
        return EMULATION_CONFIG.osVersion();
    }

    public static String emulationApp() {
        return firstNonBlank(System.getenv("EMULATION_APP"), EMULATION_CONFIG.app());
    }

    public static String emulationAppPackage() {
        return EMULATION_CONFIG.appPackage();
    }

    public static String emulationAppActivity() {
        return EMULATION_CONFIG.appActivity();
    }

    public static boolean emulationNoReset() {
        return EMULATION_CONFIG.noReset();
    }

    public static String realDeviceUrl() {
        return REAL_DEVICE_CONFIG.url();
    }

    public static String realDeviceName() {
        return REAL_DEVICE_CONFIG.device();
    }

    public static String realDeviceOsVersion() {
        return REAL_DEVICE_CONFIG.osVersion();
    }

    public static String realDeviceApp() {
        return firstNonBlank(System.getenv("REAL_DEVICE_APP"), REAL_DEVICE_CONFIG.app());
    }

    public static String realDeviceAppPackage() {
        return REAL_DEVICE_CONFIG.appPackage();
    }

    public static String realDeviceAppActivity() {
        return REAL_DEVICE_CONFIG.appActivity();
    }

    public static String realDeviceUdid() {
        return REAL_DEVICE_CONFIG.udid();
    }

    public static boolean realDeviceNoReset() {
        return REAL_DEVICE_CONFIG.noReset();
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

    private static String firstNonBlank(String firstValue, String secondValue) {
        return isBlank(firstValue) ? secondValue : firstValue;
    }

    private static Properties properties() {
        Properties properties = new Properties();

        load(properties, "config/common.properties");
        load(properties, "config/browserstack.properties");
        load(properties, "config/emulation.properties");
        load(properties, "config/real.properties");
        load(properties, "config/common.local.properties");
        load(properties, "config/browserstack.local.properties");
        load(properties, "config/emulation.local.properties");
        load(properties, "config/real.local.properties");
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
