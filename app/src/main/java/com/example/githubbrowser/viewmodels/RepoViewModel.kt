package com.example.githubbrowser.viewmodels

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.text.TextUtils
import com.example.githubbrowser.models.DataModel
import com.example.githubbrowser.services.models.ApiResponse
import com.example.githubbrowser.services.models.Repo
import com.example.githubbrowser.services.models.RepoSearchResponse
import com.example.githubbrowser.utils.AbsentLiveData
import com.example.githubbrowser.utils.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Patrick on 2018/8/4.
 */
//class RepoViewModel(application: Application) : AndroidViewModel(application)
@Singleton
class RepoViewModel
    @Inject
    constructor(private val dataModel: DataModel) : ViewModel()
{
    val isLoading = ObservableBoolean(false);
    private val query = MutableLiveData<String>();

    val repos : LiveData<ApiResponse<RepoSearchResponse>> by lazy {
        Transformations.switchMap(query, { userInput ->
            if(TextUtils.isEmpty(userInput))
                AbsentLiveData.create()
            else
                dataModel.searchRepo(userInput);
        });
    }

    fun searchRepo(query: String)
    {
        this.query.value = query;
    }
}