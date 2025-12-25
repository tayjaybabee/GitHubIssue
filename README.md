# GitHub Issue Creator Plugin

[![official JetBrains project](https://jb.gg/badges/official.svg)][jb:github]
[![Twitter Follow](https://img.shields.io/badge/follow-%40JBPlatform-1DA1F2?logo=twitter)](https://x.com/JBPlatform)
[![Build](https://github.com/JetBrains/intellij-platform-plugin-template/workflows/Build/badge.svg)][gh:build]

![IntelliJ Platform Plugin Template][file:intellij-platform-plugin-template-dark]
![IntelliJ Platform Plugin Template][file:intellij-platform-plugin-template-light]

<!-- Plugin description -->
**GitHub Issue Creator** is a JetBrains IntelliJ/PyCharm plugin that allows you to create GitHub issues directly from your IDE.

## Features

- **Auto-detect GitHub Repository**: Automatically detects the GitHub repository from your Git remote configuration
- **Secure Token Storage**: Stores your GitHub Personal Access Token securely using IntelliJ's PasswordSafe
- **Comprehensive Issue Creation**: Create issues with:
  - Title
  - Description/Body
  - Labels
  - Assignees
  - Milestone
- **Modular Architecture**: Clean package structure with separation of concerns (api, ui, auth, git)
- **Error Handling**: Robust error handling throughout the plugin

## Usage

1. **Configure GitHub Token** (first time only):
   - Go to `Tools > Create GitHub Issue`
   - When prompted, enter your GitHub Personal Access Token
   - Create a token at: https://github.com/settings/tokens (requires 'repo' scope)

2. **Create an Issue**:
   - Open a project with a GitHub repository
   - Go to `Tools > Create GitHub Issue`
   - Fill in the issue details
   - Click OK to create the issue on GitHub

## Architecture

The plugin follows a modular structure:

- `api/` - GitHub REST API client implementation
- `ui/` - Dialog UI components
- `auth/` - Authentication management with PasswordSafe
- `git/` - Git repository detection
- `actions/` - IntelliJ action implementations
=======
# GitHubIssue

![Build](https://github.com/tayjaybabee/GitHubIssue/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)

## Template ToDo list
- [x] Create a new [IntelliJ Platform Plugin Template][template] project.
- [ ] Get familiar with the [template documentation][template].
- [ ] Adjust the [pluginGroup](./gradle.properties) and [pluginName](./gradle.properties), as well as the [id](./src/main/resources/META-INF/plugin.xml) and [sources package](./src/main/kotlin).
- [ ] Adjust the plugin description in `README` (see [Tips][docs:plugin-description])
- [ ] Review the [Legal Agreements](https://plugins.jetbrains.com/docs/marketplace/legal-agreements.html?from=IJPluginTemplate).
- [ ] [Publish a plugin manually](https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html?from=IJPluginTemplate) for the first time.
- [ ] Set the `MARKETPLACE_ID` in the above README badges. You can obtain it once the plugin is published to JetBrains Marketplace.
- [ ] Set the [Plugin Signing](https://plugins.jetbrains.com/docs/intellij/plugin-signing.html?from=IJPluginTemplate) related [secrets](https://github.com/JetBrains/intellij-platform-plugin-template#environment-variables).
- [ ] Set the [Deployment Token](https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html?from=IJPluginTemplate).
- [ ] Click the <kbd>Watch</kbd> button on the top of the [IntelliJ Platform Plugin Template][template] to be notified about releases containing new features and fixes.
- [ ] Configure the [CODECOV_TOKEN](https://docs.codecov.com/docs/quick-start) secret for automated test coverage reports on PRs

<!-- Plugin description -->
This Fancy IntelliJ Platform Plugin is going to be your implementation of the brilliant ideas that you have.

This specific section is a source for the [plugin.xml](/src/main/resources/META-INF/plugin.xml) file which will be extracted by the [Gradle](/build.gradle.kts) during the build process.

To keep everything working, do not remove `<!-- ... -->` sections. 
<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "GitHubIssue"</kbd> >
  <kbd>Install</kbd>

- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/tayjaybabee/GitHubIssue/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
