package com.ikkoy.interntestapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikkoy.interntestapp.network.model.User
import com.ikkoy.interntestapp.network.response.UserResponse
import com.ikkoy.interntestapp.network.retrofit.ApiConfig

class UserViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    private var _isLoading = MutableLiveData<Boolean>()

    private val param = HashMap<String, String>()

    private fun getUser (param: Map<String, String>) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(param as HashMap<String, String>)
        client.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(call: retrofit2.Call<UserResponse>, response: retrofit2.Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.data
                } else {
                    android.util.Log.e(android.content.ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                android.util.Log.e(android.content.ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    init {
        getUser(param)
    }
}