<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# GitHub Issue Creator Changelog

## [Unreleased]

### Added
- Initial release of GitHub Issue Creator plugin
- Auto-detect GitHub repository from Git remote (HTTPS and SSH URLs)
- Secure GitHub Personal Access Token storage using IntelliJ's PasswordSafe
- Create GitHub issues with title, description, labels, assignees, and milestone
- Modular project structure with packages: api, ui, auth, git
- "Create GitHub Issue" action in Tools menu
- GitHub REST API client implementation
- Comprehensive error handling
- Support for loading repository labels and milestones

[Unreleased]: https://github.com/tayjaybabee/GitHubIssue/commits/main

## [2.4.0] - 2025-11-25

### Fixed

- Use the GitHub event release body for a condition when creating the Release Draft

### Changed

- Use `intellijIdea(version)` dependency helper instead of `create(type,  version)`
- Upgrade Gradle Wrapper to `9.2.1`
- Update `platformVersion` to `2025.2.5`
- Change since build to `252` (2025.2)
- Dependencies - upgrade `org.jetbrains.intellij.platform` to `2.10.5`
- Dependencies - upgrade `org.jetbrains.changelog` to `2.5.0`
- Dependencies - upgrade `org.jetbrains.kotlin.jvm` to `2.2.21`
- Dependencies - upgrade `org.jetbrains.kotlinx.kover` to `0.9.3`
- Dependencies - upgrade `org.jetbrains.qodana` to `2025.2.2`
- Dependencies (GitHub Actions) - upgrade `actions/checkout` to `v5`
- Dependencies (GitHub Actions) - upgrade `actions/setup-java` to `v5`
- Dependencies (GitHub Actions) - upgrade `actions/upload-artifact` to `v5`
- Dependencies (GitHub Actions) - upgrade `gradle/actions/setup-gradle` to `v5`
- Dependencies (GitHub Actions) - upgrade `JetBrains/qodana-action` to `v2025.2.2`

### Removed

- Remove the `platformType` Gradle property

## [2.3.1] - 2025-08-09

### Added

- Add `platformBundledModules` to `gradle.properties` along with `bundledModules()` helper to the Gradle build file

### Changed

- Dependencies - upgrade `org.jetbrains.intellij.platform` to `2.7.1`

### Fixed

- GitHub: Fixed the missing `$RELEASE_NOTE ` parent directory in the Release workflow

## [2.3.0] - 2025-08-09

### Added

- Added `.DS_Store` directory to `.gitignore`

### Changed

- GitHub Actions: simplify changelog handling
- Dependencies - upgrade `org.jetbrains.changelog` to `2.4.0`

## [2.2.0] - 2025-08-05

### Added

- GitHub Actions: set Gradle cache to read-only for non-build jobs
- GitHub Actions: add `jlumbroso/free-disk-space` action to build related steps to maximize build environment storage
- Added `.kotlin` directory to `.gitignore` for Kotlin 2.0

### Removed

- Remove `pluginUntilBuild` obsolete property
- GitHub Actions: Remove obsolete Plugin Verifier cache directory configuration 

### Changed

- Upgrade Gradle Wrapper to `8.14.3`
- Update `platformVersion` to `2024.3.6`
- Dependencies - upgrade `org.jetbrains.intellij.platform` to `2.7.0`
- Dependencies - upgrade `org.jetbrains.changelog` to `2.3.0`
- Dependencies - upgrade `org.jetbrains.qodana` to `2025.1.1`
- Dependencies - upgrade `org.jetbrains.kotlin.jvm` to `2.2.0`
- Dependencies (GitHub Actions) - upgrade `JetBrains/qodana-action` to `v2025.1.1`
- Dependencies (GitHub Actions) - upgrade `ad-m/github-push-action` to `v0.8.0`
- Dependencies (GitHub Actions) - upgrade `jlumbroso/free-disk-space` to `v1.3.1`
- Gradle - upgrade `org.gradle.toolchains.foojay-resolver-convention` to `1.0.0`
- Change since build to `243` (2024.3)
- Update codecov configuration based on new required upload token

## [2.1.0] - 2025-03-28

### Added

