package com.example.githubbrowser

import android.databinding.ObservableBoolean
import android.databinding.ObservableField

/**
 * Created by Patrick on 2018/8/4.
 */
class MainViewModel
{
    val dataTitle = ObservableField<String>();
    val isLoading = ObservableBoolean(false);

    private val dataModel: DataModel = DataModel();

    fun refresh()
    {
        isLoading.set(true);

        dataModel.retrieveData((object : DataModel.onDataReadyCallback
        {
            override fun onDataReady(data: String)
            {
                dataTitle.set(data);
                isLoading.set(false);
            }

        }))
    }
}