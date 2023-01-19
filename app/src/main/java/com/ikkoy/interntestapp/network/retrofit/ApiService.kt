package com.ikkoy.interntestapp.network.retrofit

import com.ikkoy.interntestapp.network.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("/api/users")
    fun getUser(
        @QueryMap parameters: HashMap<String, String>
    ): Call<UserResponse>

}