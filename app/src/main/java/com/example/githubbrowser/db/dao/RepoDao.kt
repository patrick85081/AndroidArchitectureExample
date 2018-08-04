package com.example.githubbrowser.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.githubbrowser.db.entitys.RepoSearchResult
import com.example.githubbrowser.services.models.Repo

/**
 * Created by Patrick on 2018/8/4.
 */
@Dao
abstract class RepoDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(vararg repositories: Repo)

    @Query("SELECT * FROM Repo WHERE id in (:repoIds)")
    abstract fun loadById(repoIds: List<Int>) : List<Repo>;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: RepoSearchResult);

    @Query("SELECT * FROM RepoSearchResult WHERE query = :query")
    abstract fun search(query: String) : RepoSearchResult;
}