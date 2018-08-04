package com.example.githubbrowser.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.githubbrowser.db.dao.RepoDao
import com.example.githubbrowser.db.entitys.RepoSearchResult
import com.example.githubbrowser.services.models.Repo

/**
 * Created by Patrick on 2018/8/4.
 */
@Database(entities = [
        Repo::class,
        RepoSearchResult::class],
    version = 1)
abstract class GithubDb : RoomDatabase()
{
    abstract fun repoDao() : RepoDao;
}