- Example code – `ProjectActivity`
- Added `opentest4j` test dependency, see: [Missing opentest4j dependency in Test Framework](https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-faq.html#missing-opentest4j-dependency-in-test-framework)

### Removed

- Example code – `MyApplicationActivationListener`
- Remove redundant IntelliJ Platform dependency helpers: `instrumentationTools()`, `pluginVerifier()`, `zipSigner()`
- GitHub Actions: Remove `gradle/actions/wrapper-validation` because validation is performed by default in `gradle/actions/setup-gradle@v4`

### Changed

- Change JVM version to `21`
- Upgrade Gradle Wrapper to `8.13`
- Update `platformVersion` to `2024.2.5`
- Change since/until build to `242-252.*` (2024.2-2025.2.*)
- Dependencies - upgrade `org.jetbrains.intellij.platform` to `2.5.0`
- Dependencies - upgrade `org.jetbrains.kotlin.jvm` to `2.1.20`
- Dependencies - upgrade `org.jetbrains.qodana` to `2024.3.4`
- Dependencies - upgrade `org.jetbrains.kotlinx.kover` to `0.9.1`
- Dependencies (GitHub Actions) - upgrade `gradle/actions/wrapper-validation` to `v4`
- Dependencies (GitHub Actions) - upgrade `codecov/codecov-action` to `v5`

## [2.0.2] - 2024-10-07

### Changed

- Upgrade Gradle Wrapper to `8.10.2`
- Update `platformVersion` to `2023.3.8`
- Dependencies - upgrade `org.jetbrains.intellij.platform` to `2.1.0`
- Dependencies - upgrade `org.jetbrains.qodana` to `2024.2.3`
- Dependencies (GitHub Actions) - upgrade `gradle/actions/setup-gradle` to `v4`
- Add back the `org.gradle.toolchains.foojay-resolver-convention` Gradle settings plugin

### Fixed

- Fixed _Run Plugin_ run configuration logs location

### Removed

- Removed _Run Qodana_ and _Run UI for UI Tests_ run configurations

## [2.0.1] - 2024-08-09

### Changed

- Update `platformVersion` to `2023.3.7`
- Change since/until build to `233-242.*` (2023.3-2024.2.*)
- Cleanup registering the `runIdeForUiTests` task
- Dependencies - upgrade `org.jetbrains.intellij.platform` to `2.0.1`
- Dependencies - upgrade `org.jetbrains.kotlin.jvm` to `1.9.25`
- Dependencies - upgrade `org.jetbrains.kotlinx.kover` to `0.8.3`
- Dependencies - upgrade `org.jetbrains.qodana` to `2024.1.9`

## [2.0.0] - 2024-07-30

### Changed

- Migrate to [IntelliJ Platform Gradle Plugin 2.0](https://blog.jetbrains.com/platform/2024/07/intellij-platform-gradle-plugin-2-0/).

## [1.14.2] - 2024-07-12

### Changed

- Upgrade Gradle Wrapper to `8.9`

### Removed

- Remove default plugin icon (`pluginIcon.svg`)

## [1.14.1] - 2024-06-19

### Changed

- Update `platformVersion` to `2023.2.7`
- Upgrade Gradle Wrapper to `8.8`
- Dependencies - upgrade `org.jetbrains.intellij` to `1.17.4`
- Dependencies - downgrade `org.jetbrains.kotlin.jvm` to `1.9.24`
- Dependencies - upgrade `org.jetbrains.kotlinx.kover` to `0.8.1`

## [1.14.0] - 2024-05-30

### Changed

- Update `platformVersion` to `2023.2.6`
- Change since/until build to `232-242.*` (2023.2-2024.2.*)
- Upgrade Gradle Wrapper to `8.7`
- Update Kover configuration
- Replace `org.jetbrains:annotations` library with an `com.example:exampleLibrary` placeholder
- Dependencies - upgrade `org.jetbrains.intellij` to `1.17.3`
- Dependencies - upgrade `org.jetbrains.kotlin.jvm` to `2.0.0`
- Dependencies - upgrade `org.jetbrains.kotlinx.kover` to `0.8.0`
- Dependencies - upgrade `org.jetbrains.qodana` to `2024.1.5`
- Dependencies (GitHub Actions) - replace `gradle/wrapper-validation-action@v2` with `gradle/actions/wrapper-validation@v3`
- Dependencies (GitHub Actions) - upgrade `JetBrains/qodana-action` to `v2024.1.5`
- Dependencies (GitHub Actions) - upgrade `jtalk/url-health-check-action` to `v4`

## [1.13.0] - 2024-03-11

### Changed

- Dependencies - upgrade `org.jetbrains.kotlin.jvm` to `1.9.23`
- Dependencies - upgrade `org.jetbrains.kotlinx.kover` to `0.7.6`
- Dependencies - upgrade `org.jetbrains.qodana` to `2023.3.2`
- Dependencies (GitHub Actions) - upgrade `actions/upload-artifact` to `4`
- Dependencies (GitHub Actions) - upgrade `codecov/codecov-action` to `4`
- Dependencies (GitHub Actions) - upgrade `gradle/wrapper-validation-action` to `2`
- Dependencies (GitHub Actions) - upgrade `actions/cache` to `4`
- Gradle - upgrade `org.gradle.toolchains.foojay-resolver-convention` to `0.8.0`
- Gradle - cleanup the `jvmToolchain` setup
- Run Configurations - `Run Qodana` runs the `qodanaScan` Gradle task

### Fixed

- Fixed calculation of the plugin publication channel
- Run Configurations - `Run Tests` uses the `RunAsTest` IDE feature
- Replace the whole `IntelliJ Platform Plugin Template` with the new project name when running the GitHub Actions Cleanup workflow

### Removed

- GitHub Actions: Remove the `Setup Java` step from the `releaseDraft` build step
- Gradle - Removed Qodana Gradle Plugin configuration to rely on defaults

## [1.12.0] - 2024-02-20

=======
# GitHubIssue Changelog

## [Unreleased]
### Added
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)
