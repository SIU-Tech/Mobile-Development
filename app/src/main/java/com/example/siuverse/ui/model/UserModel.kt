package com.example.siuverse.ui.model

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)