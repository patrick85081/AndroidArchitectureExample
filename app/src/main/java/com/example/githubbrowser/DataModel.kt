package com.example.githubbrowser

import android.os.Handler


/**
 * Created by Patrick on 2018/8/4.
 */
class DataModel
{
    fun retrieveData(callback: onDataReadyCallback) =
            Handler().postDelayed({
                callback.onDataReady("New Data");
            }, 1500)

    interface onDataReadyCallback
    {
        fun onDataReady(data: String);
    }
}