package com.example.githubbrowser.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.text.TextUtils
import com.example.githubbrowser.models.Resource
import com.example.githubbrowser.repositories.RepoRepository
import com.example.githubbrowser.services.models.Repo
import com.example.githubbrowser.utils.AbsentLiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Patrick on 2018/8/4.
 */
//class RepoViewModel(application: Application) : AndroidViewModel(application)
@Singleton
class RepoViewModel
    @Inject
    constructor(private val repoRepository: RepoRepository) : ViewModel()
{
    val isLoading = ObservableBoolean(false);
    private val query = MutableLiveData<String>();

    val repos : LiveData<Resource<List<Repo>>> by lazy {
        Transformations.switchMap(query, { userInput ->
            if(TextUtils.isEmpty(userInput))
                AbsentLiveData.create()
            else
                repoRepository.search(userInput);
        });
    }

    fun searchRepo(query: String)
    {
        this.query.value = query;
    }
}