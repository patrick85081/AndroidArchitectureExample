package com.example.githubbrowser.dagger.modules

import android.arch.persistence.room.Room
import com.example.githubbrowser.App
import com.example.githubbrowser.db.GithubDb
import com.example.githubbrowser.db.dao.RepoDao
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

    @Provides
    @Singleton
    fun providerDb(app : App) : GithubDb = Room.databaseBuilder(app, GithubDb::class.java, "github.db").build();

    @Provides
    @Singleton
    fun provideRepoDao(db : GithubDb) : RepoDao =  db.repoDao();
}


