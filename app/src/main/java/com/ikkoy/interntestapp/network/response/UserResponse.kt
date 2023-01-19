package com.ikkoy.interntestapp.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.ikkoy.interntestapp.network.model.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse (
    @field:SerializedName("per_page")
        val perPage: Int? = null,

    @field:SerializedName("total")
        val total: Int? = null,

    @field:SerializedName("data")
        val data: List<User>,

    @field:SerializedName("page")
        val page: Int? = null,

    @field:SerializedName("total_pages")
        val totalPages: Int? = null
) : Parcelable