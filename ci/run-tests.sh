#!/bin/sh
set -eu

PLATFORM="${PLATFORM:-android}"

if [ "$PLATFORM" = "android" ]; then
  response="$(curl -sS -u "$BROWSERSTACK_USER:$BROWSERSTACK_KEY" \
    -X POST "https://api-cloud.browserstack.com/app-automate/upload" \
    -F "url=https://www.browserstack.com/app-automate/sample-apps/android/WikipediaSample.apk")"

  BROWSERSTACK_APP_ANDROID="$(printf '%s' "$response" \
    | sed -n 's/.*"app_url"[[:space:]]*:[[:space:]]*"\([^"]*\)".*/\1/p')"

  if [ -z "$BROWSERSTACK_APP_ANDROID" ]; then
    printf '%s\n' "$response"
    exit 1
  fi

  export BROWSERSTACK_APP_ANDROID
fi

sh ./gradlew clean test -Dplatform="$PLATFORM"
