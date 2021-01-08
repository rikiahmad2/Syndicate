package com.riki.net89

import retrofit2.Call
import retrofit2.http.GET

interface Api{

    @GET("contents/")
    fun getContents(): Call<ContentResponse>
}