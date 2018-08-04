package com.example.githubbrowser.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.util.SparseIntArray
import com.example.githubbrowser.db.entitys.RepoSearchResult
import com.example.githubbrowser.services.models.Repo
import java.util.*


/**
 * Created by Patrick on 2018/8/4.
 */
@Dao
abstract class RepoDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repos: Repo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<Repo>)

    @Query("SELECT * FROM repo WHERE owner_login = :login AND name = :name")
    abstract fun load(login: String, name: String): LiveData<Repo>

    @Query("SELECT * FROM Repo "
            + "WHERE owner_login = :owner "
            + "ORDER BY stars DESC")
    abstract fun loadRepositories(owner: String): LiveData<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: RepoSearchResult)

    @Query("SELECT * FROM RepoSearchResult WHERE query = :query")
    abstract fun search(query: String): LiveData<RepoSearchResult>

    fun loadOrdered(repoIds: List<Int>): LiveData<List<Repo>>
    {
        val order = SparseIntArray()
        for ((index, repoId) in repoIds.withIndex())
        {
            order.put(repoId, index)
        }
        return Transformations.map(loadById(repoIds)) { repos ->
            Collections.sort(repos) { r1, r2 ->
                val pos1 = order.get(r1.id)
                val pos2 = order.get(r2.id)
                pos1 - pos2
            }
            repos
        }
    }

    @Query("SELECT * FROM Repo WHERE id in (:repoIds)")
    protected abstract fun loadById(repoIds: List<Int>): LiveData<List<Repo>>

    @Query("SELECT * FROM RepoSearchResult WHERE query = :query")
    abstract fun findSearchResult(query: String): RepoSearchResult
}