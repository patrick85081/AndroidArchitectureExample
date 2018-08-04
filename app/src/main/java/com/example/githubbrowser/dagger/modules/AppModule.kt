package com.example.githubbrowser.dagger.modules

import com.example.githubbrowser.services.GithubService
import com.example.githubbrowser.services.RetrofitManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Patrick on 2018/8/4.
 */
@Module
class AppModule
{
    @Provides
    @Singleton
    fun providerGithubService() : GithubService
    {
        return RetrofitManager.githubService;
    }
}


