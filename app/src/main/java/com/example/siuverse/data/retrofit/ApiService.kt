package com.example.siuverse.data.retrofit

import com.example.siuverse.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST

interface ApiService {
 @FormUrlEncoded
 @POST("api/register")
 suspend fun register(
  @Field("name") name: String,
  @Field("email") email: String,
  @Field("password") password: String
 ): RegisterResponse

}