# Changelog

All notable changes to this project are documented in this file.

## [Unreleased]

### Added

- GitHub Actions CI workflow that runs unit tests, builds the debug and release APKs, and uploads them as artifacts
  - Installs Android platform 36 to match the project's `compileSdk`
- Release signing support driven by `keystore.properties`, which CI reconstructs from repository secrets on push

### Changed

- Set `compileSdk` to 36 so that `compileSdk`, `minSdk`, and `targetSdk` all match
- Align AndroidX versions with the API 36 toolchain: `core-ktx` 1.15.0, `lifecycle-runtime-ktx` 2.8.7, `activity-compose` 1.9.3, Compose BOM 2024.12.01
  - `androidx.core` 1.19.x and `androidx.lifecycle` 2.11.x require `compileSdk` 37

### Security

- Signing keystore and `keystore.properties` are excluded from version control
- CI restores signing secrets only on push events and removes them from the runner afterwards
