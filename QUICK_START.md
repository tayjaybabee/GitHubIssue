# GitHub Issue Creator - Quick Start Guide

## What is it?

A plugin for IntelliJ IDEA and PyCharm that lets you create GitHub issues directly from your IDE.

## Installation

1. Download the plugin ZIP from releases
2. In your IDE: **File > Settings > Plugins**
3. Click the gear icon ⚙️ and select **Install Plugin from Disk**
4. Select the downloaded ZIP file
5. Restart your IDE

## First-Time Setup

### 1. Create a GitHub Personal Access Token

1. Go to https://github.com/settings/tokens
2. Click **Generate new token (classic)**
3. Give it a name like "IntelliJ Issue Creator"
4. Select the **repo** scope (this allows creating issues)
5. Click **Generate token**
6. **Copy the token** (you won't see it again!)

### 2. Configure the Plugin

1. Open any project with a GitHub repository
2. Go to **Tools > Create GitHub Issue**
3. When prompted, paste your GitHub token
4. Click OK

Your token is now securely stored. You won't need to enter it again!

## Creating an Issue

1. Make sure your project has a GitHub remote configured
   - Check with: `git remote -v`
   
2. Go to **Tools > Create GitHub Issue**

3. Fill in the issue details:
   - **Title**: Brief description (required)
   - **Description**: Detailed explanation (optional)
   - **Labels**: Comma-separated, e.g., `bug, priority-high` (optional)
   - **Assignees**: GitHub usernames, e.g., `user1, user2` (optional)
   - **Milestone**: Select from dropdown (optional)

4. Click **OK**

5. A success message will show with a link to your new issue!

## Tips

- **Labels and Milestones**: The plugin automatically loads available options from your repository
- **Multiple Labels**: Separate with commas: `bug, enhancement, documentation`
- **Multiple Assignees**: Separate with commas: `alice, bob`
- **Tooltips**: Hover over fields for helpful hints

## Troubleshooting

### "GitHub token is not configured"
→ You need to set up your token. See "First-Time Setup" above.

### "No GitHub repository detected"
→ Make sure your project has a Git remote pointing to GitHub:
```bash
git remote add origin https://github.com/username/repo.git
# or
git remote add origin git@github.com:username/repo.git
```

### "Failed to create issue"
Possible causes:
- Token doesn't have `repo` scope
- You don't have write access to the repository
- Network/connectivity issues

### Labels or Milestones not showing
- They're loaded in the background and may take a moment
- If loading fails, you can still type them manually

## Reconfiguring Your Token

If you need to change your GitHub token:

1. The token is stored securely in your IDE's password manager
2. To update it, you can:
   - Clear the old token from IDE's password storage
   - The plugin will prompt for a new token next time

## Privacy & Security

- Your GitHub token is stored **encrypted** in IntelliJ's secure password storage
- The token is **never** sent anywhere except directly to GitHub's API
- All communication with GitHub uses **HTTPS**
- The plugin only requests the permissions you need

## Support

For issues or feature requests, please visit:
https://github.com/tayjaybabee/GitHubIssue

## Keyboard Shortcuts

Currently no keyboard shortcuts are assigned by default. You can assign one:

1. **File > Settings > Keymap**
2. Search for "Create GitHub Issue"
3. Right-click and select **Add Keyboard Shortcut**
4. Press your desired key combination

---

**Enjoy creating GitHub issues from your IDE! 🎉**
