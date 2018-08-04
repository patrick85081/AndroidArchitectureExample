package com.example.githubbrowser.services.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import com.google.gson.annotations.SerializedName

@Entity(
        indices = [Index("id"), Index("owner_login")],
        primaryKeys = ["name", "owner_login"])
data class Repo(
        val id:Int,

        @SerializedName("name")
        val name: String,

        @SerializedName("full_name")
        val fullName: String,

        @SerializedName("description")
        val description: String?,

        @SerializedName("stargazers_count")
        val stars: Int,

        @Embedded(prefix =  "owner_")
        @SerializedName("owner")
        val owner: Owner
        )

