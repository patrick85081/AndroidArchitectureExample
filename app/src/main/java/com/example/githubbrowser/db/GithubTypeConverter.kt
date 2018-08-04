package com.example.githubbrowser.db

import android.annotation.SuppressLint
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.util.StringUtil
import java.util.*

/**
 * Created by Patrick on 2018/8/4.
 */
class GithubTypeConverter
{
    companion object
    {
        @SuppressLint("RestrictedApi")
        @JvmStatic
        @TypeConverter
        fun stringToIntList(data: String?) : List<Int>
        {
            if(data == null)
                return Collections.emptyList();
            return StringUtil.splitToIntList(data)!!;
        }

        @SuppressLint("RestrictedApi")
        @JvmStatic
        @TypeConverter
        fun intListToString(ints: List<Int>) : String
        {
            return StringUtil.joinIntoString(ints)!!;
        }
    }
}