package com.example.githubbrowser

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField

/**
 * Created by Patrick on 2018/8/4.
 */
//class MainViewModel(application: Application) : AndroidViewModel(application)
class MainViewModel(private val dataModel: DataModel) : ViewModel()
{
    val dataTitle = MutableLiveData<String>();
    val toastText = SingleLiveEvent<String>();
    val isLoading = ObservableBoolean(false);

    fun refresh()
    {
        isLoading.set(true);

        dataModel.retrieveData((object : DataModel.onDataReadyCallback
        {
            override fun onDataReady(data: String)
            {
                dataTitle.value = data;
                toastText.value = "下載完成";
                isLoading.set(false);
            }

        }))
    }
}