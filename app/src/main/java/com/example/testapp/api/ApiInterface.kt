package com.example.testapp.api

import com.example.testapp.model.Place
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("5d8f20833200004d00adeb82")
    fun getData(): Call<Place>
}