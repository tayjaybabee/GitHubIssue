package com.github.tayjaybabee.githubissue.git

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import git4idea.repo.GitRepository
import git4idea.repo.GitRepositoryManager

/**
 * Detects GitHub repository information from Git remotes
 */
object GitRepositoryDetector {
    
    // Regex patterns for parsing GitHub URLs
    private val HTTPS_PATTERN = """https://github\.com/([^/]+)/([^/\.]+)(?:\.git)?""".toRegex()
    private val SSH_PATTERN = """git@github\.com:([^/]+)/([^/\.]+)(?:\.git)?""".toRegex()
    
    /**
     * Data class to hold GitHub repository information
     */
    data class GitHubRepoInfo(
        val owner: String,
        val repo: String,
        val fullName: String = "$owner/$repo"
    )
    
    /**
     * Detects GitHub repository from the current project
     * @param project The current IntelliJ project
     * @return GitHubRepoInfo if a GitHub remote is found, null otherwise
     */
    fun detectGitHubRepo(project: Project): GitHubRepoInfo? {
        val vcsManager = ProjectLevelVcsManager.getInstance(project)
        if (vcsManager.allVcsRoots.isEmpty()) {
            return null
        }
        
        val gitRepoManager = GitRepositoryManager.getInstance(project)
        val repositories = gitRepoManager.repositories
        
        if (repositories.isEmpty()) {
            return null
        }
        
        // Get the first repository (most projects have one)
        val repository = repositories.first()
        
        return extractGitHubInfo(repository)
    }
    
    /**
     * Extracts GitHub owner and repo name from Git repository
     */
    private fun extractGitHubInfo(repository: GitRepository): GitHubRepoInfo? {
        // Try origin first, then any remote
        val remote = repository.remotes.find { it.name == "origin" } 
            ?: repository.remotes.firstOrNull()
        
        remote?.firstUrl?.let { url ->
            return parseGitHubUrl(url)
        }
        
        return null
    }
    
    /**
     * Parses GitHub URL to extract owner and repo name
     * Supports both HTTPS and SSH URLs:
     * - https://github.com/owner/repo.git
     * - git@github.com:owner/repo.git
     */
    private fun parseGitHubUrl(url: String): GitHubRepoInfo? {
        HTTPS_PATTERN.find(url)?.let { match ->
            val (owner, repo) = match.destructured
            return GitHubRepoInfo(owner, repo)
        }
        
        SSH_PATTERN.find(url)?.let { match ->
            val (owner, repo) = match.destructured
            return GitHubRepoInfo(owner, repo)
        }
        
        return null
    }
}
