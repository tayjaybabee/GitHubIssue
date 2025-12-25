package com.github.tayjaybabee.githubissue.ui

import com.github.tayjaybabee.githubissue.api.GitHubApiClient
import com.github.tayjaybabee.githubissue.api.GitHubApiException
import com.github.tayjaybabee.githubissue.auth.GitHubAuthManager
import com.github.tayjaybabee.githubissue.git.GitRepositoryDetector
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*

/**
 * Dialog for creating GitHub issues
 */
class CreateGitHubIssueDialog(
    private val project: Project,
    private val repoInfo: GitRepositoryDetector.GitHubRepoInfo
) : DialogWrapper(project) {
    
    private val titleField = JBTextField(40)
    private val bodyArea = JBTextArea(10, 40)
    private val labelsField = JBTextField(40)
    private val assigneesField = JBTextField(40)
    private val milestoneCombo = JComboBox<MilestoneItem>()
    
    private var availableLabels: List<String> = emptyList()
    private var availableMilestones: List<GitHubApiClient.Milestone> = emptyList()
    
    init {
        title = "Create GitHub Issue - ${repoInfo.fullName}"
        init()
        loadRepositoryData()
    }
    
    override fun createCenterPanel(): JComponent {
        val panel = JPanel(BorderLayout())
        
        // Repository info label
        val repoLabel = JBLabel("Repository: ${repoInfo.fullName}")
        repoLabel.border = JBUI.Borders.emptyBottom(10)
        
        // Create form
        val formPanel = FormBuilder.createFormBuilder()
            .addComponent(repoLabel)
            .addLabeledComponent("Title:", titleField, true)
            .addLabeledComponent("Description:", JBScrollPane(bodyArea), true)
            .addLabeledComponent("Labels (comma-separated):", labelsField, true)
            .addLabeledComponent("Assignees (comma-separated):", assigneesField, true)
            .addLabeledComponent("Milestone:", milestoneCombo, true)
            .addComponentFillVertically(JPanel(), 0)
            .panel
        
        panel.add(formPanel, BorderLayout.CENTER)
        panel.preferredSize = Dimension(600, 400)
        
        // Add tooltips
        labelsField.toolTipText = "Enter label names separated by commas, e.g., bug, enhancement"
        assigneesField.toolTipText = "Enter GitHub usernames separated by commas, e.g., user1, user2"
        
        return panel
    }
    
    /**
     * Load repository labels and milestones in the background
     */
    private fun loadRepositoryData() {
        val token = GitHubAuthManager.getToken() ?: return
        
        ProgressManager.getInstance().run(object : Task.Backgroundable(project, "Loading Repository Data", false) {
            override fun run(indicator: ProgressIndicator) {
                try {
                    val apiClient = GitHubApiClient(token)
                    
                    indicator.text = "Loading labels..."
                    availableLabels = apiClient.getLabels(repoInfo.owner, repoInfo.repo)
                    
                    indicator.text = "Loading milestones..."
                    availableMilestones = apiClient.getMilestones(repoInfo.owner, repoInfo.repo)
                    
                    SwingUtilities.invokeLater {
                        updateMilestoneCombo()
                        updateLabelsTooltip()
                    }
                } catch (e: Exception) {
                    // Silently fail - user can still create issue without auto-complete
                }
            }
        })
    }
    
    /**
     * Updates the milestone combo box with loaded milestones
     */
    private fun updateMilestoneCombo() {
        milestoneCombo.removeAllItems()
        milestoneCombo.addItem(MilestoneItem(null, "None"))
        
        availableMilestones.filter { it.state == "open" }.forEach { milestone ->
            milestoneCombo.addItem(MilestoneItem(milestone.number, milestone.title))
        }
    }
    
    /**
     * Updates the labels field tooltip with available labels
     */
    private fun updateLabelsTooltip() {
        if (availableLabels.isNotEmpty()) {
            labelsField.toolTipText = "Available labels: ${availableLabels.joinToString(", ")}"
        }
    }
    
    override fun doOKAction() {
        val title = titleField.text.trim()
        
        if (title.isEmpty()) {
            Messages.showErrorDialog(project, "Issue title cannot be empty", "Validation Error")
            return
        }
        
        val token = GitHubAuthManager.getToken()
        if (token == null) {
            Messages.showErrorDialog(project, "GitHub token not found. Please configure your token first.", "Authentication Error")
            return
        }
        
        val body = bodyArea.text.trim().ifEmpty { null }
        val labels = labelsField.text.split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .ifEmpty { null }
        
        val assignees = assigneesField.text.split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .ifEmpty { null }
        
        val milestoneItem = milestoneCombo.selectedItem as? MilestoneItem
        val milestone = milestoneItem?.number
        
        ProgressManager.getInstance().run(object : Task.Modal(project, "Creating GitHub Issue", true) {
            var issueUrl: String? = null
            var error: String? = null
            
            override fun run(indicator: ProgressIndicator) {
                indicator.isIndeterminate = true
                indicator.text = "Creating issue on GitHub..."
                
                try {
                    val apiClient = GitHubApiClient(token)
                    val response = apiClient.createIssue(
                        owner = repoInfo.owner,
                        repo = repoInfo.repo,
                        title = title,
                        body = body,
                        labels = labels,
                        assignees = assignees,
                        milestone = milestone
                    )
                    issueUrl = response.html_url
                } catch (e: GitHubApiException) {
                    error = e.message
                } catch (e: Exception) {
                    error = "Unexpected error: ${e.message}"
                }
            }
            
            override fun onSuccess() {
                if (error != null) {
                    Messages.showErrorDialog(project, error, "Failed to Create Issue")
                } else {
                    Messages.showInfoMessage(
                        project,
                        "Issue created successfully!\n\n$issueUrl",
                        "Success"
                    )
                    close(OK_EXIT_CODE)
                }
            }
            
            override fun onCancel() {
                Messages.showInfoMessage(project, "Issue creation cancelled", "Cancelled")
            }
        })
    }
    
    /**
     * Data class to hold milestone information for combo box
     */
    private data class MilestoneItem(val number: Int?, val title: String) {
        override fun toString(): String = title
    }
}
