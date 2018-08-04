package com.example.githubbrowser.services

import com.example.githubbrowser.services.models.RepoSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Patrick on 2018/8/4.
 */
interface GithubService
{
    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String) : Call<RepoSearchResponse>;
}