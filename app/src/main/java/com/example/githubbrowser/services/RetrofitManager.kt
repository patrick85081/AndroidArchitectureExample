package com.example.githubbrowser.services

import com.example.githubbrowser.utils.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Patrick on 2018/8/4.
 */
object RetrofitManager
{
    val githubService: GithubService by lazy {
        Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(GithubService::class.java);
    }


}