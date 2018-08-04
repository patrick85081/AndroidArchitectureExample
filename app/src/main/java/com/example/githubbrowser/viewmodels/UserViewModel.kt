package com.example.githubbrowser.viewmodels

import android.arch.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Patrick on 2018/8/4.
 */
@Singleton
class UserViewModel
    @Inject
    constructor() : ViewModel()
{
    fun test(callback: () -> Unit = {})
    {
        callback.invoke();
    }
}