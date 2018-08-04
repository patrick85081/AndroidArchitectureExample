package com.example.githubbrowser.models

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import com.example.githubbrowser.services.RetrofitManager
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

    fun searchRepo(query: String) : MutableLiveData<List<Repo>>
    {
        val repos = MutableLiveData<List<Repo>>();
        githubService.searchRepos(query)
                .enqueue(object : Callback<RepoSearchResponse>
                {
                    override fun onFailure(call: Call<RepoSearchResponse>?, t: Throwable?)
                    {

                    }

                    override fun onResponse(call: Call<RepoSearchResponse>?,
                                            response: Response<RepoSearchResponse>?)
                    {
//                        callback.invoke(response?.body()?.items ?: ArrayList());
                        repos.value = response?.body()?.items ?: ArrayList();
                    }
                });
        return repos;
    }

    fun retrieveData(callback: onDataReadyCallback) =
            Handler().postDelayed({
                callback.onDataReady("New Data");
            }, 3000)

    interface onDataReadyCallback
    {
        fun onDataReady(data: String);
    }
}