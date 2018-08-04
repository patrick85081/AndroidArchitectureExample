package com.example.githubbrowser.services.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Patrick on 2018/8/4.
 */
data class RepoSearchResponse(
        @SerializedName("total_count")
        val total: Int,

        @SerializedName("items")
        val items: List<Repo>
)
{
    fun getRepoIds(): List<Int>
    {
        return items.map { repo -> repo.id }.toList();
    }
}