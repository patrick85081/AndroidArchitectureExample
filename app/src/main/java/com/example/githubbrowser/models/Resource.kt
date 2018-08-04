package com.example.githubbrowser.models

import java.util.*
import android.R.attr.data
import com.google.gson.Gson
import com.google.gson.GsonBuilder


/**
 * Created by Patrick on 2018/8/4.
 */
class Resource<T>(val status: Status, val data: T?, val message: String?)
{
    companion object
    {
        @JvmStatic
        fun <T> success(data: T?): Resource<T> =
                Resource(Status.SUCCESS, data, null);

        @JvmStatic
        fun <T> error(data: T?, msg: String?) : Resource<T> =
                Resource(Status.ERROR, data, msg);

        @JvmStatic
        fun <T> loading(data: T?) : Resource<T> =
                Resource(Status.LOADING, data, null);

    }

    override fun equals(other: Any?): Boolean =
            if (this == other)
                true
            else if (other == null || javaClass != other.javaClass)
                false
            else if (other is Resource<*>)
                this.status == other.status &&
                        this.message.equals(other.message) &&
                        this.data?.equals(other.data) == true
            else false;

    override fun hashCode(): Int
    {
        var result = status.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
        GsonBuilder().create().toJson(this);
}

