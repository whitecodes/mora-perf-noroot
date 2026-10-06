# Changelog

All notable changes to this project are documented in this file.

## [Unreleased]

### Added

- GitHub Actions CI workflow that runs unit tests, builds the debug and release APKs, and uploads them as artifacts
  - Sets up the SDK with `android-actions/setup-android@v4`, relying on the platform 36 and build-tools that the runner image already provides
- Release signing support driven by `keystore.properties`, which CI reconstructs from repository secrets on push
- Dark, square-cornered Material 3 theme; dynamic color is intentionally not used
- Modal navigation drawer shell with text-only destinations (Home, Profiles, Games, LED, Settings)
- Token entry screen with local persistence; the token is saved only after `GET /api/state` succeeds
- Daemon API client (Retrofit, OkHttp, kotlinx.serialization) and a connection state machine that gates the shell
- Chinese strings under `values-zh`, kept in sync with the English base
- Unit tests for the documented `/api/state` payload, including forward compatibility with fields the app does not model yet

### Changed

- Set `compileSdk` to 36 so that `compileSdk`, `minSdk`, and `targetSdk` all match
- Align AndroidX versions with the API 36 toolchain: `core-ktx` 1.15.0, `lifecycle-runtime-ktx` 2.8.7, `activity-compose` 1.9.3, Compose BOM 2024.12.01
  - `androidx.core` 1.19.x and `androidx.lifecycle` 2.11.x require `compileSdk` 37

### Security

- Signing keystore and `keystore.properties` are excluded from version control
- CI restores signing secrets only on push events and removes them from the runner afterwards
- Cleartext HTTP is permitted only for `127.0.0.1` and `localhost`, never app-wide
- The API token is kept out of logs: OkHttp logging runs at `BASIC` and auth headers are redacted
