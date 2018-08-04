package com.example.githubbrowser.viewmodels.factorys

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.githubbrowser.models.DataModel
import com.example.githubbrowser.viewmodels.RepoViewModel

/**
 * Created by Patrick on 2018/8/4.
 */
class GithubViewModelFactory(private val dataModel: DataModel) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(RepoViewModel::class.java))
            return RepoViewModel(dataModel) as T;

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}