package com.example.githubbrowser.db.entitys

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.support.annotation.NonNull
import com.example.githubbrowser.db.GithubTypeConverter

/**
 * Created by Patrick on 2018/8/4.
 */
@Entity
@TypeConverters(GithubTypeConverter::class)
data class RepoSearchResult(
        @NonNull
        @PrimaryKey
        var query: String,

        var repoIds: List<Int>,

        val totalCount: Int)
