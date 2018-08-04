package com.example.githubbrowser.models

import android.arch.lifecycle.LiveData
import com.example.githubbrowser.db.dao.RepoDao
import com.example.githubbrowser.services.GithubService
import com.example.githubbrowser.services.models.ApiResponse
import com.example.githubbrowser.services.models.RepoSearchResponse
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Patrick on 2018/8/4.
 */
@Singleton
class DataModel
    @Inject
    constructor(private val repoDao: RepoDao, private val githubService: GithubService)
{
    fun searchRepo(query: String) : LiveData<ApiResponse<RepoSearchResponse>>
    {
        return githubService.searchRepos(query);
    }
}