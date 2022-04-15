package com.example.myapplication

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService{

    @FormUrlEncoded
    @POST("/login/")
    fun requestLogin(
        @Field("email") email:String,
        @Field("m_password") m_password:String
    ) : Call<Login>

}