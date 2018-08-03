package com.example.githubbrowser

/**
 * Created by Patrick on 2018/8/4.
 */
class MainViewModel
{
    private val dataModel: DataModel = DataModel();

    fun refresh() =
            dataModel.retrieveData((object : DataModel.onDataReadyCallback
            {
                override fun onDataReady(data: String)
                {

                }

            }))
}