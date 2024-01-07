package com.example.siuverse.data.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: UserData
)

data class UserData(

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("token")
	val token: String
)
