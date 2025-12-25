# GitHub Issue Creator Plugin - Developer Guide

## Overview

This plugin allows users to create GitHub issues directly from IntelliJ IDEA or PyCharm without leaving the IDE.

## Architecture

The plugin follows a modular architecture with clear separation of concerns:

### Package Structure

```
com.github.tayjaybabee.githubissue/
├── actions/          # IntelliJ action implementations
│   └── CreateGitHubIssueAction.kt
├── api/              # GitHub REST API client
│   └── GitHubApiClient.kt
├── auth/             # Authentication management
│   └── GitHubAuthManager.kt
├── git/              # Git repository detection
│   └── GitRepositoryDetector.kt
└── ui/               # User interface dialogs
    ├── ConfigureTokenDialog.kt
    └── CreateGitHubIssueDialog.kt
```

## Components

### GitRepositoryDetector (git package)

Detects GitHub repository information from Git remotes.

**Key Features:**
- Supports both HTTPS and SSH remote URLs
- Extracts owner and repository name
- Handles `.git` suffix variations

**Example URLs Supported:**
- `https://github.com/owner/repo.git`
- `https://github.com/owner/repo`
- `git@github.com:owner/repo.git`
- `git@github.com:owner/repo`

### GitHubAuthManager (auth package)

Manages GitHub Personal Access Token storage using IntelliJ's PasswordSafe.

**Key Features:**
- Secure token storage (encrypted)
- Simple API for storing/retrieving tokens
- Persistent across IDE sessions

**Required Token Permissions:**
- `repo` scope (for creating issues in private/public repositories)

### GitHubApiClient (api package)

REST API client for interacting with GitHub API v3.

**Key Features:**
- Create issues with full metadata
- Fetch repository labels
- Fetch repository milestones
- Proper error handling with specific exceptions
- Uses kotlinx.serialization for JSON

**API Methods:**
```kotlin
fun createIssue(
    owner: String,
    repo: String,
    title: String,
    body: String? = null,
    labels: List<String>? = null,
    assignees: List<String>? = null,
    milestone: Int? = null
): IssueResponse

fun getLabels(owner: String, repo: String): List<String>
fun getMilestones(owner: String, repo: String): List<Milestone>
```

### UI Components (ui package)

#### ConfigureTokenDialog

Dialog for entering and storing GitHub Personal Access Token.

**Features:**
- Password field for secure input
- Instructions with link to GitHub token creation
- Validates non-empty input

#### CreateGitHubIssueDialog

Main dialog for creating GitHub issues.

**Features:**
- Auto-populated repository information
- Title and description fields
- Comma-separated labels input
- Comma-separated assignees input
- Milestone dropdown (auto-loaded from repository)
- Progress indicator for API calls
- Success/error feedback

### CreateGitHubIssueAction (actions package)

IntelliJ action registered in the Tools menu.

**Behavior:**
1. Checks if GitHub token is configured
2. Prompts for token if not found
3. Detects GitHub repository from Git remotes
4. Shows error if no GitHub repository found
5. Opens CreateGitHubIssueDialog if everything is valid

## User Workflow

### First-Time Setup

1. User clicks **Tools > Create GitHub Issue**
2. Plugin prompts for GitHub token (if not configured)
3. User creates a Personal Access Token at https://github.com/settings/tokens with `repo` scope
4. User enters token in dialog
5. Token is securely stored in PasswordSafe

### Creating an Issue

1. User clicks **Tools > Create GitHub Issue**
2. Plugin detects GitHub repository from Git remote
3. Dialog opens with repository name displayed
4. Plugin loads labels and milestones in background
5. User fills in:
   - Title (required)
   - Description (optional)
   - Labels (optional, comma-separated)
   - Assignees (optional, comma-separated)
   - Milestone (optional, dropdown)
6. User clicks OK
7. Issue is created on GitHub
8. Success message shows with link to created issue

## Error Handling

The plugin handles various error scenarios:

### Authentication Errors
- **No token configured**: Prompts user to configure
- **Invalid token**: Shows error from GitHub API

### Repository Errors
- **No Git repository**: Shows error message
- **No GitHub remote**: Shows error message
- **Invalid remote URL**: Silently ignored, no repo detected

### API Errors
- **Network failures**: Gracefully handled, shows error message
- **Permission denied**: Shows GitHub API error message
- **Rate limiting**: Shows GitHub API error message
- **Invalid input**: Shows validation errors

### Loading Errors
- **Labels/milestones fail to load**: Silently handled, user can still create issue manually entering values

## Building the Plugin

```bash
./gradlew buildPlugin
```

The plugin artifact will be created in `build/distributions/`.

## Testing

Due to network restrictions in the development environment, the plugin cannot be fully built and tested. However, in a normal environment with access to JetBrains repositories, you can:

1. **Build**: `./gradlew build`
2. **Run IDE with Plugin**: `./gradlew runIde`
3. **Run Tests**: `./gradlew test`

## Installation

1. Build the plugin or download from releases
2. In IntelliJ/PyCharm: **File > Settings > Plugins > Install Plugin from Disk**
3. Select the plugin ZIP file
4. Restart IDE

## Configuration

The plugin stores the GitHub token in IntelliJ's PasswordSafe. No additional configuration files are needed.

## Troubleshooting

### "No GitHub repository detected"
- Ensure your project has a Git remote
- Verify the remote points to GitHub
- Check remote URL with: `git remote -v`

### "Failed to create issue"
- Verify your GitHub token has `repo` scope
- Check you have write access to the repository
- Ensure GitHub is accessible from your network

### Labels/Milestones not loading
- Check network connectivity to GitHub
- Verify token has correct permissions
- You can still manually enter values

## Security

- GitHub tokens are stored encrypted using IntelliJ's PasswordSafe
- Tokens are never logged or exposed in error messages
- HTTPS is used for all GitHub API communications
- Token is sent via Authorization header (Bearer token)

## Future Enhancements

Potential improvements for future versions:
- Issue template support
- Assignee auto-completion
- Label auto-completion with color indicators
- Recent labels/assignees history
- Multiple repository support
- Issue search and management
- Pull request creation
