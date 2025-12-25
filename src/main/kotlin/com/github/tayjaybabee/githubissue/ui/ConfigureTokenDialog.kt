package com.github.tayjaybabee.githubissue.ui

import com.github.tayjaybabee.githubissue.auth.GitHubAuthManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPasswordField
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * Dialog for configuring GitHub Personal Access Token
 */
class ConfigureTokenDialog(project: Project) : DialogWrapper(project) {
    
    private val tokenField = JBPasswordField()
    
    init {
        title = "Configure GitHub Token"
        init()
    }
    
    override fun createCenterPanel(): JComponent {
        val panel = JPanel(BorderLayout())
        
        val infoLabel = JBLabel("<html>Enter your GitHub Personal Access Token (PAT).<br>" +
                "The token needs 'repo' scope to create issues.<br><br>" +
                "Create a token at: https://github.com/settings/tokens</html>")
        infoLabel.border = JBUI.Borders.emptyBottom(10)
        
        val formPanel = FormBuilder.createFormBuilder()
            .addComponent(infoLabel)
            .addLabeledComponent("GitHub Token:", tokenField, true)
            .addComponentFillVertically(JPanel(), 0)
            .panel
        
        panel.add(formPanel, BorderLayout.CENTER)
        
        return panel
    }
    
    override fun doOKAction() {
        val token = String(tokenField.password).trim()
        if (token.isNotEmpty()) {
            GitHubAuthManager.storeToken(token)
        }
        super.doOKAction()
    }
    
    /**
     * Returns the entered token
     */
    fun getToken(): String? {
        return if (isOK) {
            String(tokenField.password).trim().ifEmpty { null }
        } else {
            null
        }
    }
}
