package com.example.githubbrowser.models

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.os.AsyncTask
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.WorkerThread
import com.example.githubbrowser.services.models.ApiResponse
import java.security.CodeSource


/**
 * Created by Patrick on 2018/8/4.
 */
abstract class NetworkBoundResource<ResultType, RequestType>
{
    val result = MediatorLiveData<Resource<ResultType>>();

    init
    {
        result.value = Resource.loading(null);
        val dbSource = loadFromDb();
        result.addSource(dbSource, Observer { data ->
            result.removeSource(dbSource);
            if(shouldFetch(data))
                fetchFromNetwork(dbSource);
            else
                result.addSource(dbSource, Observer { newData ->
                    result.value = Resource.success(newData);
                });
        });
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>)
    {
        val apiResponse = createCall();
        result.addSource(dbSource, Observer { newData ->
            result.value = Resource.loading(newData);
        });
        result.addSource(apiResponse, Observer { response ->
            result.removeSource(apiResponse);
            result.removeSource(dbSource);

            if(response?.isSuccessful == true)
                saveResultAndReInit(response);
            else
            {
                onFetchFailed();
                result.addSource(dbSource, Observer { newData ->
                    result.value = Resource.error(newData, response!!.errorMessage);
                })
            }
        })
    }

    @MainThread
    private fun saveResultAndReInit(response: ApiResponse<RequestType>)
    {
        object :AsyncTask<Unit, Unit, Unit>()
        {
            override fun doInBackground(vararg params: Unit?)
            {
                saveCallResult(response.body!!);
            }

            override fun onPostExecute(aVoid: Unit?)
            {
                result.addSource(loadFromDb(), Observer { newData ->
                    result.value = Resource.success(newData);
                })
            }
        }.execute();
    }

    // Called to get the cached data from the database
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected abstract fun shouldFetch(@Nullable data: ResultType?): Boolean

    // Called to create the API call.
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    protected fun onFetchFailed()
    {
    }

    // returns a LiveData that represents the resource
    fun asLiveData(): LiveData<Resource<ResultType>>
    {
        return result
    }
}