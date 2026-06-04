# Mobile tests homework

## Local run

Android tests:

```bash
gradle clean test -Dplatform=android
```

iOS tests:

```bash
gradle clean test -Dplatform=ios
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

The pipeline publishes Allure results from `build/allure-results`.
