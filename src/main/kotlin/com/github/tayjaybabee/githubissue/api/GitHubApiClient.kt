package com.github.tayjaybabee.githubissue.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

/**
 * GitHub REST API client for creating issues
 */
class GitHubApiClient(private val token: String) {
    
    companion object {
        private const val GITHUB_API_BASE = "https://api.github.com"
        private val json = Json { 
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }
    
    /**
     * Label data class for API responses
     */
    @Serializable
    data class Label(val name: String)
    
    /**
     * Request body for creating a GitHub issue
     */
    @Serializable
    data class CreateIssueRequest(
        val title: String,
        val body: String? = null,
        val labels: List<String>? = null,
        val assignees: List<String>? = null,
        val milestone: Int? = null
    )
    
    /**
     * Response from GitHub API when creating an issue
     */
    @Serializable
    data class IssueResponse(
        val number: Int,
        val title: String,
        val html_url: String,
        val state: String
    )
    
    /**
     * Error response from GitHub API
     */
    @Serializable
    data class ErrorResponse(
        val message: String,
        val documentation_url: String? = null
    )
    
    /**
     * Creates a new GitHub issue
     * @param owner Repository owner
     * @param repo Repository name
     * @param title Issue title
     * @param body Issue body/description
     * @param labels List of label names
     * @param assignees List of usernames to assign
     * @param milestone Milestone number
     * @return IssueResponse on success
     * @throws GitHubApiException on failure
     */
    fun createIssue(
        owner: String,
        repo: String,
        title: String,
        body: String? = null,
        labels: List<String>? = null,
        assignees: List<String>? = null,
        milestone: Int? = null
    ): IssueResponse {
        val url = URL("$GITHUB_API_BASE/repos/$owner/$repo/issues")
        val connection = url.openConnection() as HttpURLConnection
        
        try {
            connection.requestMethod = "POST"
            connection.setRequestProperty("Accept", "application/vnd.github+json")
            connection.setRequestProperty("Authorization", "Bearer $token")
            connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            
            val request = CreateIssueRequest(
                title = title,
                body = body,
                labels = labels?.takeIf { it.isNotEmpty() },
                assignees = assignees?.takeIf { it.isNotEmpty() },
                milestone = milestone
            )
            
            val jsonBody = json.encodeToString(request)
            
            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(jsonBody)
                writer.flush()
            }
            
            val responseCode = connection.responseCode
            
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                val response = BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                    reader.readText()
                }
                return json.decodeFromString<IssueResponse>(response)
            } else {
                val errorStream = connection.errorStream
                val errorResponse = if (errorStream != null) {
                    BufferedReader(InputStreamReader(errorStream)).use { reader ->
                        reader.readText()
                    }
                } else {
                    "Unknown error"
                }
                
                val errorMessage = try {
                    val error = json.decodeFromString<ErrorResponse>(errorResponse)
                    error.message
                } catch (e: Exception) {
                    errorResponse
                }
                
                throw GitHubApiException("Failed to create issue (HTTP $responseCode): $errorMessage")
            }
        } finally {
            connection.disconnect()
        }
    }
    
    /**
     * Fetches available labels for a repository
     * @param owner Repository owner
     * @param repo Repository name
     * @return List of label names
     */
    fun getLabels(owner: String, repo: String): List<String> {
        val url = URL("$GITHUB_API_BASE/repos/$owner/$repo/labels")
        val connection = url.openConnection() as HttpURLConnection
        
        try {
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/vnd.github+json")
            connection.setRequestProperty("Authorization", "Bearer $token")
            connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28")
            
            val responseCode = connection.responseCode
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                    reader.readText()
                }
                
                val labels = json.decodeFromString<List<Label>>(response)
                return labels.map { it.name }
            } else {
                // Return empty list on error rather than throwing
                return emptyList()
            }
        } catch (e: Exception) {
            // Return empty list on error
            return emptyList()
        } finally {
            connection.disconnect()
        }
    }
    
    /**
     * Fetches available milestones for a repository
     * @param owner Repository owner
     * @param repo Repository name
     * @return List of milestones
     */
    fun getMilestones(owner: String, repo: String): List<Milestone> {
        val url = URL("$GITHUB_API_BASE/repos/$owner/$repo/milestones")
        val connection = url.openConnection() as HttpURLConnection
        
        try {
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/vnd.github+json")
            connection.setRequestProperty("Authorization", "Bearer $token")
            connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28")
            
            val responseCode = connection.responseCode
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                    reader.readText()
                }
                
                return json.decodeFromString<List<Milestone>>(response)
            } else {
                return emptyList()
            }
        } catch (e: Exception) {
            return emptyList()
        } finally {
            connection.disconnect()
        }
    }
    
    @Serializable
    data class Milestone(
        val number: Int,
        val title: String,
        val state: String
    )
}

/**
 * Exception thrown when GitHub API calls fail
 */
class GitHubApiException(message: String) : Exception(message)
