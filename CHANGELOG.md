# Changelog

All notable changes to this project are documented in this file.

## [Unreleased]

### Added

- GitHub Actions CI workflow that runs unit tests, builds the debug and release APKs, and uploads them as artifacts
  - Installs Android platform 37.1 to match the project's `compileSdk`
- Release signing support driven by `keystore.properties`, which CI reconstructs from repository secrets on push

### Security

- Signing keystore and `keystore.properties` are excluded from version control
- CI restores signing secrets only on push events and removes them from the runner afterwards
