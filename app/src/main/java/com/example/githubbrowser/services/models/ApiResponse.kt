package com.example.githubbrowser.services.models

import android.support.annotation.Nullable
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

//
///**
// * Created by Patrick on 2018/8/4.
// */
//class ApiResponse<T> private constructor (
//        val code: Int,
//
//        val body: T?,
//
//        val errorMessage: String)
//{
//    constructor(error: Throwable) : this(500, null, error.message!!)
//
//    constructor(response: Response<T>)
//    {
//
//    }
//
//
//    fun isSuccessfull() = code in 200..299;
//}

/**
 * Common class used by API responses.
 * @param <T>
</T> */
class ApiResponse<T>
{

    val code: Int

    @Nullable
    val body: T?

    @Nullable
    val errorMessage: String?

    val isSuccessful: Boolean
        get() = code >= 200 && code < 300

    constructor(error: Throwable)
    {
        code = 500
        body = null
        errorMessage = error.message
    }

    constructor(response: Response<T>)
    {
        code = response.code()
        if (response.isSuccessful)
        {
            body = response.body()
            errorMessage = null
        }
        else
        {
            var message: String? = null
            if (response.errorBody() != null)
            {
                try
                {
                    message = response.errorBody()!!.string()
                }
                catch (ignored: IOException)
                {
                    Timber.e(ignored, "error while parsing response")
                }

            }
            if (message == null || message.trim { it <= ' ' }.isEmpty())
            {
                message = response.message()
            }
            errorMessage = message
            body = null
        }
    }
}