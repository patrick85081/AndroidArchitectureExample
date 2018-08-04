package com.example.githubbrowser.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.githubbrowser.db.dao.RepoDao
import com.example.githubbrowser.db.entitys.RepoSearchResult
import com.example.githubbrowser.services.models.Repo
import android.arch.persistence.db.SupportSQLiteDatabase
import android.support.annotation.NonNull
import android.arch.persistence.room.migration.Migration


/**
 * Created by Patrick on 2018/8/4.
 */
@Database(entities = [
    Repo::class,
    RepoSearchResult::class],
        version = 2)
abstract class GithubDb : RoomDatabase()
{
    abstract fun repoDao(): RepoDao;

    companion object
    {
        @JvmField
        val MIGRATION_1_2: Migration = object : Migration(1, 2)
        {
            override fun migrate(database: SupportSQLiteDatabase)
            {
                database.execSQL("ALTER TABLE Repo " + " ADD COLUMN html_url TEXT")
            }
        }
    }
}