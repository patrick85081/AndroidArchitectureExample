package com.example.githubbrowser.repositories

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.example.githubbrowser.db.dao.RepoDao
import com.example.githubbrowser.db.entitys.RepoSearchResult
import com.example.githubbrowser.models.NetworkBoundResource
import com.example.githubbrowser.models.Resource
import com.example.githubbrowser.services.GithubService
import com.example.githubbrowser.services.models.ApiResponse
import com.example.githubbrowser.services.models.Repo
import com.example.githubbrowser.services.models.RepoSearchResponse
import com.example.githubbrowser.utils.AbsentLiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Patrick on 2018/8/4.
 */
@Singleton
class RepoRepository @Inject constructor(private val repoDao: RepoDao,
                                         private val githubService: GithubService)
{
    fun search(query: String): LiveData<Resource<List<Repo>>>
    {
        return object : NetworkBoundResource<List<Repo>, RepoSearchResponse>()
        {
            override fun loadFromDb(): LiveData<List<Repo>>
            {
                return Transformations.switchMap(repoDao.search(query), Function { searchData ->
                    if(searchData == null)
                        AbsentLiveData.create<List<Repo>>();
                    else
                        repoDao.loadOrdered(searchData.repoIds);
                })
            }

            override fun shouldFetch(data: List<Repo>?): Boolean = data == null;

            override fun createCall(): LiveData<ApiResponse<RepoSearchResponse>> =
                    githubService.searchRepos(query)

            override fun saveCallResult(item: RepoSearchResponse)
            {
                val repoIds = item.getRepoIds();
                val repoSearchResult = RepoSearchResult(query, repoIds, item.total);
                repoDao.insertRepos(item.items);
                repoDao.insert(repoSearchResult);
            }
        }.asLiveData();
    }
}