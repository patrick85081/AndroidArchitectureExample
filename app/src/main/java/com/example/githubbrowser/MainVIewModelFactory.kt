package com.example.githubbrowser

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by Patrick on 2018/8/4.
 */
class MainVIewModelFactory(private val dataModel: DataModel) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(dataModel) as T;

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}