package com.example.githubbrowser

import com.example.githubbrowser.db.dao.RepoDao
import com.example.githubbrowser.repositories.RepoRepository
import com.example.githubbrowser.services.GithubService
import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.Test
import com.example.githubbrowser.services.models.Repo
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.example.githubbrowser.db.entitys.RepoSearchResult
import com.example.githubbrowser.models.Resource
import org.mockito.Matchers
import java.util.*
import com.example.githubbrowser.services.models.RepoSearchResponse
import com.example.githubbrowser.services.models.ApiResponse
import org.mockito.Mockito.*
import org.mockito.Mockito.`when`
import com.example.githubbrowser.utils.AbsentLiveData
import org.junit.Before


/**
 * Created by Patrick on 2018/8/4.
 */
class RepoRepositoryTest
{
    lateinit var repoDao: RepoDao ;
    lateinit var githubService: GithubService;
    lateinit var repository: RepoRepository;
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init()
    {
        repoDao = mock(RepoDao::class.java);
        githubService = mock(GithubService::class.java);
        repository = RepoRepository(repoDao, githubService);
    }

    @Test
    fun search()
    {
        repository.search("abc")

        // 驗證 RepoDao 的search 有被執行
        verify(repoDao).search("abc")
    }

    @Test
    fun search_fromDb_base()
    {
        val dbSearchResult = MutableLiveData<RepoSearchResult>()
        `when`(repoDao.search("foo")).thenReturn(dbSearchResult)

        val observer :Observer<Resource<List<Repo>>> = mock(Observer::class.java) as Observer<Resource<List<Repo>>>;
        repository.search("foo").observeForever(observer)

        verify(observer).onChanged(Resource.loading(null))
        verifyNoMoreInteractions(githubService)
    }

    @Test
    fun search_fromDb()
    {
        val dbSearchResult = MutableLiveData<RepoSearchResult>()
        `when`(repoDao.search("foo")).thenReturn(dbSearchResult)

        val observer = mock(Observer::class.java) as  Observer<Resource<List<Repo>>>;
        repository.search("foo").observeForever(observer)

        verify(observer).onChanged(Resource.loading(null))
        verifyNoMoreInteractions(githubService)

        val ids = Arrays.asList(1, 2)
        val dbResult = RepoSearchResult("foo", ids, 2)

        val repos = MutableLiveData<List<Repo>>()
        `when`(repoDao.loadOrdered(ids)).thenReturn(repos)

        dbSearchResult.postValue(dbResult)

        val repoList = ArrayList<Repo>()
        repos.postValue(repoList)
        verify(observer).onChanged(Resource.success(repoList))
        verifyNoMoreInteractions(githubService)
    }

    @Test
    fun search_fromServer()
    {
        val dbSearchResult = MutableLiveData<RepoSearchResult>()
        `when`(repoDao.search("foo")).thenReturn(dbSearchResult)

        val observer = mock(Observer::class.java) as  Observer<Resource<List<Repo>>>;
        repository.search("foo").observeForever(observer)

        verify(observer).onChanged(Resource.loading(null))
        verifyNoMoreInteractions(githubService)

        val callLiveData = MutableLiveData<ApiResponse<RepoSearchResponse>>()
        `when`(githubService.searchRepos("foo")).thenReturn(callLiveData)

        dbSearchResult.postValue(null)

        verify(repoDao, never()).loadOrdered(any())
        verify(githubService).searchRepos("foo")
    }

    @Test
    fun search_fromServer_error()
    {
        `when`(repoDao.search("foo")).thenReturn(AbsentLiveData.create())
        val apiResponse = MutableLiveData<ApiResponse<RepoSearchResponse>>()
        `when`(githubService.searchRepos("foo")).thenReturn(apiResponse)

        val observer = mock(Observer::class.java) as  Observer<Resource<List<Repo>>>;
        repository.search("foo").observeForever(observer)

        apiResponse.postValue(ApiResponse(Exception("idk")))
        verify(observer).onChanged(Resource.error(null, "idk"))
    }
}