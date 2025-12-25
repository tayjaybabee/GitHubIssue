package com.github.tayjaybabee.githubissue.auth

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe

/**
 * Manages GitHub authentication using Personal Access Token (PAT)
 * stored securely in IntelliJ's PasswordSafe
 */
object GitHubAuthManager {
    
    private const val SERVICE_NAME = "GitHubIssueCreator"
    private const val USERNAME = "github-pat"
    
    /**
     * Stores the GitHub Personal Access Token securely
     * @param token The GitHub PAT to store
     */
    fun storeToken(token: String) {
        val credentialAttributes = createCredentialAttributes()
        val credentials = Credentials(USERNAME, token)
        PasswordSafe.instance.set(credentialAttributes, credentials)
    }
    
    /**
     * Retrieves the stored GitHub Personal Access Token
     * @return The stored PAT, or null if not found
     */
    fun getToken(): String? {
        val credentialAttributes = createCredentialAttributes()
        return PasswordSafe.instance.getPassword(credentialAttributes)
    }
    
    /**
     * Checks if a token is stored
     * @return true if a token is stored, false otherwise
     */
    fun hasToken(): Boolean {
        return getToken() != null
    }
    
    /**
     * Removes the stored token
     */
    fun clearToken() {
        val credentialAttributes = createCredentialAttributes()
        PasswordSafe.instance.set(credentialAttributes, null)
    }
    
    /**
     * Creates credential attributes for PasswordSafe
     */
    private fun createCredentialAttributes(): CredentialAttributes {
        return CredentialAttributes(
            generateServiceName(SERVICE_NAME, USERNAME)
        )
    }
}
