# Mobile tests homework 22/23

## Local run

Android tests:

```bash
./gradlew clean test -Dplatform=android -DdeviceHost=browserstack
```

iOS tests:

```bash
./gradlew clean test -Dplatform=ios -DdeviceHost=browserstack
```

Android emulator tests:

```bash
./gradlew clean test -Dplatform=android -DdeviceHost=emulation
```

Android real device tests:

```bash
./gradlew clean test -Dplatform=android -DdeviceHost=real
```

Credentials can be provided by:

- `src/test/resources/config/browserstack.local.properties`
- `-Dbrowserstack.user=... -Dbrowserstack.key=...`
- `BROWSERSTACK_USER` and `BROWSERSTACK_KEY` environment variables

`browserstack.local.properties` is ignored by git.

## Jenkins

Create Jenkins credentials:

- id: `browserstack-credentials`
- type: Username with password
- username: BrowserStack username
- password: BrowserStack access key

For a Freestyle job on a Linux agent use `Execute shell`:

```bash
sh ci/run-tests.sh
```

By default Jenkins runs `deviceHost=browserstack`. For local emulator or real device runs, start Appium with:

```bash
appium server --base-path /wd/hub
```

The pipeline publishes Allure results from `build/allure-results`.
