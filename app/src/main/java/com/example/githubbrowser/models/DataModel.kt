package com.example.githubbrowser.models

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import com.example.githubbrowser.services.RetrofitManager
import com.example.githubbrowser.services.models.ApiResponse
import com.example.githubbrowser.services.models.Repo
import com.example.githubbrowser.services.models.RepoSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Patrick on 2018/8/4.
 */
class DataModel
{
    private val githubService = RetrofitManager.githubService;

    fun searchRepo(query: String) : LiveData<ApiResponse<RepoSearchResponse>>
    {
        return githubService.searchRepos(query);
    }
}