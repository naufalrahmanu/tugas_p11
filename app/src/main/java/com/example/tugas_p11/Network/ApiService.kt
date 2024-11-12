package com.example.tugas_p11.Network

import com.example.tugas_p11.Model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsers(@Query("page") page: Int): Call<UserResponse>
}