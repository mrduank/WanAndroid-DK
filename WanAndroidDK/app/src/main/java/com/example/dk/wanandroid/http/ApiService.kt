package com.example.dk.wanandroid.http

import com.example.dk.wanandroid.entity.IntegralEntity
import com.example.dk.wanandroid.entity.UserEntity
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<BaseResponse<UserEntity>>


    @GET("/lg/coin/userinfo/json")
    fun getIntegral(): Observable<BaseResponse<IntegralEntity>>
}