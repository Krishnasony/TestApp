package com.example.testapp.utils

import com.example.testapp.api.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

 var mRetrofit: Retrofit = Retrofit.Builder()
     .baseUrl("http://www.mocky.io/v2/")
     .addConverterFactory(GsonConverterFactory.create())
     .build()
 val apiService: ApiInterface =  mRetrofit.create(ApiInterface::class.java)



}