package com.github.tayjaybabee.githubissue.actions

import com.github.tayjaybabee.githubissue.auth.GitHubAuthManager
import com.github.tayjaybabee.githubissue.git.GitRepositoryDetector
import com.github.tayjaybabee.githubissue.ui.ConfigureTokenDialog
import com.github.tayjaybabee.githubissue.ui.CreateGitHubIssueDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

/**
 * Action to create a GitHub issue from the IDE
 */
class CreateGitHubIssueAction : AnAction() {
    
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        
        try {
            // Check if we have a GitHub token
            if (!GitHubAuthManager.hasToken()) {
                val result = Messages.showYesNoDialog(
                    project,
                    "GitHub token is not configured. Would you like to configure it now?",
                    "GitHub Token Required",
                    Messages.getQuestionIcon()
                )
                
                if (result == Messages.YES) {
                    val dialog = ConfigureTokenDialog(project)
                    if (!dialog.showAndGet() || dialog.getToken() == null) {
                        return
                    }
                } else {
                    return
                }
            }
            
            // Detect GitHub repository
            val repoInfo = GitRepositoryDetector.detectGitHubRepo(project)
            
            if (repoInfo == null) {
                Messages.showErrorDialog(
                    project,
                    "No GitHub repository detected. Please make sure your project has a Git remote pointing to GitHub.",
                    "No GitHub Repository"
                )
                return
            }
            
            // Show create issue dialog
            val dialog = CreateGitHubIssueDialog(project, repoInfo)
            dialog.show()
            
        } catch (e: Exception) {
            Messages.showErrorDialog(
                project,
                "An error occurred: ${e.message}",
                "Error"
            )
        }
    }
    
    override fun update(e: AnActionEvent) {
        // Enable action only when a project is open
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null
    }
}
