# GitHub Issue Creator - Project Structure

## Overview
```
GitHubIssue/
├── src/main/kotlin/com/github/tayjaybabee/githubissue/
│   ├── actions/
│   │   └── CreateGitHubIssueAction.kt         [Tools menu action]
│   ├── api/
│   │   └── GitHubApiClient.kt                 [GitHub REST API client]
│   ├── auth/
│   │   └── GitHubAuthManager.kt               [PasswordSafe integration]
│   ├── git/
│   │   └── GitRepositoryDetector.kt           [Git remote detection]
│   └── ui/
│       ├── ConfigureTokenDialog.kt            [Token setup dialog]
│       └── CreateGitHubIssueDialog.kt         [Issue creation dialog]
├── src/main/resources/META-INF/
│   └── plugin.xml                             [Plugin manifest]
├── build.gradle.kts                           [Build configuration]
├── gradle.properties                          [Plugin metadata]
└── Documentation files
```

## Component Interactions

```
User clicks "Tools > Create GitHub Issue"
            ↓
    CreateGitHubIssueAction
            ↓
    GitHubAuthManager.hasToken()?
        ├─ No → ConfigureTokenDialog
        │         ↓
        │   GitHubAuthManager.storeToken()
        └─ Yes → Continue
            ↓
    GitRepositoryDetector.detectGitHubRepo()
        ├─ Not found → Show error
        └─ Found → Continue
            ↓
    CreateGitHubIssueDialog
        ├─ Load data → GitHubApiClient.getLabels()
        │              GitHubApiClient.getMilestones()
        └─ Create → GitHubApiClient.createIssue()
            ↓
    Show success with issue URL
```

## Data Flow

```
1. Token Storage:
   User Input → ConfigureTokenDialog → GitHubAuthManager → PasswordSafe (encrypted)

2. Repository Detection:
   Git Remote → GitRepositoryDetector → GitHub URL Parsing → (owner, repo)

3. Issue Creation:
   Dialog Input → GitHubApiClient → HTTPS Request → GitHub API → Response
```

## Dependencies

### External Libraries
- **kotlinx-serialization-json**: JSON parsing for GitHub API
- **Git4Idea**: IntelliJ's bundled Git plugin for repository access
- **IntelliJ Platform SDK**: UI components, actions, password storage

### IntelliJ Platform APIs Used
- `com.intellij.openapi.ui.DialogWrapper`: Base class for dialogs
- `com.intellij.ide.passwordSafe.PasswordSafe`: Secure credential storage
- `com.intellij.openapi.actionSystem.AnAction`: Base class for actions
- `com.intellij.openapi.vcs.ProjectLevelVcsManager`: VCS integration
- `com.intellij.openapi.progress.ProgressManager`: Background tasks
- `git4idea.repo.GitRepositoryManager`: Git repository access

## Key Files Explained

### CreateGitHubIssueAction.kt
- Entry point for the plugin
- Registered in Tools menu via plugin.xml
- Orchestrates the workflow: token check → repo detection → dialog display
- Handles top-level error cases

### GitHubApiClient.kt
- Implements GitHub REST API v3 communication
- Uses HttpURLConnection for HTTP requests
- Serializable data classes for requests/responses
- Methods: createIssue(), getLabels(), getMilestones()
- Custom exception: GitHubApiException

### GitHubAuthManager.kt
- Singleton object for token management
- Stores token using CredentialAttributes and PasswordSafe
- Methods: storeToken(), getToken(), hasToken(), clearToken()

### GitRepositoryDetector.kt
- Singleton object for repository detection
- Regex patterns for HTTPS and SSH GitHub URLs
- Extracts owner and repository name from Git remotes
- Returns GitHubRepoInfo data class or null

### CreateGitHubIssueDialog.kt
- Main UI for creating issues
- Auto-loads labels and milestones in background
- Form fields: title, body, labels, assignees, milestone
- Progress indicator for API calls
- Validation and error handling

### ConfigureTokenDialog.kt
- Simple dialog for token input
- Password field for secure entry
- Instructions with link to GitHub token creation
- Stores token on OK

## Build Configuration

### gradle.properties
```properties
pluginGroup = com.github.tayjaybabee.githubissue
pluginName = GitHub Issue Creator
platformBundledPlugins = Git4Idea
```

### build.gradle.kts
```kotlin
plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")  // Added for JSON
    // ... other plugins
}

dependencies {
    implementation(libs.kotlinxSerializationJson)  // Added
    // ... other dependencies
}
```

### plugin.xml
```xml
<idea-plugin>
    <id>com.github.tayjaybabee.githubissue</id>
    <name>GitHub Issue Creator</name>
    <depends>Git4Idea</depends>
    
    <actions>
        <action id="..." class="...CreateGitHubIssueAction"
                text="Create GitHub Issue">
            <add-to-group group-id="ToolsMenu" anchor="last"/>
        </action>
    </actions>
</idea-plugin>
```

## Error Handling Strategy

### Graceful Degradation
- Labels/milestones fail to load → User can still type manually
- Network errors → Clear error messages shown
- Invalid token → Prompt to reconfigure

### User Feedback
- Progress indicators for long operations
- Success messages with issue URL
- Descriptive error messages
- Validation before API calls

### Security
- Tokens stored encrypted via PasswordSafe
- No logging of sensitive data
- HTTPS only for API communication
- Bearer token authentication

## Testing Strategy

### Manual Testing
1. Test with no token configured
2. Test with invalid token
3. Test with no Git repository
4. Test with non-GitHub repository
5. Test successful issue creation
6. Test with various field combinations
7. Test network failure scenarios

### Automated Testing (Future)
- Unit tests for GitRepositoryDetector URL parsing
- Unit tests for GitHubApiClient request building
- Mock API responses for integration tests
- UI tests for dialog interactions

## Future Enhancements

### High Priority
- Issue templates support
- Auto-completion for assignees
- Label color indicators
- Recent labels/assignees history

### Medium Priority
- Multiple repository support
- Issue search and listing
- Issue editing
- Comment addition

### Low Priority
- Pull request creation
- Project board integration
- Issue templates customization
- Batch issue creation
