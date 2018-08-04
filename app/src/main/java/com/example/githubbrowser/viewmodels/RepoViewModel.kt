package com.example.githubbrowser.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.example.githubbrowser.models.DataModel
import com.example.githubbrowser.services.models.Repo
import com.example.githubbrowser.utils.SingleLiveEvent

/**
 * Created by Patrick on 2018/8/4.
 */
//class RepoViewModel(application: Application) : AndroidViewModel(application)
class RepoViewModel(private val dataModel: DataModel) : ViewModel()
{
//    val dataTitle = MutableLiveData<String>();
//    val toastText = SingleLiveEvent<String>();
    val isLoading = ObservableBoolean(false);

    val repos = MutableLiveData<List<Repo>>();

    fun searchRepo(query: String)
    {
        isLoading.set(true);

        dataModel.searchRepo(query, {data ->
            repos.value = data;
            isLoading.set(false);
        })
    }

//    fun refresh()
//    {
//        isLoading.set(true);
//
//        dataModel.retrieveData((object : DataModel.onDataReadyCallback
//        {
//            override fun onDataReady(data: String)
//            {
//                dataTitle.value = data;
//                toastText.value = "下載完成";
//                isLoading.set(false);
//            }
//
//        }))
//    }
}